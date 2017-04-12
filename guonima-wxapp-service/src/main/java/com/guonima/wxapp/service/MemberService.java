package com.guonima.wxapp.service;


import com.guonima.wxapp.domain.MemberDO;

public interface MemberService {

    /**
     * 通过会员id获取会员信息
     *
     * @param memberId 会员id
     * @return
     */
    MemberDO getMemberById(Long memberId);
}
