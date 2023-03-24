package shop.mtcoding.project.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.project.config.auth.JwtProvider;
import shop.mtcoding.project.config.auth.LComp;
import shop.mtcoding.project.config.auth.LUser;


public class JwtVerifyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String prefixJwt = req.getHeader(JwtProvider.HEADER);
        String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");
        try {
            DecodedJWT decodedJWT = JwtProvider.verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String email = decodedJWT.getClaim("email").asString();
            String role = decodedJWT.getClaim("role").asString();
            // 내부에서 권한처리 세션
            if (role.equals("user")){
                HttpSession session =  req.getSession();
                LUser loginUser = LUser.builder().id(id).email(email).role(role).build();
                session.setAttribute("principal", loginUser);
                chain.doFilter(req, resp);
            }else{
                HttpSession session =  req.getSession();
                LComp loginComp = LComp.builder().id(id).email(email).role(role).build();
                session.setAttribute("compSession", loginComp);
                chain.doFilter(req, resp);
            }
        }catch (SignatureVerificationException sve){
            resp.setStatus(401);
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().println("로그인 다시해1");
        }catch (TokenExpiredException tee){
            resp.setStatus(401);
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().println("로그인 다시해2");
        }
    }
}