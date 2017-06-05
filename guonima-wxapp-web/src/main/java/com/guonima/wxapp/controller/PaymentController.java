package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.MemberDO;
import com.guonima.wxapp.domain.ReservationDO;
import com.guonima.wxapp.domain.ShopDO;
import com.guonima.wxapp.service.MemberService;
import com.guonima.wxapp.service.OrderService;
import com.guonima.wxapp.service.PaymentService;
import com.guonima.wxapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 订单支付controller类
 *
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

    @Autowired
    private ShopService shopService;


    @RequestMapping(method = RequestMethod.GET, value = "/{orderNo}")
    public Response getUnifiedOrderInfo(@PathVariable("orderNo") String orderNo) {

        ReservationDO reservationDO = orderService.findReservationInfo(orderNo);
        MemberDO memberDO = memberService.getMemberById(reservationDO.getMemberId());
        ShopDO shopDO = shopService.getShopsInfo(reservationDO.getShopId());
        String body = memberDO.getNickName() + "在" + shopDO.getName() + "打印照片";
        return paymentService.unifiedOrder(orderNo, reservationDO.getCost(), body);
    }


}
