<%-- 
    Document   : article-detail
    Created on : 10 mar 2025, 21:16:09
    Author     : nodel
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">

            <!-- Breadcrumb -->
            <div class="breadcrumb">
                <a href="catalogo.jsp">Inicio</a> &gt;
                <a href="catalogue">Catálogo</a> &gt;
                <span>Editar Artículo</span>
            </div>


            <h2>Editar Artículo</h2>

            <!-- Mostrar mensajes de error -->
            <c:if test="${not empty errores}">
                <div class="alert alert-danger">
                    <ul>
                        <c:forEach var="error" items="${errores}">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>

            <!-- Mostrar mensaje de éxito -->
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success">
                    ${mensaje}
                </div>
            </c:if>

            <div class="articulo-details" style="display: flex; gap: 20px;">
                <c:if test="${not empty articulo}">
                    <!-- Foto a la izquierda -->
                    <div class="articulo-foto" style="flex: 0 0 500px; text-align: left;">
                        <c:choose>
                            <c:when test="${not empty articulo.foto}">
                                <img src="assets-copia/images/store/${articulo.foto}" alt="${articulo.nombre}" class="image img" style="width: 500px; height: auto; border-radius: 8px;" />
                            </c:when>
                            <c:otherwise>
                                <p>No hay imagen disponible.</p>
                            </c:otherwise>
                        </c:choose>
                        <a href="${pageContext.request.contextPath}/article/image/${articulo.codigo}" class="btn btn-edit" style="margin-top: 10px; display: inline-block;">Cambiar Foto</a>
                    </div>

                    <!-- Formulario a la derecha -->
                    <form action="article" method="post"  class="form-sw" style="flex: 1;">
                        <input type="hidden" name="codigo" value="${articulo.codigo}">
                        <div class="articulo-info">
                            <div class="form-control">
                                <label for="nombre">Nombre:</label>
                                <input type="text" id="nombre" name="nombre" value="${articulo.nombre}" required class="form-control">
                            </div>

                            <div class="form-control">
                                <label for="descripcion">Descripción:</label>
                                <textarea id="descripcion" name="descripcion" rows="4" required class="form-control">${articulo.descripcion}</textarea>
                            </div>

                            <div class="form-control">
                                <label for="precio">Precio (€):</label>
                                <input type="number" id="precio" name="precio" step="0.01" value="${articulo.pvp}" required class="form-control">
                            </div>

                            <div class="form-control">
                                <label for="tipo">Tipo:</label>
                                <select id="tipo" name="tipo" required class="form-control">
                                    <c:forEach var="tipo" items="${tipos}">
                                        <option value="${tipo.nombre}" ${articulo.tipo == tipo.nombre ? 'selected' : ''}>${tipo.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-control">
                                <label for="fabricante">Fabricante:</label>
                                <select id="fabricante" name="fabricante" required class="form-control">
                                    <c:forEach var="fabricante" items="${fabricantes}">
                                        <option value="${fabricante.nombre}" ${articulo.fabricante == fabricante.nombre ? 'selected' : ''}>${fabricante.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-edit">Guardar Cambios</button>
                        </div>
                    </form>
                </c:if>
            </div>

            <!-- Enlace "Volver al catálogo" al final -->
            <div class="volver-catalogo">
                <c:choose>
                    <c:when test="${not empty header['Referer']}">
                        <a href="${header['Referer']}" class="btn-volver">Volver al catálogo</a>
                    </c:when>
                    <c:otherwise>
                        <a href="catalogue" class="btn-volver">Volver al catálogo</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>



    </body>
</html>
