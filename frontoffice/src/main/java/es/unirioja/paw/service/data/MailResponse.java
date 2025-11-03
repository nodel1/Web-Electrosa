/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service.data;

/**
 *
 * @author noelr
 */
public class MailResponse {

    private final boolean success;


    public MailResponse(boolean success) {
        this.success = success;
    }


    public boolean isSuccess() {
        return success;
    }


}
