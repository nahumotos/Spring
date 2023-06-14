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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")//localhost:8080
public class panelAdminControlador {
     
     @Autowired
    private NoticiaServicio noticiaServicio;
     
    @GetMapping("/form")//localhost:8080/admin/form
    public String admin(){
        return "panelAdmin.html";
    }
  
    @PostMapping("/cargarNoticia")//localhost:8080/admin/cargarNoticia
    public String upNews(@RequestParam String titulo, @RequestParam String cuerpo,HttpSession session,ModelMap modelo){
        
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            noticiaServicio.crearNoticia(titulo, cuerpo, usuario);
            modelo.put("exito", "cargado correctamente");
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "panelAdmin.html";
        }
        return "panelAdmin.html";
    }
    
    @GetMapping("/lista")
    public String listanoticia(ModelMap modelo){
        List <Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias",noticias );
        return "lista.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo ){
        modelo.put("noticia", noticiaServicio.getOne(id)); 
        return "modificar_noticia.html";
        
    }
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id,String titulo,String cuerpo, ModelMap modelo ) {
         try {
             noticiaServicio.modificarNoticia(id, titulo, cuerpo);
         } catch (MiException ex) {
             Logger.getLogger(panelAdminControlador.class.getName()).log(Level.SEVERE, null, ex);
         }
        return "redirect:../lista";
    }   
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, ModelMap modelo) throws MiException{
        noticiaServicio.eliminar(id);
        return "redirect:../lista";
    }
}
