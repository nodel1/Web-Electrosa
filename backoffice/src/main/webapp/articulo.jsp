<%-- 
    Document   : articulo
    Created on : 2 mar 2025, 13:05:28
    Author     : nodel
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="es.unirioja.paw.model.Articulo" %>
<%@ page import="es.unirioja.paw.dao.CatalogoDAO" %>
<%@ page import="es.unirioja.paw.mysql.CatalogoDaoMySQL" %>
<%@ page import="es.unirioja.paw.model.ExcepcionDeAplicacion" %>

<%
    String codigo = request.getParameter("codigo"); 
    CatalogoDAO dao = new CatalogoDaoMySQL();

    Articulo articulo = null;
    try {
        articulo = dao.findOneByCodigo(codigo); 
        if (articulo == null) {
            response.sendRedirect("catalogo.jsp"); 
        }
    } catch (ExcepcionDeAplicacion e) {
        out.print("Error al obtener el artículo: " + e.getMessage()); 
    }
%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ficha del producto - <%= articulo != null ? articulo.getNombre() : "No encontrado" %></title>

    <link rel="shortcut icon" href="assets-copia/images/logo/favicon.ico" type="image/x-icon">
    <link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />
    <link rel="stylesheet" href="assets-copia/css/reset.css">
    <link rel="stylesheet" href="assets-copia/css/megamenu.css">
    <link rel="stylesheet" href="assets-copia/css/style.css">
    <script src="assets-copia/js/modernizr.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">

</head>

<body>
    <div class="topbar">
        <span class="topbar_userinfo">Francisco </span>
        <span class="topbar_sep">/</span> 
        <a href="#" class="topbar_link"><i class="lni lni-power-button"></i> Cerrar sesión</a>
    </div>

    <header class="cd-main-header header">
        <a class="cd-logo" href="#0">
            <h1 class="store-branding-logo">
                <img src="assets-copia/images/logo-electrosa-retro.png" />
                <span>Backoffice</span>
            </h1>
        </a>

        <ul class="cd-header-buttons">
            <li><a class="cd-search-trigger" href="#cd-search">Buscar<span></span></a></li>
            <li><a class="cd-nav-trigger" href="#cd-primary-nav">Menú<span></span></a></li>
        </ul>
    </header>

    <div class="cd-overlay"></div>

    <nav class="cd-nav">
        <ul id="cd-primary-nav" class="cd-primary-nav is-fixed">
            <!-- Menú de navegación (igual que antes) -->
        </ul>
    </nav>

    <!-- Buscar -->
    <div id="cd-search" class="cd-search">
        <form action="articles.jsp">
            <input type="search" name="q" placeholder="Nombre artículo (por ejemplo: sci)">
        </form>
    </div>

    <main class="main cd-main-content">
        <div class="producto-container">
            <!-- Imagen del producto -->
            <div class="producto-imagen">
                <% if (articulo != null) { %>
                    <img src="assets-copia/images/store/<%= articulo.getFoto() %>" alt="Imagen de <%= articulo.getNombre() %>">
                <% } else { %>
                    <p>No hay imagen disponible.</p>
                <% } %>
            </div>

            <!-- Información del producto -->
            <div class="producto-info">
                <% if (articulo != null) { %>
                    <h1><%= articulo.getNombre() %></h1>
                    <p><strong>Fabricante:</strong> <%= articulo.getFabricante() %></p>
                    <p class="precio"><%= articulo.getPvp() %>€</p>
                    <p><strong>Código:</strong> <%= articulo.getCodigo() %></p>
                    <p><strong>Tipo:</strong> <%= articulo.getTipo() %></p>
                    <p><strong>Descripción:</strong> <%= articulo.getDescripcion() %></p>

                    <!-- Botón para volver al catálogo -->
                    <div class="volver-catalogo">
                        <a href="catalogo.jsp">Volver al catálogo</a>
                    </div>
                <% } else { %>
                    <p>El artículo no existe.</p>
                <% } %>
            </div>
        </div>
    </main>

    <footer class="footer">
        <p><a href="#">Programación de Aplicaciones Web - Universidad de la Rioja</a></p>
    </footer>

    <script src="assets-copia/js/jquery-2.1.1.js"></script>
    <script src="assets-copia/js/jquery.mobile.custom.min.js"></script>
    <script src="assets-copia/js/main.js"></script>
</body>

</html>