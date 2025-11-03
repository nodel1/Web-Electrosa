/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.controller;

import es.unirioja.paw.dao.PedidoDAO;
import es.unirioja.paw.mysql.PedidoDaoMySQL;
import es.unirioja.paw.model.Pedido;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 *
 * @author nodel
 */
@WebServlet(name = "PedidoController", urlPatterns = {"/pedidos"})
public class PedidoController extends HttpServlet {

    private PedidoDAO pedidoDAO = new PedidoDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Pedido> pedidos = pedidoDAO.findAll();

            request.setAttribute("pedidos", pedidos);


            request.getRequestDispatcher("/pedido-list.jsp").forward(request, response);

        } catch (ExcepcionDeAplicacion e) {
            throw new ServletException("Error al obtener los pedidos", e);
        }
    }
}
