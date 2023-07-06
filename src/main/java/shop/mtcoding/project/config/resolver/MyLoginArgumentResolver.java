package shop.mtcoding.project.config.resolver;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.auth.LUser;

// spring MVC 인터페이스
// 리졸버 - 자원을 찾고 반환하는 역할
@RequiredArgsConstructor
@Configuration
public class MyLoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession session;

    // supportsParameter - 특정 타입 인자를 처리할 수 있는지 체크
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // LoginUser 어노테이션이 있어 ?
        boolean check1 = parameter.getParameterAnnotation(LoginUser.class) != null;
        // LoginUser 어노테이션이 있는 메소드가 LUser 를 반환해 ?
        boolean check2 = LUser.class.equals(parameter.getParameterType());
        // 둘다 통과했어
        return check1 && check2;
    }

    // resolveArgument - 실제 인자 해석 반환
    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        // 요청 파라미터를 메소드 인자로 바인딩
        return session.getAttribute("principal");
    }
}
