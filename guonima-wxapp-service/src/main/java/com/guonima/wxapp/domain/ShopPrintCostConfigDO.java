package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : guonima
 * @create : 2017-04-29-22:10
 */
public class ShopPrintCostConfigDO implements Serializable {

    private static final long serialVersionUID = 41469798353453L;

    private Long id; // 唯一标识ID
    private Long shopId;  // 店铺唯一标识ID
    private String code; // 打印配置代码
    private String description; // 打印费用信息描述
    private BigDecimal price; // 打印价格
    private String remark; // 备注
    private Integer enabled; // 是否激活【1（可用）；0（不可用）】
    private Date createTime;
    private Long createUser;
    private Date modifyTime;
    private Long modifyUser;

    public ShopPrintCostConfigDO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "ShopPrintCostConfigDO{" +
                "id=" + id +
                ", shopId=" + shopId +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", remark='" + remark + '\'' +
                ", enabled=" + enabled +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", modifyTime=" + modifyTime +
                ", modifyUser=" + modifyUser +
                '}';
    }
}
