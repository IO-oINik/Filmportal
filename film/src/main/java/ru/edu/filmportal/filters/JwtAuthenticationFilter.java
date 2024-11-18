package ru.edu.filmportal.filters;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.edu.filmportal.exceptions.InvalidTokenException;
import ru.edu.filmportal.utils.JwtUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        Optional<String> tokenOptional = jwtUtils.extractJwt(authHeader);

        if (tokenOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = tokenOptional.get();
        String username;
        try {
            username = jwtUtils.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token is expired");
        } catch (SignatureException e) {
            throw new InvalidTokenException("Token signature is invalid");
        } catch (JwtException e) {
            throw new InvalidTokenException("Token is invalid");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<GrantedAuthority> authorities =
                    Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_"
                                    + jwtUtils.extractClaim(jwt, claims -> claims.get("ROLE"))));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
