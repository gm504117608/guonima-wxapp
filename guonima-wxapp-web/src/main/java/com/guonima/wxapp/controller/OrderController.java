package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.annotations.Log;
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
    @Log("保存/更新订单信息")
    public Response savePrintPhotoOrderPayment(@RequestBody OrderPaymentDTO orderPaymentDTO) {

        StringBuilder sb = new StringBuilder();
        String[] printPhotographIdArray = orderPaymentDTO.getIds().split(",");
        if (null == printPhotographIdArray || printPhotographIdArray.length == 0) {
            sb.append("需要打印的照片信息获取不到，请重新操作");
        }
        if (null == orderPaymentDTO.getMemberId()) {
            sb.append("会员id不能为空;");
        }
        if (null == orderPaymentDTO.getShopId()) {
            sb.append("店铺id不能为空;");
        }
        if (sb.length() != 0) {
            return error(1000, sb.toString());
        }
        String[] amount = orderPaymentDTO.getAmounts().split(",");
        PrintPhotographDO ppdo = null;
        ShopPrintCostConfigDO spccdo = null;
        BigDecimal result = new BigDecimal(0);
        for (int i = 0, len = printPhotographIdArray.length; i < len; i++) {
            ppdo = shopService.findPrintPhotographInfo(Long.valueOf(printPhotographIdArray[i]));
            spccdo = shopService.getShopPrintCostConfig(ppdo.getType());
            result = result.add(spccdo.getPrice().multiply(new BigDecimal(amount[i])));
        }
        if (result.compareTo(orderPaymentDTO.getCost()) != 0) {
            return error(1000, "打印照片所花费的金额不正确");
        }
        String orderNo = OrderUtil.createOrderNo();
        // 处理订单和照片关联信息
        orderService.insertOrUpdateReservationPrintPhotograph(orderPaymentDTO.getIds(),
                orderNo, orderPaymentDTO.getAmounts(), "insert");
        // 处理订单信息
        ReservationDO rdo = new ReservationDO();
        orderPaymentDTO.setCost(result);
        orderPaymentDTO.setOrderNo(orderNo);
        reservationDTO2ReservationDO(orderPaymentDTO, rdo);
        orderService.saveReservation(rdo);
        return success(orderNo);
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
        reservationDetailDTO.setStatus(rdo.getStatus());
        reservationDetailDTO.setRemark(rdo.getRemark());
        reservationDetailDTO.setCreateTime(rdo.getCreateTime());
        reservationDetailDTO.setShopName(sdo.getName());
        reservationDetailDTO.setDispatchingWay(rdo.getDispatchingWay());
        reservationDetailDTO.setDispatchingWayName(baseConfigurationService.getBaseConfigurationName("dispatchingWay", rdo.getDispatchingWay()));

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
        for (Map<String, Object> map : list) {
            printPhoto = new PrintPhoto();
            printPhoto.setDescription((String) map.get("description"));
            printPhoto.setRemark((String) map.get("remark"));
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
    public Response getPrintPhotoOrderInfo(@RequestParam String memberId, @RequestParam String status,
                                           @RequestParam String pageNum, @RequestParam String pageSize) {
        if (StringUtils.isEmpty(pageNum) || "0".equals(pageNum)) {
            pageNum = "1";
        }
        if (StringUtils.isEmpty(pageSize) || "0".equals(pageSize)) {
            pageSize = "10";
        }
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(memberId)) {
            sb.append("会员id不能为空;");
        }
        if (sb.length() != 0) {
            return error(1000, sb.toString());
        }
        Pageable page = orderService.findReservationInfo(Long.valueOf(memberId), status, Integer.valueOf(pageNum).intValue(),
                Integer.valueOf(pageSize).intValue());
        List<ReservationDO> list = page.getResult();
        ReservationDTO rdto = null;
        ShopDO sdo = null;
        ConfigurationDO cdo = null;
        List<ReservationDTO> result = new ArrayList<ReservationDTO>();
        for (ReservationDO rdo : list) {
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
     *
     * @param orderPaymentDTO 界面传入实体
     * @param rdo  数据库对应实体
     */
    private void reservationDTO2ReservationDO(OrderPaymentDTO orderPaymentDTO, ReservationDO rdo) {
        rdo.setMemberId(orderPaymentDTO.getMemberId());
        rdo.setShopId(orderPaymentDTO.getShopId());
        rdo.setStatus("S01");
        rdo.setCost(orderPaymentDTO.getCost());
        rdo.setOrderNo(orderPaymentDTO.getOrderNo());
        rdo.setConsignmentId(orderPaymentDTO.getConsignmentId());
        rdo.setDispatchingWay(orderPaymentDTO.getDispatchingWay());
        rdo.setRemark("小程序照片打印订单信息生成");
    }

}
