package com.lmh.eduService.controller;


import com.lmh.base.handler.LmhException;
import com.lmh.eduService.client.VodClient;
import com.lmh.eduService.entity.EduVideo;
import com.lmh.eduService.service.EduVideoService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-01-26
 */

@Api(tags = "课程小结视频")
@CrossOrigin
@RestController
@RequestMapping("/eduService/edu-video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    @ApiOperation("删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId=eduVideo.getVideoSourceId();
        //判断是否有视频
        if(!StringUtils.isEmpty(videoSourceId)){
            //微服务，nacos服务注册，该方法调用，删除阿里云视频
            R r = vodClient.deleteAlyVideo(videoSourceId);
            if(r.getCode()==7002){
                throw new LmhException(7002,"删除视频失败，服务器熔断");
            }
        }
        boolean b = videoService.removeById(id);
        return R.ok();
    }
    @ApiOperation("根据ID查询小节")
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }
    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

