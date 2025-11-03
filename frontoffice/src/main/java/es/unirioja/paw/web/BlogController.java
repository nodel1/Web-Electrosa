/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 *
 * @author nodel
 */
@Controller
public class BlogController {

    @GetMapping("/blog/{slug}")
    public String showBlogPost(@PathVariable String slug, Model model) {

        String title, content;
        switch (slug) {
            case "paw-2023":
                title = "PAW 2025";
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
                break;
            case "tendencias-decorativas":
                title = "Tendencias Decorativas";
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";
                break;
            case "consejos-renovar-casa":
                title = "Consejos para Renovar tu Casa";
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
                break;
            case "semana-verde":
                title = "Semana Verde";
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
                break;
            default:
                title = "Entrada no encontrada";
                content = "Lo sentimos, esta entrada no existe. Por favor, selecciona otra.";
                break;
        }


        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("slug", slug);


        return "blog-post"; 
    }
}
