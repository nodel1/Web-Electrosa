/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.ClienteDAO;
import es.unirioja.paw.model.Cliente;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nodel
 */
public class ClienteDaoMySQL implements ClienteDAO {

    @Override
    public Cliente findOneByCodigo(String codigo) throws ExcepcionDeAplicacion {
        Cliente cliente = null;
        String sql = "SELECT CODIGO, CIF, NOMBRE, CALLE, CP, CIUDAD, email, provincia, tfno, username FROM cliente WHERE CODIGO = ?";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                        rs.getString("CODIGO"),
                        rs.getString("username"),
                        rs.getString("NOMBRE"),
                        rs.getString("email"),
                        rs.getString("CP"),
                        rs.getString("CIUDAD"),
                        rs.getString("provincia")
                    );
                }
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al recuperar el cliente con código: " + codigo, e);
        }

        return cliente;
    }

    @Override
    public List<Cliente> findAll() throws ExcepcionDeAplicacion {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT CODIGO, CIF, NOMBRE, CALLE, CP, CIUDAD, email, provincia, tfno, username FROM cliente";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("CODIGO"),
                    rs.getString("username"),
                    rs.getString("NOMBRE"),
                    rs.getString("email"),
                    rs.getString("CP"),
                    rs.getString("CIUDAD"),
                    rs.getString("provincia")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al recuperar la lista de clientes", e);
        }

        return clientes;
    }
}