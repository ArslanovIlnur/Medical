package org.medical.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class GatewayRedirectHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        org.springframework.security.core.Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", "/auth/research_list");
    }
}
