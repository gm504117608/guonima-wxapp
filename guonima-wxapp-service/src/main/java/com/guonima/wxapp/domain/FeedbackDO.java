package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author guonima
 * @create 2017-04-25 17:50
 */
public class FeedbackDO implements Serializable {

    private static final long serialVersionUID = -12093918473846783L;

    private Long id; // 唯一标识id
    private Long memberId; // 会员唯一标识id
    private String type; // 反馈类型
    private String mobile; // 手机号码
    private String content; // 反馈内容
    private Integer isDispose; // 是否已经处理【1：是；0：否】
    private Date createTime;
    private Long createUser;
    private Date modifyTime;
    private Long modifyUser;

    public FeedbackDO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsDispose() {
        return isDispose;
    }

    public void setIsDispose(Integer isDispose) {
        this.isDispose = isDispose;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Override
    public String toString() {
        return "FeedbackDO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", type='" + type + '\'' +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                ", isDispose=" + isDispose +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", modifyTime=" + modifyTime +
                ", modifyUser=" + modifyUser +
                '}';
    }
}
