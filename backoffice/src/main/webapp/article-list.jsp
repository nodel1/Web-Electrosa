<%-- 
    Document   : article-list
    Created on : 7 mar 2025, 18:30:08
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
            <h2>Catálogo de Artículos</h2>

            <!-- Menú de paginación -->
            <div class="paginator-wrapper">

                <c:if test="${currentPage > 1}">
                    <a href="catalogue?p=1"><i class="lni lni-shift-left"></i> Primero</a>
                </c:if>


                <c:if test="${currentPage > 1}">
                    <a href="catalogue?p=${currentPage - 1}">Anterior</a>
                </c:if>


                <span class="current">
                    Mostrando página ${currentPage} de ${totalPages}
                </span>


                <c:if test="${currentPage < totalPages}">
                    <a href="catalogue?p=${currentPage + 1}">Siguiente</a>
                </c:if>


                <c:if test="${currentPage < totalPages}">
                    <a href="catalogue?p=${totalPages}">Último <i class="lni lni-shift-right"></i></a>
                </c:if>
            </div>


            <div class="pagination-info">
                <label>Artículos por página:</label>
                <span class="pagesize">12</span>
                <span class="records-found">(${totalArticulos} en total)</span>
            </div>

            <!-- Lista de artículos -->
            <section class="card-layout">
                <c:forEach var="articulo" items="${articulos}" varStatus="loop">
                    <div class="card-layout__item clg-item">
                        <div>${loop.index + 1}. ${articulo.codigo}</div>
                        <div class="clg-item__image">
                            <a href="article?artId=${articulo.codigo}">
                                <img src="assets-copia/images/store/${articulo.foto}" alt="${articulo.nombre}" />
                            </a>
                        </div>
                        <div class="clg-item__title">
                            <a href="article?artId=${articulo.codigo}">${articulo.nombre}</a>
                        </div>
                    </div>
                </c:forEach>
            </section>
        </main>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>
