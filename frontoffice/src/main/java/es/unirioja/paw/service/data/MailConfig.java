package es.unirioja.paw.service.data;

import java.util.Properties;

/**
 *
 * @author noelr
 */
public class MailConfig {
    private final Properties properties;
    private final String username;
    private final String password;

    public MailConfig(Properties properties) {
        this.properties = properties;
        this.username = properties.getProperty("mail.smtp.username");
        this.password = properties.getProperty("mail.smtp.password");
        if ( this.password == null) {
            throw new IllegalArgumentException("contraseña no configurados en mail.properties");
        }
                if (this.username == null ) {
            throw new IllegalArgumentException("Usuario no configurados en mail.properties");
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}