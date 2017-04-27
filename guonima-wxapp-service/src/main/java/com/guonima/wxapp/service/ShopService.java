package com.guonima.wxapp.service;

import com.guonima.wxapp.domain.PrintPhotographDO;
import com.guonima.wxapp.domain.common.Pageable;

import java.util.List;

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

    /**
     * 保存图片打印的信息
     * @param printPhotographDO 图片打印信息实体
     */
    public int savePrintPhoto(PrintPhotographDO printPhotographDO);

    /**
     * 获取上图图片信息
     * @param printPhotographDO 图片打印信息实体
     * @return
     */
    public List<PrintPhotographDO> findPrintPhotographInfo(PrintPhotographDO printPhotographDO);
}
