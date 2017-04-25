package com.guonima.wxapp.service;

import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.ConfigurationDO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author guonima
 * @create 2017-04-25 18:25
 */
@Service("baseConfigurationServiceImpl")
public class BaseConfigurationServiceImpl implements BaseConfigurationService {

    private final static Logger log = Logger.getLogger(BaseConfigurationServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<ConfigurationDO> getBaseConfiguration(String type) {
        ConfigurationDO cdo = new ConfigurationDO();
        cdo.setType(type);
        return (List<ConfigurationDO>) dao.findForList("configurationMapper.findBaseConfigurationData", cdo);
    }

    @Override
    public ConfigurationDO getBaseConfiguration(String type, String code) {
        ConfigurationDO cdo = new ConfigurationDO();
        cdo.setType(type);
        cdo.setCode(code);
        return (ConfigurationDO) dao.findForList("configurationMapper.findBaseConfigurationData", cdo);
    }
}
