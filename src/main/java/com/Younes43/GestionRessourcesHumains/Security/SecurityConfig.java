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
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@CrossOrigin(origins =" http://localhost:4200")
public class SecurityConfig {

    private final  AuthenticationProvider authenticationProvider;

    private final  JWTAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf().disable().cors().disable()
                .authorizeHttpRequests().anyRequest().permitAll()
                        // .requestMatchers("/auth/**")
                        //     .permitAll()
                        // .requestMatchers("/demandeDeSanction/rapportTeamLeader/**")
                        //     .hasAuthority("ROLE_TEAM_LEADER")
                        // .requestMatchers("/demandeDeSanction/rapportSuperviseur/**")
                        //     .hasAuthority("ROLE_SUPERVISEUR")
                        // .requestMatchers("/demandeDeSanction/rapportManager/**")
                        //     .hasAuthority("ROLE_MANAGER")
                        // .requestMatchers("/demandeDeSanction/rapportRh/**")
                        //     .hasAuthority("ROLE_RH")
                        // .requestMatchers("/demandeDeSanction/rapportRhPlus1/**")
                        //     .hasAuthority("ROLE_RH_PLUS1")
                        // .requestMatchers("/demandeDeSanction/demandePdf/**")
                        //     .hasAnyAuthority("ROLE_RH_PLUS1","ROLE_RH")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
