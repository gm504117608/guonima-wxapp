package com.guonima.wxapp.domain;

import java.io.Serializable;

public class MemberDTO implements Serializable {

    private static final long serialVersionUID = 4697983666949763147L;

    private Long memberId; // 主键

    public Long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    @Override
    public String toString() {
        return "MemberDO (" +
                "memberId=" + memberId +
                ')';
    }

}
