/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.eggNews.servicios;

import com.noticias.eggNews.entidades.Imagen;
import com.noticias.eggNews.entidades.Periodista;
import com.noticias.eggNews.entidades.Usuario;
import com.noticias.eggNews.enumeraciones.Rol;
import com.noticias.eggNews.exepciones.MiException;
import com.noticias.eggNews.repositorios.ImagenRepositorio;
import com.noticias.eggNews.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nahuel
 */
@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio ur;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombreUsuario, String contrasenia, String contrasenia2) throws MiException {
        validar(nombreUsuario, contrasenia, contrasenia2);
        Usuario us = new Usuario();
        us.setNombreUsuario(nombreUsuario);
        us.setFechaAlta(new Date());
        us.setRol(Rol.USER);
        us.setActivo(true);
        us.setContrasenia(contrasenia);
        us.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
        Imagen imagen = imagenServicio.guardar(archivo);
        us.setImagen(imagen);
        ur.save(us);

    }

    public void actualizar(MultipartFile archivo, Long id, String nombreUsuario, String contrasenia, String contrasenia2) throws MiException {
        validar(nombreUsuario, contrasenia, contrasenia2);
        Optional<Usuario> respuesta = ur.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
            //usuario.setFechaAlta(new Date());
            //usuario.setRol(Rol.USER);
            //usuario.setActivo(true);
            Long idImagen = null;
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setImagen(imagen);
            ur.save(usuario);
        }
    }

    private void validar(String nombreUsuario, String contrasenia, String contrasenia2) throws MiException {

        if (nombreUsuario.isEmpty()) {
            throw new MiException("nombre del usuario no puede ser nulo");
        }
        if (contrasenia.isEmpty()) {
            throw new MiException(" contrasenia no puede ser menor a 8");
        }
        if (!contrasenia.equals(contrasenia2)) {
            throw new MiException(" Las contrasenias deben ser iguales");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario us = ur.buscarPorNombreUsuario(nombreUsuario);

        if (us != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + us.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", us);
            return new User(us.getNombreUsuario(), us.getContrasenia(), permisos);
        } else {
            return null;
        }

    }

    public Usuario getOne(Long id) {
        return ur.getOne(id);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();
        usuarios = ur.findAll(Sort.by("id").descending());
        return usuarios;
    }

    @Transactional
    public void cambiarRol(Long id, String rol) {
        Optional<Usuario> respuesta = ur.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            if (rol.equals("Periodista")) {
                usuario.setRol(Rol.PERIODISTA);
            } else if (rol.equals("Admin")) {
                usuario.setRol(Rol.ADMIN);
            } else {
                usuario.setRol(Rol.USER);
            }
            ur.save(usuario);
        }
    }

    @Transactional
    public void cambiarEstado(Long id, String activo) {
        Optional<Usuario> respuesta = ur.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            if (activo.equals("False")) {
                usuario.setActivo(Boolean.FALSE);
            } else {
                usuario.setActivo(Boolean.TRUE);
            }
            ur.save(usuario);
        }
    }
}
