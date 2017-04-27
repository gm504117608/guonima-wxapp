package com.guonima.wxapp.controller;

import com.guonima.wxapp.Response;
import com.guonima.wxapp.domain.PrintPhotographDO;
import com.guonima.wxapp.service.BaseConfigurationService;
import com.guonima.wxapp.service.ShopService;
import com.guonima.wxapp.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
     *
     */
    @RequestMapping(method = RequestMethod.POST, value = "/shops/photo/upload")
    public Response photoUpload(HttpServletRequest request, @RequestBody PrintPhotographDO printPhotographDO) {

        StringBuilder sb = new StringBuilder();
        Long memberId = printPhotographDO.getMemberId();
        if(null == memberId){
            sb.append("会员信息获取不到;");
        }
        Long shopId = printPhotographDO.getShopId();
        if(null == shopId){
            sb.append("店铺信息获取不到;");
        }
        if(sb.length() != 0){
            return error(2000, sb.toString());
        }
        String path = FileUploadUtil.fileUploadAndGetPath(request, "file", (shopId + "/" + memberId + "/"));
        if(null == path){
            sb.append("照片上传失败;");
        }
        String type = printPhotographDO.getType(); //打印类型
        if(null == type){
            sb.append("打印类型不能为空;");
        }
        if(sb.length() != 0){
            return error(2000, sb.toString());
        }
        Integer amount = printPhotographDO.getAmount();
        if(amount == null){ // 默认打印数量为1
            printPhotographDO.setAmount(1);
        }
        printPhotographDO.setStoreUrl(path);
        return success(shopService.savePrintPhoto(printPhotographDO));
    }
}
