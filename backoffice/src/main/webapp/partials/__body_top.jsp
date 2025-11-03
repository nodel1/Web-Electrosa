<%-- 
    Document   : __body_top
    Created on : 5 mar 2025, 18:13:13
    Author     : nodel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="topbar">
    <c:if test="${not empty sessionScope.usuario}">
        <span class="topbar_userinfo">${sessionScope.usuario.username}</span>
        <span class="topbar_sep">/</span> 
        <a href="${pageContext.request.contextPath}/auth/logout" class="topbar_link">
            <i class="lni lni-power-button"></i> Cerrar sesión
        </a>
    </c:if>
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
    </ul> <!-- cd-header-buttons -->
</header>

