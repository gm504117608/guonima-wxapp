package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单支付前界面传输实体类
 *
 * @author : guonima
 * @create : 2017-05-01-17:44
 */
public class OrderPaymentDTO implements Serializable {

    private static final long serialVersionUID = -1092389474L;

    private String ids; // 需要打印照片信息唯一标识id 拼串 格式 xxx,xxx,xxx
    private String amounts; // 打印照片数量 拼串 格式 xxx,xxx,xxx
    private String orderNo; // 订单号
    private Long memberId; // 会员唯一的标识
    private Long shopId; // 店铺唯一标识
    private BigDecimal cost; // 需要支付金额
    private Long consignmentId; // 收件人地址唯一标识id
    private String dispatchingWay; // 配送方式



    public OrderPaymentDTO() {

    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(Long consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getDispatchingWay() {
        return dispatchingWay;
    }

    public void setDispatchingWay(String dispatchingWay) {
        this.dispatchingWay = dispatchingWay;
    }

    @Override
    public String toString() {
        return "OrderPaymentDTO{" +
                "ids='" + ids + '\'' +
                ", amounts='" + amounts + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", memberId=" + memberId +
                ", shopId=" + shopId +
                ", cost=" + cost +
                ", consignmentId=" + consignmentId +
                ", dispatchingWay='" + dispatchingWay + '\'' +
                '}';
    }
}

