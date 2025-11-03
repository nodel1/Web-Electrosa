/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.service.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author noelr
 */
public class MailRequest {

    private static final Logger logger = LoggerFactory.getLogger(MailRequest.class); // Logger estático


    public final String recipient;


    public final String subject;

    public final String content;


    public final String contentType = "text/html; charset=utf-8";


    public MailRequest(String recipient, String subject, String content) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }


    public boolean isComplete() {
        if (recipient == null || recipient.isEmpty()) {
            logger.warn("Campo 'recipient' null o vacío: {}", recipient);
            return false;
        }
        if (subject == null || subject.isEmpty()) {
            logger.warn("Campo 'subject' null o vacío: {}", subject);
            return false;
        }
        if (content == null || content.isEmpty()) {
            logger.warn("Campo 'content' null o vacío: {}", content);
            return false;
        }
        return true;
    }


    public String getRecipient() { return recipient; }
    public String getSubject() { return subject; }
    public String getContent() { return content; }
    public String getContentType() { return contentType; }
}
