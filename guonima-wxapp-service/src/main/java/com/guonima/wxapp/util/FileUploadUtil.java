package com.guonima.wxapp.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


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
        if (StringUtils.isEmpty(imgName) || StringUtils.isEmpty(path)) {
            return null;
        }
        String savePath = request.getSession().getServletContext().getRealPath("/") + PHOTO_PATH + path;
        File file = new File(savePath);
        //如果文件夹不存在则创建
        if (!file.exists()) {
            file.mkdirs();
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
        String imgPath = savePath + fileName;
        try {
            imgFile.transferTo(new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newName = time + "." + prefix;
        renameFile(savePath, fileName, newName);
        return PHOTO_PATH + path + newName;
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldName 原来的文件名
     * @param newName 新文件名
     */
    private static void renameFile(String path, String oldName, String newName) {
        if (!oldName.equals(newName)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldFile = new File(path + oldName);
            File newFile = new File(path + newName);
            if (!oldFile.exists()) {
                return;//重命名文件不存在
            }
            oldFile.renameTo(newFile);
        }
    }
}
