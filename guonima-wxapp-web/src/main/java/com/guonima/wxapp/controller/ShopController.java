package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.PrintPhotographDO;
import com.guonima.wxapp.service.BaseConfigurationService;
import com.guonima.wxapp.service.ShopService;
import com.guonima.wxapp.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

        String memberId = request.getParameter("memberId");
        StringBuilder sb = new StringBuilder();
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
        if (null == path) {
            sb.append("照片上传失败;");
        }
        String type = request.getParameter("type");//打印类型
        if (StringUtils.isEmpty(type)) {
            sb.append("打印类型不能为空;");
        }
        if (sb.length() != 0) {
            return error(2000, sb.toString());
        }
        String amount = request.getParameter("amount");//打印数量
        if (StringUtils.isEmpty(amount)) { // 默认打印数量为1
            amount = "1";
        }

        PrintPhotographDO printPhotographDO = new PrintPhotographDO();
        String id = request.getParameter("id");
        if(StringUtils.isEmpty(id)){
            printPhotographDO.setId(null);
        }else{
            printPhotographDO.setId(Long.parseLong(id));
        }
        printPhotographDO.setMemberId(Long.parseLong(memberId));
        printPhotographDO.setShopId(Long.parseLong(shopId));
        printPhotographDO.setClipping(request.getParameter("clipping"));
        printPhotographDO.setTypesetting(request.getParameter("typesetting"));
        printPhotographDO.setAmount(Integer.parseInt(amount));
        printPhotographDO.setDescription(request.getParameter("description"));
        printPhotographDO.setType(request.getParameter("type"));
        printPhotographDO.setRemark(request.getParameter("remark"));
        printPhotographDO.setStoreUrl(path);

        return success(shopService.savePrintPhoto(printPhotographDO));
    }
}
