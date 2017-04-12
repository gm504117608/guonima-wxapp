package com.guonima.wxapp.controller;

import com.guonima.wxapp.domain.MemberDO;
import com.guonima.wxapp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(method = RequestMethod.GET, value = "/members/{id}")
    public MemberDO getUser(@PathVariable("id") Long id) throws IOException {
        return memberService.getMemberById(id);
    }

}