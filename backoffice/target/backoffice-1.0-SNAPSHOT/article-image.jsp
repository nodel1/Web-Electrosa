<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Cambiar Foto del Artículo</title>
        <%@ include file='/partials/__head.jsp' %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets-copia/css/reset.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets-copia/css/megamenu.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets-copia/css/style.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets-copia/images/logo/favicon.ico" type="image/x-icon">
    </head>
    <body>
        <%@ include file='/partials/__body_top.jsp' %>
        <main class="cd-main-content main">

            <!-- Breadcrumb -->
            <div class="breadcrumb">
                <a href="catalogo.jsp">Inicio</a> >
                <a href="catalogue">Catálogo</a> >
                <span>Cambiar Foto</span>
            </div>

            <h2>Cambiar Foto del Artículo</h2>

            <!-- Mostrar mensajes de error -->
            <c:if test="${not empty errores}">
                <div class="alert alert-danger">
                    <ul>
                        <c:forEach var="error" items="${errores}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </div>
                <% request.getSession().removeAttribute("errores"); %> <!-- Limpiar errores después de mostrar -->
            </c:if>

            <!-- Mostrar mensaje de éxito -->
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success">
                    ${mensaje}
                </div>
                <% request.getSession().removeAttribute("mensaje"); %> <!-- Limpiar mensaje después de mostrar -->
            </c:if>

            <c:set var="codigo" value="${param.codigo != null ? param.codigo : (articulo != null ? articulo.codigo : null)}" />
            <c:if test="${not empty codigo}">
                <%
                    // Recargar el artículo basado en el código
                    es.unirioja.paw.dao.ArticuloDao articuloDAO = new es.unirioja.paw.mysql.ArticuloDaoMySQL();
                    es.unirioja.paw.model.Articulo articulo = articuloDAO.findByCodigo((String) pageContext.getAttribute("codigo"));
                    request.setAttribute("articulo", articulo);
                %>
            </c:if>

            <c:if test="${not empty articulo}">
                <div class="articulo-image" style="display: flex; gap: 20px;">
                    <!-- Foto actual -->
                    <div class="current-image" style="flex: 0 0 500px; text-align: left;">
                        <c:choose>
                            <c:when test="${not empty articulo.foto}">
                                <img src="${pageContext.request.contextPath}/assets-copia/images/store/${articulo.foto}" alt="${articulo.nombre}" style="width: 500px; height: auto; border-radius: 8px;" />
                            </c:when>
                            <c:otherwise>
                                <p>No hay imagen actual.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Formulario para subir nueva foto -->
                    <form action="${pageContext.request.contextPath}/article/image/${articulo.codigo}" method="post" enctype="multipart/form-data" style="flex: 1;">
                        <div class="form-control">
                            <label for="foto">Nueva Foto:</label>
                            <input type="file" id="foto" name="foto" accept="image/*" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-edit" style="width: 50%; font-size: 0.8em;">Guardar Nueva Foto</button>
                    </form>
                </div>
            </c:if>

            <!-- Enlace "Volver" -->
            <div class="volver-catalogo">
                <a href="catalogue" class="btn-volver">Volver al Catálogo</a>
            </div>
        </main>
        <%@ include file='/partials/__footer.jsp' %>
        <%@ include file='/partials/__navigation_menu.jsp' %>
        <%@ include file='/partials/__body_bottom.jsp' %>
    </body>
</html>