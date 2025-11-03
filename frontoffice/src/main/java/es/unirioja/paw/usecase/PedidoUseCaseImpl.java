/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.usecase;

import es.unirioja.paw.jpa.PedidoEntity;
import es.unirioja.paw.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author noelr
 */
@Service
public class PedidoUseCaseImpl implements PedidoUseCase {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public void crearPedido(PedidoEntity pedido) {
        if (pedido != null && pedido.getLineas() != null) {
            // Guardar el pedido y sus líneas asociadas
            pedidoRepository.save(pedido);
        } else {
            throw new IllegalArgumentException("El pedido o sus líneas no pueden ser nulos.");
        }
    }
}