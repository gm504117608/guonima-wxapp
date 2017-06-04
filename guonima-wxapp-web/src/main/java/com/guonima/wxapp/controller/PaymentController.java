package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.MemberDO;
import com.guonima.wxapp.domain.ReservationDO;
import com.guonima.wxapp.service.MemberService;
import com.guonima.wxapp.service.OrderService;
import com.guonima.wxapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单支付controller类
 * @author : guonima
 * @create : 2017-04-29-23:58
 */
@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;


    @RequestMapping(method = RequestMethod.GET, value = "/{orderNo}")
    public Response getUnifiedOrderInfo(@PathVariable("orderNo") String orderNo) {

        ReservationDO reservationDO = orderService.findReservationInfo(orderNo);
        MemberDO memberDO = memberService.getMemberById(reservationDO.getMemberId());




        return null;

    }


}
