package com.guonima.wxapp.service;

import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.ConsigneeAddressDO;
import com.guonima.wxapp.domain.DistrictDO;
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
    public int save(ConsigneeAddressDO consigneeAddressDO) {
        Long id = consigneeAddressDO.getId();
        Integer isUsing = consigneeAddressDO.getIsUsing();
        // 是否默认地址处理 是否默认地址【1：是；0：否】
        if(isUsing == 1){
            ConsigneeAddressDO temp = new ConsigneeAddressDO();
            temp.setIsUsing(0);
            temp.setMemberId(consigneeAddressDO.getMemberId());
            update(temp);
        }
        // 保存数据
        if (null == id) {
            return insert(consigneeAddressDO);
        } else {
            return update(consigneeAddressDO);
        }
    }

    private int insert(ConsigneeAddressDO consigneeAddressDO) {
        try {
            return dao.save("consignmentAddressMapper.insert", consigneeAddressDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int update(ConsigneeAddressDO consigneeAddressDO) {
        try {
            return dao.update("consignmentAddressMapper.update", consigneeAddressDO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<DistrictDO> getProvinceDatas() {
        DistrictDO dd = new DistrictDO();
        return (List<DistrictDO>) dao.findForList("districtMapper.findProvinceData", dd);
    }

    @Override
    public List<DistrictDO> getCityDatas(String code) {
        DistrictDO dd = new DistrictDO();
        dd.setCode(code);
        return (List<DistrictDO>) dao.findForList("districtMapper.findCityData", dd);
    }

    @Override
    public List<DistrictDO> getAreaDatas(String code) {
        DistrictDO dd = new DistrictDO();
        dd.setCode(code);
        return (List<DistrictDO>) dao.findForList("districtMapper.findAreaData", dd);
    }

    @Override
    public DistrictDO getDistrictDatas(String code) {
        DistrictDO dd = new DistrictDO();
        dd.setCode(code);
        return (DistrictDO) dao.findForObject("districtMapper.findDistrictData", dd);
    }
}
