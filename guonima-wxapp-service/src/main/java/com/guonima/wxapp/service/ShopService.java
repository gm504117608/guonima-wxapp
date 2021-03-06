package com.guonima.wxapp.service;

import com.guonima.wxapp.domain.PrintPhotographDO;
import com.guonima.wxapp.domain.ShopDO;
import com.guonima.wxapp.domain.ShopPrintCostConfigDO;
import com.guonima.wxapp.domain.common.Pageable;

import java.util.List;

/**
 * @author guonima
 * @create 2017-04-20 14:03
 */
public interface ShopService {

    /**
     * 获取店铺信息
     *
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return
     */
    public Pageable getShopsInfo(int pageNum, int pageSize);

    /**
     * 获取店铺信息
     *
     * @param id 店铺唯一标识id
     * @return
     */
    public ShopDO getShopsInfo(Long id);

    /**
     * 保存图片打印的信息
     *
     * @param printPhotographDO 图片打印信息实体
     */
    public int savePrintPhoto(PrintPhotographDO printPhotographDO);

    /**
     * 删除图片打印的信息
     *
     * @param id 图片打印信息实体唯一标识
     */
    public int deletePrintPhoto(Long id);

    /**
     * 获取上图图片信息
     *
     * @param shopId   店铺唯一标识id
     * @param memberId 会员唯一标识id
     * @param pageNum  当前页号
     * @param pageSize 每页显示数量
     * @return
     */
    public Pageable findPrintPhotographInfo(Long shopId, Long memberId, int pageNum, int pageSize);

    /**
     * 获取上图图片信息
     *
     * @param id 打印图片唯一标识id
     * @return
     */
    public PrintPhotographDO findPrintPhotographInfo(Long id);

    /**
     * 通过店铺ID获取店铺下面配置的打印花费信息
     *
     * @param shopId
     * @return
     */
    public List<ShopPrintCostConfigDO> getShopPrintCostConfigList(Long shopId);

    /**
     * 通过打印话费代码获取店铺下面配置的打印花费信息
     *
     * @param code
     * @return
     */
    public ShopPrintCostConfigDO getShopPrintCostConfig(String code);

}
