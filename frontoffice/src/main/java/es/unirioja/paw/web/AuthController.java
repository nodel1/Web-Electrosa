/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.service.AuthService;
import es.unirioja.paw.service.PedidoService;
import es.unirioja.paw.usecase.RegistroClienteRequest;
import es.unirioja.paw.usecase.RegistroClienteResponse;
import es.unirioja.paw.usecase.RegistroClienteUseCase;
import es.unirioja.paw.usecase.RegistroClienteValidationResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author nodel
 */
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PedidoService pedidoService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RegistroClienteUseCase registroClienteUseCase; // Añadido

    private final String USER_REGISTERED_KEY = "userRegistered";

    public AuthController(AuthService authService) {
        this.authService = authService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/auth/login")
    public String getLogin(@RequestParam(value = "redirect", required = false) String redirect, Model model) {
        if (redirect != null) {
            model.addAttribute("mensaje", "Por favor, inicia sesión para continuar.");
        }
        model.addAttribute("title", "Iniciar Sesión - Electrosa");
        return "login";
    }

    @PostMapping("/auth/login")
    public String login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            HttpServletRequest request,
            Model model) {

        ClienteEntity cliente = authService.authenticate(username, password);

        if (cliente != null) {
            HttpSession session = request.getSession();
            session.setAttribute("cliente", cliente);
            model.addAttribute("cliente", cliente);

            // Inicializar cesta en sesión
            CestaCompraEntity cestaEntity = pedidoService.findCestaCliente(cliente.getCodigo());
            if (cestaEntity == null) {
                cestaEntity = new CestaCompraEntity();
                cestaEntity.setCodigoCliente(cliente.getCodigo());
                cestaEntity.setFechaInicio(new Timestamp(System.currentTimeMillis()));
                cestaEntity.setLineas(new ArrayList<>());
                // guardar en BD si es necesario
                // cestaEntity = pedidoService.saveCesta(cestaEntity); // REVISAR SI HACER
            }
            session.setAttribute("cesta", cestaEntity);

            // Comprobar si hay una URL de retorno en la sesión
            String returnUrl = (String) session.getAttribute("returnUrl");
            if (returnUrl != null) {
                // Eliminar la URL de retorno de la sesión después de usarla
                session.removeAttribute("returnUrl");
                return "redirect:" + returnUrl;
            }

            // Si no hay URL de retorno, redirigir al área de cliente por defecto
            return "redirect:/cliente/cuenta";
        } else {
            model.addAttribute("error", "Credenciales incorrectas. Por favor, intenta de nuevo.");
            model.addAttribute("title", "Iniciar Sesión - Electrosa");
            return "login";
        }
    }

    @GetMapping("/auth/logout")
    public String getLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/register")
    public String registro(Model model) {
        return "/register";
    }

    @PostMapping("/auth/register")
    public String registroSubmit(
            @RequestParam("email") String email,
            @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("repetirContrasena") String repetirContrasena,
            @RequestParam("aceptaPrivacidad") boolean aceptaPrivacidad,
            HttpSession session,
            Model model) {

        // Validación manual
        RegistroClienteValidationResponse validationResponse = validateRegistroPayload(email, nombreUsuario, contrasena, repetirContrasena, aceptaPrivacidad);

        if (!validationResponse.isSuccess()) {
            model.addAttribute("messages", validationResponse.getMessageCollection());
            // Mantener los valores ingresados en el formulario
            model.addAttribute("email", email);
            model.addAttribute("nombreUsuario", nombreUsuario);
            return "register"; // Vuelve a la vista con mensajes de error
        }

        // Crear objeto RegistroPostPayload manualmente
        RegistroPostPayload payload = new RegistroPostPayload();
        payload.setEmail(email);
        payload.setNombreUsuario(nombreUsuario);
        payload.setContrasena(contrasena);
        payload.setRepetirContrasena(repetirContrasena);
        payload.setAceptaPrivacidad(aceptaPrivacidad);

        // Llamar al caso de uso para registrar
        RegistroClienteResponse registroClienteResponse = registroClienteUseCase.registrar(new RegistroClienteRequest(payload));

        if (registroClienteResponse.isSuccess()) {
            session.setAttribute(USER_REGISTERED_KEY, registroClienteResponse.getCliente());
            return "redirect:/auth/welcome";
        }

        // Si el UseCase falla, combinar los errores (aunque la validación manual ya pasó)
        model.addAttribute("messages", registroClienteResponse.getMessage() != null ? registroClienteResponse.getMessage() : new ArrayList<String>());
        // Mantener los valores ingresados en el formulario
        model.addAttribute("email", email);
        model.addAttribute("nombreUsuario", nombreUsuario);
        model.addAttribute("title", "Área de Cliente - Electrosa");
        return "register";
    }

    @GetMapping("/auth/welcome")
    public String welcome(Model model, HttpSession session) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute(USER_REGISTERED_KEY);
        if (cliente != null) {
            model.addAttribute("clienteRegistrado", cliente);
            session.removeAttribute(USER_REGISTERED_KEY);
        }
        return "welcome";
    }

    private RegistroClienteValidationResponse validateRegistroPayload(
            String email, String nombreUsuario, String contrasena, String repetirContrasena, boolean aceptaPrivacidad) {
        RegistroClienteValidationResponse response = new RegistroClienteValidationResponse();
        response.setSuccess(true);

        if (email == null || email.isBlank()) {
            response.getMessageCollection().add("Campo 'Email': debe proporcionar un valor");
            response.setSuccess(false);
        }
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            response.getMessageCollection().add("Campo 'Nombre de usuario': debe proporcionar un valor");
            response.setSuccess(false);
        }
        if (contrasena == null || contrasena.isBlank()) {
            response.getMessageCollection().add("Campo 'Contraseña': debe proporcionar un valor");
            response.setSuccess(false);
        }
        if (repetirContrasena == null || repetirContrasena.isBlank()) {
            response.getMessageCollection().add("Campo 'Repita contraseña': debe proporcionar un valor");
            response.setSuccess(false);
        }
        if (!contrasena.equals(repetirContrasena)) {
            response.getMessageCollection().add("Las contraseñas no coinciden");
            response.setSuccess(false);
        }
        if (!aceptaPrivacidad) {
            response.getMessageCollection().add("Debe aceptar la política de privacidad");
            response.setSuccess(false);
        }
        // Validación de longitud (ejemplo: máximo 50 caracteres para email y nombreUsuario)
        if (email != null && email.length() > 50) {
            response.getMessageCollection().add("Campo 'Email': longitud máxima es 50 caracteres");
            response.setSuccess(false);
        }
        if (nombreUsuario != null && nombreUsuario.length() > 50) {
            response.getMessageCollection().add("Campo 'Nombre de usuario': longitud máxima es 50 caracteres");
            response.setSuccess(false);
        }
        if (contrasena != null && contrasena.length() > 100) {
            response.getMessageCollection().add("Campo 'Contraseña': longitud máxima es 100 caracteres");
            response.setSuccess(false);
        }

        return response;
    }

    @GetMapping("/about/delivery")
    public String deliveryStats(Model model) {
        model.addAttribute("contentTitle", "Plazos de entrega");
        return "delivery";
    }

}
