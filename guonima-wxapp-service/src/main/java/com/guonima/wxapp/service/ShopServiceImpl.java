package com.guonima.wxapp.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.guonima.wxapp.dao.DaoSupport;
import com.guonima.wxapp.domain.PrintPhotographDO;
import com.guonima.wxapp.domain.ShopDO;
import com.guonima.wxapp.domain.ShopPrintCostConfigDO;
import com.guonima.wxapp.domain.common.Pageable;
import com.guonima.wxapp.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 店铺信息service类
 *
 * @author guonima
 * @create 2017-04-20 14:03
 */
@Service("shopServiceImpl")
public class ShopServiceImpl implements ShopService {

    private final static Logger log = Logger.getLogger(ShopServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public Pageable getShopsInfo(int pageNum, int pageSize) {
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

    @Override
    public ShopDO getShopsInfo(Long id) {
        ShopDO sdo = new ShopDO();
        sdo.setId(id);
        return (ShopDO) dao.findForObject("shopMapper.findShopInfo", sdo);
    }

    @Override
    public int savePrintPhoto(PrintPhotographDO printPhotographDO) {
        Long id = printPhotographDO.getId();
        try {
            if (id == null) {
                return dao.insert("printPhotographMapper.insert", printPhotographDO);
            } else {
                return dao.update("printPhotographMapper.update", printPhotographDO);
            }
        } catch (Exception e) {
            throw new ServiceException("上传图片信息保存出现错误： " + e.getMessage());
        }
    }

    public int deletePrintPhoto(Long id) {
        try {
            return dao.update("printPhotographMapper.delete", id);
        } catch (Exception e) {
            throw new ServiceException("上传图片信息删除出现错误： " + e.getMessage());
        }
    }

    @Override
    public Pageable findPrintPhotographInfo(Long shopId, Long memberId, int pageNum, int pageSize) {
        PrintPhotographDO printPhotographDO = new PrintPhotographDO();
        printPhotographDO.setMemberId(memberId);
        printPhotographDO.setShopId(shopId);
        Page<PrintPhotographDO> page = PageHelper.startPage(pageNum, pageSize);
        List<PrintPhotographDO> list = (List<PrintPhotographDO>) dao.findForList("printPhotographMapper.findPrintPhotographInfo", printPhotographDO);
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
    public PrintPhotographDO findPrintPhotographInfo(Long id) {
        PrintPhotographDO printPhotographDO = new PrintPhotographDO();
        printPhotographDO.setId(id);
        return (PrintPhotographDO) dao.findForObject("printPhotographMapper.findPrintPhotographInfo", printPhotographDO);
    }

    @Override
    public List<ShopPrintCostConfigDO> getShopPrintCostConfigList(Long shopId) {
        ShopPrintCostConfigDO shopPrintCostConfigDO = new ShopPrintCostConfigDO();
        shopPrintCostConfigDO.setShopId(shopId);
        return (List<ShopPrintCostConfigDO>) dao.findForList("shopPrintCostConfigMapper.findShopPrintCostConfigInfo", shopPrintCostConfigDO);
    }

    @Override
    public ShopPrintCostConfigDO getShopPrintCostConfig(String code) {
        ShopPrintCostConfigDO shopPrintCostConfigDO = new ShopPrintCostConfigDO();
        shopPrintCostConfigDO.setCode(code);
        return (ShopPrintCostConfigDO) dao.findForObject("shopPrintCostConfigMapper.findShopPrintCostConfigInfo", shopPrintCostConfigDO);
    }
}
