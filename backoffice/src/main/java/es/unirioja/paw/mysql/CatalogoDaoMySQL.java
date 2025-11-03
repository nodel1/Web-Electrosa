/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Fabricante;
import es.unirioja.paw.model.TipoArticulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.System;

/**
 *
 * @author nodel
 */
public class CatalogoDaoMySQL implements CatalogoDAO {

    @Override
    public List<Articulo> findAll() throws ExcepcionDeAplicacion {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo";

        try (Connection conn = ConnectionManagerDS.getConnection();
                
                
                
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Articulo articulo = new Articulo();
                articulo.setCodigo(rs.getString("codigo"));
                articulo.setNombre(rs.getString("nombre"));
                articulo.setPvp(rs.getDouble("pvp"));
                articulo.setTipo(rs.getString("tipo"));
                articulo.setFabricante(rs.getString("fabricante"));
                articulo.setFoto(rs.getString("foto"));
                articulo.setDescripcion(rs.getString("descripcion"));
                articulos.add(articulo);
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al obtener la lista de artículos", e);
        }

        return articulos;
    }

    @Override
    public Articulo findOneByCodigo(String codigo) throws ExcepcionDeAplicacion {
        Articulo articulo = null;
    String sql = "SELECT * FROM articulo WHERE codigo = ?";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                articulo = new Articulo();
                articulo.setCodigo(rs.getString("codigo"));
                articulo.setNombre(rs.getString("nombre"));
                articulo.setPvp(rs.getDouble("pvp"));
                articulo.setTipo(rs.getString("tipo"));
                articulo.setFabricante(rs.getString("fabricante"));
                articulo.setFoto(rs.getString("foto"));
                articulo.setDescripcion(rs.getString("descripcion"));
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al obtener el artículo con código: " + codigo, e);
        }

        return articulo;
    }

@Override
public List<TipoArticulo> findTiposArticulo() throws ExcepcionDeAplicacion {
    List<TipoArticulo> tipos = new ArrayList<>();
    String sql = "SELECT DISTINCT tipo FROM articulo"; 

    try (Connection conn = ConnectionManagerDS.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            TipoArticulo tipo = new TipoArticulo(rs.getString("tipo"));
            tipos.add(tipo); // Añadir el objeto a la lista
        }

    } catch (SQLException e) {
        throw new ExcepcionDeAplicacion("Error al recuperar los tipos de artículos", e);
    }

    return tipos;
}
}
