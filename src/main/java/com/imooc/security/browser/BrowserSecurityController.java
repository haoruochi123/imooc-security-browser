package com.imooc.security.browser;

import com.imooc.security.browser.support.SimpleResponse;
import com.imooc.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: BrowserSecurityController  
 * @Description: 权限登录controller
 * @author 郝若池
 * @date 2019年6月2日 下午5:00:59
 */
@RestController
public class BrowserSecurityController {

	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy =new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@GetMapping("/autentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(savedRequest !=null) {
			String targetUrl = savedRequest.getRedirectUrl();
			System.out.println(targetUrl);
			
			//判断是否以.html结尾
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
			
		}
		
		return new SimpleResponse("访问需要身份认证，请引导用户到登录页面！");
		
	}



}
