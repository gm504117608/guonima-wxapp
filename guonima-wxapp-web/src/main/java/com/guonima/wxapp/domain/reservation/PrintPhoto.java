package com.guonima.wxapp.domain.reservation;

import java.io.Serializable;

/**
 * @author guonima
 * @create 2017-05-05 11:43
 */
public class PrintPhoto implements Serializable {

    private static final long serialVersionUID = 123123123123L;

    private String description; //照片描述
    private String type; //打印类型
    private String storeUrl; //照片存储路径
    private Integer amount; // 打印数量

    public PrintPhoto(){};

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

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PrintPhoto{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", storeUrl='" + storeUrl + '\'' +
                ", amount=" + amount +
                '}';
    }
}
