<%-- 
    Document   : client-list
    Created on : 11 mar 2025, 18:22:34
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
            <h2>Lista de Clientes</h2>

            <!-- Mostrar el número total de clientes -->
            <div class="pagination-info">
                <p>Total de clientes encontrados: ${clienteCollection.size()}</p>
            </div>

            <!-- Tabla de clientes -->
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Código Postal</th>
                            <th>Ciudad</th>
                            <th>Provincia</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cliente" items="${clienteCollection}">
                            <tr>

                                <td>${cliente.codigo}</td>
                                <td>${cliente.nombre}</td>
                                <td>${cliente.email}</td>
                                <td>${cliente.codigoPostal}</td>
                                <td>${cliente.ciudad}</td>
                                <td>${cliente.provincia}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>

