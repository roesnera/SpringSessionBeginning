package com.adamaroesner.springsessionattempt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.GET,"/*").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/register", "/register/new").permitAll();
                    authorize.requestMatchers("/secured/**").authenticated();
                })
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
                .formLogin(form -> {
                    form.loginPage("/login.html");
//                    form.loginProcessingUrl("/login/process");
                    form.defaultSuccessUrl("/secured/hello");
                })
//                .logout(logout -> logout.permitAll())
                .build();
    }
}
