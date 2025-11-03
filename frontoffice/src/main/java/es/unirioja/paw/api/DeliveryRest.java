package es.unirioja.paw.api;

import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/api/delivery")
public class DeliveryRest {

    @Autowired
    private PedidoService pedidoService;

    // Operación para cursar un pedido y establecer la fecha de entrega en las líneas
    @PostMapping("/{pedidoId}/schedule")
    public ResponseEntity<PedidoEntity> scheduleDelivery(@PathVariable String pedidoId, @RequestBody DeliveryRequest request) {
        PedidoEntity pedido = pedidoService.findOne(pedidoId);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        // Solo actualizar si no está cursado (cursado == null o 0)
        if (pedido.getCursado() == null || pedido.getCursado() == 0) {
            pedido.setCursado(1); // Marcar como cursado
            LocalDate fechaEstimada = LocalDate.now().plusDays(request.ndays()); // Usar ndays() directamente
            java.sql.Date fechaSql = java.sql.Date.valueOf(fechaEstimada);

            // Actualizar la fechaEntregaPrevista en todas las líneas
            List<LineaPedidoEntity> lineas = pedido.getLineas();
            if (lineas != null) {
                for (LineaPedidoEntity linea : lineas) {
                    linea.setFechaEntregaPrevista(fechaSql); // Usar fechaEntregaPrevista
                }
            }
            pedidoService.save(pedido); // Guardar cambios en pedido y líneas
        }

        return ResponseEntity.ok(pedido);
    }

    // Operación para cancelar el estado cursado
    @PostMapping("/{pedidoId}/schedule/cancel")
    public ResponseEntity<PedidoEntity> cancelSchedule(@PathVariable String pedidoId) {
        PedidoEntity pedido = pedidoService.findOne(pedidoId);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        // Solo actualizar si está cursado (cursado == 1)
        if (pedido.getCursado() != null && pedido.getCursado() == 1) {
            pedido.setCursado(0); // Volver a pendiente
            List<LineaPedidoEntity> lineas = pedido.getLineas();
            if (lineas != null) {
                for (LineaPedidoEntity linea : lineas) {
                    linea.setFechaEntregaPrevista(null); // Eliminar fecha estimada
                }
            }
            pedidoService.save(pedido); // Guardar cambios
        }

        return ResponseEntity.ok(pedido);
    }
}