package shop.mtcoding.project.config.auth;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.project.model.comp.Comp;
import shop.mtcoding.project.model.user.User;

//private static final String SECRET = System.getenv("secret")
public class JwtProvider {
    private static final String SUBJECT = "JWT_HMAC";
    private static final int EXP = 1000 * 60 * 60 * 24; // 만료 1시간
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 1칸 필요
    public static final String HEADER = "Authorization";

    public static String create(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            String jwt = JWT.create().withSubject(SUBJECT)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                    .withClaim("id", user.getUserId())
                    .withClaim("email", user.getEmail())
                    .withClaim("role", "user")
                    .sign(Algorithm.HMAC512(SecretKey.KEY));
            return TOKEN_PREFIX + jwt;
        } else {
            Comp comp = (Comp) obj;
            String jwt = JWT.create().withSubject(SUBJECT)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                    .withClaim("id", comp.getCompId())
                    .withClaim("email", comp.getEmail())
                    .withClaim("role", "comp")
                    .sign(Algorithm.HMAC512(SecretKey.KEY));
            return TOKEN_PREFIX + jwt;
        }
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        // when
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecretKey.KEY))
                .build().verify(jwt);
        return decodedJWT;
    }
}
