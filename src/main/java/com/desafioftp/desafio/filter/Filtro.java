package com.desafioftp.desafio.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Filtro implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        System.out.println("Request " + req.getRequestURI());
        chain.doFilter(request, response);
        System.out.println("Response " + res.getContentType());
//        System.out.println("Remote host " + request.getRemoteHost());
//        System.out.println("Remote Address " + request.getRemoteAddr());
    }
}
