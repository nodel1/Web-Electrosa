<%-- 
    Document   : login
    Created on : 18 mar 2025, 18:28:20
    Author     : nodel
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Electrosa - Iniciar Sesión</title>

        <!-- Favicon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets-copia/images/logo/favicon.ico" type="image/x-icon">

        <!-- Estilos CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets-copia/css/reset.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets-copia/css/megamenu.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets-copia/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets-copia/css/login.css"> <!-- Estilos específicos para el login -->

        <!-- Fuentes de Google -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    </head>
    <body class="login-body">
        <div class="login-container">
            <!-- Logo y título -->
            <img src="${pageContext.request.contextPath}/assets-copia/images/logo-electrosa-retro.png" alt="Logo Electrosa Retro" class="login-logo">
            <h2 class="login-title">Iniciar Sesión</h2>

            <!-- Formulario de login -->
            <form method="POST" action="${pageContext.request.contextPath}/auth/login" class="login-form">
                <!-- Campo de usuario -->
                <div class="login-input-group">
                    <label for="username" class="login-label">
                        <i class="lni lni-user"></i> Usuario
                    </label>
                    <input type="text" id="username" name="username" value="${username}" required class="login-input-line" placeholder="Ingresa tu usuario">
                </div>

                <!-- Campo de contraseña -->
                <div class="login-input-group">
                    <label for="password" class="login-label">
                        <i class="lni lni-lock"></i> Contraseña
                    </label>
                    <input type="password" id="password" name="password" required class="login-input-line" placeholder="Ingresa tu contraseña">
                </div>

                <!-- Botón de envío -->
                <input type="submit" value="ACCEDER" class="login-submit">
            </form>

            <!-- Mensaje de error -->
            <c:if test="${not empty error}">
                <div class="login-error">
                    <i class="lni lni-warning"></i> ${error}
                </div>
            </c:if>
        </div>
    </body>
</html>
