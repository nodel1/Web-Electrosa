<%-- 
    Document   : pedido-list
    Created on : 11 mar 2025, 17:26:38
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
            <h2>Lista de Pedidos</h2>

            <!-- Tabla de pedidos -->
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Código Cliente</th>
                            <th>Código Postal</th>
                            <th>Calle</th>
                            <th>Ciudad</th>
                            <th>Provincia</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pedido" items="${pedidos}">
                            <tr>
                                <td>${pedido.codigo}</td>
                                <td>${pedido.codigoCliente}</td>
                                <td>${pedido.cp}</td>
                                <td>${pedido.calle}</td>
                                <td>${pedido.ciudad}</td>
                                <td>${pedido.provincia}</td>
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

