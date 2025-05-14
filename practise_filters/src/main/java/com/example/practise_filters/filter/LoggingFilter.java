package com.example.practise_filters.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // Wrap the request to cache the body
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        // Wrap the response to cache the body
        CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(httpServletResponse);

        // Log the request details
        logRequestDetails(cachedBodyHttpServletRequest, httpServletResponse);

        // Continue the filter chain
        filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);
        // Log the response details
        logResponseDetails(cachedBodyHttpServletResponse);

        // Copy the response body back to the original response
        servletResponse.getOutputStream().write(cachedBodyHttpServletResponse.getBody());

    }

    private void logResponseDetails(CachedBodyHttpServletResponse cachedBodyHttpServletResponse) {
        log.info("Response Status: {}", cachedBodyHttpServletResponse.getStatus());
        log.info("Response Headers: {}", cachedBodyHttpServletResponse.getHeaderNames());
        log.info("Response Body: {}", new String(cachedBodyHttpServletResponse.getBody(), StandardCharsets.UTF_8));
    }

    private void logRequestDetails(CachedBodyHttpServletRequest cachedBodyHttpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.info("Request Method: {}", cachedBodyHttpServletRequest.getMethod());
        log.info("Request URI: {}", cachedBodyHttpServletRequest.getRequestURI());
        log.info("Request Headers: {}", cachedBodyHttpServletRequest.getHeaderNames());
        log.info("Request Body: {}", new String(cachedBodyHttpServletRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
    }
}
