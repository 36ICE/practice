package com.example.myspring;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 **/
public class UrlFilter implements Filter {


    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponseWrapper httpResponse = new HttpServletResponseWrapper((HttpServletResponse) response);
//        System.out.println(httpRequest.getRequestURI());
//        String path = httpRequest.getRequestURI();
//        if (path.contains("%")) {
//            path = path.replaceAll("%", "%25");
//            System.out.println(path);
//            httpRequest.getRequestDispatcher(path).forward(request, response);
//        } else {
//            chain.doFilter(request, response);
//
//        }
        chain.doFilter(request, response);
        return;
    }
}
