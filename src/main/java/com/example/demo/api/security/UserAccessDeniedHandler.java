package com.example.demo.api.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {
 	
	   @Override
       public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) 
    	throws IOException {
	        System.out.println("로그인 오류");
	        response.sendRedirect("/exception/accessdenied");
	   }
}
