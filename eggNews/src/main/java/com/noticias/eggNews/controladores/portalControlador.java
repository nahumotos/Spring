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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")//localhost:8080
public class portalControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreUsuario, @RequestParam String contrasenia, @RequestParam String contrasenia2,MultipartFile archivo, ModelMap modelo) {
        try {
            usuarioServicio.registrar(archivo,nombreUsuario, contrasenia, contrasenia2);
            modelo.put("exito", "usuario agregado correctamente");
            List<Noticia> noticias = noticiaServicio.listarNoticias();
            modelo.addAttribute("noticias", noticias);
            return "inicio.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombreUsuario", nombreUsuario);
            return "registro.hmtl";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error!=null) {
            modelo.put("error","Usuario o Contraseña invalidos...");
        }
        return "login.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PERIODISTA','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/addmin/dashboard";
        }
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";

    }
    
    @GetMapping("/perfil/{id}")
    public String modificarUsuario(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo,@PathVariable Long id,@RequestParam String nombreUsuario,@RequestParam String contrasenia,@RequestParam String contrasenia2,ModelMap modelo) {
        try {
           usuarioServicio.actualizar(archivo, id, nombreUsuario, contrasenia, contrasenia2);
           modelo.put("exito","Usuario actualizado correctamente");
           return "inicio.html";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombreUsuario);
            return "usuario_modificar.html";        
        }
    }
}
