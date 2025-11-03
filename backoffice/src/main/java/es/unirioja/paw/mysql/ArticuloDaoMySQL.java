/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.mysql;

import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import es.unirioja.paw.dao.ArticuloDao;
import es.unirioja.paw.model.TipoArticulo;

/**
 *
 * @author nodel
 */
public class ArticuloDaoMySQL implements ArticuloDao {

    @Override
    public List<Articulo> findAll() throws ExcepcionDeAplicacion {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT codigo, nombre, pvp, tipo, fabricante, foto, descripcion FROM articulo";

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
            throw new ExcepcionDeAplicacion("Error al recuperar los artículos", e);
        }

        return articulos;
    }

    @Override
    public Articulo findByCodigo(String codigo) throws ExcepcionDeAplicacion {
        Articulo articulo = null;
        String sql = "SELECT codigo, nombre, pvp, tipo, fabricante, foto, descripcion FROM articulo WHERE codigo = ?";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            try (ResultSet rs = pstmt.executeQuery()) {
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
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al recuperar el artículo con código: " + codigo, e);
        }

        return articulo;
    }

    @Override
    public List<Articulo> findByPage(int pageNumber, int pageSize) throws ExcepcionDeAplicacion {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT codigo, nombre, pvp, tipo, fabricante, foto, descripcion FROM articulo LIMIT ? OFFSET ?";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int offset = (pageNumber - 1) * pageSize;
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
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
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al recuperar los artículos paginados", e);
        }

        return articulos;
    }

    @Override
    public int countAll() throws ExcepcionDeAplicacion {
        String sql = "SELECT COUNT(*) AS total FROM articulo";

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al contar los artículos", e);
        }

        return 0;
    }

@Override
public List<TipoArticulo> findTiposArticulo() throws ExcepcionDeAplicacion {
    List<TipoArticulo> tipos = new ArrayList<>();
    String sql = "SELECT DISTINCT tipo FROM articulo";

    try (Connection conn = ConnectionManagerDS.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            TipoArticulo tipo = new TipoArticulo(rs.getString("tipo")); // Crear un objeto TipoArticulo
            tipos.add(tipo); // Añadir el objeto a la lista
        }

    } catch (SQLException e) {
        throw new ExcepcionDeAplicacion("Error al recuperar los tipos de artículos", e);
    }

    return tipos;
}


    @Override
    public void saveEntity(Articulo articulo) throws ExcepcionDeAplicacion {
        String sql;
        if (articulo.getCodigo() == null || articulo.getCodigo().isEmpty()) {
            // Insertar un nuevo artículo
            sql = "INSERT INTO articulo (codigo, nombre, pvp, tipo, fabricante, foto, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        } else {
            // Actualizar un artículo existente
            sql = "UPDATE articulo SET nombre = ?, pvp = ?, tipo = ?, fabricante = ?, foto = ?, descripcion = ? WHERE codigo = ?";
        }

        try (Connection conn = ConnectionManagerDS.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (articulo.getCodigo() == null || articulo.getCodigo().isEmpty()) {
                // Insertar un nuevo artículo
                pstmt.setString(1, articulo.getCodigo());
                pstmt.setString(2, articulo.getNombre());
                pstmt.setDouble(3, articulo.getPvp());
                pstmt.setString(4, articulo.getTipo());
                pstmt.setString(5, articulo.getFabricante());
                pstmt.setString(6, articulo.getFoto());
                pstmt.setString(7, articulo.getDescripcion());
            } else {
                // Actualizar un artículo existente
                pstmt.setString(1, articulo.getNombre());
                pstmt.setDouble(2, articulo.getPvp());
                pstmt.setString(3, articulo.getTipo());
                pstmt.setString(4, articulo.getFabricante());
                pstmt.setString(5, articulo.getFoto());
                pstmt.setString(6, articulo.getDescripcion());
                pstmt.setString(7, articulo.getCodigo());
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion("Error al guardar el artículo", e);
        }
    }
}
