package com.online.shop.ecombackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfiguration {

    private  JWTFilter jwtFilter;
    public SecurityConfiguration(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Filter chain to configure security.
     * @param http The security object.
     * @return The chain built.
     * @throws Exception Thrown on error configuring.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //TODO: Proper authentication.
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests(request ->
                        request.requestMatchers("/product", "/auth/register", "/auth/login", "/auth/verify",
                                        "/auth/forgot", "/auth/reset"
                                        , "/error"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated());

        return http.build();


    }

}
