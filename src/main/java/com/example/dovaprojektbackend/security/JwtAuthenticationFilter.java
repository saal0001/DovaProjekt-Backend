package com.example.dovaprojektbackend.security;

import com.example.dovaprojektbackend.model.enums.Role;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import com.example.dovaprojektbackend.repository.UserRepository;
import com.example.dovaprojektbackend.service.DevJwtTokenProvider;
import com.example.dovaprojektbackend.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final DevJwtTokenProvider devJwtTokenProvider;

    public JwtAuthenticationFilter(
            @Autowired(required = false) JwtTokenProvider jwtTokenProvider,
            @Autowired(required = false) DevJwtTokenProvider devJwtTokenProvider,
            UserRepository userRepository,
            BikeshopRepository bikeshopRepository) {

        this.jwtTokenProvider = jwtTokenProvider;
        this.devJwtTokenProvider = devJwtTokenProvider;
        this.userRepository = userRepository;
        this.bikeshopRepository = bikeshopRepository;

        // Validation: at least one provider must be available
        if (jwtTokenProvider == null && devJwtTokenProvider == null) {
            throw new IllegalStateException(
                    "At least one JWT token provider must be available (JwtTokenProvider or DevJwtTokenProvider)"
            );
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Hent JWT token fra Authorization header
            String token = getJwtFromRequest(request);

            UUID userId = null;
            String email = null;
            String role = null;

            // Valider token og hent user data
            if (StringUtils.hasText(token)) {
                boolean isTokenValid = false;
                if (jwtTokenProvider != null){
                    isTokenValid = jwtTokenProvider.validateToken(token);
                } else if (devJwtTokenProvider != null) {
                    isTokenValid = devJwtTokenProvider.validateToken(token);
                }

               if (isTokenValid){
                   if (jwtTokenProvider != null){
                       userId = jwtTokenProvider.getSupabaseUserId(token);
                       email = jwtTokenProvider.getEmailFromToken(token);

                       // Hent rolle fra DATABASE i stedet for JWT token
                       role = determineUserRole(userId);
                   } else if (devJwtTokenProvider != null) {
                       userId = devJwtTokenProvider.getSupabaseUserId(token);
                       email = devJwtTokenProvider.getEmailFromToken(token);
                   }


                   if (role != null) {
                       // Opret authority baseret på rolle fra database
                       SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());

                       CustomUserPrincipal principal =
                               new CustomUserPrincipal(
                                       userId,
                                       email,
                                       Role.valueOf(role)
                               );

                       // Opret authentication object med user ID og email
                       UsernamePasswordAuthenticationToken authentication =
                               new UsernamePasswordAuthenticationToken(principal, null, List.of(authority));

                       authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                       // Sæt authentication i SecurityContext
                       SecurityContextHolder.getContext().setAuthentication(authentication);
                   }
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