package com.learn.productservice.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspects {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspects.class);

	
	@Pointcut("execution(* com.learn.productservice.service.*.*(..))")
	public void serviceLayer() {
		
	}
	
	@Before("execution (* com.learn.productservice.controller.*.*(..))")
	public  void logBefore(JoinPoint  joinPoint) {
		System.out.println("Executing @Before advice");
		String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        LOGGER.info("Method {} called with arguments: {}", methodName, Arrays.toString(args));

 	}
	
	@After("serviceLayer()")
	public void logAfter(JoinPoint  joinPoint) {
		String methodName =joinPoint.getSignature().getName();
//		System.out.println("Executing @After advice...." +methodName);
        LOGGER.info("After :Method {} called", methodName);

	}
	@AfterReturning(pointcut = "serviceLayer()" , returning = "result")
	public void logAfterReturning(JoinPoint   joinPoint,  Object  result) {
		String methodName =joinPoint.getSignature().getName();

		System.out.println(" Executing @AfterReturning advice .. methodName: " + joinPoint.getSignature().getName() + 
				" return value ="+ result);
        LOGGER.info("Method {} Returning with value: {}", methodName,result );

	}
	
	@AfterThrowing(pointcut = "serviceLayer()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
		//exception.getClass()
		System.out.println("@AfterThrowing..  methodName:" +joinPoint.getSignature().getName() +"  message : " +exception.getMessage());
		String methodName =joinPoint.getSignature().getName();

        LOGGER.info("Method {}  Throwing an exception {} with message : {}", methodName,exception.getClass(),exception.getMessage() );

	}
	
	
	
	
	

}
