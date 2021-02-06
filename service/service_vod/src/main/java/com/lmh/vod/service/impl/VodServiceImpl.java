package com.lmh.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.lmh.base.handler.LmhException;
import com.lmh.vod.service.VodService;
import com.lmh.vod.utils.InitVodClient;
import com.lmh.utils.ResultCode;
import com.lmh.vod.utils.VodPropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadAlyVideo(MultipartFile file) {
        try {
            String fileName=file.getOriginalFilename();
            String title=fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream= null;
            inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId=null;
            if (response.isSuccess()) {
                videoId=response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId=response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteAlyVideo(String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch(Exception e) {
            e.printStackTrace();
            throw new LmhException(ResultCode.VIDEO_NOT_DELETE.getCode(),ResultCode.VIDEO_NOT_DELETE.getMessage());
        }
    }

    @Override
    public void deleteAlyVideoList(List<String> listVideoIds) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //videoIdList转换成1，2，3形式
            String videoIds = StringUtils.join(listVideoIds.toArray(), ",");

            //向request设置视频id
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);

        }catch(Exception e) {
            e.printStackTrace();
            throw new LmhException(ResultCode.VIDEO_NOT_DELETE.getCode(),ResultCode.VIDEO_NOT_DELETE.getMessage());
        }
    }
}
