package kr.co.hellopet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	protected void configure(HttpSecurity http) throws Exception{
		// 접근권한
		http.authorizeRequests().antMatchers("/").permitAll();
		
		// 위조 방지
		http.csrf().disable();
		
		// 로그인 설정
		http.formLogin().loginPage("/member/login").defaultSuccessUrl("/index").failureUrl("/member/login?success=100")
		.usernameParameter("uid").passwordParameter("pass");
		
		// 자동로그인 설정
		http.rememberMe()
		.key("UniqueAndSecret").rememberMeParameter("remember-me")
		.tokenValiditySeconds(60*60*24).userDetailsService(userService);
		
		
		// 로그아웃 설정
		http.logout().invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
		.logoutSuccessUrl("/index");
       
	}
	
	@Autowired
	private SecurityUserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// 로그인 인증 처리 서비스, 암호화 방식 설정
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
