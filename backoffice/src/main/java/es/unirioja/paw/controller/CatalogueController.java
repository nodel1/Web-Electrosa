/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.controller;


import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.ArticuloDaoMySQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import es.unirioja.paw.dao.ArticuloDao;

/**
 *
 * @author nodel
 */
@WebServlet(name = "CatalogueController", urlPatterns = {"/catalogue"})
public class CatalogueController extends HttpServlet {

    private ArticuloDao articuloDAO = new ArticuloDaoMySQL();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 12; 
        int pageNumber = 1; 


        String pageParam = request.getParameter("p");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                pageNumber = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }
        }

        try {

            List<Articulo> articulos = articuloDAO.findByPage(pageNumber, pageSize);


            int totalArticulos = articuloDAO.countAll();


            int totalPages = (int) Math.ceil((double) totalArticulos / pageSize);

 
            request.setAttribute("articulos", articulos);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalArticulos", totalArticulos);


            request.getRequestDispatcher("/article-list.jsp").forward(request, response);

        } catch (ExcepcionDeAplicacion e) {
            throw new ServletException("Error al recuperar los artículos", e);
        }
    }
}
