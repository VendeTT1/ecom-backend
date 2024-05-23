package com.online.shop.ecombackend.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.online.shop.ecombackend.dao.UserRepository;
import com.online.shop.ecombackend.model.User;
import com.online.shop.ecombackend.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {
    /** The JWT Service. */
    private JWTService jwtService;
    /** The Local User DAO. */
    private UserRepository userRepository;

    /**
     * Constructor for spring injection.
     * @param jwtService
     * @param userRepository
     */
    public JWTFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<User> opUser = userRepository.findByUsernameIgnoreCase(username);
                if (opUser.isPresent()) {
                    User user = opUser.get();
                    if(user.isEmailVerfified()) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (JWTDecodeException ex) {
            }
        }
        filterChain.doFilter(request, response);
    }

  
}
