package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.MemberDO;
import com.guonima.wxapp.domain.ReservationDO;
import com.guonima.wxapp.domain.ShopDO;
import com.guonima.wxapp.service.MemberService;
import com.guonima.wxapp.service.OrderService;
import com.guonima.wxapp.service.PaymentService;
import com.guonima.wxapp.service.ShopService;
import com.guonima.wxapp.service.trade.wxpay.util.WXSignUtils;
import com.guonima.wxapp.service.trade.wxpay.util.WxXmlParseUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.SortedMap;

/**
 * 订单支付controller类
 *
 * @author : guonima
 * @create : 2017-04-29-23:58
 */
@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    private final static Logger log = Logger.getLogger(PaymentController.class);

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

    /**
     * 微信支付回调函数
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST, value = "/callBack/wxpay")
    public void wxpayCallback(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String xmlResult = getWxPayCallbackParam(request, writer);

        log.info("微信支付回调返回参数： " + xmlResult);
        System.out.println("微信支付回调返回参数： " + xmlResult);

        //解析 回调返回结果 成 Map
        SortedMap<String, String> result = WxXmlParseUtil.getCallbackParam(xmlResult);
        if (MapUtils.isEmpty(result)) {
            writer.write(WxXmlParseUtil.backWeixin("SUCCESS", "未获取到微信返回的结果"));
        }
        String return_code = result.get("return_code");
        if ("SUCCESS".equals(return_code)) {
            // 反校验签名
            String sign = WXSignUtils.createSign("UTF-8", result);
            if (sign.equals(result.get("sign"))) {
                // 处理业务逻辑，写支付信息
                if ("FAIL".equalsIgnoreCase(paymentService.wxpayCallback(result))) {
                    writer.write(WxXmlParseUtil.backWeixin("FAIL", "第三方服务器支付信息保存失败"));
                } else {
                    writer.write(WxXmlParseUtil.backWeixin("SUCCESS", "OK"));
                }
            } else {
                writer.write(WxXmlParseUtil.backWeixin("FAIL", "签名失败"));
            }
        } else {
            writer.write(WxXmlParseUtil.backWeixin("FAIL", "微信返回结果状态码是失败"));
        }
    }

    /**
     * 获取微信回调函数返回来的参数信息
     *
     * @param request
     * @param writer
     * @return
     */
    private String getWxPayCallbackParam(HttpServletRequest request, PrintWriter writer) {
        // 读取回调函数返回来的参数信息
        StringBuffer sb = new StringBuffer();
        InputStream in = null;
        BufferedReader br = null;
        try {
            in = request.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            writer.write(WxXmlParseUtil.backWeixin("FAIL", "未获取到微信返回的结果"));
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
