package com.lmh.eduService.client;

import com.lmh.eduService.client.impl.UcenterClientImpl;
import com.lmh.orderVo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)//加入需要调用服务名，注册中心中的命子
public interface UcenterClient {
    @PostMapping("/ucenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id);
}
