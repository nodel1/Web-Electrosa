/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service;

import es.unirioja.paw.service.data.MailConfig;
import es.unirioja.paw.service.data.MailRequest;
import es.unirioja.paw.service.data.MailResponse;

/**
 *
 * @author noelr
 */
public interface Mailer {
    /**
     * Envía un correo electrónico basado en la solicitud proporcionada y la configuración.
     * @param config Configuración del servidor de correo.
     * @param request Datos para enviar el correo.
     * @return Resultado del envío.
     */
    MailResponse send(MailRequest request);
}
