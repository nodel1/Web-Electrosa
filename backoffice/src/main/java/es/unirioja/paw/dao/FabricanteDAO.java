/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;
import es.unirioja.paw.model.Fabricante;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.util.List;

/**
 *
 * @author nodel
 */
public interface FabricanteDAO {

    /**
     * Recupera todos los fabricantes de la base de datos.
     *
     * @return Lista de fabricantes.
     */
    List<Fabricante> findAll() throws ExcepcionDeAplicacion;
}
