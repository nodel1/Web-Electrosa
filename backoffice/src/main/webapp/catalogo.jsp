<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="es.unirioja.paw.model.Articulo" %>
<%@ page import="es.unirioja.paw.dao.CatalogoDAO" %>
<%@ page import="es.unirioja.paw.mysql.CatalogoDaoMySQL" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Electrosa</title>

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

            <li class="has-children">
                <a href="#"><i class="lni lni-rocket-5"></i> Ventas</a>
                <ul class="cd-nav-gallery is-hidden">
                    <li class="go-back"><a href="#0">Menú</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets-copia/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Pedidos</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets-copia/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Almacenes</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets-copia/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Envíos</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets-copia/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Herramientas de ventas</h3>
                        </a>
                    </li>
                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-dashboard-square-1"></i> Catálogo</a>
                <ul class="cd-nav-icons is-hidden">
                    <li class="go-back"><a href="#0">Menú</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li>
                        <a class="cd-nav-item item-9" href="#">
                            <h3>Productos</h3>
                            <p>Todos los productos</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-10" href="#">
                            <h3>Categorías</h3>
                            <p>Categorías / Tipos de producto</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-11" href="#">
                            <h3>Fabricantes</h3>
                            <p>Fabricantes y proveedores</p>
                        </a>
                    </li>
                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-rocket-5"></i> Analítica</a>
                <ul class="cd-nav-gallery is-hidden">
                    <li class="go-back"><a href="#0">Menú</a></li>
                    <li class="see-all"><a href="#">Ver todas las opciones</a></li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets-copia/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Cuadro de mando</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets-copia/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Marketing</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets-copia/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Facturación</h3>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item" href="#">
                            <img src="assets-copia/images/megamenu/img.jpg" alt="Product Image">
                            <h3>Finanzas</h3>
                        </a>
                    </li>
                </ul>
            </li>

            <li class="has-children">
                <a href="#"><i class="lni lni-sliders-horizontal-square-2"></i> Ajustes</a>
                <ul class="cd-nav-icons is-hidden">
                    <li class="go-back"><a href="#0">Menú</a></li>
                    <li>
                        <a class="cd-nav-item item-1" href="#">
                            <h3>Usuarios</h3>
                            <p>Usuarios del sistema</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-2" href="#">
                            <h3>Rendimiento</h3>
                            <p>Optimizaciones, cache y módulos</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-3" href="#">
                            <h3>Importar</h3>
                            <p>Importación de datos en el catálogo</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-4" href="#">
                            <h3>Servicios web</h3>
                            <p>API de servicios web REST</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-5" href="#">
                            <h3>Seguridad</h3>
                            <p>Parámetros de seguridad de la tienda online</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-6" href="#">
                            <h3>Base de datos</h3>
                            <p>Gestor simple SQL y copias de seguridad</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-7" href="#">
                            <h3>Información</h3>
                            <p>Información del sistema</p>
                        </a>
                    </li>
                    <li>
                        <a class="cd-nav-item item-8" href="#">
                            <h3>Logs</h3>
                            <p>Registros de errores y auditoría</p>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>

    <!-- Buscar -->
    <div id="cd-search" class="cd-search">
        <form action="articles.jsp">
            <input type="search" name="q" placeholder="Nombre artículo (por ejemplo: sci)">
        </form>
    </div>

    <main class="main cd-main-content">
        <h2>Catálogo de productos</h2>

        <%
            CatalogoDAO dao = new CatalogoDaoMySQL();
            List<Articulo> articulos = dao.findAll();
        %>

<table border="1" style="width: 100%; border-collapse: collapse;">
    <thead>
        <tr>
            <th style="text-align: center; vertical-align: middle;">Imagen</th>
            <th style="text-align: center; vertical-align: middle;">Código</th>
            <th style="text-align: center; vertical-align: middle;">Nombre</th>
            <th style="text-align: center; vertical-align: middle;">Tipo</th>
            <th style="text-align: center; vertical-align: middle;">Fabricante</th>
            <th style="text-align: center; vertical-align: middle;">PVP</th>
        </tr>
            </thead>
            <tbody>
                <%
                    int contador = 0;
                    for (Articulo a : articulos) {
                        String rowClass = (contador % 2 == 0) ? "even-row" : "odd-row";
                %>
                <tr class="<%= rowClass %>">
                    <td style="text-align: center; vertical-align: middle;"><img src="assets-copia/images/store/<%= a.getFoto() %>" alt="Imagen de <%= a.getNombre() %>" width="150"></td>
                    <td style="text-align: center; vertical-align: middle;"><a href="articulo.jsp?codigo=<%= a.getCodigo() %>"><%= a.getCodigo() %></a></td> 
                    <td style="text-align: center; vertical-align: middle;"><a href="articulo.jsp?codigo=<%= a.getCodigo() %>"><%= a.getNombre() %></a></td> 
                    <td style="text-align: center; vertical-align: middle;"><%= a.getTipo() %></td>
                    <td style="text-align: center; vertical-align: middle;"><%= a.getFabricante() %></td>
                    <td style="text-align: center; vertical-align: middle;"><%= a.getPvp() %>€</td>
                </tr>
                <%
                        contador++;
                    }
                %>
            </tbody>
        </table>
    </main>

    <footer class="footer">
        <p><a href="#">Programación de Aplicaciones Web - Universidad de la Rioja</a></p>
    </footer>

    <script src="assets-copia/js/jquery-2.1.1.js"></script>
    <script src="assets-copia/js/jquery.mobile.custom.min.js"></script>
    <script src="assets-copia/js/main.js"></script>
</body>

</html>
