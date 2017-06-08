package com.guonima.wxapp.service;

import com.alibaba.fastjson.JSONObject;
import com.guonima.wxapp.config.Environment;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.MemberDO;
import com.guonima.wxapp.exception.ServiceException;
import com.guonima.wxapp.redis.RedisClient;
import com.guonima.wxapp.util.HttpUtil;
import com.guonima.wxapp.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员信息操作类
 */
@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

    private final static Logger log = Logger.getLogger(MemberServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public MemberDO getMemberById(Long memberId) {
        MemberDO member = new MemberDO();
        member.setId(memberId);
        return getMemberInfo(member);
    }

    @Override
    public Map<String, Object> login(MemberDO memberDO, String token) {
        if (StringUtils.isEmpty(token)) {
            token = SecurityUtil.createToken(memberDO.getOpenid());
        }
        MemberDO member = getMemberInfoRedis(memberDO, token);
        try {
            if (member == null) { // 新增
                dao.insert("memberMapper.insert", memberDO);
            } else { // 修改
                dao.update("memberMapper.update", memberDO);
            }
            setMemberInfoRedis(memberDO, token);
        } catch (Exception e) {
            throw new ServiceException("微信登录我方应用出现错误：" + e.getMessage());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", memberDO.getId());
        result.put("token", token);
        return result;
    }

    @Override
    public String getWxOpenidSessionKey(MemberDO memberDO, String code) {

        String url = Environment.WX_CODE_URL +
                "?appid=" + Environment.WX_APPID +
                "&secret=" + Environment.WX_SECRET +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        String result = HttpUtil.getResponseByPost(url);
        log.debug("通过登录凭证获取微信信息 ： " + result);

        if (StringUtils.isEmpty(result)) {
            return "通过登录凭证获取微信信息出现异常";
        }
        JSONObject wxInfo = JSONObject.parseObject(result);
        Integer errCode = (Integer) wxInfo.get("errcode");
        if (null == errCode) {
            // 通过sessionKey 和 iv 来解密 encryptedData 数据获取 UnionID(小程绑定公众号之后的关联值) 。
//            String session_key = (String) wxInfo.get("session_key"); //
            String openid = (String) wxInfo.get("openid");
//            Integer expires_in = (Integer) wxInfo.get("expires_in");
            memberDO.setOpenid(openid);
        }
        return (String) wxInfo.get("errmsg");
    }

    @Override
    public int save(MemberDO memberDO, String token) {
        try {
            int result = dao.update("memberMapper.update", memberDO);
            setMemberInfoRedis(memberDO, token);
            return result;
        } catch (Exception e) {
            throw new ServiceException("修改用户信息出现错误：", e.getCause());
        }
    }

    /**
     * 获取会员信息
     *
     * @param memberDO
     * @return
     */
    private MemberDO getMemberInfo(MemberDO memberDO) {
        return (MemberDO) dao.findForObject("memberMapper.findMemberInfo", memberDO);
    }

    /**
     * 获取会员信息，带缓存功能
     *
     * @param memberDO 用户信息对象
     * @param token    回话唯一标识
     * @return
     */
    private MemberDO getMemberInfoRedis(MemberDO memberDO, String token) {
        MemberDO mdo = RedisClient.get("member-" + token, MemberDO.class);
        if (null == mdo) {
            mdo = (MemberDO) dao.findForObject("memberMapper.findMemberInfo", memberDO);
            try {
                RedisClient.set("member-" + token, mdo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mdo;
    }

    /**
     * 更新缓存中用户信息
     *
     * @param memberDO 用户信息对象
     * @param token    回话唯一标识
     * @return
     */
    private void setMemberInfoRedis(MemberDO memberDO, String token) {
        try {
            RedisClient.set("member-" + token, memberDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
