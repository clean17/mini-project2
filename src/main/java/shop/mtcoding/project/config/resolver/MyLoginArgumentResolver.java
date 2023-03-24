package shop.mtcoding.project.config.resolver;



import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.auth.LUser;

@RequiredArgsConstructor
@Configuration
public class MyLoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession session;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean check1 = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean check2 = LUser.class.equals(parameter.getParameterType());
        return check1 && check2;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return session.getAttribute("principal");
    }
}
