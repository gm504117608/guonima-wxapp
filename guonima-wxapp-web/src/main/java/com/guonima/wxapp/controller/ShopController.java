package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.*;
import com.guonima.wxapp.domain.common.Pageable;
import com.guonima.wxapp.service.ConsignmentAddressService;
import com.guonima.wxapp.service.ShopService;
import com.guonima.wxapp.util.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺信息controller类
 *
 * @author guonima
 * @create 2017-04-20 14:01
 */
@RestController
@RequestMapping("/")
public class ShopController extends BaseController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ConsignmentAddressService consignmentAddressService;

    @RequestMapping(method = RequestMethod.GET, value = "/shops")
    public Response getShopInfo(@RequestParam int pageNum, @RequestParam int pageSize) {
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        Pageable page = shopService.getShopsInfo(pageNum, pageSize);
        List<ShopDO> list = page.getResult();
        ShopDTO sdto = null;
        List<ShopDTO> result = new ArrayList<ShopDTO>();
        DistrictDO ddo = new DistrictDO();
        for (ShopDO sdo : list) {
            sdto = new ShopDTO();
            sdto.setId(sdo.getId()); // 唯一标识id
            sdto.setName(sdo.getName()); // 店铺名称
            sdto.setMobile(sdo.getMobile()); // 手机号码
            sdto.setProvince(sdo.getProvince()); // 省份
            ddo = consignmentAddressService.getDistrictDatas(sdo.getProvince());
            sdto.setProvinceName(ddo.getDescription()); // 省份名称
            sdto.setCity(sdo.getCity()); // 城市
            ddo = consignmentAddressService.getDistrictDatas(sdo.getCity());
            sdto.setCityName(ddo.getDescription()); // 城市名称
            sdto.setArea(sdo.getArea()); // 行政区
            ddo = consignmentAddressService.getDistrictDatas(sdo.getArea());
            sdto.setAreaName(ddo.getDescription()); // 行政区名称
            sdto.setAddress(sdo.getAddress()); // 详细地址
            sdto.setIconUrl(sdo.getIconUrl()); // 展示店铺图片地址
            sdto.setRemark(sdo.getRemark()); // 备注
            sdto.setEnabled(sdo.getEnabled()); // 是否激活【1（可用）；0（不可用）】
            sdto.setCreateTime(sdo.getCreateTime());
            sdto.setCreateUser(sdo.getCreateUser());
            sdto.setModifyTime(sdo.getModifyTime());
            sdto.setModifyUser(sdo.getModifyUser());

            result.add(sdto);
        }
        page.setResult(result);
        return success(page);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shops/printCost/{shopId}")
    public Response getShopPrintCostConfig(@PathVariable("shopId") Long shopId) {
        return success(shopService.getShopPrintCostConfigList(shopId));
    }

    /**
     * 保存上传图片信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/shops/photo/upload")
    public Response photoUpload(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();
        String memberId = request.getParameter("memberId");
        if (StringUtils.isEmpty(memberId)) {
            sb.append("会员信息获取不到;");
        }
        String shopId = request.getParameter("shopId");
        if (StringUtils.isEmpty(shopId)) {
            sb.append("店铺信息获取不到;");
        }
        if (sb.length() != 0) {
            return error(2000, sb.toString());
        }
        String path = FileUploadUtil.fileUploadAndGetPath(request, "file", (shopId + "/" + memberId + "/"));
        if (StringUtils.isEmpty(path)) {
            sb.append("照片上传失败;");
        }
        String type = request.getParameter("type");//打印类型
        if (StringUtils.isEmpty(type)) {
            sb.append("打印类型不能为空;");
        }
        if (sb.length() != 0) {
            return error(2000, sb.toString());
        }
        // 构造 打印照片信息实体
        PrintPhotographDO printPhotographDO = new PrintPhotographDO();
        String id = request.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            printPhotographDO.setId(Long.parseLong(id));
        }
        printPhotographDO.setMemberId(Long.parseLong(memberId));
        printPhotographDO.setShopId(Long.parseLong(shopId));
        printPhotographDO.setClipping(request.getParameter("clipping"));
        printPhotographDO.setTypesetting(request.getParameter("typesetting"));
        printPhotographDO.setDescription(request.getParameter("description"));
        printPhotographDO.setType(request.getParameter("type"));
        printPhotographDO.setRemark(request.getParameter("remark"));
        printPhotographDO.setStoreUrl(path);

        return success(shopService.savePrintPhoto(printPhotographDO));
    }

    /**
     * 修改上传图片附属信息， 不包含图片信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/shops/photos")
    public Response photoUploadNotHasPhoto(@RequestBody PrintPhotographDO printPhotographDO) {

        StringBuilder sb = new StringBuilder();
        Long id = printPhotographDO.getId();
        if (id == null) {
            sb.append("照片信息获取不到;");
        }
        Long memberId = printPhotographDO.getMemberId();
        if (memberId == null) {
            sb.append("会员信息获取不到;");
        }
        Long shopId = printPhotographDO.getShopId();
        if (shopId == null) {
            sb.append("店铺信息获取不到;");
        }
        String type = printPhotographDO.getType();//打印类型
        if (StringUtils.isEmpty(type)) {
            sb.append("打印类型不能为空;");
        }
        if (sb.length() != 0) {
            return error(2000, sb.toString());
        }
        return success(shopService.savePrintPhoto(printPhotographDO));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shops/photos")
    public Response getPrintPhotographInfo(@RequestParam("shopId") Long shopId, @RequestParam("memberId") Long memberId,
                                           @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        Pageable page = shopService.findPrintPhotographInfo(shopId, memberId, pageNum, pageSize);
        List<PrintPhotographDO> list = page.getResult();
        List<PrintPhotographDTO> result = new ArrayList<PrintPhotographDTO>();
        PrintPhotographDTO ppdto = null;
        ConfigurationDO cd = null;
        for (PrintPhotographDO ppdo : list) {
            ppdto = new PrintPhotographDTO();
            PrintPhotographDO2PrintPhotographDTO(ppdo, ppdto);
            result.add(ppdto);
        }
        page.setResult(result);
        return success(page);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shops/photos/{id}")
    public Response getPrintPhotographInfo(@PathVariable("id") Long id) {
        PrintPhotographDO ppdo = shopService.findPrintPhotographInfo(id);
        PrintPhotographDTO ppdto = new PrintPhotographDTO();
        PrintPhotographDO2PrintPhotographDTO(ppdo, ppdto);
        return success(ppdto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shops/payment/{ids}")
    public Response getPaymentPrintPhoto(@PathVariable("ids") String ids) {
        String[] idArray = ids.split(",");
        if (null == idArray || idArray.length == 0) {
            return error(2000, "获取打印照片信息的唯一标识不存在");
        }
        List<PrintPhotographDTO> result = new ArrayList<PrintPhotographDTO>();
        for (String id : idArray) {
            PrintPhotographDO ppdo = shopService.findPrintPhotographInfo(Long.valueOf(id));
            PrintPhotographDTO ppdto = new PrintPhotographDTO();
            PrintPhotographDO2PrintPhotographDTO(ppdo, ppdto);
            result.add(ppdto);
        }
        return success(result);
    }

    /**
     * 两个对象之间相互赋值处理
     *
     * @param printPhotographDO  打印照片数据库实体
     * @param printPhotographDTO 打印照片界面展示实体
     */
    private void PrintPhotographDO2PrintPhotographDTO(PrintPhotographDO printPhotographDO, PrintPhotographDTO printPhotographDTO) {
        printPhotographDTO.setId(printPhotographDO.getId());
        printPhotographDTO.setMemberId(printPhotographDO.getMemberId());
        printPhotographDTO.setShopId(printPhotographDO.getShopId());
        printPhotographDTO.setDescription(printPhotographDO.getDescription());
        printPhotographDTO.setType(printPhotographDO.getType());
        ShopPrintCostConfigDO spccdo = shopService.getShopPrintCostConfig(printPhotographDO.getType());
        printPhotographDTO.setTypeName(spccdo.getDescription());
        printPhotographDTO.setTypeRemark(spccdo.getRemark());
        printPhotographDTO.setPrice(spccdo.getPrice());
        printPhotographDTO.setStoreUrl(printPhotographDO.getStoreUrl());
        printPhotographDTO.setClipping(printPhotographDO.getClipping());
        printPhotographDTO.setTypesetting(printPhotographDO.getTypesetting());
        printPhotographDTO.setIsPrint(printPhotographDO.getIsPrint());
        printPhotographDTO.setRemark(printPhotographDO.getRemark());
        printPhotographDTO.setCreateTime(printPhotographDO.getCreateTime());
        printPhotographDTO.setModifyTime(printPhotographDO.getModifyTime());
    }

}
