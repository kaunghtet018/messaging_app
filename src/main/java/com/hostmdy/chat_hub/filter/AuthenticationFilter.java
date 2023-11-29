package com.hostmdy.chat_hub.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		
		Long userId = (Long) session.getAttribute("userId");
		if(userId!=null) {
			chain.doFilter(request, response);
		}
		else {
			if(httpRequest.getRequestURI().endsWith("/account/signin")||httpRequest.getRequestURI().endsWith("/account/signup")) {
				chain.doFilter(httpRequest, httpResponse);
			}
			else {
				httpResponse.sendRedirect("/account/signin");
			}
		}
		
	}

}
