package es.unirioja.paw.controller;

import es.unirioja.paw.dao.ArticuloDao;
import es.unirioja.paw.dao.CatalogoDAO;
import es.unirioja.paw.dao.FabricanteDAO;
import es.unirioja.paw.mysql.CatalogoDaoMySQL;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.model.Fabricante;
import es.unirioja.paw.model.TipoArticulo;
import es.unirioja.paw.mysql.ArticuloDaoMySQL;
import es.unirioja.paw.mysql.FabricanteDAOMySQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nodel
 */
@WebServlet(name = "ArticleController", urlPatterns = {"/article"})
public class ArticleController extends HttpServlet {

    private CatalogoDAO catalogoDAO = new CatalogoDaoMySQL();
    private FabricanteDAO fabricanteDAO = new FabricanteDAOMySQL();
    private ArticuloDao articuloDAO = new ArticuloDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el parámetro artId de la URL
        String artId = request.getParameter("artId");

        if (artId == null || artId.isEmpty()) {
            // Si no se da un artId, redirigir al catálogo
            response.sendRedirect("catalogue");
            return;
        }

        try {
            // Buscar el artículo en la base de datos
            Articulo articulo = catalogoDAO.findOneByCodigo(artId);

            List<TipoArticulo> tipos = catalogoDAO.findTiposArticulo();
            List<Fabricante> fabricantes = fabricanteDAO.findAll();

            if (articulo == null) {
                request.setAttribute("enlaceSalir", "catalogue");
                response.sendError(404, "El artículo solicitado no existe");
                return;
            }

            // Obtener el mensaje de la sesión si existe
            String mensaje = (String) request.getSession().getAttribute("mensaje");
            if (mensaje != null) {
                request.setAttribute("mensaje", mensaje);
                request.getSession().removeAttribute("mensaje"); // Limpiar el mensaje de la sesión
            }

            request.setAttribute("articulo", articulo);
            request.setAttribute("tipos", tipos);
            request.setAttribute("fabricantes", fabricantes);

            request.getRequestDispatcher("/article-detail.jsp").forward(request, response);

        } catch (ExcepcionDeAplicacion e) {
            throw new ServletException("Error al obtener el artículo", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();

        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precioStr = request.getParameter("precio");
        String tipo = request.getParameter("tipo");
        String fabricante = request.getParameter("fabricante");

        System.out.println("CODIGO: " + codigo); // PARA PROBAR EL ERROR, BORRAR LUEGO
        System.out.println("Nombre: " + nombre);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Precio: " + precioStr);
        System.out.println("Tipo: " + tipo);
        System.out.println("Fabricante: " + fabricante);

        if (codigo == null || codigo.trim().isEmpty()) {
            errores.add("El código del artículo es requerido.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.add("El nombre del artículo es requerido.");
        }

        if (nombre.length() >= 50) {
            errores.add("El nombre del artículo es demasiado grande.");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            errores.add("La descripción del artículo es requerida.");
        }

        if (descripcion.length() >= 200) {
            errores.add("La descripción del artículo es demasiado grande.");
        }

        if (precioStr == null || precioStr.trim().isEmpty()) {
            errores.add("El precio del artículo es requerido.");
        } else {
            try {
                double precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    errores.add("El precio no puede ser negativo ni cero.");
                }
            } catch (NumberFormatException e) {
                errores.add("El precio debe ser un número válido.");
            }
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            errores.add("El tipo del artículo es requerido.");
        }

        if (fabricante == null || fabricante.trim().isEmpty()) {
            errores.add("El fabricante del artículo es requerido.");
        }

        // Si hay errores, volver a mostrar el formulario con los mensajes de error
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);

            try {
                Articulo articulo = articuloDAO.findByCodigo(codigo);
                request.setAttribute("articulo", articulo);
                List<TipoArticulo> tipos = catalogoDAO.findTiposArticulo();
                List<Fabricante> fabricantes = fabricanteDAO.findAll();
                request.setAttribute("tipos", tipos);
                request.setAttribute("fabricantes", fabricantes);
            } catch (ExcepcionDeAplicacion e) {
                throw new ServletException("Error al recuperar el artículo", e);
            }

            request.getRequestDispatcher("/article-detail.jsp").forward(request, response);
            return;
        }

        try {
            // Recuperar el artículo de la base de datos
            Articulo articulo = articuloDAO.findByCodigo(codigo);

            // Actualizar los atributos del artículo
            articulo.setNombre(nombre);
            articulo.setDescripcion(descripcion);
            articulo.setPvp(Double.parseDouble(precioStr));
            articulo.setTipo(tipo);
            articulo.setFabricante(fabricante);

            // Guardar los cambios en la base de datos
            articuloDAO.saveEntity(articulo);

            // Almacenar el mensaje en la sesión
            request.getSession().setAttribute("mensaje", "Cambios realizados correctamente.");

            // Redirigir a la misma página para evitar reenvío del formulario
            response.sendRedirect("article?artId=" + codigo);

        } catch (ExcepcionDeAplicacion e) {
            throw new ServletException("Error al actualizar el artículo", e);
        }
    }
}