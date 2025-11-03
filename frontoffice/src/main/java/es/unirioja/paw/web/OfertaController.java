/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import com.github.javafaker.Faker;
import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.repository.ArticuloRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author noelr
 */
@Controller
public class OfertaController {

    @Autowired
    private ArticuloRepository articuloRepository;

    private final Faker faker = new Faker();

    @GetMapping("/ofertas/packs")
    public String mostrarOfertas(Model model) {
        // Obtener tres artículos aleatorios
        List<ArticuloEntity> articulos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Optional<ArticuloEntity> articulo = randomArticulo();
            articulo.ifPresent(articulos::add);
        }

        // Calcular el artículo de promoción (Robot Aspirador)
        if (!articulos.isEmpty()) {
            ArticuloEntity primerArticulo = articulos.get(0);
            BigDecimal precioPromocion = primerArticulo.getPvp().multiply(new BigDecimal("0.10")); // 10% del precio del primer artículo
            ArticuloEntity promocion = new ArticuloEntity();
            promocion.setCodigo(primerArticulo.getCodigo());
            promocion.setNombre("ROBOT ASPIRADOR");
            promocion.setPvp(precioPromocion);
            promocion.setFoto(primerArticulo.getFoto()); // Usar la misma foto del primer artículo como placeholder
            model.addAttribute("promocion", promocion);
        }

        // Calcular precio final (suma de los tres artículos)
        BigDecimal precioFinal = articulos.stream()
                .map(ArticuloEntity::getPvp)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Añadir atributos al modelo
        model.addAttribute("articulos", articulos);
        model.addAttribute("precioFinal", precioFinal);

        return "ofertas"; // Nombre de la vista
    }

    private Optional<ArticuloEntity> randomArticulo() {
        long count = articuloRepository.count();
        if (count == 0) {
            return Optional.empty();
        }
        int pageNumber = faker.number().numberBetween(0, (int) count - 1);
        Pageable pageable = PageRequest.of(pageNumber, 1);
        Page<ArticuloEntity> articuloPage = articuloRepository.findAll(pageable);
        if (articuloPage.getContent().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(articuloPage.getContent().get(0));
    }
}