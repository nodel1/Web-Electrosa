/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.model.Pedido;
import es.unirioja.paw.dao.PedidoDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nodel
 */
public class PedidoDaoMySQL implements PedidoDAO {

    @Override
    public List<Pedido> findAll() throws ExcepcionDeAplicacion {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT codigo, codigoCliente, cp, calle, ciudad, provincia FROM pedido";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setCodigo(rs.getString("codigo"));
                pedido.setCodigoCliente(rs.getString("codigoCliente"));
                pedido.setCp(rs.getString("cp"));
                pedido.setCalle(rs.getString("calle"));
                pedido.setCiudad(rs.getString("ciudad"));
                pedido.setProvincia(rs.getString("provincia"));
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al obtener la lista de pedidos", e);
        }

        return pedidos;
    }
}
