package com.wusi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @ Description   :  告知函图片上传controller
 * @ Author        :  wusi
 * @ CreateDate    :  2019/12/27$ 10:40$
 */
@RestController
@Slf4j
@RequestMapping("/api/web/picupload")
public class imgUploadController {
    @RequestMapping(value = "imgupload",method = RequestMethod.POST)
    public String imgUpload(MultipartFile file){
        if(file.isEmpty()){
            return "请选择正确图片";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        log.info("后缀名：" + suffixName);
        //未避免重复 拼接uuid
        String newName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."),fileName.length());
        // 文件上传后的路径
        //获取根目录
        String path=null;
        try {
           path = ResourceUtils.getURL("src\\main\\resources").getPath();
            //path = ResourceUtils.getURL("classpath:").getPath();
            System.out.println("++++++++++++++"+path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String filePath ="/imgupload";
        //String filePath ="/home/imgupload";
        String se = filePath + "/"+newName;
        System.out.println(se);
        File dest = new File(se);
        // 检测上传文件夹是否存在目录 不存在新建文件夹
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功!";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            log.error("上传失败",e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传失败",e.getMessage());
        }
        return "上传失败!";
    }
}
