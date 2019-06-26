package com.imooc.security.browser.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName: HrcAuthenticationSuccessHandler  
 * @Description: 登录认证成功处理器
 * @author 郝若池
 * @date 2019年6月2日 下午6:17:50
 */
@Component
public class HrcAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RedirectStrategy redirectStrategy;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
//		response.setContentType("application/json;charset=UTF-8");
//		response.getWriter().write(objectMapper.writeValueAsString(authentication));
		redirectStrategy.sendRedirect(request, response, "/index.html");
		
	}

}
