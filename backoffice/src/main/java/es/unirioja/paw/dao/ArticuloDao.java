/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.TipoArticulo;
import java.util.List;

/**
 *
 * @author nodel
 */
public interface ArticuloDao{

    /**
     * Recupera todos los artículos de la base de datos.
     *
     * @return Lista de artículos.
     */
    List<Articulo> findAll()throws ExcepcionDeAplicacion;

    /**
     * Recupera un artículo por su código.
     *
     * @param codigo Código del artículo.
     * @return El artículo encontrado, o null si no existe.
     */
    Articulo findByCodigo(String codigo)throws ExcepcionDeAplicacion;

    /**
     * Recupera una lista de artículos paginada.
     *
     * @param pageNumber Número de página (empezando desde 1).
     * @param pageSize   Tamaño de la página (número de artículos por página).
     * @return Lista de artículos para la página solicitada.
     */
    List<Articulo> findByPage(int pageNumber, int pageSize)throws ExcepcionDeAplicacion;

    /**
     * Cuenta el número total de artículos en la base de datos.
     *
     * @return Número total de artículos.
     */
    int countAll()throws ExcepcionDeAplicacion;
    
        /**
     * Recupera una lista de todos los tipos de artículos disponibles.
     *
     * @return Lista de tipos de artículos.
     */
    List<TipoArticulo> findTiposArticulo() throws ExcepcionDeAplicacion;
    
        /**
     * Guarda o actualiza un artículo en la base de datos.
     * Si el artículo tiene un código, se actualiza; de lo contrario, se inserta como nuevo.
     *
     * @param articulo El artículo a guardar o actualizar.
     * @throws ExcepcionDeAplicacion Si ocurre un error al guardar el artículo.
     */
    void saveEntity(Articulo articulo) throws ExcepcionDeAplicacion;
    
}
