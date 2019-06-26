package com.imooc.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ClassName: HrcUserDetailService  
 * @Description: 自定义密码验证
 * @author 郝若池
 * @date 2019年6月2日 下午5:20:04
 */
@Component
public class HrcUserDetailService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		logger.info("用户名"+username);
		logger.info("密码"+passwordEncoder.encode("duanmeas321"));
		return new User(username, passwordEncoder.encode("duanmeas321"), true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}

}
