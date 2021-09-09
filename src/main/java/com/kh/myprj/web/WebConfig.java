package com.kh.myprj.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.myprj.web.interceptor.LoginCheckInterceptor;
import com.kh.myprj.web.interceptor.MeasuringInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new MeasuringInterceptor())
//						.order(1)
//						.addPathPatterns("/**"); //모든 경로에 적용
//	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())
		        .order(0)
		        .addPathPatterns("/**")
		        .excludePathPatterns(
		        		"/",
		        		"/login",
		        		"/logout",
		        		"/members/join",
		        		"/help/**",
		        		"/css/**",
		        		"/js/**",
		        		"/img/**",
		        		"/webjars/**",
		        		"/error/**"
		        		);
	}
}
