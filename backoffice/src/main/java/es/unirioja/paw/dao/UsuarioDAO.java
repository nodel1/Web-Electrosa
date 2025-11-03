/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.Usuario;
import es.unirioja.paw.model.ExcepcionDeAplicacion;

/**
 *
 * @author nodel
 */
public interface UsuarioDAO {

    boolean countByUsernameAndPassword(String username, String password) throws ExcepcionDeAplicacion;

    /**
     * Obtiene el rol de un usuario específico.
     * @param username El nombre del usuario.
     * @return El rol del usuario o null si no se encuentra.
     * @throws ExcepcionDeAplicacion Si ocurre un error al acceder a la base de datos.
     */
    String obtenerRolUsuario(String username) throws ExcepcionDeAplicacion;
}