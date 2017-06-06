package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author guonima
 * @create 2017-06-06 9:55
 */
public class PaymentDO implements Serializable {

    private static final long serialVersionUID = -12123847878501L;

    private Long id; // 唯一标识id
    private String orderNo; // 订单号
    private String couponNo; // 优惠券号
    private BigDecimal cost; // 支付金额
    private String transactionId; // 支付订单号
    private String remark; // 备注
    private Date finishTime; // 支付完成时间
    private Date createTime; // 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PaymentDO{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", couponNo='" + couponNo + '\'' +
                ", cost=" + cost +
                ", transactionId='" + transactionId + '\'' +
                ", remark='" + remark + '\'' +
                ", finishTime=" + finishTime +
                ", createTime=" + createTime +
                '}';
    }
}
