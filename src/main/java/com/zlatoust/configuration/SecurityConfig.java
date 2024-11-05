package com.zlatoust.configuration;


import com.zlatoust.models.PersonRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.zlatoust.exceptions.handler.UnauthorizedHandler;
import com.zlatoust.security.ClientDetailsService;
import com.zlatoust.security.JwtAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final ClientDetailsService clientDetailsService;
    private final UnauthorizedHandler unauthorizedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors
                        .configurationSource(apiConfigurationSource()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .securityMatcher(ApiConstants.API_PATH + "/**", "/actuator/**")
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(ApiConstants.API_PATH + "/auth/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers(HttpMethod.GET, ApiConstants.API_PATH + "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, ApiConstants.API_PATH + "/**").hasAuthority(PersonRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.DELETE, ApiConstants.API_PATH + "/**").hasAuthority(PersonRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.PATCH, ApiConstants.API_PATH + "/**").hasAuthority(PersonRole.ADMIN.toString())
                        .requestMatchers(HttpMethod.PUT, ApiConstants.API_PATH + "/**").hasAuthority(PersonRole.ADMIN.toString())
                        .requestMatchers(ApiConstants.API_PATH+"/admin/**").hasAuthority(PersonRole.ADMIN.toString())
                        .requestMatchers("/actuator/**").hasAuthority(PersonRole.ADMIN.toString())
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(clientDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    public CorsConfigurationSource apiConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Collections.singletonList(CLIENT_URL));
        config.setAllowedHeaders(Collections.singletonList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("PATCH", "GET", "POST", "DELETE", "PUT"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("PATCH","GET","POST","DELETE","PUT"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(new CorsFilter(source));
        filterBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterBean;
    }

}
