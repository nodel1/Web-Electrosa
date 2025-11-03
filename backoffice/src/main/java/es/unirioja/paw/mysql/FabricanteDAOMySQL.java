/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.dao.FabricanteDAO;
import es.unirioja.paw.model.Fabricante;
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
public class FabricanteDAOMySQL implements FabricanteDAO {

    @Override
    public List<Fabricante> findAll() throws ExcepcionDeAplicacion {
        List<Fabricante> fabricantes = new ArrayList<>();
        String sql = "SELECT DISTINCT (FABRICANTE) FROM articulo";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Fabricante fabricante = new Fabricante();
                fabricante.setNombre(rs.getString("fabricante"));
                fabricantes.add(fabricante);
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al recuperar los fabricantes", e);
        }

        return fabricantes;
    }
}
