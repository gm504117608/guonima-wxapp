package com.guonima.wxapp.service;

import com.alibaba.fastjson.JSONObject;
import com.guonima.wxapp.config.Environment;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.MemberDO;
import com.guonima.wxapp.exception.ServiceException;
import com.guonima.wxapp.redis.RedisClient;
import com.guonima.wxapp.util.AESUtil;
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
        MemberDO member = getMemberInfo(memberDO);
        Long id = null;
        try {
            if (null == member) { // 新增
                dao.insert("memberMapper.insert", memberDO);
                id = memberDO.getId();
            } else { // 修改
                dao.update("memberMapper.update", memberDO);
                id = member.getId();
            }
        } catch (Exception e) {
            throw new ServiceException("微信登录我方应用出现错误：" + e.getMessage());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);
        // 缓存token处理
        result.put("token", cacheToken(token, memberDO.getOpenid(), id));
        return result;
    }

    @Override
    public String getWxOpenidSessionKey(MemberDO memberDO, String code, String encryptedData, String iv) {

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
            // 通过sessionKey 和 iv 来解密 encryptedData 数据获取 UnionID (小程序绑定公众号之后的关联值) 。
            String session_key = (String) wxInfo.get("session_key"); //
            String openid = (String) wxInfo.get("openid");
            Integer expires_in = (Integer) wxInfo.get("expires_in");
            memberDO.setOpenid(openid);
            // 对encryptedData加密数据进行AES解密
            decryptEncryptedData(memberDO, encryptedData, session_key, iv);
        }
        return (String) wxInfo.get("errmsg");
    }

    @Override
    public int save(MemberDO memberDO) {
        try {
            return dao.update("memberMapper.update", memberDO);
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
     * 用户判断用户登录是否失效
     *
     * @param token  回话token值
     * @param openId 微信openid
     * @param id     用户id
     */
    private String cacheToken(String token, String openId, Long id) {

        if (StringUtils.isNotEmpty(token)) {
            String str = RedisClient.get(token, String.class);
            if (StringUtils.isNotEmpty(str)) {
                return token;
            }
        }
        token = SecurityUtil.createToken(openId);
        // 24 * 60 * 60
        try {
            RedisClient.set(token, 86400, String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 对encryptedData加密数据进行AES解密
     *
     * @param memberDO
     * @param encryptedData
     * @param session_key
     * @param iv
     */
    private void decryptEncryptedData(MemberDO memberDO, String encryptedData, String session_key, String iv) {
        try {
            // 解密出来的数据包含了用户的所有信息
            String result = AESUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                JSONObject userInfo = JSONObject.parseObject(result);
                memberDO.setUnionid(userInfo.getString("unionId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("解密微信encryptedData参数出现错误：" + e.getMessage());
        }
    }

}
