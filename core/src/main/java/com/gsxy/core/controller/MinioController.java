package com.gsxy.core.controller;

import com.alibaba.fastjson2.JSONArray;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@CrossOrigin
@Api(value = "文件板块接口",tags = {"文件板块接口"})
@RestController
@RequestMapping("/minio")
@Slf4j
public class MinioController {

    @Autowired
    private FileStorageService minioService;

//    /**
//     * 单文件上传
//     * 直接将传入的文件通过io流形式直接写入(服务器)指定路径下
//     *
//     * @param file 上传的文件
//     * @return
//     */
//    @GetMapping("/add/{file}")
//    @ApiOperation("单文件上传")
//    public String singleFileUpload(MultipartFile file) {
//        //实际情况下，这些路径都应该是服务器上面存储文件的路径
//        String filePath = System.getProperty("user.dir") + "\\file\\";
//        File dir = new File(filePath);
//        if (!dir.exists()) dir.mkdir();
//
//        if (file == null) {
//            return JSONArray.toJSONString(ResponseVo.builder()
//                            .message("文件路径为null")
//                    .build());
//        }
//        InputStream fileInputStream = null;
//        FileOutputStream fileOutputStream = null;
//        try {
//            String filename = file.getOriginalFilename();
//            fileOutputStream = new FileOutputStream(filePath + filename);
//            fileInputStream = file.getInputStream();
//
//            byte[] buf = new byte[1024 * 8];
//            int length;
//            while ((length = fileInputStream.read(buf)) != -1) {//读取fis文件输入字节流里面的数据
//                fileOutputStream.write(buf, 0, length);//通过fos文件输出字节流写出去
//            }
//            log.info("单文件上传完成！文件路径:{},文件名:{},文件大小:{}", filePath, filename, file.getSize());
//            return JSONArray.toJSONString(ResponseVo.builder()
//                            .message("单文件上传完成")
//                    .build());
//        } catch (IOException e) {
//            return JSONArray.toJSONString(ResponseVo.builder()
//                            .message("单文件上传失败")
//                    .build());
//        } finally {
//            try {
//                if (fileOutputStream != null) {
//                    fileOutputStream.close();
//                    fileOutputStream.flush();
//                }
//                if (fileInputStream != null) {
//                    fileInputStream.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * @author hln 2024-8-29
     *  文件上传（支持多文件）
     * @param files
     * @return
     */
//    @PostMapping("/upload")
    @ApiOperation("文件上传（支持多文件）")
    public String uploadFiles(List<MultipartFile> files) {
//    public String uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                String fileName = minioService.uploadFile(file);
                // 这里可以根据需求保存文件名或路径到数据库
                System.out.println("Uploaded file: " + fileName);
            }
            return "Files uploaded successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload files.";
        }
    }

}
