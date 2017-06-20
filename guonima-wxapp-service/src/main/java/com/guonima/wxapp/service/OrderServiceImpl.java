package com.guonima.wxapp.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.ReservationDO;
import com.guonima.wxapp.domain.ReservationPrintPhotographDO;
import com.guonima.wxapp.domain.common.Pageable;
import com.guonima.wxapp.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单相关信息service
 *
 * @author : guonima
 * @create : 2017-04-30-00:10
 */
@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

    private final static Logger log = Logger.getLogger(OrderServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int insertOrUpdateReservationPrintPhotograph(String printPhotographIds, String orderNo, String amounts, String flag) {
        String[] printPhotographIdArray = printPhotographIds.split(",");
        if (null == printPhotographIdArray || printPhotographIdArray.length == 0) {
            return 0;
        }
        String[] amount = amounts.split(",");
        ReservationPrintPhotographDO rppdo = null;
        List<ReservationPrintPhotographDO> list = new ArrayList<ReservationPrintPhotographDO>();
        for (int i = 0, len = printPhotographIdArray.length; i < len; i++) {
            rppdo = new ReservationPrintPhotographDO();
            rppdo.setAmount(Integer.valueOf(amount[i]));
            rppdo.setOrderNo(orderNo);
            rppdo.setPrintPhotographId(Long.valueOf(printPhotographIdArray[i]));
            list.add(rppdo);
        }
        try {
            if ("insert".equals(flag)) {
                dao.batchInsert("reservationPrintPhotographMapper.insert", list);
            }
            if ("update".equals(flag)) {
                dao.batchUpdate("reservationPrintPhotographMapper.update", list);
            }
        } catch (Exception e) {
            log.error("保存/更新订单和照片打印信息关联出现错误： " + e.getMessage());
            throw new ServiceException("保存/更新订单和照片打印信息关联出现错误： " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int saveReservation(ReservationDO reservationDO) {
        try {
            return dao.insert("reservationMapper.insert", reservationDO);
        } catch (Exception e) {
            throw new ServiceException("保存订单信息出现错误： " + e.getMessage());
        }
    }

    @Override
    public int updateReservation(ReservationDO reservationDO) {
        try {
            return dao.insert("reservationMapper.update", reservationDO);
        } catch (Exception e) {
            throw new ServiceException("更新订单信息出现错误： " + e.getMessage());
        }
    }

    @Override
    public ReservationDO findReservationInfo(String orderNo) {
        ReservationDO reservationDO = new ReservationDO();
        reservationDO.setOrderNo(orderNo);
        return (ReservationDO) dao.findForObject("reservationMapper.findReservationInfo", reservationDO);
    }

    @Override
    public Pageable findReservationInfo(Long memberId, String status, int pageNum, int pageSize) {
        ReservationDO reservationDO = new ReservationDO();
        reservationDO.setMemberId(memberId);
        reservationDO.setStatus(status);
        Page<ReservationDO> page = PageHelper.startPage(pageNum, pageSize);
        List<ReservationDO> list = (List<ReservationDO>) dao.findForList("reservationMapper.findReservationInfo", reservationDO);
        // 取分页信息
        Pageable result = new Pageable();
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotalRows(page.getTotal());//获取总记录数
        result.setTotalNum(page.getPages());
        result.setResult(list);
        return result;
    }

    @Override
    public List<Map<String, Object>> findReservationPrintPhotographInfo(String orderNo) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orderNo", orderNo);
        return (List<Map<String, Object>>) dao.findForList("reservationMapper.findReservationPrintPhotographInfo", param);
    }

}
