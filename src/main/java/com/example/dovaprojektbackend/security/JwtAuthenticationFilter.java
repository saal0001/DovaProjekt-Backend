package com.example.dovaprojektbackend.security;

import com.example.dovaprojektbackend.repository.BikeshopRepository;
import com.example.dovaprojektbackend.repository.UserRepository;
import com.example.dovaprojektbackend.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final BikeshopRepository bikeshopRepository;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, BikeshopRepository bikeshopRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.bikeshopRepository = bikeshopRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Hent JWT token fra Authorization header
            String token = getJwtFromRequest(request);

            // Valider token og hent user data
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                UUID userId = jwtTokenProvider.getSupabaseUserId(token);
                String email = jwtTokenProvider.getEmailFromToken(token);

                // Hent rolle fra DATABASE i stedet for JWT token
                String role = determineUserRole(userId);

                if (role != null) {
                    // Opret authority baseret på rolle fra database
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());

                    // Opret authentication object med user ID og email
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, List.of(authority));

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Sæt authentication i SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Finder brugerens rolle fra database
     * Tjekker først i users tabel, derefter i bikeshop tabel
     * @param userId Brugerens UUID
     * @return Rolle som String (CUSTOMER, ADMIN, eller SHOP), eller null hvis ikke fundet
     */
    private String determineUserRole(UUID userId) {
        // Tjek i users tabel først
        return userRepository.findById(userId)
                .map(user -> user.getRole())
                .map(role -> role.name())
                .orElseGet(() -> {
                    // Hvis ikke i users, tjek om det er en bikeshop
                    return bikeshopRepository.existsById(userId) ? "SHOP" : null;
                });
    }

    /**
     * Henter JWT token fra Authorization header
     * @param request HTTP request
     * @return JWT token uden "Bearer " prefix
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}