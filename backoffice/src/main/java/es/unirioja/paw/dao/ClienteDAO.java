/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.Cliente;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import java.util.List;

public interface ClienteDAO {

    /**
     * Recupera un cliente por su código.
     *
     * @param codigo Código del cliente.
     * @return El cliente encontrado, o null si no existe.
     * @throws ExcepcionDeAplicacion Si ocurre un error al acceder a la base de datos.
     */
    Cliente findOneByCodigo(String codigo) throws ExcepcionDeAplicacion;

    /**
     * Recupera todos los clientes de la base de datos.
     *
     * @return Lista de clientes.
     * @throws ExcepcionDeAplicacion Si ocurre un error al acceder a la base de datos.
     */
    List<Cliente> findAll() throws ExcepcionDeAplicacion;
}
