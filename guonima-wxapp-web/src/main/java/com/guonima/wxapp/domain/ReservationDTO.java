package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : guonima
 * @create : 2017-04-30-01:01
 */
public class ReservationDTO implements Serializable {

    private static final long serialVersionUID = -3847878501L;


    private Long id; // 唯一标识id
    private Long memberId; // 会员唯一的标识
    private Long shopId; // 店铺唯一标识
    private String orderNo; // 订单号
    private BigDecimal cost; // 花费金额
    private String type; // 订单状态
    private String remark; // 备注
    private Date createTime; // 创建时间
    private Date modifyTime; // 修改时间
    private String printPhotographIds; // 打印照片信息拼串

    public ReservationDTO() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPrintPhotographIds() {
        return printPhotographIds;
    }

    public void setPrintPhotographIds(String printPhotographIds) {
        this.printPhotographIds = printPhotographIds;
    }

    @Override
    public String toString() {
        return "ReservationDO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", shopId=" + shopId +
                ", orderNo='" + orderNo + '\'' +
                ", cost=" + cost +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", printPhotographIds='" + printPhotographIds + '\'' +
                '}';
    }
}
