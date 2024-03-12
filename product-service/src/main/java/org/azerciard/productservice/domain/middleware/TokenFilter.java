package org.azerciard.productservice.domain.middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.azerciard.productservice.domain.Util.AppContext;
import org.azerciard.productservice.domain.model.dto.request.RequestById;
import org.azerciard.productservice.service.abstracts.UserServiceClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Order(1)
public class TokenFilter implements Filter {

    UserServiceClient userServiceClient;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing");
            return;
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        var result = userServiceClient.validateToken(new RequestById(token)).getBody();
        if (!result.isSuccess() || result.getData() == null) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }
        AppContext.setToken(token);
        AppContext.setId(result.getData());

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}