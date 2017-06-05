package com.guonima.wxapp.aop;

import org.aspectj.lang.JoinPoint;

/**
 * 日志切入点接口类
 */
public interface LogPoint {

    void saveLog(JoinPoint joinPoint, String methodName, String operate);

    void saveLog(JoinPoint joinPoint, String methodName, Throwable e);

}
