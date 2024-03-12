package org.azerciard.userservice.domain.middleware;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.userservice.exception.UserNotFoundException;
import org.azerciard.userservice.repository.UserRepository;
import org.azerciard.userservice.service.concrete.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static org.azerciard.userservice.domain.config.Const.WHITE_LIST;

@Component
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    JwtService jwtService;
    UserRepository userRepository;


    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authenticationHeader = request.getHeader("Authorization");
        final String id;
        final String jwt;


        final String path = request.getRequestURI();

        if (Arrays.stream(WHITE_LIST).anyMatch(path::endsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authenticationHeader.substring(7);
        id = jwtService.extractUsername(jwt);
        UserDetails userDetails = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}






