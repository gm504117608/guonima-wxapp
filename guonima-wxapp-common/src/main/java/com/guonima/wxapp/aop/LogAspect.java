package com.guonima.wxapp.aop;

import com.guonima.wxapp.annotations.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志切面处理类
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private LogPoint logPoint;

    /**
     * 定义一个注解切入点
     */
    @Pointcut("@annotation(com.guonima.wxapp.annotations.Log)")
    public void sysLogAspect() {
    }

    /**
     * 保存系统操作日志
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     */
    @Around("sysLogAspect()")
    //@Around(value = "@annotation(com.podinns.annotations.Log)")
    public Object saveLog(JoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Method method = currentMethod(joinPoint, methodName);
        Log log = method.getAnnotation(Log.class);
        if (log != null) {
            logPoint.saveLog(joinPoint, methodName, log.value());
        }
        return ((ProceedingJoinPoint) joinPoint).proceed();
    }

    /**
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 方法对象
     */
    private Method currentMethod(JoinPoint joinPoint, String methodName) {

        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

    /**
     * 定义一个异常切入点
     */
    @Pointcut("execution(* com.guonima.wxapp.controller..*.*(..)) "
            + "|| execution(* com.guonima.wxapp.service..*.*(..))")
    public void exceptionLogCall() {
    }

    /**
     * 出现异常错误信息拦截
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "exceptionLogCall()", throwing = "e")
    public void doThrowing(JoinPoint joinPoint, Exception e) {
        e.printStackTrace();
        try {
            logPoint.saveLog(joinPoint, joinPoint.getSignature().getName(), e);
        } catch (Exception exp) {
            logger.error("异常信息:{}", exp.getMessage());

        }
    }
}