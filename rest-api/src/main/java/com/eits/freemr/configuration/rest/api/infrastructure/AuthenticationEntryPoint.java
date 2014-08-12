package com.eits.freemr.configuration.rest.api.infrastructure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * The Class AuthenticationEntryPoint.
 */
public class AuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * Instantiates a new Freemr authentication entry point.
     * 
     * @param loginFormUrl
     *            the login form url
     */
    public AuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getServletPath().equals("/index.html")) {
            super.commence(request, response, authException);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }
    }
}
