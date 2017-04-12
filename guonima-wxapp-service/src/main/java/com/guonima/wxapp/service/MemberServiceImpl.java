package com.guonima.wxapp.service;

import com.guonima.wxapp.config.Environment;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.MemberDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 会员信息操作类
 *
 */
@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public MemberDO getMemberById(Long memberId) {
        System.out.println(Environment.BAIDU_AK);

        MemberDO member = new MemberDO();
        member.setId(memberId);
        return (MemberDO) dao.findForObject("memberMapper.findMemberInfo", member);
    }

}
