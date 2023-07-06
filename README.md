# 구인구직 사이트 제작 ( REST API server )

> ## 시연영상 ( Youtube )

<a href="https://youtu.be/0UsQJWybmxE">![image](https://user-images.githubusercontent.com/118657689/234799992-f20dea08-d827-4c57-ad99-e36270c6edef.png)</a>
<br>

> ## 발표자료 ( PDF )

![image](https://user-images.githubusercontent.com/118657689/234800218-e3c1444b-ef3e-46bb-877a-dabebe900d91.png)

- <a href="https://github.com/clean17/mini-project2/files/11340918/6._.2_PPT.pdf">[6조_미니프로젝트2_PPT.pdf]</a>

<br>

- 1차로 만든 구인구직 사이트를 Rest 서버로 바꾼뒤 model을 사용하지 않고 ResponseEntity를 이용해 Dto하나로 json 데이터를 리턴한다.

<br>

> ## 프로젝트 기간

- 2023.03.14 ~ 2023.03.27

<br>

> ## 기술 스택

- JDK 11
- Spring Boot 2.7.8
- MyBatis
- 테스트 h2 DB
- 프로덕션 MySql DB


<br>

> ## 기능정리

- Rest Api 문서를 보고 프론트가 작업할 수 있도록 Rest서버로 변환
- 여러개의 model을 하나의 Dto로 변환 및 Builder 패턴 사용
- MyBatis resultMap 으로 Dto에 매핑
- JWT를 이용한 인증처리 방식
- AOP를 이용해서 Validation Check 및 Exception 핸들링 처리, 중복되는 코드 처리
- Base64로 인코딩된 데이터 송수신 처리
- EC2 클라우드 임시 배포
- Junit을 이용한 컨트롤러 단위테스트



<br>

> ## 의존성 주입


```
implementation group: 'com.auth0', name: 'java-jwt', version: '4.3.0'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springframework.boot:spring-boot-starter-aop'
implementation 'javax.servlet:jstl'
implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.3.0'
compileOnly 'org.projectlombok:lombok'
developmentOnly 'org.springframework.boot:spring-boot-devtools'
runtimeOnly 'com.h2database:h2'
annotationProcessor 'org.projectlombok:lombok'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation group: 'org.mybatis.spring.boot', name: 'mybatis-spring-boot-starter-test', version: '2.2.2'
```

<br>

> ## 테이블 모델링

<br>

![mini-project-table (1)](https://user-images.githubusercontent.com/118657689/236441203-cf21bfc4-5dba-4996-a1af-554707407efa.jpg)

<br>
<br>

> ## 기술 블로그

- <a href="https://velog.io/@merci/series/Rest-Api-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8"> 블로그 링크 </a>

<br>

> ## 담당 기능

- 박인우 (팀장) 
  - JWT 발급, JWT인증필터 만들어서 추가 <br>
  - 필터에서 principal을 만들어서 세션에 추가한 뒤 <br>
  - 세션에서 principal가져와 권한처리하는 @LoginUser( AOP )만들어서 중복코드 제거 <br>
  - @Valid 로 유효성 검사 + 익셉션 핸들러 추가 ( alert )  <br>
  - 사진( Base64 ) 받아서 디코딩 + UUID 추가해서 저장 <br>
  - Rest Api 문서 작성 및 DTO 생성 <br>
  - MyBatis의 Result Mapper로 ORM 적용<br>
  - EC2에 jar 배포 + RDS 연결<br>

- 이인화 
  - Dto 생성 및 Rest Api 문서 작성

- 김유현 
  - Dto 생성 및 Rest Api 문서 작성 

- 강은희 
  - 고객센터 페이지 CRUD기능 구현



<br>




> ## 보완점

- i/o 를 줄이기 위해서 조회를 1번만 할 수 있도록 쿼리를 치밀하게 짜야한다.
- ResultMap에 익숙하지 않아서 처음 매핑할때 시간이 너무 오래걸렸다.
- REST API 규칙을 지키지 못하고 만들었다가 나중에 알아서 고치지 못했는데 다음에는 확실히 지켜서 만들것이다.

<br>

> ## 후기

- 박인우

  스프링의 새로운 기술들을 사용해봐서 좋았고, REST API를 직접 구현하면서 왜 REST아키텍처를 이용하는지 조금 알게 되어서 좋았다.


- 김유현


  2차 프로젝트 까지 하면서, 아직도 개념이 잡히지 않거나 이해가 어려웠던 부분을 팀원들과의 조언과 공부하는 시간을 통해 조금 더 성장하는 시간이었고 부족한 부분을 더 채워 나가고 싶다.

- 이인화


  1,2차 프로젝트를 하면서 부족함을 많이 느꼈지만, 많이 배웠고 이것을 토대로 더 열심히 할 것이다.


- 강은희


  지난 시간에 못한 부분을 채울 수 있는 시간 같아서 다행이라고 생각했습니다. 부족하기도 했지만, 많이 배운 시간이라고 생각합니다.

<br>

> ## Valid AOP

익셉션 핸들러를 통해 `BindException `을 핸들링 했었지만 AOP를 구현해서 에러 처리를 분리시켰습니다.<br>
`Aspect`를 이용해서 AOP를 구현하면 스프링 실행시 자동으로 `AOP Proxy`를 만들어 비즈니스 로직을 감싸게 됩니다. 이때 리플렉션이 사용됩니다. <br>

```java
@Aspect
@Component
public class ValidAdvice {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    @Around("postMapping() || putMapping()")
    public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(),error.getDefaultMessage());
                    }
                    throw new MyValidationException(errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
```

<br>

> ## Session AOP

일반 회원과 기업 회원을 각각 `User`와 `Comp`로 나눴기에 로그인시 세션을 검증하는 코드를 AOP로 분리시켜서 중복된 코드를 작성하지 않도록 했습니다.<br>
세션 정보를 가져오도록 어노테이션을 만들고 로그인 되어 있을때 해당 세션을 `Aspect`로 등록된 클래스가 바인딩합니다.<br>
마찬가지로 따로 등록하지 않아도 됩니다.<br>

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
```

```java
@Aspect
@Component
public class LoginAdvice {

    @Around("execution(* shop.mtcoding.aopstudy.controller..*.*(..))")
    public Object loginUserAdvice(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();

        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();

        Annotation[][] annotationsPA = method.getParameterAnnotations();

        for (int i = 0; i < args.length; i++) {
            Annotation[] annotations = annotationsPA[i];
            for (Annotation anno : annotations) {
                if (anno instanceof LoginUser) {
                    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                            .getRequest();
                    HttpSession session = req.getSession();
                    User principal = (User) session.getAttribute("principal");
                    if (principal != null) {
                    }
                    return jp.proceed(new Object[] { principal });
                }
            }
        }
        return jp.proceed();
    }
}
```

<br>

> ## HandlerMethodArgumentResolver

위와 유사한 방법으로 리졸버를 이용해서 세션에서 데이터를 가져와서 바인딩하는 방법입니다.<br>
`AOP`와는 다르게 컴포넌트로 등록되어 있지 않으므로 인터셉터와 마찬가지로 직접 등록해야 합니다.

```java
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
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        return session.getAttribute("principal");
    }
}
```
```java
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MyLoginArgumentResolver myLoginArgumentResolver;
    private final CompLoginArgumentResolver compLoginArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(myLoginArgumentResolver);
        resolvers.add(compLoginArgumentResolver);
    }
}   
```

<br>

> ## JWT 필터
인증 방식을 쿠키에서 JWT로 변경했습니다.<br>
일반 회원과 기업 회원으로 Role을 분리시키고 각각의 JWT를 생성해서 반환했습니다.<br>
인증이 되면 서버 내부에서 요청주기 동안 유효한 세션을 생셩하도록 했습니다. <br>
직접 만든 JWT필터를 서블릿 컨테이너의 필터체인에 등롭합니다.

```java
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
            if (role.equals("user")){
                HttpSession session =  req.getSession();
                LUser loginUser = LUser.builder().id(id).email(email).role(role).build();
                session.setAttribute("principal", loginUser);
                session.setMaxInactiveInterval(1);
                chain.doFilter(req, resp);
            }else{
                HttpSession session =  req.getSession();
                LComp loginComp = LComp.builder().id(id).email(email).role(role).build();
                session.setAttribute("compSession", loginComp);
                session.setMaxInactiveInterval(1);
                chain.doFilter(req, resp);
            }
        }catch (SignatureVerificationException sve){
            resp.setStatus(401);
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().println("가짜 토큰");
        }catch (TokenExpiredException tee){
            resp.setStatus(401);
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().println("토큰 만료");
        }
    }
}
```
```java
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
```

