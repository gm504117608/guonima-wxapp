package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.ConfigurationDO;
import com.guonima.wxapp.domain.PrintPhotographDO;
import com.guonima.wxapp.domain.PrintPhotographDTO;
import com.guonima.wxapp.service.BaseConfigurationService;
import com.guonima.wxapp.service.ShopService;
import com.guonima.wxapp.util.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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
    private BaseConfigurationService baseConfigurationService;

    @RequestMapping(method = RequestMethod.GET, value = "/shops")
    public Response getShopInfo(@RequestParam int pageNum, @RequestParam int pageSize) {
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        return success(shopService.getShops(pageNum, pageSize));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shops/photo/type")
    public Response getShopPhotoType() {
        return success(baseConfigurationService.getBaseConfiguration("photoType"));
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


    @RequestMapping(method = RequestMethod.GET, value = "/shops/photos/{shopId}/{memberId}")
    public Response getPrintPhotographInfo(@PathVariable("shopId") Long shopId, @PathVariable("memberId") Long memberId) {
        PrintPhotographDO printPhotographDO = new PrintPhotographDO();
        printPhotographDO.setMemberId(memberId);
        printPhotographDO.setShopId(shopId);
        List<PrintPhotographDO> list = shopService.findPrintPhotographInfo(printPhotographDO);
        List<PrintPhotographDTO> result = new ArrayList<PrintPhotographDTO>();
        PrintPhotographDTO ppdto = null;
        ConfigurationDO cd = null;
        for (PrintPhotographDO ppdo : list) {
            ppdto = new PrintPhotographDTO();
            PrintPhotographDO2PrintPhotographDTO(ppdo, ppdto);
            result.add(ppdto);
        }
        return success(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shops/photos/{id}")
    public Response getPrintPhotographInfo(@PathVariable("id") Long id) {
        PrintPhotographDO ppdo = shopService.findPrintPhotographInfo(id);
        PrintPhotographDTO ppdto = new PrintPhotographDTO();
        PrintPhotographDO2PrintPhotographDTO(ppdo, ppdto);
        return success(ppdto);
    }

    /**
     * 两个对象之间相互赋值处理
     * @param printPhotographDO 打印照片数据库实体
     * @param printPhotographDTO 打印照片界面展示实体
     */
    private void PrintPhotographDO2PrintPhotographDTO(PrintPhotographDO printPhotographDO, PrintPhotographDTO printPhotographDTO){
        printPhotographDTO.setId(printPhotographDO.getId());
        printPhotographDTO.setMemberId(printPhotographDO.getMemberId());
        printPhotographDTO.setShopId(printPhotographDO.getShopId());
        printPhotographDTO.setDescription(printPhotographDO.getDescription());
        printPhotographDTO.setType(printPhotographDO.getType());
        ConfigurationDO cd = baseConfigurationService.getBaseConfiguration("photoType", printPhotographDO.getType());
        printPhotographDTO.setTypeName(cd.getDescription());
        printPhotographDTO.setTypeRemark(cd.getRemark());
        printPhotographDTO.setStoreUrl(printPhotographDO.getStoreUrl());
        printPhotographDTO.setClipping(printPhotographDO.getClipping());
        printPhotographDTO.setTypesetting(printPhotographDO.getTypesetting());
        printPhotographDTO.setIsPrint(printPhotographDO.getIsPrint());
        printPhotographDTO.setRemark(printPhotographDO.getRemark());
        printPhotographDTO.setCreateTime(printPhotographDO.getCreateTime());
        printPhotographDTO.setModifyTime(printPhotographDO.getModifyTime());
    }

}
