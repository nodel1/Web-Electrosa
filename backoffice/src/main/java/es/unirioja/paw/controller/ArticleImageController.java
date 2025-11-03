package es.unirioja.paw.controller;

import es.unirioja.paw.dao.ArticuloDao;
import es.unirioja.paw.model.Articulo;
import es.unirioja.paw.model.ExcepcionDeAplicacion;
import es.unirioja.paw.mysql.ArticuloDaoMySQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/article/image/*")
@MultipartConfig(maxFileSize = 10485760) // 10MB límite
public class ArticleImageController extends HttpServlet {

    private static final String UPLOAD_DIR = "assets-copia/images/store/";
    private static final String VIEW_PATH = "/article-image.jsp";

    private ArticuloDao articuloDAO = new ArticuloDaoMySQL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String codigo = pathInfo.substring(1);
            try {
                Articulo articulo = articuloDAO.findByCodigo(codigo);
                if (articulo != null) {
                    request.setAttribute("articulo", articulo);
                    request.getRequestDispatcher(VIEW_PATH).forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Artículo no encontrado");
                }
            } catch (ExcepcionDeAplicacion e) {
                throw new ServletException("Error al obtener el artículo", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Código de artículo no especificado");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String codigo = pathInfo.substring(1);
            Part filePart = request.getPart("foto");
            try {
                Articulo articulo = articuloDAO.findByCodigo(codigo);

                if (articulo != null) {
                    List<String> errores = new ArrayList<>();
                    String fileName = null;

                    if (filePart != null && filePart.getSize() > 0) {
                        fileName = codigo + "_" + System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                        String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIR;
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        File file = new File(uploadPath + fileName);
                        Files.copy(filePart.getInputStream(), file.toPath());

                        articulo.setFoto(fileName);
                        articuloDAO.saveEntity(articulo);
                    } else {
                        errores.add("Debes seleccionar una imagen.");
                    }

                    if (errores.isEmpty()) {
                        request.getSession().setAttribute("mensaje", "Foto actualizada con éxito."); // Usar sesión para mensaje
                    } else {
                        request.getSession().setAttribute("errores", errores); // Usar sesión para errores
                    }
                    response.sendRedirect(request.getContextPath() + VIEW_PATH + "?codigo=" + codigo); // Redirigir con código
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Artículo no encontrado");
                }
            } catch (ExcepcionDeAplicacion e) {
                throw new ServletException("Error al actualizar la foto", e);
            }
        }
    }
}