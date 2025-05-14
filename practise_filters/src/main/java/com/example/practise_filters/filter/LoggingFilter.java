package com.example.practise_filters.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequestWrapper httpServletRequest = (HttpServletRequestWrapper) servletRequest;
        System.out.println("URL : " + httpServletRequest.getRequestURI());
        System.out.println("IP : " + httpServletRequest.getRemoteAddr());
        System.out.println("Http method : " + httpServletRequest.getMethod());
        httpServletRequest.getInputStream().

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
