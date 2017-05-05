package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.*;
import com.guonima.wxapp.domain.common.Pageable;
import com.guonima.wxapp.domain.reservation.PrintPhoto;
import com.guonima.wxapp.domain.reservation.ReservationDTO;
import com.guonima.wxapp.domain.reservation.ReservationDetailDTO;
import com.guonima.wxapp.service.BaseConfigurationService;
import com.guonima.wxapp.service.ConsignmentAddressService;
import com.guonima.wxapp.service.OrderService;
import com.guonima.wxapp.service.ShopService;
import com.guonima.wxapp.util.OrderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private BaseConfigurationService baseConfigurationService;

    @Autowired
    private ConsignmentAddressService consignmentAddressService;

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
        rdo.setConsignmentId(orderPaymentDTO.getConsignmentId());
        rdo.setDispatchingWay(orderPaymentDTO.getDispatchingWay());
        orderService.updateReservation(rdo);
        // 更新打印照片数量
        orderService.updateReservationPrintPhotograph(orderPaymentDTO.getIds(),
                orderPaymentDTO.getOrderNo(), orderPaymentDTO.getAmounts());
        return success(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders/config/{type}")
    public Response getBaseConfiguration(@PathVariable("type") String type) {
       return success(baseConfigurationService.getBaseConfiguration(type));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders/{orderNo}")
    public Response getPrintPhotoOrderDetailInfo(@PathVariable("orderNo") String orderNo) {
        // 订单信息
        ReservationDO rdo = orderService.findReservationInfo(orderNo);
        // 店铺信息
        ShopDO sdo = shopService.getShopsInfo(rdo.getShopId());
        // 收货地址信息
        ConsigneeAddressDO cado = consignmentAddressService.getConsignmentAddressById(rdo.getConsignmentId());
        // 订单详细内容信息
        List<Map<String, Object>> list = orderService.findReservationPrintPhotographInfo(orderNo);

        ReservationDetailDTO reservationDetailDTO = new ReservationDetailDTO();

        reservationDetailDTO.setOrderNo(rdo.getOrderNo());
        reservationDetailDTO.setCost(rdo.getCost());
        ConfigurationDO cdo = baseConfigurationService.getBaseConfiguration("orderStatus", rdo.getStatus());
        reservationDetailDTO.setStatusName(cdo.getDescription());
        reservationDetailDTO.setRemark(rdo.getRemark());
        reservationDetailDTO.setCreateTime(rdo.getCreateTime());
        reservationDetailDTO.setShopName(sdo.getName());
        reservationDetailDTO.setDispatchingWay(rdo.getDispatchingWay());

        reservationDetailDTO.setName(cado.getName());
        reservationDetailDTO.setMobile(cado.getMobile());
        DistrictDO ddo = consignmentAddressService.getDistrictDatas(cado.getProvince());
        String provinceName = ddo.getDescription();
        ddo = consignmentAddressService.getDistrictDatas(cado.getCity());
        String cityName = ddo.getDescription();
        ddo = consignmentAddressService.getDistrictDatas(cado.getArea());
        String areaName = ddo.getDescription();
        reservationDetailDTO.setAddress(provinceName + cityName + areaName + cado.getAddress());

        PrintPhoto printPhoto = null;
        ShopPrintCostConfigDO spccdo = null;
        List<PrintPhoto> ppList = new ArrayList<PrintPhoto>();
        for(Map<String, Object> map : list){
            printPhoto = new PrintPhoto();
            printPhoto.setDescription((String) map.get("description"));
            printPhoto.setStoreUrl((String) map.get("storeUrl"));
            spccdo = shopService.getShopPrintCostConfig((String) map.get("type"));
            printPhoto.setType(spccdo.getRemark());
            printPhoto.setAmount(Integer.parseInt(String.valueOf(map.get("amount"))));
            ppList.add(printPhoto);
        }
        reservationDetailDTO.setPrintPhotoList(ppList);
        return success(reservationDetailDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    public Response getPrintPhotoOrderInfo(@RequestParam Long memberId, @RequestParam String status,
                                           @RequestParam int pageNum, @RequestParam int pageSize) {
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        StringBuilder sb = new StringBuilder();
        if (memberId == null) {
            sb.append("会员id不能为空;");
        }
        if (sb.length() != 0) {
            error(2000, sb.toString());
        }
        Pageable page = orderService.findReservationInfo(memberId, status, pageNum, pageSize);
        List<ReservationDO> list = page.getResult();
        ReservationDTO rdto = null;
        ShopDO sdo = null;
        ConfigurationDO cdo = null;
        List<ReservationDTO> result = new ArrayList<ReservationDTO>();
        for(ReservationDO rdo : list){
            rdto = new ReservationDTO();
            rdto.setId(rdo.getId());
            rdto.setMemberId(rdo.getMemberId());
            rdto.setShopId(rdo.getShopId());
            sdo = shopService.getShopsInfo(rdo.getShopId());
            rdto.setShopName(sdo.getName());
            rdto.setOrderNo(rdo.getOrderNo());
            rdto.setCost(rdo.getCost());
            rdto.setStatus(rdo.getStatus());
            cdo = baseConfigurationService.getBaseConfiguration("orderStatus", rdo.getStatus());
            rdto.setStatusName(cdo.getDescription());
            rdto.setRemark(rdo.getRemark());
            rdto.setCreateTime(rdo.getCreateTime());
            rdto.setModifyTime(rdo.getModifyTime());
            result.add(rdto);
        }
        page.setResult(result);
        return success(page);
    }

    /**
     * 订单实体之间相互转换
     * @param rdto 界面传入实体
     * @param rdo 数据库对应实体
     */
    private void reservationDTO2ReservationDO(ReservationDTO rdto, ReservationDO rdo){
        rdo.setMemberId(rdto.getMemberId());
        rdo.setShopId(rdto.getShopId());
        rdo.setStatus("S01");
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
