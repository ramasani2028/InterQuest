package com.interquest.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // ⬅️ REQUIRED IMPORT
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // ⬅️ REQUIRED DEPENDENCY

    // The IDE was confused because you had two definitions of doFilterInternal.
    // This is the correct, official method signature inherited from OncePerRequestFilter.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Check if token exists in the 'Authorization: Bearer <token>' header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract the JWT token (skip "Bearer ")
        jwt = authHeader.substring(7);

        try {
            // 3. Extract the username (email) from the token payload
            userEmail = jwtService.extractUsername(jwt);

            // 4. Check if the user is not already authenticated
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load UserDetails (including roles/authorities) from the database
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // 5. Validate the token against the loaded user details
                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {

                    // Create an authentication token to be placed in the security context
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // Credentials are null since the token authenticates
                            userDetails.getAuthorities()
                    );

                    // Add details about the request (e.g., source IP)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the user as authenticated for the duration of this request
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // If token validation fails (expired, corrupted signature, etc.)
            // You can optionally log the error here.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token.");
            return;
        }

        // 6. Pass the request down the filter chain (to the controller)
        filterChain.doFilter(request, response);
    }
}