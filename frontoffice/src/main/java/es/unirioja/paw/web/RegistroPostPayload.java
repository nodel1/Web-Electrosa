package es.unirioja.paw.web;

/**
 *
 * @author noelr
 */
public class RegistroPostPayload {
    private String nombreUsuario; 
    private String nombre;
    private String apellidos;
    private String email;
    private String contrasena;    
    private String repetirContrasena; 
    private boolean aceptaPrivacidad; 

    // Getters y setters
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getRepetirContrasena() { return repetirContrasena; }
    public void setRepetirContrasena(String repetirContrasena) { this.repetirContrasena = repetirContrasena; }
    public boolean isAceptaPrivacidad() { return aceptaPrivacidad; }
    public void setAceptaPrivacidad(boolean aceptaPrivacidad) { this.aceptaPrivacidad = aceptaPrivacidad; }
}