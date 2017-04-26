package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.FeedbackDO;
import com.guonima.wxapp.service.BaseConfigurationService;
import com.guonima.wxapp.service.FeedbackService;
import com.guonima.wxapp.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户反馈信息controller类
 * @author guonima
 * @create 2017-04-25 17:39
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private BaseConfigurationService baseConfigurationService;

    @RequestMapping(method = RequestMethod.GET, value = "/type")
    public Response getFeedbackType() {
        return success(baseConfigurationService.getBaseConfiguration("feedback"));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public Response save(@RequestBody FeedbackDO feedbackDO) {
        StringBuilder sb = new StringBuilder();
        String type = feedbackDO.getType();
        if (StringUtils.isEmpty(type)) {
            sb.append("【反馈类型】不能为空;");
        }
        String mobile = feedbackDO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            sb.append("【联系电话】不能为空;");
        } else {
            if (!CommonUtil.isPhoneNum(mobile)) {
                sb.append("【联系电话】格式不正确;");
            }
        }
        String content = feedbackDO.getContent();
        if (StringUtils.isNotEmpty(content)) {
            if(!CommonUtil.checkLength(content, 0, 400)){
                sb.append("【反馈内容】长度在0到400之间;");
            }
        }
        if (sb.length() != 0) {
            return error(2000, sb.toString());
        }
        return success(feedbackService.save(feedbackDO));
    }

}
