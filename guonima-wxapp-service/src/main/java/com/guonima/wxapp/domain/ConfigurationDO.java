package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author guonima
 * @create 2017-04-25 18:29
 */
public class ConfigurationDO implements Serializable {

    private static final long serialVersionUID = -6912749763147L;

    private Long id;
    private Long parentId; // 父级的唯一标识id
    private String code; // 代码值
    private String description; // 配置信息描述
    private String type; // 配置类型
    private Integer enabled; // 是否激活【1（可用）；0（不可用）】
    private String remark; // 备注
    private Date createTime;
    private Long createUser;
    private Date modifyTime;
    private Long modifyUser;

    public ConfigurationDO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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
        return "ConfigurationDO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", modifyTime=" + modifyTime +
                ", modifyUser=" + modifyUser +
                '}';
    }
}
