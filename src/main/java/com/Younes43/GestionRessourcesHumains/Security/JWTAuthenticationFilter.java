package com.Younes43.GestionRessourcesHumains.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final  JWTService jwtservice;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {

        final String token;
        final String header;
        final String appUsername;
        header =request.getHeader("Authorization");
        if(header ==null || !header.startsWith("Bearer")  ) {
            filterChain.doFilter(request, response);

            return;
        }

        token=header.substring(7);
        appUsername=jwtservice.extractAppUsername(token);

        if(appUsername!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails=userDetailsService.loadUserByUsername(appUsername);
            if(jwtservice.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken( userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request, response);




    }
}
