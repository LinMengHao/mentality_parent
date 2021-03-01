package com.lmh.order.client.fallback;


import com.lmh.order.client.EduClient;
import com.lmh.orderVo.CourseWebVoOrder;
import com.lmh.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EduClientFallBack implements EduClient {
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String id) {
        log.error("熔断器被执行");
        return null;
    }

    public R updateBuyCountById(String id) {
        log.error("熔断器被执行");
        return R.error();
    }
}
