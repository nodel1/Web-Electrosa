/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.usecase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author noelr
 */


public class RegistroClienteValidationResponse {
    private boolean success = true;
    private List<String> messageCollection = new ArrayList<>();

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public List<String> getMessageCollection() { return messageCollection; }
}
