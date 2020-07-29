package com.blog.demo.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.blog.demo.entity.SysUsers;
import com.blog.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration

public class WebSecutityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserServiceImpl userService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(http);
		http.authorizeRequests().antMatchers("/admin/**").hasRole("admin").antMatchers("/user/**")
				.hasAnyRole("admin", "user").anyRequest().authenticated().and().formLogin().loginPage("/login.html")
				.loginProcessingUrl("/login").successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
							Authentication authentication) throws IOException, ServletException {
						resp.setContentType("application/json;charset=utf-8");
						SysUsers users = (SysUsers) authentication.getPrincipal();
						users.setPassword(null);
						PrintWriter out = resp.getWriter();
						List<Object> list = new ArrayList<Object>();
						list.add("success");
						list.add(users);
						out.write(new ObjectMapper().writeValueAsString(list));
						out.flush();
						out.close();
					}
				}).failureHandler((req, resp, e) -> {
					resp.setContentType("application/json;charset=utf-8");
					PrintWriter out = resp.getWriter();
					out.write(e.getMessage());
					out.flush();
					out.close();
				}).permitAll().and().logout()
				// 默认注销行为为logout，可以通过下面的方式来修改
				.logoutUrl("/logout").logoutSuccessHandler(new LogoutSuccessHandler() {
					@Override
					public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp,
							Authentication authentication) throws IOException, ServletException {
						resp.setContentType("application/json;charset=utf-8");
						PrintWriter out = resp.getWriter();
						out.write(new ObjectMapper().createObjectNode().put("msg", "注销成功！").toString());
						out.flush();
						out.close();
					}
				}).permitAll().and().csrf().disable().exceptionHandling()
				.accessDeniedHandler(new AccessDeniedHandler() {

					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response,
							AccessDeniedException accessDeniedException) throws IOException, ServletException {
						// TODO Auto-generated method stub
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json");
						response.getWriter()
								.println(new ObjectMapper().createObjectNode().put("msg", "没有权限访问呀！").toString());
						response.getWriter().flush();
						response.getWriter().close();

					}
				}).authenticationEntryPoint(new AuthenticationEntryPoint() {

					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response,
							org.springframework.security.core.AuthenticationException authException)
							throws IOException, ServletException { // TODO Auto-generated method stub
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json");
						response.getWriter()
								.println(new ObjectMapper().createObjectNode().put("msg", "请先登录！").toString());
						response.getWriter().flush();
						response.getWriter().close();
					}
				});
		//控制一个用户只能在一个客户端登陆
		http.sessionManagement().maximumSessions(1).expiredUrl("/login");

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(web);
		web.ignoring().antMatchers("/sortbloglist").antMatchers("/sortblogcardlist").antMatchers("/allbloglist")
				.antMatchers("/blogdetail").antMatchers("/blogcomments").antMatchers("/postmessage")
				.antMatchers("/allmessage").antMatchers("/postcomment").antMatchers("/static/**").antMatchers("/getBlogLabel");
	}

}
