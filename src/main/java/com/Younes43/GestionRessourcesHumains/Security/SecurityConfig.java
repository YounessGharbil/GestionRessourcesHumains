package com.Younes43.GestionRessourcesHumains.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final  AuthenticationProvider authenticationProvider;

    private final  JWTAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf().disable()
                .authorizeHttpRequests()
                        .requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/demandeDeSanction/**").permitAll()
                        //.hasAnyAuthority("ROLE_RH_PLUS1","ROLE_RH","ROLE_MANAGER","ROLE_SUPERVISEUR","ROLE_TEAM_LEADER")
                        .requestMatchers("/user/**")
                        .hasAnyAuthority("ROLE_RH_PLUS1","ROLE_RH")
                        .requestMatchers("/salarie/**")
                        .hasAnyAuthority("ROLE_RH_PLUS1","ROLE_RH")

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
