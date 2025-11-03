package es.unirioja.paw.usecase;

import es.unirioja.paw.web.RegistroPostPayload;

/**
 *
 * @author noelr
 */
public class RegistroClienteRequest {

    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String contrasena;
    private String repetirContrasena;

    public RegistroClienteRequest(RegistroPostPayload payload) {
        if (payload == null) {
            throw new IllegalArgumentException("El payload no puede ser nulo");
        }
        this.nombreUsuario = payload.getNombreUsuario();
        this.nombre = payload.getNombre();
        this.apellidos = payload.getApellidos();
        this.email = payload.getEmail();
        this.contrasena = payload.getContrasena();
        this.repetirContrasena = payload.getRepetirContrasena();
    }

    // Getters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRepetirContrasena() {
        return repetirContrasena;
    }
}
