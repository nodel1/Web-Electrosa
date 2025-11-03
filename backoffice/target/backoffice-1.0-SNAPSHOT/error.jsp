<%-- 
    Document   : error
    Created on : 17 mar 2025, 18:18:10
    Author     : nodel
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <h2>Error ${requestScope['jakarta.servlet.error.status_code']} </h2>

            <div class="error-container" style="display: flex; gap: 20px;">
                <!-- Foto a la izquierda -->
                <div class="error-foto" style="flex: 0 0 500px; text-align: left;">
                    <img src="assets-copia/images/96938.jpg" alt="Error" class="image img" style="width: 500px; height: auto; border-radius: 8px;" />
                </div>

                <!-- Detalles del error a la derecha -->
                <div class="error-details" style="flex: 1;">
                    <p>Lo sentimos, no hemos podido completar la petición.</p>

                    <div class="error-info">
                        <p><strong>Excepción:</strong></p>
                        <p><strong>Tipo de excepción:</strong></p>
                        <p><strong>URL:</strong> ${requestScope['jakarta.servlet.error.request_uri']}</p>
                        <p><strong>Servlet:</strong> ${requestScope['jakarta.servlet.error.servlet_name']}</p>
                        <p><strong>Mensaje:</strong> 
                            <c:out 
                                default="Error de aplicación" 
                                value="${requestScope['jakarta.servlet.error.message']}" />
                        </p>
                    </div>

                    <!-- Botón "Salir de aquí" -->
                    <a href="${not empty requestScope.enlaceSalir ? requestScope.enlaceSalir : 'catalogue'}" class="btn btn-primary">
                        Salir de aquí
                    </a>
                </div>
            </div>
        </main>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>

