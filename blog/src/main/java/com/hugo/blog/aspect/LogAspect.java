package com.hugo.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.hugo.blog.web.*.*(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String ClassMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+ joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        doAfterReturn Result = new doAfterReturn(url,ip,ClassMethod,args);
        log.info("Result : {}",Result);
    }

    @After("log()")
    public void doAfter(){
        log.info("————————after——————————");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void  doAfterReturn(Object result){
        log.info("Result : {}",result);
    }

    @ToString
    @AllArgsConstructor
    public class doAfterReturn {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;


    }



}
