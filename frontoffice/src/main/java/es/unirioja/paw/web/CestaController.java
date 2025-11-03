package es.unirioja.paw.web;

import es.unirioja.paw.jpa.CestaCompraEntity;
import es.unirioja.paw.jpa.ClienteEntity;
import es.unirioja.paw.usecase.CestaCompraUseCase;
import es.unirioja.paw.usecase.CestaCompraUseCase.AddToCartRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/cliente/cesta", "/clientes/cesta"})
public class CestaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
private EntityManager entityManager;
    
    @Autowired
    private CestaCompraUseCase cestaCompraUseCase;

    private CestaCompraEntity cestaFromSession(HttpSession session) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        if (cliente == null) {
            logger.warn("No hay cliente en sesión!");
            return null;
        }
        if (cesta == null) {
            logger.warn("Cliente {}: se crea cesta nueva", cliente.getCodigo());
            cesta = new CestaCompraEntity();
            cesta.setCodigoCliente(cliente.getCodigo());
            cesta.setFechaInicio(new Timestamp(System.currentTimeMillis()));
            cesta.setLineas(new ArrayList<>());
            session.setAttribute("cesta", cesta);
        }
        return cesta;
    }

    @GetMapping
    public String mostrarCesta(HttpSession session, Model model) {
        CestaCompraEntity cesta = cestaFromSession(session);
        if (cesta != null) {
            model.addAttribute("cesta", cesta);
            BigDecimal totalGeneral = BigDecimal.ZERO;
            for (int i = 0; i < cesta.getLineas().size(); i++) {
                BigDecimal precio = cesta.getLineas().get(i).getPrecio() != null ? cesta.getLineas().get(i).getPrecio() : BigDecimal.ZERO;
                int cantidad = cesta.getLineas().get(i).getCantidad();
                BigDecimal totalLinea = precio.multiply(new BigDecimal(cantidad));
                totalGeneral = totalGeneral.add(totalLinea);
            }
            model.addAttribute("totalGeneral", totalGeneral);
            return "cesta";
        } else {
            return "redirect:/auth/login";
        }
    }

    @PostMapping
    public String addToCart(@RequestParam("codigoArticulo") String codigoArticulo, HttpSession session) {
        CestaCompraEntity cesta = cestaFromSession(session);
        if (cesta != null) {
            cestaCompraUseCase.add(new AddToCartRequest(codigoArticulo, cesta));
            session.setAttribute("cesta", cesta); // Actualizar cesta en sesión
            return "redirect:/cliente/cesta";
        } else {
            return "redirect:/auth/login";
        }
    }

    @PostMapping("/vaciar")
    public String vaciarCesta(HttpSession session) {
        CestaCompraEntity cesta = (CestaCompraEntity) session.getAttribute("cesta");
        if (cesta != null) {
            cesta.getLineas().clear();
            session.setAttribute("cesta", cesta);
        }
        return "redirect:/cliente/cesta";
    }

}
