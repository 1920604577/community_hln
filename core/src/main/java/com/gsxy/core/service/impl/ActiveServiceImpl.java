package com.gsxy.core.service.impl;

import com.gsxy.core.controller.MinioController;
import com.gsxy.core.mapper.ActiveMapper;
import com.gsxy.core.pojo.Active;
import com.gsxy.core.pojo.Channel;
import com.gsxy.core.pojo.bo.ActiveAddBo;
import com.gsxy.core.pojo.enums.ActiveTypeEnum;
import com.gsxy.core.pojo.enums.ChannelTypeEnum;
import com.gsxy.core.pojo.vo.ActiveVo;
import com.gsxy.core.pojo.vo.ResponseVo;
import com.gsxy.core.service.ActiveService;
import com.gsxy.core.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

import static com.gsxy.core.pojo.enums.CodeValues.ERROR_CODE;
import static com.gsxy.core.pojo.enums.CodeValues.SUCCESS_CODE;
import static com.gsxy.core.pojo.enums.MessageValues.ERROR_MESSAGE;
import static com.gsxy.core.pojo.enums.MessageValues.SUCCESS_MESSAGE;

@Service
public class ActiveServiceImpl implements ActiveService {

    @Autowired
    private ActiveMapper activeMapper;
//    @Autowired
//    private MinioController minioController;
    @Autowired
    private MinIOFileStorageServiceImpl minIOFileStorageServiceImpl;

    @Override
    @Transactional
    public ResponseVo addActive(ActiveAddBo activeAddBo) {

        Active active = activeMapper.queryIsHave(activeAddBo.getId());
        Long loginUserId = LoginUtils.getLoginUserId();

        //类型转换
        ActiveTypeEnum typeEnum = null;
        if (activeAddBo.getType().equals("ORDINARY"))
            typeEnum = ActiveTypeEnum.ORDINARY;
        else if (activeAddBo.getType().equals("IMPORTANT"))
            typeEnum = ActiveTypeEnum.IMPORTANT;

        Long isSuccess = null;
        String filePath = null;
        if (ObjectUtils.isEmpty(active)) {

            if(!ObjectUtils.isEmpty(activeAddBo.getFilePath())){
                List<MultipartFile> filePaths = activeAddBo.getFilePath();
                try {
                    for (MultipartFile file : filePaths) {
                        // 上传文件到MinIO服务器
                        String fileMinIo = minIOFileStorageServiceImpl.uploadFile(file);
                        filePath += "," + fileMinIo;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            isSuccess = activeMapper.add(
                    Active.builder()
                            .title(activeAddBo.getTitle())
                            .channelId(activeAddBo.getChannelId())
                            .message(activeAddBo.getMessage())
                            .startTime(activeAddBo.getStartTime())
                            .filePath(filePath == null ? null : filePath.replaceFirst(",",""))
                            .type(typeEnum)
                            .createdBy(loginUserId)
                            .createdTime(new Date())
                            .endTime(activeAddBo.getEndTime())
                            .build()
            );

            if(!ObjectUtils.isEmpty(activeAddBo.getFilePath()) && !activeAddBo.getFilePath().equals("")){
                return ResponseVo.builder()
                        .code(ERROR_CODE)
                        .data(null)
                        .message(ERROR_MESSAGE)
                        .build();
            }
        } else {

            String filePathOld = null;
            if(!ObjectUtils.isEmpty(activeAddBo.getFilePathOld()) && !ObjectUtils.isEmpty(activeAddBo.getFilePath())){
                List<MultipartFile> filePaths = activeAddBo.getFilePath();

                try {
                    for (MultipartFile file : activeAddBo.getFilePathOld()) {
                        filePathOld += "," + file;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    for (MultipartFile file : filePaths) {
                        // 上传文件到MinIO服务器
                        String fileMinIo = null;
                        if(!filePathOld.contains(String.valueOf(file))){
                            fileMinIo = minIOFileStorageServiceImpl.uploadFile(file);
                        } else {
                            fileMinIo = String.valueOf(file);
                        }
                        filePath += "," + fileMinIo;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!ObjectUtils.isEmpty(activeAddBo.getFilePath())) {
                List<MultipartFile> filePaths = activeAddBo.getFilePath();
                try {
                    for (MultipartFile file : filePaths) {
                        // 上传文件到MinIO服务器
                        String fileMinIo = minIOFileStorageServiceImpl.uploadFile(file);
                        filePath += "," + fileMinIo;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            isSuccess = activeMapper.update(
                    Active.builder()
                            .id(activeAddBo.getId())
                            .title(activeAddBo.getTitle() == null ? null : activeAddBo.getTitle())
                            .channelId(activeAddBo.getChannelId())
                            .message(activeAddBo.getMessage() == null ? null : activeAddBo.getMessage())
                            .filePath(filePath == null ? null : filePath.replaceFirst(",",""))
                            .startTime(activeAddBo.getStartTime() == null ? null : activeAddBo.getStartTime())
                            .type(typeEnum)
                            .updatedBy(loginUserId)
                            .updatedTime(new Date())
                            .endTime(activeAddBo.getEndTime() == null ? null : activeAddBo.getEndTime())
                            .build()
            );

            if(!ObjectUtils.isEmpty(activeAddBo.getFilePathOld()) && !activeAddBo.getFilePathOld().equals("")){
                //删除minio上的更新前的文件
                if(!ObjectUtils.isEmpty(activeAddBo.getFilePathOld())){
                    List<MultipartFile> filePaths = activeAddBo.getFilePathOld();
                    try {
                        for (MultipartFile file : filePaths) {
                            if(!filePath.contains(String.valueOf(file))){
                                minIOFileStorageServiceImpl.delete(String.valueOf(file));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L) {
            return ResponseVo.builder()
                    .code(ERROR_CODE)
                    .data(null)
                    .message(ERROR_MESSAGE)
                    .build();
        }

        return ResponseVo.builder()
                .data(null)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo deleteActive(Long id) {

        Long isSuccess = activeMapper.deleteActive(id);

        if (!ObjectUtils.isEmpty(isSuccess) && isSuccess == 0L) {
            return ResponseVo.builder()
                    .code(ERROR_CODE)
                    .data(null)
                    .message(ERROR_MESSAGE)
                    .build();
        }

        // TODO 这里关于活动如果有上传的附件文件的话可以在此处删除（附件文件在minio上存储） -> 目前没做

        return ResponseVo.builder()
                .data(null)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public ResponseVo queryActiveByPage(Long page, Long limit, String title,Long channelId ,String type) {

        page = (page - 1) * limit;
        ActiveTypeEnum typeEnum = null;

        //类型转换
        if(!ObjectUtils.isEmpty(type) && type.equals("ORDINARY"))
            typeEnum = ActiveTypeEnum.ORDINARY;
        else if (!ObjectUtils.isEmpty(type) && type.equals("IMPORTANT"))
            typeEnum = ActiveTypeEnum.IMPORTANT;

        List<ActiveVo> activeVoLists = activeMapper.queryActiveByPage(page, limit, title,channelId ,typeEnum);
        Long count = activeMapper.queryActiveByPageCount(title,channelId ,typeEnum);

        return ResponseVo.builder()
                .data(activeVoLists)
                .count(count)
                .code(SUCCESS_CODE)
                .message(SUCCESS_MESSAGE)
                .build();
    }
}
