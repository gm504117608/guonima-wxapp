package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysLog
 * @Description: 操作日志表
 */
public class SysLogDO implements Serializable {

    private static final long serialVersionUID = -7757695229455089190L;

    public static final int TYPE_ACCESS = 0; //操作日志
    public static final int TYPE_EXCEPTION = 1; //异常日志

    private Long id;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 用户名称
     */
    private String memberName;
    /**
     * 日志内容
     */
    private String content;
    /**
     * 用户操作
     */
    private String operation;
    /**
     * 类型(0系统类型)(1异常)
     */
    private int type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 请求url
     */
    private String requestUrl;
    /**
     * ip
     */
    private String remoteAddr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", content='" + content + '\'' +
                ", operation='" + operation + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", exception='" + exception + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", remoteAddr='" + remoteAddr + '\'' +
                '}';
    }
}
