package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.*;
import com.guonima.wxapp.service.OrderService;
import com.guonima.wxapp.service.ShopService;
import com.guonima.wxapp.util.OrderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 订单相关信息controller
 *
 * @author : guonima
 * @create : 2017-04-29-23:58
 */
@RestController
@RequestMapping("/")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopService shopService;

    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    public Response savePrintPhotoOrder(@RequestBody ReservationDTO reservationDTO) {
        StringBuilder sb = new StringBuilder();
        if (null == reservationDTO.getMemberId()) {
            sb.append("会员id不能为空;");
        }
        if (null == reservationDTO.getShopId()) {
            sb.append("店铺id不能为空;");
        }
        if (StringUtils.isEmpty(reservationDTO.getPrintPhotographIds())) {
            sb.append("没有选择需要打印的照片信息;");
        }
        if (sb.length() != 0) {
            error(2000, sb.toString());
        }
        String orderNo = OrderUtil.createOrderNo();
        // 处理订单和照片关联信息
        orderService.saveReservationPrintPhotograph(reservationDTO.getPrintPhotographIds(), orderNo);
        // 处理订单信息
        ReservationDO rdo = new ReservationDO();
        reservationDTO2ReservationDO(reservationDTO, rdo);
        rdo.setOrderNo(orderNo);
        orderService.saveReservation(rdo);
        return success(orderNo);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orders/payment")
    public Response savePrintPhotoOrderPayment(@RequestBody OrderPaymentDTO orderPaymentDTO){
        String[] printPhotographIdArray = orderPaymentDTO.getIds().split(",");
        if(null == printPhotographIdArray || printPhotographIdArray.length == 0){
            return error(2000, "需要打印的照片信息获取不到，请重新操作");
        }
        String[] amount = orderPaymentDTO.getAmounts().split(",");
        PrintPhotographDO ppdo = null;
        ShopPrintCostConfigDO spccdo = null;
        BigDecimal result = new BigDecimal(0);
        for(int i = 0, len = printPhotographIdArray.length; i < len; i++){
            ppdo = shopService.findPrintPhotographInfo(Long.valueOf(printPhotographIdArray[i]));
            spccdo = shopService.getShopPrintCostConfig(ppdo.getType());
            result = result.add(spccdo.getPrice().multiply(new BigDecimal(amount[i])));
        }
        if(!result.equals(orderPaymentDTO.getCost())){
            return error(2000, "打印照片所花费的金额不正确");
        }
        // 更新订单金额
        ReservationDO rdo = new ReservationDO();
        rdo.setOrderNo(orderPaymentDTO.getOrderNo());
        rdo.setCost(result);
        orderService.updateReservation(rdo);
        // 更新打印照片数量
        orderService.updateReservationPrintPhotograph(orderPaymentDTO.getIds(),
                orderPaymentDTO.getOrderNo(), orderPaymentDTO.getAmounts());
        return success(null);
    }

    /**
     * 订单实体之间相互转换
     * @param rdto 界面传入实体
     * @param rdo 数据库对应实体
     */
    private void reservationDTO2ReservationDO(ReservationDTO rdto, ReservationDO rdo){
        rdo.setMemberId(rdto.getMemberId());
        rdo.setShopId(rdto.getShopId());
        rdo.setType("S01");
        rdo.setCost(calculatePrintPhotoCost(rdto.getPrintPhotographIds()));
        rdo.setRemark("照片打印订单信息生成");
    }

    /**
     * 计算下单打印照片信息需要花费的金额
     * @param printPhotographIds 打印照片信息唯一标识id串
     * @return
     */
    private BigDecimal calculatePrintPhotoCost(String printPhotographIds){
        String[] printPhotographIdArray = printPhotographIds.split(",");
        if(null == printPhotographIdArray || printPhotographIdArray.length == 0){
            return new BigDecimal(0);
        }
        PrintPhotographDO ppdo = null;
        ShopPrintCostConfigDO spccdo = null;
        BigDecimal result = new BigDecimal(0);
        for(String id : printPhotographIdArray){
            ppdo = shopService.findPrintPhotographInfo(Long.valueOf(id));
            spccdo = shopService.getShopPrintCostConfig(ppdo.getType());
            result = result.add(spccdo.getPrice()); // 默认值打印一张照片
        }
        return result;
    }

}