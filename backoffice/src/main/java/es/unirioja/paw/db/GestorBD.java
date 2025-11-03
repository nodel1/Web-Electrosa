package es.unirioja.paw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nodel
 */
public class GestorBD {
    private static final String URL = "jdbc:mysql://localhost:3306/electrosa?serverTimezone=GMT%2B2";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    public List<String> getArticulosLike(String nombre) throws SQLException {
        List<String> articulos = new ArrayList<>();
        String query = "SELECT nombre FROM ARTICULO WHERE nombre LIKE ?";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado", e);
        }
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                articulos.add(rs.getString("nombre"));
            }
        }
        return articulos;
    }
}
