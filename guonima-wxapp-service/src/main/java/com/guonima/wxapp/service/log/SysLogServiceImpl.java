package com.guonima.wxapp.service.log;

import com.guonima.wxapp.aop.LogPoint;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.SysLogDO;
import com.guonima.wxapp.exception.ServiceException;
import com.guonima.wxapp.redis.RedisClient;
import com.guonima.wxapp.util.HttpUtil;
import com.guonima.wxapp.util.IPUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * SysLogDO 表数据服务层接口实现类
 */
@Service("SysLogServiceImpl")
public class SysLogServiceImpl implements SysLogService, LogPoint {

    private static final Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    private static final String LOG_CONTENT = "[类名]:%s,[方法]:%s,[参数]:%s";

    @Override
    public void saveLog(JoinPoint joinPoint, String methodName, String operate) {
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        SysLogDO log = new SysLogDO();
        // 处理用户信息
        getMemberInfo(log, request);
        log.setContent(operateContent(joinPoint, methodName, request));
        log.setOperation(operate);
        log.setCreateTime(new Date(System.currentTimeMillis()));
        log.setType(SysLogDO.TYPE_ACCESS);
        log.setRemoteAddr(IPUtils.getClientAddress(request));
        log.setRequestUrl(request.getRequestURI());
        insert(log);
    }

    /**
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 操作内容
     */
    private String operateContent(JoinPoint joinPoint, String methodName, HttpServletRequest request) {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        StringBuffer bf = new StringBuffer();
        if (params != null && params.length > 0) {
            for (Object obj : params) {
                bf.append(obj.toString());
            }
//            Enumeration<String> paraNames = request.getParameterNames();
//            while (paraNames.hasMoreElements()) {
//                String key = paraNames.nextElement();
//                bf.append(key).append("=");
//                bf.append(request.getParameter(key)).append("&");
//            }
            if (StringUtils.isBlank(bf.toString())) {
                bf.append(request.getQueryString());
            }
        }
        return String.format(LOG_CONTENT, className, methodName, bf.toString());
    }

    @Override
    public void saveLog(JoinPoint joinPoint, String methodName, Throwable e) {

        SysLogDO log = new SysLogDO();
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        log.setContent(adminOptionContent(joinPoint, methodName, request));
        // 处理用户信息
        getMemberInfo(log, request);
        log.setOperation("异常");
        log.setCreateTime(new Date(System.currentTimeMillis()));
        log.setRemoteAddr(IPUtils.getClientAddress(request));
        log.setRequestUrl(request.getRequestURI());
        log.setException(e == null ? null : e.toString());
        log.setType(e == null ? SysLogDO.TYPE_ACCESS : SysLogDO.TYPE_EXCEPTION);

        /*========日志文件记录=========*/
        logger.error("=====异常通知开始=====");
        logger.error("异常代码:" + e.getClass().getName());
        logger.error("异常信息:" + e.getMessage());
        logger.error("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
        logger.error("请求参数:" + log.getContent());
        logger.error("=====异常通知结束=====");

        insert(log);
    }

    @Override
    public int insert(SysLogDO sysLogDO) {
        try {
            return dao.insert("sysLogMapper.insert", sysLogDO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("aop记录操作日志保存出现错误： " + e.getMessage());
            throw new ServiceException("aop记录操作日志保存出现错误： " + e.getMessage());
        }
    }

    private String adminOptionContent(JoinPoint joinPoint, String methodName, HttpServletRequest request) {
        if (joinPoint == null) {
            return null;
        }
        String operateClassName = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();
        StringBuffer rs = new StringBuffer();
        String className = null;
        int index = 1;
        for (Object info : args) {
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型：" + className + "，值：");
            Method[] methods = info.getClass().getDeclaredMethods();
            for (Method method : methods) {
                String childMethodName = method.getName();
                if (childMethodName.indexOf("get") == -1) {
                    continue;
                }
                Object rsValue = null;
                try {
                    rsValue = method.invoke(info);
                    if (rsValue == null) {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                rs.append("(" + childMethodName + " : " + rsValue + ")");
            }
            rs.append("]");
            index++;
        }
        return String.format(LOG_CONTENT, operateClassName, methodName, rs.toString());
    }

    /**
     * 获取当前登录用户信息
     *
     * @param log     日志信息
     * @param request 请求对象
     */
    private void getMemberInfo(SysLogDO log, HttpServletRequest request) {
        String token = request.getHeader("token");
        String id = RedisClient.get(token, String.class);
        log.setMemberId(Long.valueOf(id));
//        log.setMemberName(me.getNickName());
    }
}