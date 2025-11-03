<%@ page import="es.unirioja.paw.db.GestorBD" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resultados de la Búsqueda</title>

    <link rel="shortcut icon" href="assets-copia/images/logo/favicon.ico" type="image/x-icon">
    <link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />
    <link rel="stylesheet" href="assets-copia/css/reset.css">
    <link rel="stylesheet" href="assets-copia/css/megamenu.css">
    <link rel="stylesheet" href="assets-copia/css/style.css">
    
    <script src="assets-copia/js/modernizr.js"></script>
</head>

<body>
    <div class="topbar">
        <span class="topbar_userinfo">Francisco</span>
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
            <li><a class="cd-search-trigger" href="#cd-search">Search<span></span></a></li>
            <li><a class="cd-nav-trigger" href="#cd-primary-nav">Menu<span></span></a></li>
        </ul>
    </header>

    <!-- Menú de navegación -->
    <div class="cd-overlay"></div>

    <nav class="cd-nav">
        <ul id="cd-primary-nav" class="cd-primary-nav is-fixed">
            <li class="has-children">
                <a href="#"><i class="lni lni-rocket-5"></i> Ventas</a>
                <ul class="cd-nav-gallery is-hidden">
                    <li class="go-back"><a href="#0">Menu</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Pedidos</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Almacenes</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Envíos</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Herramientas de ventas</h3></a></li>
                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-dashboard-square-1"></i> Catálogo</a>
                <ul class="cd-nav-icons is-hidden">
                    <li class="go-back"><a href="#0">Menu</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Productos</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Categorías</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Fabricantes</h3></a></li>
                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-rocket-5"></i> Analítica</a>
                <ul class="cd-nav-gallery is-hidden">
                    <li class="go-back"><a href="#0">Menu</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Cuadro de mando</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Marketing</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Facturación</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Finanzas</h3></a></li>
                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-sliders-horizontal-square-2"></i> Ajustes</a>
                <ul class="cd-nav-icons is-hidden">
                    <li class="go-back"><a href="#0">Menu</a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Usuarios</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Rendimiento</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Importar</h3></a></li>
                    <li><a class="cd-nav-item" href="#"><h3>Seguridad</h3></a></li>
                </ul>
            </li>
        </ul>
    </nav>

    <div id="cd-search" class="cd-search">
        <form action="articles.jsp">
            <input type="search" name="q" placeholder="Nombre artículo (por ejemplo: sci)">
        </form>
    </div>

    <!-- Contenido principal -->
    <main class="cd-main-content main">
        <section>
            <h2>Resultados de la búsqueda</h2>

            <%
                String query = request.getParameter("q");
                if (query != null && !query.isEmpty()) {
                    GestorBD gestorBD = new GestorBD();
                    List<String> articulos = gestorBD.getArticulosLike(query);
            %>

            <p>Búsqueda de <strong>'<%= query %>'</strong> en todo el catálogo de productos:</p>

            <ul>
                <%
                    for (String articulo : articulos) {
                %>
                <li><%= articulo %></li>
                <%
                    }
                %>
            </ul>

            <%
                } else {
            %>
            <p>No se ha ingresado un término de búsqueda válido.</p>
            <%
                }
            %>
        </section>
    </main>

    <footer class="footer">
        <p><a href="#">Programación de Aplicaciones Web - Universidad de la Rioja</a></p>
    </footer>

    <script src="assets-copia/js/jquery-2.1.1.js"></script>
    <script src="assets-copia/js/jquery.mobile.custom.min.js"></script>
    <script src="assets-copia/js/main.js"></script>

</body>

</html>
