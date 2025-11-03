package es.unirioja.paw.web;

import es.unirioja.paw.jpa.StockEntity;
import java.time.LocalDate;
import java.util.List;
import es.unirioja.paw.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nodel
 */
@RestController
@RequestMapping("/api/articulo") 
public class ApiArticuloController {

    @Autowired 
    private StockRepository stockRepository;

    @GetMapping("/{articuloId:.+}/vendido/hoy")
    public Integer vendidoHoy(@PathVariable("articuloId") String articuloId) {
        int diaDelMes = LocalDate.now().getDayOfMonth(); // Hoy es 18
        return diaDelMes;
    }

    @GetMapping("/{articuloId:.+}/stock")
    public List<StockEntity> getStock(@PathVariable("articuloId") String articuloId) {
        return stockRepository.findByCodigoArticulo(articuloId);
    }
}