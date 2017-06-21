package com.guonima.wxapp.controller;

import com.guonima.wxapp.domain.ConsigneeAddressDO;
import com.guonima.wxapp.domain.ConsigneeAddressDTO;
import com.guonima.wxapp.domain.DistrictDO;
import com.guonima.wxapp.service.ConsignmentAddressService;

/**
 * @author guonima
 * @create 2017-06-21 14:08
 */
public interface ConsignmentAddress {

    /**
     * 将收货地址数据库实体转成界面传输实体
     *
     * @param cado  数据库实体数据
     * @param cadto 界面展示数据
     */
    default void consigneeAddressDO2consigneeAddressDTO(ConsigneeAddressDO cado, ConsigneeAddressDTO cadto,
                                                               ConsignmentAddressService consignmentAddressService) {
        cadto.setId(cado.getId());// 唯一标识id
        cadto.setMemberId(cado.getMemberId()); //会员唯一标识id
        cadto.setName(cado.getName()); //收件人
        cadto.setMobile(cado.getMobile()); //手机号码
        cadto.setProvince(cado.getProvince()); //省份
        DistrictDO ddo = consignmentAddressService.getDistrictDatas(cado.getProvince());
        cadto.setProvinceName(ddo.getDescription()); //省份名称
        cadto.setCity(cado.getCity()); //城市
        ddo = consignmentAddressService.getDistrictDatas(cado.getCity());
        cadto.setCityName(ddo.getDescription()); //城市名称
        cadto.setArea(cado.getArea()); //区域
        ddo = consignmentAddressService.getDistrictDatas(cado.getArea());
        cadto.setAreaName(ddo.getDescription()); //区域名称
        cadto.setAddress(cado.getAddress()); //详细地址
        cadto.setPostcode(cado.getPostcode()); //邮编
        cadto.setIsUsing(cado.getIsUsing()); //是否默认地址【1：是；0：否】
        cadto.setCreateTime(cado.getCreateTime());
        cadto.setModifyTime(cado.getModifyTime());
    }
}
