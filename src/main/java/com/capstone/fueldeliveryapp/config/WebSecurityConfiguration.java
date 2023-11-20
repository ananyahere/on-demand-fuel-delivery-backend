package com.capstone.fueldeliveryapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.time.Duration;
import java.util.Arrays;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserDetailsService jwtService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    org.springframework.web.cors.CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("*"));
                    configuration.setExposedHeaders(Arrays.asList("Authorization"));
                    configuration.setAllowCredentials(true);
                    configuration.setMaxAge(Duration.ofHours(24));
                    return configuration;
                }))
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/authenticate", "/register", "/catchphase", "/city").permitAll()    // provide path where we do not want apply authentication
                                .requestMatchers(HttpMethod.OPTIONS).permitAll() // to configure Spring Security to permit all OPTIONS requests.
                                .anyRequest().authenticated()
                )
                .exceptionHandling(
                        e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint) // This specifies an authentication entry point, which can respond with custom messages or actions when an unauthenticated user tries to access a secure resource.
                )
                .sessionManagement(
                        s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        httpSecurity
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
