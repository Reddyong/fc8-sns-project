package com.fc8.snsproject.config;

import com.fc8.snsproject.config.filter.JwtTokenFilter;
import com.fc8.snsproject.domain.user.service.UserService;
import com.fc8.snsproject.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    private final UserService userService;

    @Value("${jwt.secret.key}")
    private String key;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        http.authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers(
                                    "/api/*/users/join",
                                    "/api/*/users/login"
                            ).permitAll()
                            .requestMatchers("/api/**").authenticated()
                            .anyRequest().permitAll();
                })
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
