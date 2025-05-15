package com.example.practise_filters.filter;

import com.example.practise_filters.model.AuditLog;
import com.example.practise_filters.model.AuditLogRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@Slf4j
public class LoggingFilter implements Filter {
    @Autowired
    private AuditLogRepository auditLogRepository;

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

        AuditLog auditLog = new AuditLog();

        // Log the request details
        logRequestDetails(cachedBodyHttpServletRequest, auditLog);

        // Continue the filter chain
        filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);
        // Log the response details
        logResponseDetails(cachedBodyHttpServletResponse, auditLog);

        auditLog.setTimestamp(LocalDateTime.now());

        // Save the audit log to the database
        auditLogRepository.save(auditLog);

        // Copy the response body back to the original response
        servletResponse.getOutputStream().write(cachedBodyHttpServletResponse.getBody());

    }

    private void logResponseDetails(CachedBodyHttpServletResponse cachedBodyHttpServletResponse, AuditLog auditLog) {
        auditLog.setResponseBody(new String(cachedBodyHttpServletResponse.getBody(), StandardCharsets.UTF_8));
        auditLog.setResponseStatus(cachedBodyHttpServletResponse.getStatus());
        auditLog.setResponseHeaders(cachedBodyHttpServletResponse.getHeaderNames().toString());
        log.info("Response Status: {}", cachedBodyHttpServletResponse.getStatus());
        log.info("Response Headers: {}", cachedBodyHttpServletResponse.getHeaderNames());
        log.info("Response Body: {}", new String(cachedBodyHttpServletResponse.getBody(), StandardCharsets.UTF_8));
    }

    private void logRequestDetails(CachedBodyHttpServletRequest cachedBodyHttpServletRequest, AuditLog auditLog) throws IOException {
        auditLog.setRequestBody(new String(cachedBodyHttpServletRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
        auditLog.setRequestMethod(cachedBodyHttpServletRequest.getMethod());
        auditLog.setRequestUri(cachedBodyHttpServletRequest.getRequestURI());
        auditLog.setRequestHeaders(cachedBodyHttpServletRequest.getHeaderNames().toString());
        log.info("Request Method: {}", cachedBodyHttpServletRequest.getMethod());
        log.info("Request URI: {}", cachedBodyHttpServletRequest.getRequestURI());
        log.info("Request Headers: {}", cachedBodyHttpServletRequest.getHeaderNames());
        log.info("Request Body: {}", new String(cachedBodyHttpServletRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
    }

}
