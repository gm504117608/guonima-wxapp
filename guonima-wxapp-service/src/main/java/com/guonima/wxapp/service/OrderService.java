package com.guonima.wxapp.service;

import com.guonima.wxapp.domain.ReservationDO;

/**
 * @author : guonima
 * @create : 2017-04-30-00:09
 */
public interface OrderService {

    /**
     * 保存订单和打印照片信息之间的关联信息
     * @param printPhotographIds 打印照片信息id串
     * @param orderNo 订单号
     * @return
     */
    public int saveReservationPrintPhotograph(String printPhotographIds, String orderNo);

    /**
     * 更新订单和打印照片信息之间的关联信息
     * @param printPhotographIds 打印照片信息id串 格式 xx,xx,xx
     * @param orderNo 订单号
     * @param amounts 打印数量串 格式 xx,xx,xx
     * @return
     */
    public int updateReservationPrintPhotograph(String printPhotographIds, String orderNo, String amounts);


    /**
     * 保存订单实体信息
     * @param reservationDO 订单实体信息
     * @return
     */
    public int saveReservation(ReservationDO reservationDO);

    /**
     * 更新订单实体信息
     * @param reservationDO 订单实体信息
     * @return
     */
    public int updateReservation(ReservationDO reservationDO);


}
