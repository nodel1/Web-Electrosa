/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.dao;

import es.unirioja.paw.model.Almacen;
import es.unirioja.paw.model.ExcepcionDeAplicacion;

import java.util.List;

/**
 *
 * @author nodel
 */
public interface AlmacenDAO {

    // Método para obtener todos los almacenes
    public List<Almacen> findAll() throws ExcepcionDeAplicacion;
    
    // Puedes agregar otros métodos según sea necesario, por ejemplo, para obtener un almacen por su código
    public Almacen findByCodigo(String codigo) throws ExcepcionDeAplicacion;
}
