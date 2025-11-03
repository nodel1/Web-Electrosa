/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service;

import es.unirioja.paw.service.data.MailConfig;
import es.unirioja.paw.service.data.MailRequest;
import es.unirioja.paw.service.data.MailResponse;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author noelr
 */
@Service
public class MailerImpl implements Mailer {
    

    private static final Logger logger = LoggerFactory.getLogger(MailerImpl.class);

    
    //private final MailConfig mailConfig;
    
    //public MailerImpl(MailConfig mailConfig) {
    //    this.mailConfig = mailConfig;
    //}
    
    
      /*  
    @Override
    public MailResponse send(MailRequest request) {
        if (!request.isComplete()) {
            logger.warn("MailRequest incompleto: no se enviará el correo");
            return new MailResponse(false);
        }

        Properties properties = mailConfig.getProperties();
        String username = mailConfig.getUsername();
        String password = mailConfig.getPassword();
        boolean success = true;

        Session session = Session.getInstance(properties);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(request.getRecipient()));
            message.setSubject(request.getSubject());
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(request.getContent(), request.getContentType());
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message, username, password);
            logger.info("Correo enviado correctamente a {}", request.getRecipient());
        } catch (Exception ex) {
            logger.error("Error building or sending message to {}", request.getRecipient(), ex);
            success = false;
        }
        return new MailResponse(success);
    }*/

    @Override
    public MailResponse send(MailRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}