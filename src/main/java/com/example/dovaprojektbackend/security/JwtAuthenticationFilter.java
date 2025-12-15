package com.example.dovaprojektbackend.security;

import com.example.dovaprojektbackend.model.enums.Role;
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

        System.out.println("\n========== JWT FILTER START ==========");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request Method: " + request.getMethod());

        try {
            String token = getJwtFromRequest(request);

            System.out.println("Token present: " + (token != null));
            if (token != null) {
                System.out.println("Token (first 20 chars): " + token.substring(0, Math.min(20, token.length())) + "...");
            }

            if (StringUtils.hasText(token)) {
                System.out.println("Validating token...");
                boolean isValid = jwtTokenProvider.validateToken(token);
                System.out.println("Token valid: " + isValid);

                if (isValid) {
                    UUID userId = jwtTokenProvider.getSupabaseUserId(token);
                    String email = jwtTokenProvider.getEmailFromToken(token);

                    System.out.println("User ID from token: " + userId);
                    System.out.println("Email from token: " + email);

                    String roleString = determineUserRole(userId);
                    System.out.println("Role from database: " + roleString);

                    if (roleString != null) {
                        Role role = Role.valueOf(roleString);

                        CustomUserPrincipal principal = new CustomUserPrincipal(userId, email, role);

                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleString.toUpperCase());
                        System.out.println("Authority created: " + authority.getAuthority());

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(principal, null, List.of(authority));

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("✅ Authentication set successfully!");
                        System.out.println("Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                        System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                    } else {
                        System.out.println("❌ No role found for user in database!");
                    }
                } else {
                    System.out.println("❌ Token validation failed!");
                }
            } else {
                System.out.println("❌ No token provided in request");
            }
        } catch (Exception ex) {
            System.out.println("❌ EXCEPTION in JWT filter: " + ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("========== JWT FILTER END ==========\n");
        filterChain.doFilter(request, response);
    }

    private String determineUserRole(UUID userId) {
        return userRepository.findById(userId)
                .map(user -> user.getRole())
                .map(role -> role.name())
                .orElseGet(() -> {
                    return bikeshopRepository.existsById(userId) ? "shop" : null;
                });
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("Authorization header: " + bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}