package com.Uziel.UCastanedaProgramacionNCapas.Configuration;

import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtService;
import com.Uziel.UCastanedaProgramacionNCapas.Service.TokenListaNegraService;
import com.Uziel.UCastanedaProgramacionNCapas.Service.UserDetailsJPAService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    
    private final UserDetailsJPAService userDetailsJPAService;
    private final JwtService jwtService;
    private final TokenListaNegraService tokenListaNegraService;
    
    public SpringSecurityConfiguration(UserDetailsJPAService userDetailsJPAService, 
            JwtService jwtService,
            TokenListaNegraService tokenListaNegraService){
        this.userDetailsJPAService = userDetailsJPAService;
        this.jwtService = jwtService;
        this.tokenListaNegraService = tokenListaNegraService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf( csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/Login").permitAll()
                        .requestMatchers("/api/Logout").authenticated()
                        .requestMatchers("/UsuarioIndex/**")
                        .hasAnyRole("Administrador", "Gerente", "Lider", "Colaborador", "Tercero")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsJPAService)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsJPAService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
    
    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter(jwtService, userDetailsJPAService, tokenListaNegraService);
    }
    
    
}
