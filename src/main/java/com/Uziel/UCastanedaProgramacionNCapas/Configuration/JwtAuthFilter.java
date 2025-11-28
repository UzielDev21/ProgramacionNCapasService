package com.Uziel.UCastanedaProgramacionNCapas.Configuration;

import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthFilter extends OncePerRequestFilter{
    
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService){
     this.jwtService = jwtService;
     this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = authHeader.substring(7);
        
        if (!jwtService.isTokenValid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Invalido");
            return;
        }
        
        Claims claims = jwtService.getAllClaims(token);
        String username = claims.getSubject();
        int idUsuario = (int) claims.get("idUsuario");
        
        String rol = (String) claims.get("rol");
        String authority = "ROLE_" + rol.toUpperCase();
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, 
                null, 
                List.of( new SimpleGrantedAuthority(authority)));
        
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
    
    

}
