/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.eggNews.repositorios;

import com.noticias.eggNews.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nahuel
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, Long> {
    
}
