package com.lmh.eduService.client;

import com.lmh.eduService.client.impl.VodClientImpl;
import com.lmh.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)//加入需要调用服务名，注册中心中的命子
public interface VodClient {
    //删除单个视频
    @DeleteMapping("/vodService/deleteAlyVideo/{id}")
    public R deleteAlyVideo(@PathVariable("id") String id);

    //删除多个视频
    @DeleteMapping("/vodService/deleteAlyVideoList")
    public R deleteAlyVideoList(@RequestParam("videoIdList") List<String> listVideoIds);
}
