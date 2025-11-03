/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import es.unirioja.paw.exception.AccessNotAuthorizedException;
import es.unirioja.paw.jpa.ArticuloEntity;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.jpa.LineaPedidoEntity;
import es.unirioja.paw.jpa.LineaanuladaEntity;
import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.jpa.PedidoanuladoEntity;
import es.unirioja.paw.repository.PedidoAnuladoRepository;
import es.unirioja.paw.exception.PedidoNotFoundException;

import es.unirioja.paw.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.unirioja.paw.usecase.AnularPedidoUseCase;

/**
 *
 * @author nodel
 */
@Controller
@RequestMapping({"/cliente", "/clientes"})
public class ClienteController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoAnuladoRepository pedidoAnuladoRepository;

    @Autowired
    private AnularPedidoUseCase anularPedidoUseCase;

    public ClienteController(PedidoService pedidoService, PedidoAnuladoRepository pedidoAnuladoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoAnuladoRepository = pedidoAnuladoRepository;
    }

    @GetMapping("/cuenta")
    public String getCuenta(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        model.addAttribute("cliente", cliente);
        model.addAttribute("title", "Área de Cliente - Electrosa");
        return "areaCliente";
    }

    @GetMapping("/pedidos")
    public String getPedidos(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        List<PedidoEntity> pedidos = pedidoService.findByCliente(cliente.getCodigo());
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("title", "Mis Pedidos - Electrosa");
        return "pedidos";
    }

    @GetMapping({"/pedidos/{codigo}", "/pedido/{codigo}"})
    public String detallePedido(@PathVariable("codigo") String codigo, HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        if (cliente == null) {
            throw new AccessNotAuthorizedException("No está autenticado para consultar pedidos.");
        }

        // Recuperar el pedido de la base de datos
        PedidoEntity pedido = pedidoService.findOne(codigo);
        if (pedido == null) {
            throw new PedidoNotFoundException("Pedido con código " + codigo + " no encontrado");
        }

        // Verificar que el pedido pertenece al cliente
        if (!cliente.getCodigo().equals(pedido.getCodigoCliente())) {
            throw new AccessNotAuthorizedException("No está autorizado para consultar este pedido");
        }

        // Preparar los datos para la vista
        List<LineaPedidoEntity> lineas = pedido.getLineas();
        List<ArticuloEntity> articulos = new ArrayList<>();
        for (LineaPedidoEntity linea : lineas) {
            articulos.add(linea.getArticulo());
        }
        model.addAttribute("lineas", lineas);
        model.addAttribute("articulos", articulos);
        model.addAttribute("pedido", pedido);
        model.addAttribute("title", "Detalles del Pedido - Electrosa");
        boolean puedeAnularse = lineas.stream().allMatch(linea -> linea.getFechaEntregaReal() == null);
        model.addAttribute("puedeAnularse", puedeAnularse);
        return "detalle_pedido";
    }

    @GetMapping("/pedidos/anulados")
    public String getAnulados(HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        List<PedidoanuladoEntity> pedidos = pedidoAnuladoRepository.findByCodigoCliente(cliente.getCodigo());
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("title", "Pedidos Anulados - Electrosa");
        return "pedidosAnulados";
    }

    @Transactional
    @GetMapping("/pedidos/anulados/{codigo}")
    public String detallePedidoAnulado(@PathVariable("codigo") String codigo, HttpSession session, Model model) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        Optional<PedidoanuladoEntity> pedidodetalle = pedidoAnuladoRepository.findById(codigo);
        if (pedidodetalle.isPresent()) {
            PedidoanuladoEntity pedidoAnulado = pedidodetalle.get();
            if (!pedidoAnulado.getCodigoCliente().equals(cliente.getCodigo())) {
                return "redirect:/cliente/pedidos/anulados";
            }
            List<LineaanuladaEntity> lineas = new ArrayList<>(pedidoAnulado.getLineas());
            List<ArticuloEntity> articulos = new ArrayList<>();
            for (LineaanuladaEntity linea : lineas) {
                ArticuloEntity articulo = linea.getArticulo();
                if (articulo != null) {
                    articulos.add(articulo);
                }
            }
            model.addAttribute("lineas", lineas);
            model.addAttribute("articulos", articulos);
            model.addAttribute("pedido", pedidoAnulado);
            model.addAttribute("title", "Detalles del Pedido Anulado - Electrosa");
        } else {
            return "redirect:/cliente/pedidos/anulados";
        }
        return "detalle_anulado";
    }

    @PostMapping("/pedidos/anular")
    public String anularPedido(@RequestParam("pedidoId") String pedidoId, HttpSession session) {
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        PedidoEntity pedido = pedidoService.findOne(pedidoId);
        if (pedido != null && pedido.getCodigoCliente().equals(cliente.getCodigo())) {
            List<LineaPedidoEntity> lineas = pedido.getLineas();
            boolean puedeAnularse = lineas.stream().allMatch(linea -> linea.getFechaEntregaReal() == null);
            if (puedeAnularse) {
                anularPedidoUseCase.anularPedido(pedido);
            } else {
                logger.warn("Intento de anular un pedido con entrega real: {}", pedidoId);
            }
        } else {
            logger.warn("Intento de anular un pedido no válido o no autorizado: {}", pedidoId);
        }
        return "redirect:/cliente/pedidos";
    }

}
