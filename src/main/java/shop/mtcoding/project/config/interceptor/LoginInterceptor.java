package shop.mtcoding.project.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import shop.mtcoding.project.config.auth.LComp;
import shop.mtcoding.project.config.auth.LUser;

@Configuration
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/user/*")) { // "/blocked"로 시작하는 요청을 차단
            LUser principal = (LUser) request.getSession().getAttribute("principal");
            if (principal == null) {
                // System.out.println("테스트 : 세션이 없어 !!!");
                response.sendRedirect("/userlogin");
                return false;
            }
        }
        if (requestURI.startsWith("/comp/*")) { // "/blocked"로 시작하는 요청을 차단
            LComp compSession = (LComp) request.getSession().getAttribute("compSession");
            if (compSession == null) {
                response.sendRedirect("/complogin");
                return false;
            }
        }
        return true;

        // System.out.println("테스트 : "+ request.getRequestURL().toString());
        // if
        // (request.getRequestURL().toString().equals("http://localhost:8080/user/**"))
        // {

        // }
        // if
        // (request.getRequestURL().toString().equals("http://localhost:8080/comp/**"))
        // {

        // }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
    }
}
