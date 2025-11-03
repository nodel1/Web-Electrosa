/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author nodel
 */

public class ConnectionManagerDS {
    private static DataSource ds;
    private static final String URL = "jdbc:mysql://localhost:3306/electrosa?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/electrosa");
        } catch (NamingException e) {
            System.err.println("No se encontró el DataSource. Se usará una conexión manual.");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (ds != null) {
            return ds.getConnection();
        } else {
            return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
}
