package shop.mtcoding.project.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.interceptor.LoginInterceptor;
import shop.mtcoding.project.config.resolver.CompLoginArgumentResolver;
import shop.mtcoding.project.config.resolver.MyLoginArgumentResolver;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MyLoginArgumentResolver myLoginArgumentResolver;
    private final CompLoginArgumentResolver compLoginArgumentResolver;
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
        .addPathPatterns("/**") // 모든 URL에 대해 인터셉터를 수행하도록 설정합니다.
        .excludePathPatterns("/", "/user/join", "/user/emailCheck",
        "/user/login", "/user/login2", "/comp/join", "/comp/emailCheck",  
        "/comp/login", "/jobs/info", "/juso", "/jusoPopup", "/logout", "/jobs/search",
        "/help", "/jobs/info/search", "/jobs/info/list"  
        ); // 인터셉터를 수행하지 않도록 설정합니다.
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(myLoginArgumentResolver);
        resolvers.add(compLoginArgumentResolver);
    }
}   