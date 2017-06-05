package com.guonima.wxapp.service;

import com.guonima.wxapp.domain.FeedbackDO;

/**
 * @author guonima
 * @create 2017-04-25 17:40
 */
public interface FeedbackService {

    /**
     * 保存用户反馈信息
     *
     * @param feedbackDO 用户反馈实体
     * @return
     */
    public int save(FeedbackDO feedbackDO);
}
