package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.MemberDO;
import com.guonima.wxapp.domain.MemberDTO;
import com.guonima.wxapp.service.MemberService;
import com.guonima.wxapp.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(method = RequestMethod.GET, value = "/members/{id}")
    public Response getMemberInfo(@PathVariable("id") Long id) {
        return success(memberService.getMemberById(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public Response login(@RequestBody MemberDTO memberDTO, @RequestHeader("token") String token) {
        StringBuffer sb = new StringBuffer();
        String code = memberDTO.getCode();
        if (StringUtils.isEmpty(code)) {
            sb.append("登录凭证code不能为空;");
        }
        if (sb.length() != 0) {
            return error(1000, sb.toString());
        }
        MemberDO memberDO = new MemberDO();
        MemberDTO2MemberDO(memberDTO, memberDO);
        String result = memberService.getWxOpenidSessionKey(memberDO, code,
                memberDTO.getEncryptedData(), memberDTO.getIv());
        if (StringUtils.isNotEmpty(result)) {
            return error(2000, result);
        }
        return success(memberService.login(memberDO, token));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/members")
    public Response save(@RequestBody MemberDTO memberDTO, @RequestHeader("token") String token) {
        StringBuilder sb = new StringBuilder();
        String signature = memberDTO.getSignature();
        if (StringUtils.isNotEmpty(signature)) {
            if (!CommonUtil.checkLength(signature, 1, 180)) {
                sb.append("【个性签名】长度在1到180之间;");
            }
        }
        String mobile = memberDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            sb.append("【联系电话】不能为空;");
        } else {
            if (!CommonUtil.isPhoneNum(mobile)) {
                sb.append("【联系电话】格式不正确;");
            }
        }
        String email = memberDTO.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            if (!CommonUtil.checkEmail(email)) {
                sb.append("【邮箱】格式不正确;");
            }
        }
        if (sb.length() != 0) {
            return error(1000, sb.toString());
        }
        MemberDO memberDO = new MemberDO();
        MemberDTO2MemberDO(memberDTO, memberDO);
        return success(memberService.save(memberDO));
    }

    /**
     * 将界面传入实体数据赋值到数据库表对应实体数据
     *
     * @param memberDTO 界面传入会员实体信息
     * @param memberDO  数据库表会员实体信息
     */
    private void MemberDTO2MemberDO(MemberDTO memberDTO, MemberDO memberDO) {
        memberDO.setId(memberDTO.getId());
        memberDO.setNickName(memberDTO.getNickName());
        memberDO.setMobile(memberDTO.getMobile());
        memberDO.setEmail(memberDTO.getEmail());
        memberDO.setGender(memberDTO.getGender());
        memberDO.setCountry(memberDTO.getCountry());
        memberDO.setProvince(memberDTO.getProvince());
        memberDO.setCity(memberDTO.getCity());
        memberDO.setAvatarUrl(memberDTO.getAvatarUrl());
        memberDO.setSignature(memberDTO.getSignature());
    }

}