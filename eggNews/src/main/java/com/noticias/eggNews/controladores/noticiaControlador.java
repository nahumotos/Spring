/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.eggNews.controladores;

import com.noticias.eggNews.servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nahuel
 */
@Controller
@RequestMapping("/noticia")//localhost:8080
public class noticiaControlador {

     @Autowired
    private NoticiaServicio noticiaServicio;
     
    @GetMapping("/vistaNoticia/{id}")
    public String vista(@PathVariable Long id, ModelMap modelo ){
        modelo.put("noticia", noticiaServicio.getOne(id)); 
        return "vistaNoticia.html";
        
    }
    
    
}
