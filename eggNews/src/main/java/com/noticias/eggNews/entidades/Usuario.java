/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.eggNews.entidades;

import com.noticias.eggNews.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;


/**
 *
 * @author Nahuel
 */
@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String nombreUsuario;
    private String contrasenia;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private Boolean activo;
    @OneToOne
    private Imagen imagen;
    public Usuario() {
    }

    public Usuario(Long id, String nombreUsuario, String contrasenia, Date fechaAlta, Rol rol, Boolean activo, Imagen imagen) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.fechaAlta = fechaAlta;
        this.rol = rol;
        this.activo = activo;
        this.imagen = imagen;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
    
    
    
}
