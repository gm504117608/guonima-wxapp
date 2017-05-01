package com.guonima.wxapp.service;

import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.ReservationDO;
import com.guonima.wxapp.domain.ReservationPrintPhotographDO;
import com.guonima.wxapp.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单相关信息service
 * @author : guonima
 * @create : 2017-04-30-00:10
 */
@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

    private final static Logger log = Logger.getLogger(OrderServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int saveReservationPrintPhotograph(String printPhotographIds, String orderNo) {
        String[] printPhotographIdArray = printPhotographIds.split(",");
        if(null == printPhotographIdArray || printPhotographIdArray.length == 0){
            return 0;
        }
        ReservationPrintPhotographDO rppdo = null;
        List<ReservationPrintPhotographDO> list = new ArrayList<ReservationPrintPhotographDO>();
        for (String id : printPhotographIdArray){
            rppdo = new ReservationPrintPhotographDO();
            rppdo.setAmount(1);
            rppdo.setOrderNo(orderNo);
            rppdo.setPrintPhotographId(Long.valueOf(id));
            list.add(rppdo);
        }
        try{
            dao.batchSave("reservationPrintPhotographMapper.insert", list);
        }catch(Exception e){
            throw new ServiceException("保存订单和照片打印信息关联出现错误： " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateReservationPrintPhotograph(String printPhotographIds, String orderNo, String amounts) {
        String[] printPhotographIdArray = printPhotographIds.split(",");
        if(null == printPhotographIdArray || printPhotographIdArray.length == 0){
            return 0;
        }
        String[] amount = amounts.split(",");
        ReservationPrintPhotographDO rppdo = null;
        List<ReservationPrintPhotographDO> list = new ArrayList<ReservationPrintPhotographDO>();
        for(int i = 0, len = printPhotographIdArray.length; i < len; i++){
            rppdo = new ReservationPrintPhotographDO();
            rppdo.setAmount(Integer.valueOf(amount[i]));
            rppdo.setOrderNo(orderNo);
            rppdo.setPrintPhotographId(Long.valueOf(printPhotographIdArray[i]));
            list.add(rppdo);
        }
        try{
            dao.batchUpdate("reservationPrintPhotographMapper.update", list);
        }catch(Exception e){
            throw new ServiceException("更新订单和照片打印信息关联出现错误： " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int saveReservation(ReservationDO reservationDO) {
        try{
            return dao.save("reservationMapper.insert", reservationDO);
        }catch(Exception e){
            throw new ServiceException("保存订单信息出现错误： " + e.getMessage());
        }
    }

    @Override
    public int updateReservation(ReservationDO reservationDO) {
        try{
            return dao.save("reservationMapper.update", reservationDO);
        }catch(Exception e){
            throw new ServiceException("更新订单信息出现错误： " + e.getMessage());
        }
    }
}