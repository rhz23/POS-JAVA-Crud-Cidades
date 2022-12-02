package com.rhzaninelli.crudcidades;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public PasswordEncoder cifrador(){
//        return new BCryptPasswordEncoder();
//    }

    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/").hasAnyAuthority("listar", "admin")
                .antMatchers("/").authenticated()
                .antMatchers("/criar").hasAuthority("admin")
                .antMatchers("/excluir").hasAuthority("admin")
                .antMatchers("/preparaAlterar").hasAuthority("admin")
                .antMatchers("/alterar").hasAuthority("admin")
                .antMatchers("/mostrar").authenticated()
                .anyRequest().denyAll()
                .and()
//                .formLogin()
//                .loginPage("/login.html").permitAll()
//                .and()
//                .logout().permitAll();
                .oauth2Login().userInfoEndpoint().userAuthoritiesMapper(userAuthoritiesMapper());
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void printSenhas() {
//        System.out.println(this.cifrador().encode("teste123"));
//    }
//
//    @EventListener(InteractiveAuthenticationSuccessEvent.class)
//    public void printUsuarioAtual(InteractiveAuthenticationSuccessEvent event){
//
//        var usuario = event.getAuthentication().getName();
//
//    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> Set.of(new SimpleGrantedAuthority("admin"));
    }
}
