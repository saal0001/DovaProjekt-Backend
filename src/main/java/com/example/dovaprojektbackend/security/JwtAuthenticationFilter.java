package com.example.dovaprojektbackend.security;

import com.example.dovaprojektbackend.model.Bikeshop;
import com.example.dovaprojektbackend.model.Customer;
import com.example.dovaprojektbackend.model.enums.Role;
import com.example.dovaprojektbackend.repository.BikeshopRepository;
import com.example.dovaprojektbackend.repository.CustomerRepository;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerRepository  customerRepository;
    private final BikeshopRepository bikeshopRepository;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,CustomerRepository  customerRepository, BikeshopRepository bikeshopRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerRepository = customerRepository;
        this.bikeshopRepository = bikeshopRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1️⃣ Ingen Authorization-header → Fortsæt uden authentication
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // 2️⃣ Valider JWT token
            if (!jwtTokenProvider.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 3️⃣ Udtræk bruger info fra token
            UUID userId = jwtTokenProvider.getSupabaseUserId(token);
            String email = jwtTokenProvider.getEmailFromToken(token);

            if (userId == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 4️⃣ Hent rolle fra database (ROBUST & PÅLIDELIG)
            Role role = getUserRoleFromDatabase(userId);

            // 5️⃣ Hvis bruger ikke findes, behandl som customer (Google OAuth første login)
            if (role == null) {
                role = Role.customer;
            }

            // 6️⃣ Opret Spring Security authority
            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_" + role.name().toUpperCase());

            // 7️⃣ Opret custom principal
            CustomUserPrincipal principal =
                    new CustomUserPrincipal(userId, email, role);

            // 8️⃣ Opret authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            List.of(authority)
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // 9️⃣ Sæt authentication i SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // Log fejl men lad ikke hele requesten fejle
            logger.error("Could not set user authentication in security context", e);
            SecurityContextHolder.clearContext();
        }

        // 🔟 Fortsæt filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Henter brugerens rolle fra databasen.
     * Tjekker først users tabel, derefter bikeshop tabel.
     *
     * @param userId Brugerens UUID
     * @return Brugerens rolle eller null hvis ikke fundet
     */
    private Role getUserRoleFromDatabase(UUID userId) {
        // Tjek først i users tabel (customers og admins)
        Optional<Customer> customer = customerRepository.findById(userId);
        if (customer.isPresent()) {
            return customer.get().getRole();
        }

        // Tjek derefter i bikeshop tabel
        Optional<Bikeshop> shop = bikeshopRepository.findById(userId);
        if (shop.isPresent()) {
            return Role.shop;
        }

        // Bruger ikke fundet i nogen tabel
        return null;
    }
}