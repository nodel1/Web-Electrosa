package es.unirioja.paw.service;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicios relacionados con pedidos
 */
public interface PedidoService {

    /**
     * @param codigoCliente Codigo del cliente
     * @return Coleccion de los pedidos del cliente (compras realizadas)
     */
    public List<PedidoEntity> findByCliente(String codigoCliente);

    /**
     * Busqueda de un pedido por su codigo
     *
     * @param codigoPedido
     * @return Pedido
     */
    public PedidoEntity findOne(String codigoPedido);

    /**
     *
     * @param codigoCliente Codigo del cliente
     * @return Cesta de la compra (coleccion de articulos en la cesta)
     */
    public CestaCompraEntity findCestaCliente(String codigoCliente);

    public void deleteCestaCliente(String codigoCliente);
    
    /**
     * Elimina un pedido por su codigo
     *
     * @param codigoPedido Codigo del pedido a eliminar
     */

    @Transactional
    public void delete(String codigoPedido);
    
    @Transactional
    public void save(PedidoEntity pedido);

}
