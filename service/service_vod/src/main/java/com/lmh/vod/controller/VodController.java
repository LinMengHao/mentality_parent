package com.lmh.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lmh.base.handler.LmhException;
import com.lmh.vod.service.VodService;
import com.lmh.vod.utils.InitVodClient;
import com.lmh.utils.R;
import com.lmh.utils.ResultCode;
import com.lmh.vod.utils.VodPropertiesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "视频点播")
@CrossOrigin
@RestController
@RequestMapping("/vodService")
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("视频上传到阿里云")
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file){
        //返回上传视频id
        String videoId=vodService.uploadAlyVideo(file);
        return R.ok().data("videoId",videoId);
    }

    @ApiOperation("删除单个视频")
    @DeleteMapping("deleteAlyVideo/{id}")
    public R deleteAlyVideo(@PathVariable String id){
        vodService.deleteAlyVideo(id);
        return R.ok();
    }

    @ApiOperation("删除多个视频")
    @DeleteMapping("deleteAlyVideoList")
    public R deleteAlyVideoList(@RequestParam("videoIdList") List<String> listVideoIds){
        vodService.deleteAlyVideoList(listVideoIds);
        return R.ok();
    }

    @ApiOperation("根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try{
            //创建初始化对象
            DefaultAcsClient client
                    = InitVodClient.initVodClient(VodPropertiesUtils.ACCESS_KEY_ID, VodPropertiesUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
            //向设置视频id
            request.setVideoId(id);
            //调用方法获取凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch (Exception e){
            throw new LmhException(ResultCode.VIDEO_NOT_GET.getCode(),ResultCode.VIDEO_NOT_GET.getMessage());
        }
    }
}
