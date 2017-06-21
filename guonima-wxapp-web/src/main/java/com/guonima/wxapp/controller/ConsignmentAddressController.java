package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.ConsigneeAddressDO;
import com.guonima.wxapp.domain.ConsigneeAddressDTO;
import com.guonima.wxapp.domain.DistrictDO;
import com.guonima.wxapp.service.ConsignmentAddressService;
import com.guonima.wxapp.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author guonima
 * @create 2017-04-21 17:15
 */
@RestController
@RequestMapping("/consignment/")
public class ConsignmentAddressController extends BaseController implements ConsignmentAddress {
    @Autowired
    private ConsignmentAddressService consignmentAddressService;

    @RequestMapping(method = RequestMethod.GET, value = "/members/{memberId}")
    public Response getConsignmentAddressByMemberId(@PathVariable("memberId") Long memberId) {
        List<ConsigneeAddressDO> list = consignmentAddressService.getConsignmentAddressByMemberId(memberId);
        Iterator<ConsigneeAddressDO> it = list.iterator();
        ConsigneeAddressDO cado = null;
        List<ConsigneeAddressDTO> result = new ArrayList<ConsigneeAddressDTO>();
        ConsigneeAddressDTO cadto = null;
        while (it.hasNext()) {
            cado = it.next();
            cadto = new ConsigneeAddressDTO();
            consigneeAddressDO2consigneeAddressDTO(cado, cadto, consignmentAddressService);
            result.add(cadto);
        }
        return success(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Response getConsignmentAddressById(@PathVariable("id") Long id) {
        ConsigneeAddressDO cado = consignmentAddressService.getConsignmentAddressById(id);
        ConsigneeAddressDTO cadto = new ConsigneeAddressDTO();
        consigneeAddressDO2consigneeAddressDTO(cado, cadto, consignmentAddressService);
        return success(cadto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public Response save(@RequestBody ConsigneeAddressDO consigneeAddressDO) {
        // 基本规则校验
        StringBuilder sb = new StringBuilder();
        String name = consigneeAddressDO.getName();
        if (StringUtils.isEmpty(name)) {
            sb.append("【姓名】不能为空;");
        } else {
            if (!CommonUtil.checkLength(name, 1, 40)) {
                sb.append("【姓名】长度在1到40之间;");
            }
        }
        String mobile = consigneeAddressDO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            sb.append("【联系电话】不能为空;");
        } else {
            if (!CommonUtil.isPhoneNum(mobile)) {
                sb.append("【联系电话】格式不正确;");
            }
        }
        String address = consigneeAddressDO.getAddress();
        if (StringUtils.isEmpty(address)) {
            sb.append("【详细地址】不能为空;");
        } else {
            if (!CommonUtil.checkLength(address, 1, 90)) {
                sb.append("【详细地址】长度在1到90之间;");
            }
        }
        String postcode = consigneeAddressDO.getPostcode();
        if (StringUtils.isEmpty(postcode)) {
            sb.append("【邮编】不能为空;");
        } else {
            if (!CommonUtil.checkLength(postcode, 0, 6)) {
                sb.append("【邮编】长度在0到6之间;");
            }
        }
        if (sb.length() != 0) {
            return error(1000, sb.toString());
        }
        return success(consignmentAddressService.save(consigneeAddressDO));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/province")
    public Response getProvinceDatas() {
        return success(consignmentAddressService.getProvinceDatas());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/city/{code}")
    public Response getCityDatas(@PathVariable("code") String code) {
        return success(consignmentAddressService.getCityDatas(code));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/area/{code}")
    public Response getAreaDatas(@PathVariable("code") String code) {
        return success(consignmentAddressService.getAreaDatas(code));
    }
}
