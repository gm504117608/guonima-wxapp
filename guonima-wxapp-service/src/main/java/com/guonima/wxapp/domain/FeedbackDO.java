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
    private Integer type; // 反馈类型
    private String mobile; // 手机号码
    private String content; // 反馈内容
    private Integer isDispose; // 是否已经处理【1：是；0：否】
    private Date create_time;
    private Long create_user;
    private Date modify_time;
    private Long modify_user;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Long getCreate_user() {
        return create_user;
    }

    public void setCreate_user(Long create_user) {
        this.create_user = create_user;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public Long getModify_user() {
        return modify_user;
    }

    public void setModify_user(Long modify_user) {
        this.modify_user = modify_user;
    }

    @Override
    public String toString() {
        return "FeedbackDO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", type=" + type +
                ", mobile='" + mobile + '\'' +
                ", content='" + content + '\'' +
                ", isDispose=" + isDispose +
                ", create_time=" + create_time +
                ", create_user=" + create_user +
                ", modify_time=" + modify_time +
                ", modify_user=" + modify_user +
                '}';
    }
}
