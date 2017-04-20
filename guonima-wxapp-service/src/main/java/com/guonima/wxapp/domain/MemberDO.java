package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberDO implements Serializable {

    private static final long serialVersionUID = 4697983666949763147L;

    private Long id; // 用户id
    private String openid; // openid是公众号的普通用户的唯一的标识
    private String nickName; // 用户名称
    private String mobile; // 联系号码
    private String email; // 邮箱
    private String postcode; // 邮编
    private Integer gender; // 性别
    private String country; // 国家
    private String province; // 省份
    private String city; // 城市
    private String avatarUrl; // 头像地址
    private String signature; // 个性签名
    private Integer enabled; // 是否激活【1（可用）；0（不可用）】
    private Date createTime; // 创建时间
    private Date modifyTime; // 修改时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "MemberDO{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", postcode='" + postcode + '\'' +
                ", gender=" + gender +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", signature='" + signature + '\'' +
                ", enabled='" + enabled + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
