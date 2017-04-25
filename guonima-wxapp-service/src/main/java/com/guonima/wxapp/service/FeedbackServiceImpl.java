package com.guonima.wxapp.service;

import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.FeedbackDO;
import com.guonima.wxapp.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author guonima
 * @create 2017-04-25 17:40
 */
@Service("feedbackServiceImpl")
public class FeedbackServiceImpl implements FeedbackService {

    private final static Logger log = Logger.getLogger(FeedbackServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int save(FeedbackDO feedbackDO) {
        try {
            return dao.save("feedbackMapper.insert", feedbackDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("用户反馈信息保存出现错误： " + e.getMessage());
        }
    }
}
