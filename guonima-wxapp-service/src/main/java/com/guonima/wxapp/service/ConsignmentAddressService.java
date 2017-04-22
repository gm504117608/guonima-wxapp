package com.guonima.wxapp.service;

import com.guonima.wxapp.domain.ConsigneeAddressDO;

import java.util.List;

/**
 * @author guonima
 * @create 2017-04-21 17:22
 */
public interface ConsignmentAddressService {

    /**
     * 通过会员id获取该会员下面的所有收货地址
     * @param memberId 会员id
     * @return
     */
    public List<ConsigneeAddressDO> getConsignmentAddressByMemberId(Long memberId);

    /**
     * 通过收货地址唯一标识id获取收货地址信息
     * @param id 收货地址唯一标识id
     * @return
     */
    public ConsigneeAddressDO getConsignmentAddressById(Long id);

    /**
     * 通过收货地址唯一标识id获取收货地址信息
     * @param consigneeAddressDO 收货地址实体
     * @return
     */
    public int insert(ConsigneeAddressDO consigneeAddressDO);

    /**
     * 通过收货地址唯一标识id获取收货地址信息
     * @param consigneeAddressDO 收货地址实体
     * @return
     */
    public int update(ConsigneeAddressDO consigneeAddressDO);
}
