package com.guonima.wxapp.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


/**
 * 文件上传操作类
 */
public class FileUploadUtil {

    private final static String PHOTO_PATH = "resources/photo/"; // 上传图片的存放位置

    /**
     * 图片上传，并返回图片的地址
     *
     * @param request
     * @param imgName 图片上传的组件的name名称
     * @param path    图片上传的路径
     * @return
     */
    public static String fileUploadAndGetPath(HttpServletRequest request, String imgName, String path) {
        try {
            if (StringUtils.isEmpty(imgName) || StringUtils.isEmpty(path)) {
                return null;
            }
            String savePath = request.getSession().getServletContext().getRealPath("/") + PHOTO_PATH + path;
            File file = new File(savePath);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            //转型为MultipartHttpRequest(重点的所在)
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            //获得图片（根据前台的name名称得到上传的文件）
            MultipartFile imgFile = multipartRequest.getFile(imgName);
            //获取上传文件的文件名称
            String fileName = imgFile.getOriginalFilename();
            //文件名为空返回空地址
            if (StringUtils.isEmpty(fileName)) {
                return null;
            }
            String imgPath = savePath + "/" + fileName;
            imgFile.transferTo(new File(imgPath));
            return path + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
