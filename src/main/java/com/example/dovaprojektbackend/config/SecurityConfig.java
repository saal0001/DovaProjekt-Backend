package com.example.dovaprojektbackend.config;

import com.example.dovaprojektbackend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Aktiver @PreAuthorize annotations
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deaktiver CSRF (vi bruger stateless JWT)
                .csrf(csrf -> csrf.disable())

                // Aktiver CORS
                .cors(cors -> cors.configure(http))

                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // Konfigurer endpoint beskyttelse
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (ingen authentication krævet)
                        .requestMatchers("/api/public/**", "/api/health/**","/h2-console/**","/shops/**","/ydelser/**","/api/**").permitAll()

                        // Alle andre endpoints kræver authentication
                        .anyRequest().authenticated()
                )

                // Stateless session (JWT baseret)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Tilføj JWT authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
