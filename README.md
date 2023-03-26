# 구인구직 사이트 제작

> ## 프로젝트 소개
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
 <img src="src\main\resources\static\images\mini-project-table.jpg">

<br>
<br>

> ## 기술 블로그

- 추가 예정

<br>

> ## 담당 기능
- 박인우 (팀장) 
  - 중복 코드 분리 및 로직 캡슐화를 통한 리팩토링
  - 세션이 필요할경우 `@LoginUser`로 세션 접근 및 `@Valid`로 유효성 검사 할 수 있는 AOP제공
  - JWT를 발급해서 인증처리를 필터에서 처리, 권한검사는 세션을 잠시 생성해서 처리
  - Base64로 인코딩된 json 데이터를 받아서 디코딩 및 uuid를 추가해서 저장 및 DB에 경로 저장
  - Dto 생성 및 Rest Api 문서 작성
  - EC2 클라우드 배포

- 이인화 
  - Dto 생성 및 Rest Api 문서 작성

- 김유현 
  - Dto 생성 및 Rest Api 문서 작성 

- 강은희 
  - 고객센터 페이지 CRUD기능 구현



<br>


> ## 시연영상
- post man 시연
https://youtu.be/0UsQJWybmxE

<br>

> ## 보완점
- i/o 를 줄이기 위해서 조회를 1번만 할 수 있도록 쿼리를 치밀하게 짜야한다.
- ResultMap에 익숙하지 않아서 처음 매핑할때 시간이 너무 오래걸렸다.

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

