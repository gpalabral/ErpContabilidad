package com.bap.erp.vista.security;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class LoginFilter extends javax.servlet.http.HttpServlet implements Filter {

    private static Logger log = Logger.getLogger(LoginFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hres = (HttpServletResponse) response;
        String requestPath = hreq.getPathInfo();

        /**
         * Valida la autenticacion
         */
        System.out.println("requestPath:::: "+requestPath);
        if (requestPath != null && requestPath.endsWith(".xhtml") && !requestPath.endsWith("login.xhtml")) {

            if (!isAuthenticated(hreq)) {
                log.info("Send Redirect::: requestPath::: " + requestPath);
                System.out.println("Send Redirect::: requestPath::: " + requestPath);
                System.out.println("REDIRECT::: "+hreq.getContextPath() + "/erp/login.xhtml");
                hres.sendRedirect(hreq.getContextPath() + "/erp/login.xhtml");

            }
        }
        chain.doFilter(request, response);

    }

    private boolean isAuthenticated(HttpServletRequest request) {
        System.out.println("Preguntado si esta autenticado " + request.getSession().getAttribute("auth"));
        System.out.println("Autenticado: " + request.getSession().getAttribute("auth"));
        return request.getSession().getAttribute("auth") != null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
