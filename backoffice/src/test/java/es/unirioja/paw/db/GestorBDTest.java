/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package es.unirioja.paw.db;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author nodel
 */
public class GestorBDTest {
    
 @Test
 public void testGetArticulosLike() throws Exception {
    System.out.println("getArticulosLike");
    String nombre = "Sci";
    GestorBD instance = new GestorBD();
    List<String> result = instance.getArticulosLike(nombre);
    assertNotNull(result);
    assertTrue(result.size() == 4);
 }

    
}
