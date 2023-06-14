/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.eggNews.controladores;

import com.noticias.eggNews.entidades.Noticia;
import com.noticias.eggNews.entidades.Usuario;
import com.noticias.eggNews.exepciones.MiException;
import com.noticias.eggNews.servicios.NoticiaServicio;
import com.noticias.eggNews.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nahuel
 */
@Controller
@RequestMapping("/addmin")
public class AdminControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "panel.html";
    }
    
    @GetMapping("/listaUsuarios")
    public String listaUsuarios(ModelMap modelo){
        List <Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios",usuarios );
        return "listaUsuarios.html";
    }
    
   @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable Long id, ModelMap modelo) {
       
        modelo.put("usuario", usuarioServicio.getOne(id));
        return "redirect:/addmin/listaUsuarios";
    }

    @PostMapping("/modificarRol/{id}")
    public String rolCambiado(@PathVariable Long id, String rol) {
        
        usuarioServicio.cambiarRol(id, rol);
        
        return "redirect:/addmin/listaUsuarios";
    }

    @PostMapping("/modificarEstado/{id}")
    public String cambiarEstado(@PathVariable Long id, String activo){
        usuarioServicio.cambiarEstado(id,activo);
        
       return "redirect:/addmin/listaUsuarios";
    }
    


}
