package ru.edu.filmportal.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.edu.filmportal.filters.FilterExceptionHandler;
import ru.edu.filmportal.filters.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final FilterExceptionHandler filterExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/docs", "/v3/api-docs", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/user/me").authenticated()
                        .requestMatchers("/api/v1/user/{id}").authenticated()
                        .requestMatchers("/api/v1/user/all").hasRole("ADMIN")
                        .requestMatchers("/api/v1/user/findByNickname/{nickname}").hasRole("ADMIN")
                        .requestMatchers("/api/v1/user/{id}/edit").hasRole("ADMIN")
                        .requestMatchers("/api/v1/user/{id}/delete").hasRole("ADMIN")
                        .requestMatchers("/api/v1/role/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler, JwtAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8222"); // Разрешаем доступ с другого сервера
        configuration.addAllowedMethod("GET"); // Разрешаем только GET-метод
        configuration.addAllowedHeader("*"); // Разрешаем все заголовки
        configuration.setAllowCredentials(true); // Разрешаем отправку cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/v3/api-docs", configuration); // Применяем CORS ко всем путям
        return source;
    }
}
