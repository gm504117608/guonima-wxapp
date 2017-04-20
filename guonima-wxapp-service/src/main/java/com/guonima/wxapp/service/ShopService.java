package com.guonima.wxapp.service;

import com.guonima.wxapp.domain.common.Pageable;

/**
 * @author guonima
 * @create 2017-04-20 14:03
 */
public interface ShopService {

    /**
     * 获取店铺信息
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return
     */
    public Pageable getShops(int pageNum, int pageSize);
}
