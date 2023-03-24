package shop.mtcoding.project.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.project.config.filter.JwtVerifyFilter;



@Configuration
public class FilterRegisterConfig {
    
    @Bean 
    public FilterRegistrationBean<?> jwtVerifyFilterRegister(){
        FilterRegistrationBean<JwtVerifyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtVerifyFilter());
        registration.addUrlPatterns("/user/*"); // user 일때만 토큰 검사
        registration.addUrlPatterns("/comp/*"); // comp 일때만 토큰 검사
        registration.setOrder(1);
        return registration;
    }
}