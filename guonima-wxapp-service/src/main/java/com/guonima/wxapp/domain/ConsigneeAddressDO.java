package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货地址实体
 *
 * @author guonima
 * @create 2017-04-21 17:21
 */
public class ConsigneeAddressDO implements Serializable {

    private static final long serialVersionUID = 4697983666949763147L;

    private Long id; // 唯一标识id
    private Long memberId; //会员唯一标识id
    private String name; //收件人
    private String mobile; //手机号码
    private String province; //省份
    private String city; //城市
    private String area; //区域
    private String address; //详细地址
    private String postcode; //邮编
    private Integer isUsing; //是否默认地址【1：是；0：否】
    private Date createTime;
    private Date modifyTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(Integer isUsing) {
        this.isUsing = isUsing;
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
        return "ConsigneeAddressDO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", isUsing=" + isUsing +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
