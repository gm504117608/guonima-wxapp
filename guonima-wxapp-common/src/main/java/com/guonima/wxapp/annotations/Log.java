package com.guonima.wxapp.annotations;

import java.lang.annotation.*;

/**
 * 系统日志注解
 */
@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取  
@Target(ElementType.METHOD)//目标是方法  
@Documented//文档生成时，该注解将被包含在javadoc中，可去掉  
public @interface Log {

    /**
     * 操作描述
     */
    String value() default "";

}
