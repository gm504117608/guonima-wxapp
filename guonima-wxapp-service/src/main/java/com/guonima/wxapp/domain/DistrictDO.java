package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author guonima
 * @create 2017-04-24 14:58
 */
public class DistrictDO implements Serializable {

    private static final long serialVersionUID = -23457819502234L;

    private Long id;
    private String code;
    private String description;
    private Integer enabled;
    private Date createTime;

    public DistrictDO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "DistrictDO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", createTime=" + createTime +
                '}';
    }
}
