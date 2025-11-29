package com.Uziel.UCastanedaProgramacionNCapas.Configuration;

import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtService;
import com.Uziel.UCastanedaProgramacionNCapas.Service.TokenListaNegraService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenListaNegraService tokenListaNegraService;

    public JwtAuthFilter(JwtService jwtService, 
            UserDetailsService userDetailsService, 
            TokenListaNegraService tokenListaNegraService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenListaNegraService = tokenListaNegraService;
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
        
        if (tokenListaNegraService.isBlackListed(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inhabilitado por logout");
            System.out.println("Token en la lista negra: " + token);
            return;
        }

        if (!jwtService.isTokenValid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Invalido");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            Claims claims = jwtService.getAllClaims(token);
            String username = claims.getSubject();
            int idUsuario = (int) claims.get("idUsuario");

            String rol = (String) claims.get("rol");

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (!userDetails.isEnabled()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario deshabilitado");
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        filterChain.doFilter(request, response);
    }
}
