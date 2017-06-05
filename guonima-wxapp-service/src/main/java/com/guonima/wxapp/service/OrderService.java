package com.guonima.wxapp.service;

import com.guonima.wxapp.domain.ReservationDO;
import com.guonima.wxapp.domain.common.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author : guonima
 * @create : 2017-04-30-00:09
 */
public interface OrderService {

    /**
     * 保存订单和打印照片信息之间的关联信息
     *
     * @param printPhotographIds 打印照片信息id串
     * @param orderNo            订单号
     * @return
     */
    public int saveReservationPrintPhotograph(String printPhotographIds, String orderNo);

    /**
     * 更新订单和打印照片信息之间的关联信息
     *
     * @param printPhotographIds 打印照片信息id串 格式 xx,xx,xx
     * @param orderNo            订单号
     * @param amounts            打印数量串 格式 xx,xx,xx
     * @return
     */
    public int updateReservationPrintPhotograph(String printPhotographIds, String orderNo, String amounts);


    /**
     * 保存订单实体信息
     *
     * @param reservationDO 订单实体信息
     * @return
     */
    public int saveReservation(ReservationDO reservationDO);

    /**
     * 更新订单实体信息
     *
     * @param reservationDO 订单实体信息
     * @return
     */
    public int updateReservation(ReservationDO reservationDO);

    /**
     * 获取订单实体信息
     *
     * @param orderNo 订单号
     * @return
     */
    public ReservationDO findReservationInfo(String orderNo);

    /**
     * 获取订单实体信息(带分页功能)
     *
     * @param memberId 会员id
     * @param status   订单状态
     * @param pageNum  当前页数
     * @param pageSize 每页显示数量
     * @return
     */
    public Pageable findReservationInfo(Long memberId, String status, int pageNum, int pageSize);

    /**
     * 获取订单详细内容信息
     *
     * @param orderNo 订单号
     * @return
     */
    public List<Map<String, Object>> findReservationPrintPhotographInfo(String orderNo);
}
