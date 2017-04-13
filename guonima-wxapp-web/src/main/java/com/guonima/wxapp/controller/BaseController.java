package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;

/**
 * @author guonima
 * @create 2017-04-12 13:24
 */
public class BaseController {

    /**
     * 操作成功
     *
     * @param obj 返回数据
     * @return
     */
    public Response success(Object obj) {
        Response r = new Response();
        r.setCode(0);
        r.setMessage("操作成功");
        r.setData(obj);
        return r;
    }

    /**
     * 操作成功
     *
     * @param message 提示信息
     * @param obj     返回数据
     * @return
     */
    public Response success(String message, Object obj) {
        Response r = new Response();
        r.setCode(0);
        r.setMessage(message);
        r.setData(obj);
        return r;
    }

    /**
     * 操作失败
     *
     * @param code    错误代码
     * @param message 错误提示信息
     * @return
     */
    public Response error(int code, String message) {
        Response r = new Response();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    /**
     * 操作失败
     *
     * @param code    错误代码
     * @param message 错误提示信息
     * @param obj     返回数据
     * @return
     */
    public Response error(int code, String message, Object obj) {
        Response r = new Response();
        r.setCode(code);
        r.setMessage(message);
        r.setData(obj);
        return r;
    }


}
