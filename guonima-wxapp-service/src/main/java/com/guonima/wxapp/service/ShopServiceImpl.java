package com.guonima.wxapp.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.ShopDO;
import com.guonima.wxapp.domain.common.Pageable;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 店铺信息service类
 * @author guonima
 * @create 2017-04-20 14:03
 */
@Service("shopServiceImpl")
public class ShopServiceImpl implements ShopService {

    private final static Logger log = Logger.getLogger(ShopServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public Pageable getShops(int pageNum, int pageSize) {
        Page<ShopDO> page = PageHelper.startPage(pageNum, pageSize);
        List<ShopDO> list = (List<ShopDO>) dao.findForList("shopMapper.findShopInfo", new ShopDO());
        // 取分页信息
        Pageable result = new Pageable();
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setTotalRows(page.getTotal());//获取总记录数
        result.setTotalNum(page.getPages());
        result.setResult(list);
        return result;
    }
}
