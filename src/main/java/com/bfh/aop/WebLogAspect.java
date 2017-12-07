package com.bfh.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Author: bfh
 * Date: 2017/12/3
 */
@Aspect
@Component
@Order(-5)
public class WebLogAspect {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());

	/**
	 * 定义一个切入点.
	 * ~ *  代表任意修饰符及任意返回值.
	 * ~ ..  表示当前包及子包
	 * ~ * 表示类名，*即所有类
	 * ~ .*(..)  表示任何方法名，括号表示参数，两个点表示任何参数类型
	 */
	@Pointcut("execution(* com.bfh.controller..*.*(..))")
	public void webLog(){}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		logger.info("WebLogAspect.doBefore()");

		// 接收到请求，记录请求内容
		logger.info("WebLogAspect.doBefore()");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();


		// 记录下请求内容
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
		//获取所有参数方法一：
		Enumeration<String> enu=request.getParameterNames();
		while(enu.hasMoreElements()){
			String paraName = enu.nextElement();
			System.out.println(paraName+": "+request.getParameter(paraName));
		}
	}

	@AfterReturning("webLog()")
	public void  doAfterReturning(JoinPoint joinPoint){
		// 处理完请求，返回内容
		logger.info("WebLogAspect.doAfterReturning()");
	}
}
