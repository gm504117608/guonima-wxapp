package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : guonima
 * @create : 2017-04-30-00:03
 */
public class ReservationDO implements Serializable {

    private static final long serialVersionUID = -3847878501L;


    private Long id; // 唯一标识id
    private Long memberId; // 会员唯一的标识
    private Long shopId; // 店铺唯一标识
    private Long consignmentId; // 收货地址唯一标识
    private String orderNo; // 订单号
    private BigDecimal cost; // 话费金额
    private String status; // 订单状态
    private String dispatchingWay; // 配送方式
    private String remark; // 备注
    private Date createTime; // 创建时间
    private Date modifyTime; // 修改时间

    public ReservationDO() {
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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(Long consignmentId) {
        this.consignmentId = consignmentId;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "ReservationDO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", shopId=" + shopId +
                ", consignmentId=" + consignmentId +
                ", orderNo='" + orderNo + '\'' +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                ", dispatchingWay='" + dispatchingWay + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
