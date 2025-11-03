/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.UsuarioDAO;
import es.unirioja.paw.model.Usuario;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.UsuarioDaoMySQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


/**
 *
 * @author nodel
 */


@WebServlet(name = "LoginController", urlPatterns = {"/auth/login"})
public class LoginController extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDaoMySQL(); 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar la vista de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Usuario y contraseña son obligatorios");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            // Verificar si existe un usuario con las credenciales dadas
            if (usuarioDAO.countByUsernameAndPassword(username, password)) {
                // Verificar si el usuario tiene el rol de administrador
                String rol = usuarioDAO.obtenerRolUsuario(username);
                if ("administrador".equals(rol)) {
                    // Autenticación exitosa
                    HttpSession session = request.getSession();
                    Usuario usuario = new Usuario();
                    usuario.setUsername(username);
                    session.setAttribute("usuario", usuario);

                    // Redirigir a la página solicitada originalmente o a la página de inicio
                    String redirectURL = (String) session.getAttribute("redirectURL");
                    if (redirectURL != null) {
                        response.sendRedirect(redirectURL);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/catalogue");
                    }
                } else {
                    // El usuario no tiene el rol de administrador
                    request.setAttribute("error", "El usuario no tiene permisos de administrador");
                    request.setAttribute("username", username); // Mantener el nombre de usuario en el formulario
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            } else {
                // Autenticación fallida
                request.setAttribute("error", "Usuario o contraseña incorrecta");
                request.setAttribute("username", username); // Mantener el nombre de usuario en el formulario
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (ExcepcionDeAplicacion e) {
            throw new ServletException("Error al buscar el usuario en la base de datos", e);
        }
    }
}