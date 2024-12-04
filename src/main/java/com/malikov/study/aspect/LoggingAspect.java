package com.malikov.study.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.malikov.study.serivce.TaskService.*(..))")
    public void taskServiceMethods() {
    }

    @Before("taskServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Before advice: вызывается метод {} с аргументами {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }


    @AfterReturning(value = "taskServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("AfterReturning advice: метод {} успешно завершён. Результат: {}",
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(value = "taskServiceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("AfterThrowing advice: метод {} выбросил исключение: {}",
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }

    @Around("taskServiceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Around advice: до выполнения метода {} с аргументами {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
        Object result;
        try {
            result = joinPoint.proceed(); // выполнение метода
            log.info("Around advice: метод {} выполнен успешно. Результат: {}",
                    joinPoint.getSignature().getName(),
                    result);
        } catch (Throwable ex) {
            log.error("Around advice: метод {} завершился ошибкой: {}",
                    joinPoint.getSignature().getName(),
                    ex.getMessage());
            throw ex;
        }
        log.info("Around advice: после выполнения метода {}", joinPoint.getSignature().getName());
        return result;
    }
}
