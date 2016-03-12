package com.bap.erp.ws;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter{
    
//    @Override
//    public ContainerResponse filter(ContainerRequest creq, ContainerResponse cresp) {
//
//        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Origin", "http://10.0.0.42:8080/");
//        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Credentials", "true");
//        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
//        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
//
//        return cresp;
//    }
//    
    
    	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
            
//                System.out.println("**************CORSFilter****************");
            
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}
    
}
