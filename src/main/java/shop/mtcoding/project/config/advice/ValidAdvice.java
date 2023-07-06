package shop.mtcoding.project.config.advice;



import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import shop.mtcoding.project.config.exception.MyValidationException;

@Aspect
@Component
public class ValidAdvice {

    // AOP를 구현함으로써 비즈니스 로직과 인증, 인가 코드를 분리시킨다. -> 각 코드의 역할을 명확히
    // 여러 에러들이 오더라도 여기서 타입을 정제하고 여러 익셉션으로 분화시킬수 있다.
    
    // 특정 예외를 잡아서 공통된 로직을 실행해보자
    // Pointcut - 리플렉션 이용
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
        return proceedingJoinPoint.proceed(); // 정상적으로 해당 메서드를 실행 
    }
}
