package es.unirioja.paw.web;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.jpa.DireccionEntity;
import es.unirioja.paw.jpa.LineaCestaCompraEntity;
import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.usecase.CestaCompraUseCase;
import es.unirioja.paw.usecase.PedidoUseCase;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author noelr
 */
@Controller
@RequestMapping("/cliente")
public class PedidoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CestaCompraUseCase cestaCompraUseCase;

    @Autowired
    private PedidoUseCase pedidoUseCase;

    @PostMapping("/proceder-compra")
    public String mostrarFormularioCompra(HttpSession session, Model model) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        if (cesta == null || cesta.getLineas().isEmpty()) {
            return "redirect:/cliente/cesta";
        }
        model.addAttribute("cesta", cesta);
        return "proceder-compra"; // Vista con el formulario de dirección
    }

    @PostMapping("/confirmar-pedido")
    public String confirmarPedido(
            @RequestParam("calle") String calle,
            @RequestParam("cp") String cp,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("provincia") String provincia,
            @RequestParam("fechaEntrega") String fechaEntrega, // Fecha en formato yyyy-MM-dd
            HttpSession session,
            Model model) {

        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");

        if (cesta == null || cliente == null || cesta.getLineas().isEmpty()) {
            return "redirect:/cliente/cesta";
        }

        // Crear el PedidoEntity
        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigoCliente(cliente.getCodigo());
        pedido.setFechacierre(new Date(System.currentTimeMillis())); // Usamos fechacierre en lugar de fechaInicio

        // Configurar la dirección
        DireccionEntity direccionEntity = new DireccionEntity();
        direccionEntity.setCalle(calle);
        direccionEntity.setCp(cp);
        direccionEntity.setCiudad(ciudad);
        direccionEntity.setProvincia(provincia);
        pedido.setDireccion(direccionEntity);

        // Convertir la fecha de entrega deseada a Date
        Date fechaEntregaDeseada = Date.valueOf(fechaEntrega);

        // Convertir líneas de la cesta a líneas del pedido
        List<LineaPedidoEntity> lineasPedido = new ArrayList<>();
        for (LineaCestaCompraEntity linea : cesta.getLineas()) {
            LineaPedidoEntity lineaPedido = new LineaPedidoEntity();
            lineaPedido.setArticulo(linea.getArticulo());
            lineaPedido.setCantidad(linea.getCantidad());
            lineaPedido.setPrecioBase(linea.getPrecio()); // Usamos precioBase como equivalente a precio de la cesta
            lineaPedido.setFechaEntregaDeseada(fechaEntregaDeseada); // Propagar la fecha deseada a todas las líneas
            lineaPedido.setPrecioReal(linea.getPrecio().doubleValue()); // Convertir BigDecimal a Double para precioReal
            lineaPedido.setPedido(pedido);
            lineasPedido.add(lineaPedido);
        }
        pedido.setLineas(lineasPedido);

        // Guardar el pedido
        pedidoUseCase.crearPedido(pedido);

        // Vaciar la cesta
        cesta.getLineas().clear();
        session.setAttribute("cesta", cesta);

        // Redirigir a la cesta
        return "redirect:/cliente/cesta";
    }
}