<%-- 
    Document   : cliente
    Created on : 7 mar 2025, 12:41:52
    Author     : nodel
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <%@ include file='/partials/__head.jsp'%>
    <body>
        <%@ include file='/partials/__body_top.jsp'%>
        <main class="cd-main-content main">
            <h2>Detalles del Cliente</h2>

            <!-- Contenedor principal para la foto y los detalles -->
            <div class="cliente-container">
                <!-- Foto del cliente (a la izquierda) -->
                <div class="cliente-foto">
                    <img src="assets-copia/images/avatar/7294811.jpg" alt="Foto de ${cliente.nombre}" />
                </div>

                <!-- Detalles del cliente (a la derecha) -->
                <div class="cliente-details">
                    <h3>${cliente.nombre}</h3>
                <div class="lines">
                    <div>
                        <label>Código:</label> ${cliente.codigo}
                    </div>
                    <div>
                        <label>Usuario:</label> ${cliente.username}
                    </div>
                    <div>
                        <label>Email:</label> ${cliente.email}
                    </div>
                    <div>
                        <label>Código Postal:</label> ${cliente.codigoPostal}
                    </div>
                    <div>
                        <label>Ciudad:</label> ${cliente.ciudad}
                    </div>
                    <div>
                        <label>Provincia:</label> ${cliente.provincia}
                    </div>
                </div>
                </div>
            </div>
        </main>
        <%@ include file='/partials/__footer.jsp'%>
        <%@ include file='/partials/__navigation_menu.jsp'%>
        <%@ include file='/partials/__body_bottom.jsp'%>
    </body>
</html>