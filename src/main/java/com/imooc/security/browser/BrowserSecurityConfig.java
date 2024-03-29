package com.imooc.security.browser;

import com.imooc.security.browser.handler.HrcAthenticationFailureHandler;
import com.imooc.security.browser.handler.HrcAuthenticationSuccessHandler;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;
import com.imooc.security.core.validate.sms.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.validate.sms.SmsCodeFilter;
import com.sun.org.apache.xpath.internal.operations.And;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author 郝若池
 * @ClassName: BrowserSecurityConfig
 * @Description: 权限登录页面配置
 * @date 2019年6月2日 下午4:58:44
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(BrowserSecurityConfig.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private HrcAuthenticationSuccessHandler hrcAuthenticationSuccessHandler;

    @Autowired
    private HrcAthenticationFailureHandler hrcAthenticationFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Bean
    public RedirectStrategy redirectStrategy() {
        return new DefaultRedirectStrategy();
    }

    // 用户密码加密方法
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 记住我的token
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        //第一次建表后注释掉，不然要报错
//		jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        return jdbcTokenRepositoryImpl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 图形验证器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(hrcAthenticationFailureHandler);

        //短信验证过滤器
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(hrcAthenticationFailureHandler);

        http
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class).formLogin()
//              .loginPage("/autentication/require")
                .loginPage(securityProperties.getBrowser().getLoginPage())
                .loginProcessingUrl("/authentication/form")
                .successHandler(hrcAuthenticationSuccessHandler)
                .failureHandler(hrcAthenticationFailureHandler)
                .and()
                .rememberMe().
                tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getTokenValiditySeconds())
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/login/**",
                        "/authentication/form",
                        "/autentication/require",
                        "/code/**",
                        "/exception",
                        "/post",
                        "/user",
                        "/user/regist",
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getLoginPage())
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(springSocialConfigurer);

        logger.info("security配置完成");
    }
    /*
     * @Override protected void configure(AuthenticationManagerBuilder auth) throws
     * Exception { auth.inMemoryAuthentication()
     * .withUser("admin").password("admin").roles("ADMIN") .and()
     * .withUser("terry").password("terry").roles("USER") .and()
     * .withUser("larry").password("larry").roles("USER"); }
     */

}
