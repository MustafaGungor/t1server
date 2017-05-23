package com.mebitech.web.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "http://localhost:8181");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,OPTIONS,HEAD");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "X-Custom-Header,Authorization,X-Requested-With,Content-Type,Accept,Origin");
		((HttpServletResponse) response).addHeader("Access-Control-Max-Age", "3600");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");

		filterChain.doFilter(request, response);
	}



}
