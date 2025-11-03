/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unrioja.paw.mysql;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;

import java.util.List;

/**
 *
 * @author nodel
 */
public class CatalogoDaoMySQLTest {
    public static void main(String[] args) {
        CatalogoDaoMySQL dao = new CatalogoDaoMySQL();

        try {

            System.out.println("todos los artículos:");
            List<Articulo> articulos = dao.findAll();

            if (articulos.isEmpty()) {
                System.out.println("No se encontraron artículos en la base de datos.");
            } else {
                System.out.println("Lista de artículos:");
                for (Articulo a : articulos) {
                    System.out.println(a.getCodigo() + " - " + a.getNombre() + " - " + a.getPvp() + "€");
                }
            }


            String codigoBuscado = "698-3 "; 
            System.out.println("\nBuscando el artículo con código: " + codigoBuscado);
            
            try {
                Articulo articulo = dao.findOneByCodigo(codigoBuscado);
                if (articulo != null) {
                    System.out.println("Artículo encontrado: " + articulo.getNombre() + " - " + articulo.getPvp() + "€");
                } else {
                    System.out.println("No se encontró el artículo con código: " + codigoBuscado);
                }
            } catch (ExcepcionDeAplicacion e) {
                System.out.println("Error al buscar el artículo: " + e.getMessage());
            }

        } catch (ExcepcionDeAplicacion e) {
            e.printStackTrace();
        }
    }
}
