<%-- 
    Document   : prueba
    Created on : 5 mar 2025, 11:39:38
    Author     : nodel
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="es.unirioja.paw.model.Articulo" %>
<%@ page import="es.unirioja.paw.dao.CatalogoDAO" %>
<%@ page import="es.unirioja.paw.mysql.CatalogoDaoMySQL" %>
<%@ page import="es.unirioja.paw.model.ExcepcionDeAplicacion" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prueba de Métodos</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Prueba de Métodos</h1>

    <h2>Prueba de findAll()</h2>
    <%
        CatalogoDAO dao = new CatalogoDaoMySQL();
        List<Articulo> articulos = null;
        try {
            articulos = dao.findAll();
        } catch (ExcepcionDeAplicacion e) {
            out.println("<p style='color: red;'>Error al obtener la lista de artículos: " + e.getMessage() + "</p>");
        }
    %>

    <% if (articulos != null && !articulos.isEmpty()) { %>
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>PVP</th>
                    <th>Tipo</th>
                    <th>Fabricante</th>
                    <th>Foto</th>
                    <th>Descripción</th>
                </tr>
            </thead>
            <tbody>
                <% for (Articulo a : articulos) { %>
                    <tr>
                        <td><%= a.getCodigo() %></td>
                        <td><%= a.getNombre() %></td>
                        <td><%= a.getPvp() %>€</td>
                        <td><%= a.getTipo() %></td>
                        <td><%= a.getFabricante() %></td>
                        <td><img src="<%= a.getFoto() %>" alt="Imagen de <%= a.getNombre() %>" width="50"></td>
                        <td><%= a.getDescripcion() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <p>No se encontraron artículos.</p>
    <% } %>

    <h2>Prueba de findOneByCodigo()</h2>
    <%
        String codigoBuscado = "ART-001"; // Cambia este valor por el código que quieras probar
        Articulo articulo = null;
        try {
            articulo = dao.findOneByCodigo(codigoBuscado);
        } catch (ExcepcionDeAplicacion e) {
            out.println("<p style='color: red;'>Error al buscar el artículo: " + e.getMessage() + "</p>");
        }
    %>

    <% if (articulo != null) { %>
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>PVP</th>
                    <th>Tipo</th>
                    <th>Fabricante</th>
                    <th>Foto</th>
                    <th>Descripción</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><%= articulo.getCodigo() %></td>
                    <td><%= articulo.getNombre() %></td>
                    <td><%= articulo.getPvp() %>€</td>
                    <td><%= articulo.getTipo() %></td>
                    <td><%= articulo.getFabricante() %></td>
                    <td><img src="<%= articulo.getFoto() %>" alt="Imagen de <%= articulo.getNombre() %>" width="50"></td>
                    <td><%= articulo.getDescripcion() %></td>
                </tr>
            </tbody>
        </table>
    <% } else { %>
        <p>No se encontró el artículo con código: <%= codigoBuscado %></p>
    <% } %>
</body>
</html>
