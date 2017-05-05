package com.guonima.wxapp.service;

import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.ConfigurationDO;
import com.guonima.wxapp.redis.RedisClient;
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
        return getBaseConfigurationInfoRedis(type);
    }

    @Override
    public ConfigurationDO getBaseConfiguration(String type, String code) {
        List<ConfigurationDO> list = getBaseConfigurationInfoRedis(type);
        for(ConfigurationDO cdo : list){
            if(cdo.getCode().equals(code)){
                return cdo;
            }
        }
        return null;
    }

    /**
     * 获取基础配置信息表中指定 类型 的信息
     *
     * @param type 配置类型
     * @return
     */
    private List<ConfigurationDO> getBaseConfigurationInfoRedis(String type) {
        List<ConfigurationDO> list = RedisClient.lGet(type, ConfigurationDO.class);
        if (null == list) {
            log.info("取缓存中配置类型为【" + type + "】的配置信息");
            ConfigurationDO cdo = new ConfigurationDO();
            cdo.setType(type);
            list = (List<ConfigurationDO>) dao.findForList("configurationMapper.findBaseConfigurationData", cdo);
            try {
                RedisClient.set(type, list);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
