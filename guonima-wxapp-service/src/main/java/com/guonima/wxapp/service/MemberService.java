package com.guonima.wxapp.service;


import com.guonima.wxapp.domain.MemberDO;

import java.util.Map;

public interface MemberService {

    /**
     * 通过会员id获取会员信息
     *
     * @param memberId 会员id
     * @return
     */
    public MemberDO getMemberById(Long memberId);

    /**
     * 微信小程序登录成功之后处理自己业务逻辑
     *
     * @param memberDO 会员信息实体
     * @param token    回话唯一标识
     * @return 返回一个session，其有有效时间，前端接口传参需带有
     */
    public Map<String, Object> login(MemberDO memberDO, String token);

    /**
     * 通过 code 去微信服务器获取 session_key 和 openid
     *
     * @param memberDO 会员信息实体
     * @param code     微信登录凭证
     */
    public String getWxOpenidSessionKey(MemberDO memberDO, String code);

    /**
     * 保存用户一些可修改数据
     *
     * @param memberDO 会员信息实体
     * @param token    回话唯一标识
     */
    public int save(MemberDO memberDO, String token);

}
