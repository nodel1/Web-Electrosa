/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author nodel
 */
@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro (si es necesario)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Obtener la ruta solicitada
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Excluir rutas públicas (login, logout y recursos estáticos)
        if (path.startsWith("/auth/login") || path.startsWith("/auth/logout") || path.startsWith("/assets-copia/")) {
            chain.doFilter(request, response); // Permitir el acceso sin autenticación
            return;
        }

        // Verificar si el usuario está autenticado
        if (session != null && session.getAttribute("usuario") != null) {
            // Usuario autenticado, permitir el acceso
            chain.doFilter(request, response);
        } else {
            // Usuario no autenticado, redirigir al login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
        }
    }

    @Override
    public void destroy() {
        // Limpieza del filtro (si es necesario)
    }
}
