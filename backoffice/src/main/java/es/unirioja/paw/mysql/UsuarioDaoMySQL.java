/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.UsuarioDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author nodel
 */


public class UsuarioDaoMySQL implements UsuarioDAO {

    @Override
    public boolean countByUsernameAndPassword(String username, String password) throws ExcepcionDeAplicacion {
        String sql = "SELECT username, password FROM usuario WHERE username = ?";
        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPasswordHash = rs.getString("password");

                    // Verificar la contraseña
                    if (verifyPassword(password, storedPasswordHash)) {
                        return true; // Existe un usuario con las credenciales dadas
                    }
                }
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al buscar el usuario por nombre de usuario", e);
        }
        return false; 
    }

    @Override
    public String obtenerRolUsuario(String username) throws ExcepcionDeAplicacion {
        String sql = "SELECT rol FROM rolesusuario WHERE username = ?";
        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol");
                }
            }
        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al obtener el rol del usuario", e);
        }
        return null;
    }

    private boolean verifyPassword(String inputPassword, String storedPasswordHash) {
        String inputPasswordHash = encodeValue(inputPassword, "SHA-1");
        return inputPasswordHash.equals(storedPasswordHash);
    }

    private String encodeValue(String value, String algorithm) {
        byte[] valueBytes = value.getBytes();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (Exception ex) {
            throw new RuntimeException("Error al obtener la instancia de MessageDigest", ex);
        }
        messageDigest.reset();
        messageDigest.update(valueBytes);
        byte[] hashBytes = messageDigest.digest();
        StringBuilder stringBuffer = new StringBuilder();
        for (byte b : hashBytes) {
            if ((b & 0xFF) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Integer.toHexString(b & 0xFF));
        }
        return stringBuffer.toString();
    }
}
