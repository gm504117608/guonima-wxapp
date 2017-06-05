package com.guonima.wxapp.service;

import com.guonima.wxapp.domain.ConfigurationDO;

import java.util.List;

/**
 * @author guonima
 * @create 2017-04-25 18:25
 */
public interface BaseConfigurationService {

    /**
     * 获取基本配置信息
     *
     * @param type 配置类型
     * @return
     */
    public List<ConfigurationDO> getBaseConfiguration(String type);

    /**
     * 获取基本配置信息
     *
     * @param type 配置类型
     * @param code 代码值
     * @return
     */
    public ConfigurationDO getBaseConfiguration(String type, String code);
}
