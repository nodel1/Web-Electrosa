/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import java.util.Locale;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author noelr
 */
@Controller
public class LanguageController {

    private static final Logger LOGGER = Logger.getLogger(LanguageController.class.getName());
    private final List<String> languageCollection = Arrays.asList("es", "en", "fr", "de");

    @GetMapping("/lang")
    public String changeLanguage(@RequestParam("lang") String lang, HttpSession session) {
        LOGGER.info("Procesando cambio de idioma: " + lang);
        LOGGER.info("Entrando en LanguageController");

        if (languageCollection.contains(lang)) {
            Locale locale = new Locale(lang);
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
            LOGGER.info("Locale actualizado a: " + locale);
        } else {
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("es"));
            LOGGER.info("Idioma no válido, se usa 'es' como predeterminado");
        }

        return "redirect:./?lang=" + lang;
    }
}
