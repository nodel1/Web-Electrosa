package es.unirioja.paw.usecase;

import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.LineaanuladaEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import es.unirioja.paw.repository.PedidoAnuladoRepository;
import es.unirioja.paw.service.PedidoService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnularPedidoUseCase {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoAnuladoRepository pedidoAnuladoRepository;

    @Transactional
    public void anularPedido(PedidoEntity pedido) {
        if (pedido == null) {
            throw new IllegalStateException("El pedido no puede anularse porque no existe.");
        }

        PedidoanuladoEntity pedidoAnulado = new PedidoanuladoEntity();
        pedidoAnulado.setCodigo(pedido.getCodigo());
        pedidoAnulado.setFechacierre((Timestamp) pedido.getFechacierre());
        pedidoAnulado.setFechaanulacion(new Timestamp(System.currentTimeMillis()));
        pedidoAnulado.setCodigoCliente(pedido.getCodigoCliente());


        List<LineaanuladaEntity> lineasAnuladas = new ArrayList<>();
        for (LineaPedidoEntity linea : pedido.getLineas()) {
            LineaanuladaEntity lineaAnulada = new LineaanuladaEntity();
            lineaAnulada.setArticulo(linea.getArticulo());
            lineaAnulada.setCantidad(linea.getCantidad());
            lineaAnulada.setPedido(pedidoAnulado);
            lineasAnuladas.add(lineaAnulada);
        }
        pedidoAnulado.setLineas(lineasAnuladas);

    try {
        pedidoAnuladoRepository.save(pedidoAnulado);
    } catch (OptimisticLockException e) {
        throw new IllegalStateException("El pedido ha sido modificado por otra transacción. Intente nuevamente.");
    } //ME DEBERIA SALTAR AQUI LA EXCEPCION
    try {
        pedidoService.delete(pedido.getCodigo());
    } catch (OptimisticLockException e) {
        throw new IllegalStateException("El pedido no puede ser eliminado porque ha sido modificado por otra transacción.");
    }
    }
}
