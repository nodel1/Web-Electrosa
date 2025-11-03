/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.usecase;

import es.unirioja.paw.jpa.ClienteEntity;
import java.util.List;

/**
 *
 * @author noelr
 */
public class RegistroClienteResponse {

    private boolean success;
    private ClienteEntity cliente;
    private List<String> errorMessage;

    public RegistroClienteResponse(boolean success, ClienteEntity cliente, List<String> errorMessage) {
        this.success = success;
        this.cliente = cliente;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public String getMessage() {
        if (success) {
            return "Registro completado con éxito.";
        } else {
            if (errorMessage == null || errorMessage.isEmpty()) {
                return "Error desconocido en el registro.";
            } else {
                return String.join(", ", errorMessage);
            }
        }
    }
}
