package es.unirioja.paw.usecase;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.LineaCestaCompraEntity;

public interface CestaCompraUseCase {
    void add(AddToCartRequest request);

    public static class AddToCartRequest {
        private final String codigoArticulo;
        private final CestaCompraEntity cesta;

        public AddToCartRequest(String codigoArticulo, CestaCompraEntity cesta) {
            this.codigoArticulo = codigoArticulo;
            this.cesta = cesta;
        }

        public String getCodigoArticulo() { return codigoArticulo; }
        public CestaCompraEntity getCesta() { return cesta; }
    }

    public static class AddToCartResponse {
        private final CestaCompraEntity cesta;
        private final LineaCestaCompraEntity linea;

        public AddToCartResponse(CestaCompraEntity cesta, LineaCestaCompraEntity linea) {
            this.cesta = cesta;
            this.linea = linea;
        }

        public CestaCompraEntity getCesta() { return cesta; }
        public LineaCestaCompraEntity getLinea() { return linea; }
    }
}