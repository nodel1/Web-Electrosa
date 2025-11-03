/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.unirioja.paw.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author nodel
 */
@Controller
@RequestMapping("/about") 
public class AboutController {

    @GetMapping("/team") 
    public String team(Model model) {
        model.addAttribute("pageTitle", "Nuestro equipo");
        return "team";
    }
}
