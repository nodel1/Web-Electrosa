package es.unirioja.paw.usecase;

import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.LineaCestaCompraEntity;
import es.unirioja.paw.repository.ArticuloRepository;
import es.unirioja.paw.repository.CestaCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CestaCompraUseCaseImpl implements CestaCompraUseCase {

    @Autowired
    private CestaCompraRepository cestaRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Override
    public void add(AddToCartRequest request) {
        CestaCompraEntity cesta = request.getCesta();
        String codigoArticulo = request.getCodigoArticulo();

        // Buscar el artículo en la cesta
        Optional<LineaCestaCompraEntity> lineaExistente = cesta.findLineaArticulo(codigoArticulo);

        LineaCestaCompraEntity linea;
        if (lineaExistente.isPresent()) {
            // Si el artículo ya estaba, incrementar cantidad
            linea = lineaExistente.get();
            linea.incrementCantidad(1);
        } else {
            // Si no estaba, crear nueva línea
            ArticuloEntity articulo = articuloRepository.findById(codigoArticulo)
                    .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado: " + codigoArticulo));
            linea = new LineaCestaCompraEntity();
            linea.setArticulo(articulo);
            linea.setCantidad(1);
            linea.setPrecio(articulo.getPvp());
            linea.setCesta(cesta);
            linea.setFechaEntregaDeseada(null); // RERVISAR ESTO
            cesta.getLineas().add(linea);
        }

        //  Guardar en BD
        cestaRepository.save(cesta);
    }
}