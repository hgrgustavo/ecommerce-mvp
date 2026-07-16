package com.ecommerce.customer.accounts.infrastructure.web;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component // TODO: create explicit configuration class
@Order(value=HIGHEST_PRECEDENCE)
public class ContentCachingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest invitation, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) invitation;
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 32);
        chain.doFilter(wrappedRequest, response);
    }
}