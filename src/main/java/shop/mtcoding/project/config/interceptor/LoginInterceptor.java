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
        if (requestURI.startsWith("/user/*")) { 
            LUser principal = (LUser) request.getSession().getAttribute("principal");
            if (principal == null) {
                response.sendRedirect("/userlogin");
                return false;
            }
        }
        if (requestURI.startsWith("/comp/*")) { 
            LComp compSession = (LComp) request.getSession().getAttribute("compSession");
            if (compSession == null) {
                response.sendRedirect("/complogin");
                return false;
            }
        }
        return true;
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
