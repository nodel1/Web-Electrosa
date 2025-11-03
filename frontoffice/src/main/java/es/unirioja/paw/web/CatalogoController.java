package es.unirioja.paw.web;

import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.repository.ArticuloRepository;
import es.unirioja.paw.exception.ArticuloNotFoundException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    private static final int PAGE_SIZE = 15;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticuloRepository articuloRepository;

    @GetMapping(value = "")
    public String verCatalogo(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "fabricante", defaultValue = "-1") String fabricante,
            @RequestParam(name = "tipo", defaultValue = "-1") String tipo,
            ModelMap model) {

        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE);

        // Aplicar filtros según los parámetros
        Page<ArticuloEntity> articulosPage = articuloRepository.findByFabricanteAndTipo(fabricante, tipo, pageable);

        logger.info("Número de artículos en la página: {}", articulosPage.getContent().size());
        logger.info("Total de artículos: {}", articulosPage.getTotalElements());
        logger.info("Página actual: {}, Total de páginas: {}", page, articulosPage.getTotalPages());
        logger.info("Filtros aplicados - Fabricante: {}, Tipo: {}", fabricante, tipo);
        logger.info("Artículos recuperados: {}", articulosPage.getContent());

        // Añadir atributos al modelo
        model.addAttribute("articulos", articulosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articulosPage.getTotalPages());
        model.addAttribute("totalItems", articulosPage.getTotalElements());
        model.addAttribute("selectedFabricante", fabricante); // Para mantener el valor en el formulario
        model.addAttribute("selectedTipo", tipo);             // Para mantener el valor en el formulario

        return "catalogo";
    }

    @GetMapping(value = "/{articleId}")
    public String getArticulo(@PathVariable String articleId, ModelMap model) {
        Optional<ArticuloEntity> articulo = articuloRepository.findById(articleId);
        if (articulo.isPresent()) {
            logger.info("Artículooooo recuperado: {} - {}", articulo.get().getCodigo(), articulo.get().getNombre());
            model.addAttribute("articulo", articulo.get());
            model.addAttribute("title", "Detalles de " + articulo.get().getNombre());
            return "ficha";
        } else {
            logger.warn("Artículo no encontrado: {}", articleId);
            throw new ArticuloNotFoundException("Artículo con código " + articleId + " no encontrado");
        }
    }
}


