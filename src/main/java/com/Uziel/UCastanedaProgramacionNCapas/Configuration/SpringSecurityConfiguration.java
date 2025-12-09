package com.Uziel.UCastanedaProgramacionNCapas.Configuration;

import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtService;
import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtTokenUsoService;
import com.Uziel.UCastanedaProgramacionNCapas.Service.TokenListaNegraService;
import com.Uziel.UCastanedaProgramacionNCapas.Service.UserDetailsJPAService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    
    private final UserDetailsJPAService userDetailsJPAService;
    private final JwtService jwtService;
    private final TokenListaNegraService tokenListaNegraService;
    private final JwtTokenUsoService jwtTokenUsoService;
    
    public SpringSecurityConfiguration(UserDetailsJPAService userDetailsJPAService,
            JwtService jwtService,
            TokenListaNegraService tokenListaNegraService,
            JwtTokenUsoService jwtTokenUsoService) {
        this.userDetailsJPAService = userDetailsJPAService;
        this.jwtService = jwtService;
        this.tokenListaNegraService = tokenListaNegraService;
        this.jwtTokenUsoService = jwtTokenUsoService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsJPAService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
    
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService, userDetailsJPAService, tokenListaNegraService, jwtTokenUsoService);
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:8081"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        corsConfiguration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsSource;
    }
    
}
