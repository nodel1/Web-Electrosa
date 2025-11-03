/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.AlmacenDAO;
import es.unirioja.paw.model.Almacen;
import es.unirioja.paw.model.ExcepcionDeAplicacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nodel
 */
public class AlmacenDaoMySQL implements AlmacenDAO {

    @Override
    public List<Almacen> findAll() throws ExcepcionDeAplicacion {
        List<Almacen> almacenes = new ArrayList<>();
        String sql = "SELECT * FROM almacen";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Almacen almacen = new Almacen();
                almacen.setCodigo(rs.getString("codigo"));
                almacen.setCalle(rs.getString("calle"));
                almacen.setCiudad(rs.getString("ciudad"));
                almacen.setCp(rs.getString("cp"));
                almacen.setProvincia(rs.getString("provincia"));
                almacenes.add(almacen);
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al obtener la lista de almacenes", e);
        }

        return almacenes;
    }

    @Override
    public Almacen findByCodigo(String codigo) throws ExcepcionDeAplicacion {
        Almacen almacen = null;
        String sql = "SELECT * FROM almacen WHERE codigo = ?"; 

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                almacen = new Almacen();
                almacen.setCodigo(rs.getString("codigo"));
                almacen.setCalle(rs.getString("calle"));
                almacen.setCiudad(rs.getString("ciudad"));
                almacen.setCp(rs.getString("cp"));
                almacen.setProvincia(rs.getString("provincia"));
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al obtener el almacén con código: " + codigo, e);
        }

        return almacen;
    }
}
