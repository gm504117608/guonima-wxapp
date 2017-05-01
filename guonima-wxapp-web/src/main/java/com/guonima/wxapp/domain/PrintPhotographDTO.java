package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author guonima
 * @create 2017-04-28 12:00
 */
public class PrintPhotographDTO implements Serializable {

    private static final long serialVersionUID = 4697983666949763147L;


    private Long id; //唯一标识id
    private Long memberId; //会员唯一标识id
    private Long shopId; //店铺唯一标识id
    private String description; //照片描述
    private String type; //打印类型
    private String typeName; //打印类型名称
    private String typeRemark; //打印类型备注
    private BigDecimal price; // 打印价格
    private String storeUrl; //照片存储路径
    private String clipping; //裁剪方式
    private String typesetting; //排版方式
    private Integer isPrint; // 是否已经打印【1：是；0：否】
    private String remark; // 备注
    private Date createTime;
    private Date modifyTime;

    public PrintPhotographDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeRemark() {
        return typeRemark;
    }

    public void setTypeRemark(String typeRemark) {
        this.typeRemark = typeRemark;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getClipping() {
        return clipping;
    }

    public void setClipping(String clipping) {
        this.clipping = clipping;
    }

    public String getTypesetting() {
        return typesetting;
    }

    public void setTypesetting(String typesetting) {
        this.typesetting = typesetting;
    }

    public Integer getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
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
        return "PrintPhotographDTO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", shopId=" + shopId +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", typeRemark='" + typeRemark + '\'' +
                ", price='" + price + '\'' +
                ", storeUrl='" + storeUrl + '\'' +
                ", clipping='" + clipping + '\'' +
                ", typesetting='" + typesetting + '\'' +
                ", isPrint=" + isPrint +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
