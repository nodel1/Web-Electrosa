package es.unirioja.paw.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/cliente/cuenta", "/clientes/cuenta"}) // Solo protege esta URL
public class ClienteAuthnFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ClienteAuthnFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // No crear sesión si no existe
        String requestURI = httpRequest.getRequestURI();

        // Si el usuario NO está autenticado y va a /cliente/cuenta, redirigirlo al login
        if (session == null || session.getAttribute("cliente") == null) {
            logger.info("Usuario no autenticado. Redirigiendo a login desde {}", requestURI);

            session = httpRequest.getSession(true); // Crear sesión si no existe
            session.setAttribute("returnUrl", requestURI); // Guardar URL destino

            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
            return;
        }

        // Si está autenticado, permitir el acceso
        chain.doFilter(request, response);
    }
}
