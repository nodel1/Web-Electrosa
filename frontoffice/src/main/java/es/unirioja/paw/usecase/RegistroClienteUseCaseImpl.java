package es.unirioja.paw.usecase;

import com.github.javafaker.Faker;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.jpa.UsuarioEntity;
import es.unirioja.paw.repository.ClienteRepository;
import es.unirioja.paw.repository.UsuarioRepository;
import es.unirioja.paw.service.Mailer;
import es.unirioja.paw.service.SimpleEncoder;
import es.unirioja.paw.service.data.MailConfig;
import es.unirioja.paw.service.data.MailRequest;
import es.unirioja.paw.service.data.MailResponse;
import es.unirioja.paw.usecase.RegistroClienteRequest;
import es.unirioja.paw.usecase.RegistroClienteResponse;
import es.unirioja.paw.usecase.RegistroClienteUseCase;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroClienteUseCaseImpl implements RegistroClienteUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegistroClienteUseCaseImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final SimpleEncoder simpleEncoder;
    private final Mailer mailer;
    private final Faker faker = new Faker(Locale.of("es"));

    private static final int MAX_EMAIL_LENGTH = 50;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MAX_PASSWORD_LENGTH = 100;

    @Autowired
    public RegistroClienteUseCaseImpl(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository,
            SimpleEncoder simpleEncoder, Mailer mailer) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.simpleEncoder = simpleEncoder;
        this.mailer = mailer;
    }

    private MailConfig buildMailConfig() throws IOException {
        final String filename = "mail.properties";
        InputStream propertiesAsInputStream = this.getClass().getResourceAsStream(String.format("/%s", filename));
        Properties prop = new Properties();
        if (propertiesAsInputStream != null) {
            prop.load(propertiesAsInputStream);
        } else {
            logger.error("No se encontró el archivo mail.properties");
        }
        return new MailConfig(prop);
    }

    @Override
    @Transactional
    public RegistroClienteResponse registrar(RegistroClienteRequest request) {
        List<String> errores = new ArrayList<>();

        // Validación de datos requeridos
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            errores.add("Campo 'Email': debe proporcionar un valor");
        }
        if (request.getNombreUsuario() == null || request.getNombreUsuario().trim().isEmpty()) {
            errores.add("Campo 'Nombre de usuario': debe proporcionar un valor");
        }
        if (request.getContrasena() == null || request.getContrasena().trim().isEmpty()) {
            errores.add("Campo 'Contraseña': debe proporcionar un valor");
        }
        String repetirContrasena = (String) request.getRepetirContrasena();
        if (repetirContrasena == null || repetirContrasena.trim().isEmpty()) {
            errores.add("Campo 'Repita contraseña': debe proporcionar un valor");
        }

        // Validación de contraseñas idénticas
        if (!request.getContrasena().equals(request.getRepetirContrasena())) {
            errores.add("Las contraseñas no coinciden");
        }

        // Validación de longitudes
        if (request.getEmail() != null && request.getEmail().length() > MAX_EMAIL_LENGTH) {
            errores.add("Campo 'Email': longitud máxima es " + MAX_EMAIL_LENGTH + " caracteres");
        }
        if (request.getNombreUsuario() != null && request.getNombreUsuario().length() > MAX_USERNAME_LENGTH) {
            errores.add("Campo 'Nombre de usuario': longitud máxima es " + MAX_USERNAME_LENGTH + " caracteres");
        }
        if (request.getContrasena() != null && request.getContrasena().length() > MAX_PASSWORD_LENGTH) {
            errores.add("Campo 'Contraseña': longitud máxima es " + MAX_PASSWORD_LENGTH + " caracteres");
        }

        // Validación de nombre de usuario único
        if (clienteRepository.findByUsername(request.getNombreUsuario()) != null) {
            errores.add("El nombre de usuario ya está en uso");
        }

        // Si hay errores, devolver respuesta con mensajes
        if (!errores.isEmpty()) {
            return new RegistroClienteResponse(false, null, errores);
        }

        try {
            // 1. Verificar si el username ya existe (ya validado arriba, pero mantenido por consistencia)
            if (clienteRepository.findByUsername(request.getNombreUsuario()) != null) {
                errores.add("El nombre de usuario ya está en uso");
                return new RegistroClienteResponse(false, null, errores);
            }

            // 2. Guardar cliente en la base de datos
            UsuarioEntity usuario = saveUsuario(request);
            ClienteEntity cliente = saveCliente(request);

            // 3. Enviar correo de bienvenida
           // sendMailBienvenida(cliente);     //QUITAR ESTRO DE COMENTARIO CUNADO ARREGLE EL MAIL PROPIERTIES

            return new RegistroClienteResponse(true, cliente, null);

        } catch (Exception e) {
            logger.error("Error al registrar el cliente", e);
            errores.add("Error al registrar el cliente: " + e.getMessage());
            return new RegistroClienteResponse(false, null, errores);
        }
    }

    private ClienteEntity saveCliente(RegistroClienteRequest request) {
        logger.info("Guardando cliente: {}", request.getNombreUsuario());
        ClienteEntity cliente = new ClienteEntity();
        // Datos reales del request
        cliente.setEmail(request.getEmail());
        cliente.setUsername(request.getNombreUsuario());

        cliente.setNombre(faker.name().fullName()); // Generar nombre aleatorio

        // Datos mockeados con Faker
        cliente.setCalle(faker.address().streetAddress());
        cliente.setCiudad(faker.address().cityName());
        cliente.setProvincia(faker.address().state());
        cliente.setCif("A" + faker.number().digits(8));
        cliente.setCp(faker.address().zipCode());
        cliente.setTfno(faker.phoneNumber().phoneNumber());

        cliente = clienteRepository.save(cliente);
        logger.info("Cliente guardado: {}", cliente.getUsername());
        return cliente;
    }

private void sendMailBienvenida(ClienteEntity cliente) {
        logger.info("Enviando mail para {}", cliente.getEmail());
        String email = cliente.getEmail();
        if (email == null || email.trim().isEmpty()) {
            logger.warn("No se puede enviar correo: email no proporcionado para {}", cliente.getUsername());
            return;
        }

        MailRequest mailRequest = new MailRequest(
            email,
            "Gracias por registrarte en Electrosa, " + (cliente.getNombre() != null ? cliente.getNombre() : "Cliente"),
            "En unos momentos recibirás un correo electrónico de confirmación de tu registro como nuevo cliente."
        );

        try {
            MailConfig mailConfig = buildMailConfig();
            MailResponse mailResponse = mailer.send( mailRequest);        // CAMBIAR ESTO
            if (!mailResponse.isSuccess()) {
                logger.warn("Fallo al enviar correo de bienvenida a {}", email);
            } else {
                logger.info("Correo de bienvenida enviado exitosamente a {}", email);
            }
        } catch (IOException e) {
            logger.error("Error al enviar correo de bienvenida a {}", email, e);
        }
    }

    private UsuarioEntity saveUsuario(RegistroClienteRequest request) {
        logger.info("Registrando usuario: {}", request.getNombreUsuario());
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(request.getNombreUsuario());
        usuario.setPassword(simpleEncoder.sha1(request.getContrasena()));
        logger.info("Guardando usuario {}", usuario.getUsername());
        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

}
