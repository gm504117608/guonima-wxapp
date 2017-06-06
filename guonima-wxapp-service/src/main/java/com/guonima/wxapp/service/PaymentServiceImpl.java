package com.guonima.wxapp.service;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.PaymentDO;
import com.guonima.wxapp.domain.ReservationDO;
import com.guonima.wxapp.service.trade.wxpay.config.WxpayConfig;
import com.guonima.wxapp.service.trade.wxpay.util.HttpsUtils;
import com.guonima.wxapp.service.trade.wxpay.util.WXSignUtils;
import com.guonima.wxapp.service.trade.wxpay.util.WxXmlParseUtil;
import com.guonima.wxapp.util.DateUtil;
import com.guonima.wxapp.util.HttpUtil;
import com.guonima.wxapp.util.IPUtils;
import com.guonima.wxapp.util.XmlParserUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author : guonima
 * @create : 2017-05-20-13:53
 */
@Service("paymentServiceImpl")
public class PaymentServiceImpl implements PaymentService {

    private final static Logger log = Logger.getLogger(PaymentServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Autowired
    private OrderService orderService;

    @Override
    public Response unifiedOrder(String orderNo, BigDecimal cost, String body) {

        SortedMap<String, String> param = new TreeMap<String, String>();
        param.put("appid", WxpayConfig.APP_ID); // 微信分配的小程序ID
        param.put("mch_id", WxpayConfig.MCH_ID); // 微信支付分配的商户号
//        param.put("device_info", ""); // 终端设备号
        param.put("nonce_str", RandomStringUtils.randomAlphanumeric(28)); // 随机字符串
        param.put("sign_type", "MD5"); // 签名类型
        param.put("body", body); // 商品描述
//        param.put("detail", ""); // 商品详情
//        param.put("attach", ""); // 附加数据
        param.put("out_trade_no", orderNo); // 商户订单号
//        param.put("fee_type", ""); // 货币类型
        cost = cost.multiply(new BigDecimal(100));
        param.put("total_fee", cost.toString()); // 总金额 （单位 分）
        param.put("spbill_create_ip", IPUtils.getClientAddress(HttpUtil.getHttpServletRequest())); // 终端IP
        param.put("time_start", DateUtil.format(new Date(), DateUtil.FULL_NOT_LINE_DATE)); // 交易起始时间
        param.put("time_expire", timeExpire()); // 交易结束时间
        param.put("trade_type", "JSAPI"); // 交易类型
        param.put("notify_url", WxpayConfig.NOTIFY_URL); // 通知地址

        // 生成签名
        String sign = WXSignUtils.createSign("UTF-8", param);
        param.put("sign", sign); // 签名

        return unifiedOrder(param);
    }

    /**
     * 统一下单处理
     *
     * @param param 统一下单参数
     * @return
     */
    private Response unifiedOrder(SortedMap<String, String> param) {

        String xmlParam = WxXmlParseUtil.getUnifiedOrderParam(param);
        String unifiedOrderResult = HttpsUtils.httpsRequest(WxpayConfig.UNIFIED_ORDER_URL, "POST", xmlParam).toString();

        Response response = new Response();
        if (StringUtils.isEmpty(unifiedOrderResult)) {
            log.warn("微信支付统一下单失败 ： 调用返回参数为空");
            response.setCode(2000);
            response.setMessage("微信支付统一下单失败");
            return response;
        }
        String return_code = XmlParserUtil.getNodeValue(unifiedOrderResult, "/xml/return_code");
        if (!"SUCCESS".equalsIgnoreCase(return_code)) {
            log.warn("微信支付统一下单失败 ：  " + XmlParserUtil.getNodeValue(unifiedOrderResult, "/xml/return_msg"));
            response.setCode(2000);
            response.setMessage("微信支付统一下单失败");
            return response;
        }
        String result_code = XmlParserUtil.getNodeValue(unifiedOrderResult, "/xml/result_code");
        if (!"SUCCESS".equalsIgnoreCase(result_code)) {
            log.warn("微信支付统一下单失败 ： " + XmlParserUtil.getNodeValue(unifiedOrderResult, "/xml/err_code_des"));
            response.setCode(2000);
            response.setMessage("微信支付统一下单失败");
            return response;
        }
        String prepay_id = XmlParserUtil.getNodeValue(unifiedOrderResult, "/xml/prepay_id");
        response.setCode(0);
        response.setMessage("操作成功");
        response.setData(getPaymentParam(prepay_id));
        return response;
    }

    /**
     * 获取前端支付参数
     *
     * @param prepay_id 预支付id
     */
    private SortedMap<String, String> getPaymentParam(String prepay_id){
        SortedMap<String, String> orderParams = new TreeMap<String, String>();
        // 下单成功获取预支付交易id之后的参数：开始生成签名
        orderParams.put("appid", WxpayConfig.APP_ID); // 微信开放平台审核通过的应用APPID
        orderParams.put("partnerid", WxpayConfig.MCH_ID); // 微信支付分配的商户号
        orderParams.put("prepayid", prepay_id); // 预支付交易会话ID
        orderParams.put("package", "Sign=WXPay"); // 扩展字段
        orderParams.put("noncestr", RandomStringUtils.randomAlphanumeric(28)); // 随机字符串
        orderParams.put("timestamp", String.valueOf(new Date().getTime())); // 时间戳
        orderParams.put("sign", WXSignUtils.createSign("UTF-8", orderParams)); // 签名

        return orderParams;
    }

    /**
     * 订单失效时间
     */
    private String timeExpire() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, WxpayConfig.TIMEOUT_EXPRESS);
        return DateUtil.format(now.getTime(), DateUtil.FULL_NOT_LINE_DATE);
    }

    @Override
    public String wxpayCallback(Map<String, String> param) {
        String orderNo = param.get("out_trade_no"); // 业务订单号
        // 获取订单信息
        ReservationDO rd = orderService.findReservationInfo(orderNo);
        if (null == rd) {
            return "FAIL";
        }
        BigDecimal amount = new BigDecimal(Integer.parseInt(param.get("total_fee")));// 单位 分
        amount = amount.divide(new BigDecimal(100)); // 转换成元
        // 订单金额校验
        if (amount.compareTo(rd.getCost()) != 0) {
            return "FAIL";
        }
        // 订单信息
        rd = new ReservationDO();
        rd.setOrderNo(orderNo);
        rd.setStatus("S02"); // 待发货
        // 支付信息
        PaymentDO pd = new PaymentDO();
        pd.setCost(amount);
        pd.setCouponNo(null);
        pd.setOrderNo(orderNo);
        pd.setTransactionId(param.get("transaction_id"));
        pd.setRemark("微信订单支付");
        pd.setFinishTime(DateUtil.parse(param.get("time_end"), DateUtil.FULL_NOT_LINE_DATE));

        try {
            dao.update("reservationMapper.update", rd);
            dao.insert("paymentMapper.insert", pd);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
        return "SUCCESS";
    }

}
