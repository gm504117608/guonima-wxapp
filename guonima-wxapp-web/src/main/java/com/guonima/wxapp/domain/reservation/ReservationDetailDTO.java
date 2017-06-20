package com.guonima.wxapp.domain.reservation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author guonima
 * @create 2017-05-05 11:27
 */
public class ReservationDetailDTO implements Serializable {

    private static final long serialVersionUID = 2341123412L;

    private String shopName; // 店铺名称
    private String orderNo; // 订单号
    private BigDecimal cost; // 花费金额
    private String statusName; // 订单状态名称
    private String status; // 订单状态
    private String dispatchingWay; // 配送方式
    private String dispatchingWayName; // 配送方式名称
    private String remark; // 备注
    private Date createTime; // 创建时间

    private String name; //收件人
    private String mobile; //手机号码
    private String address; //收件地址

    private List<PrintPhoto> printPhotoList;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDispatchingWay() {
        return dispatchingWay;
    }

    public void setDispatchingWay(String dispatchingWay) {
        this.dispatchingWay = dispatchingWay;
    }

    public String getDispatchingWayName() {
        return dispatchingWayName;
    }

    public void setDispatchingWayName(String dispatchingWayName) {
        this.dispatchingWayName = dispatchingWayName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PrintPhoto> getPrintPhotoList() {
        return printPhotoList;
    }

    public void setPrintPhotoList(List<PrintPhoto> printPhotoList) {
        this.printPhotoList = printPhotoList;
    }

    @Override
    public String toString() {
        return "ReservationDetailDTO{" +
                "shopName='" + shopName + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", cost=" + cost +
                ", statusName='" + statusName + '\'' +
                ", status='" + status + '\'' +
                ", dispatchingWay='" + dispatchingWay + '\'' +
                ", dispatchingWayName='" + dispatchingWayName + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", printPhotoList=" + printPhotoList +
                '}';
    }
}


