package com.imooc.security.browser.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.browser.support.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: HrcAthenticationFailureHandler  
 * @Description: 登录认证失败处理器
 * @author 郝若池
 * @date 2019年6月2日 下午6:17:10
 */
@Component
public class HrcAthenticationFailureHandler implements AuthenticationFailureHandler{

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		
	}

}
