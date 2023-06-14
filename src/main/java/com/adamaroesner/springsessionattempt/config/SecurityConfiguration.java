package com.adamaroesner.springsessionattempt.config;

import com.adamaroesner.springsessionattempt.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

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
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .formLogin(form -> {
                    form.loginPage("/login.html");
                    form.loginProcessingUrl("/login");
                    form.defaultSuccessUrl("/secured/hello");
                })
//                .logout(logout -> logout.permitAll())
                .build();
    }
}
