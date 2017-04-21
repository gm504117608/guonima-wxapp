package com.guonima.wxapp.service;

import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.ConsigneeAddressDO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收货地址service类
 *
 * @author guonima
 * @create 2017-04-21 17:22
 */
@Service("consignmentAddressServiceImpl")
public class ConsignmentAddressServiceImpl implements ConsignmentAddressService {

    private final static Logger log = Logger.getLogger(ConsignmentAddressServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<ConsigneeAddressDO> getConsignmentAddressByMemberId(Long memberId) {
        ConsigneeAddressDO cado = new ConsigneeAddressDO();
        cado.setMemberId(memberId);
        return (List<ConsigneeAddressDO>) dao.findForList("consignmentAddressMapper.getConsignmentAddressByMemberId", cado);
    }

    @Override
    public ConsigneeAddressDO getConsignmentAddressById(Long id) {
        ConsigneeAddressDO cado = new ConsigneeAddressDO();
        cado.setId(id);
        return (ConsigneeAddressDO) dao.findForObject("consignmentAddressMapper.getConsignmentAddressById", cado);
    }

    @Override
    public int insert(ConsigneeAddressDO consigneeAddressDO) {
        try {
            return dao.save("consignmentAddressMapper.insert", consigneeAddressDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int update(ConsigneeAddressDO consigneeAddressDO) {
        try {
            return dao.save("consignmentAddressMapper.update", consigneeAddressDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
