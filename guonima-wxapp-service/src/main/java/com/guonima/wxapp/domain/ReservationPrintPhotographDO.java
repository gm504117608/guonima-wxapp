package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : guonima
 * @create : 2017-04-30-00:16
 */
public class ReservationPrintPhotographDO implements Serializable {

    private static final long serialVersionUID = -123137570L;

    private Long id; // 用户id
    private String orderNo; // 订单号
    private Long printPhotographId; // 打印照片唯一标识ID
    private Integer amount; // 打印数量
    private Date createTime; // 创建时间

    public ReservationPrintPhotographDO() {

    }

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

    public Long getPrintPhotographId() {
        return printPhotographId;
    }

    public void setPrintPhotographId(Long printPhotographId) {
        this.printPhotographId = printPhotographId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ReservationPrintPhotographDO{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", printPhotographId=" + printPhotographId +
                ", amount=" + amount +
                ", createTime=" + createTime +
                '}';
    }
}
