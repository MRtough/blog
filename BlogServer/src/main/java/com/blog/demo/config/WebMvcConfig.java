package com.blog.demo.config;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		//配置静态资源虚拟访问路径
		String userProfile = "file:E:\\java ee\\BlogServer\\src\\main\\resources\\static\\uploadFile\\userProfile\\";
		String blogImgfile = "file:E:\\java ee\\BlogServer\\src\\main\\resources\\static\\uploadFile\\blogImg\\";
		String blogCoverfile = "file:E:\\java ee\\BlogServer\\src\\main\\resources\\static\\uploadFile\\blogCover\\";
		String staticfile="file:E:\\java ee\\BlogServer\\src\\main\\resources\\static\\";
		registry.addResourceHandler("/static/**")
				.addResourceLocations(staticfile)
				.addResourceLocations(userProfile)
				.addResourceLocations(blogImgfile)
				.addResourceLocations(blogCoverfile);
				System.out.println("file:"+userProfile);
				/*.addResourceLocations("classpath:/static/")
				.addResourceLocations("classpath:/static/uploadFile/userProfile/")
				.addResourceLocations("classpath:/static/uploadFile/blogImg/")
				.addResourceLocations("classpath:/static/uploadFile/blogCover/");*/
	}

}
