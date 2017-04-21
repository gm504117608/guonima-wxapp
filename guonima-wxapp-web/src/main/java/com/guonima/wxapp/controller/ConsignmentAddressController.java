package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.ConsigneeAddressDO;
import com.guonima.wxapp.service.ConsignmentAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author guonima
 * @create 2017-04-21 17:15
 */
@RestController
@RequestMapping("/consignment/")
public class ConsignmentAddressController extends BaseController {
    @Autowired
    private ConsignmentAddressService consignmentAddressService;

    @RequestMapping(method = RequestMethod.GET, value = "/members/{id}")
    public Response getConsignmentAddressByMemberId(@PathVariable("id") Long memberId) throws IOException {
        return success(consignmentAddressService.getConsignmentAddressByMemberId(memberId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Response getConsignmentAddressById(@PathVariable("id") Long id) throws IOException {
        return success(consignmentAddressService.getConsignmentAddressById(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insert")
    public Response insert(@RequestBody ConsigneeAddressDO consigneeAddressDO) {
        // TODO 基本规则校验

        return success(consignmentAddressService.insert(consigneeAddressDO));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public Response update(@RequestBody ConsigneeAddressDO consigneeAddressDO) {
        // TODO 基本规则校验

        return success(consignmentAddressService.update(consigneeAddressDO));
    }

}
