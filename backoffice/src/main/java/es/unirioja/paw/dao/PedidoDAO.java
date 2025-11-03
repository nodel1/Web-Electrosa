/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.Pedido;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.util.List;

/**
 *
 * @author nodel
 */
public interface PedidoDAO {

    /**
     * Recupera todos los pedidos de la base de datos.
     *
     * @return Lista de pedidos.
     * @throws ExcepcionDeAplicacion Si ocurre un error al acceder a la base de datos.
     */
    List<Pedido> findAll() throws ExcepcionDeAplicacion;
}
