/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.eggNews.servicios;

import com.noticias.eggNews.entidades.Noticia;
import com.noticias.eggNews.entidades.Usuario;
import com.noticias.eggNews.exepciones.MiException;
import com.noticias.eggNews.repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo,Usuario usuario ) throws MiException {
        
            validar(titulo, cuerpo);
            Noticia noticia = new Noticia();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setCreador(usuario);
            noticiaRepositorio.save(noticia);

    }

    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList();
        noticias = noticiaRepositorio.findAll(Sort.by("id").descending());
        return noticias;
    }

    @Transactional
    public void modificarNoticia(Long id, String titulo, String cuerpo) throws MiException {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        validar(titulo, cuerpo);
        validar(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);

            noticiaRepositorio.save(noticia);
        }

    }

    public Noticia getOne(Long id) {
        return noticiaRepositorio.getOne(id);
    }

    @Transactional
    public void eliminar(Long id) throws MiException {

        Noticia noticia = noticiaRepositorio.getById(id);

        noticiaRepositorio.delete(noticia);

    }

    private void validar(String titulo, String cuerpo) throws MiException {

        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("titulo no puede ser nulo");
        }
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException(" cuerpono puede ser nulo");
        }

    }

    private void validar(Long id) throws MiException {
        if (id == null) {
            throw new MiException("id no puede ser nulo");
        }
    }

}
