/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.eggNews.entidades;

import java.util.ArrayList;
import javax.persistence.Entity;


/**
 *
 * @author Nahuel
 */
@Entity
public class Periodista extends Usuario{
    private ArrayList<Noticia> misNoticias;
    private Integer sueldoMensual;

    public Periodista() {
    }

    public Periodista(ArrayList<Noticia> misNoticias, Integer sueldoMensual) {
        this.misNoticias = misNoticias;
        this.sueldoMensual = sueldoMensual;
    }

    public ArrayList<Noticia> getMisNoticias() {
        return misNoticias;
    }

    public void setMisNoticias(ArrayList<Noticia> misNoticias) {
        this.misNoticias = misNoticias;
    }

    public Integer getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(Integer sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }
    

}
