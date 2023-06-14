/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.eggNews;

import com.noticias.eggNews.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Nahuel
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {
    @Autowired
    public UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
       http
               .authorizeRequests()
                    .antMatchers("/addmin/*").hasRole("ADMIN")
                    .antMatchers("/css/*","/js/*","/img/*","/**")
                    .permitAll()
               .and().formLogin()
                       .loginPage("/login")
                       .loginProcessingUrl("/logincheck")//debe coincidir con el action del formulario el logueo
                       .usernameParameter("nombreUsuario")
                       .passwordParameter("contrasenia")
                       .defaultSuccessUrl("/inicio")
                       .permitAll()
               .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
               .and().csrf()
                       .disable();
    }
    
}
