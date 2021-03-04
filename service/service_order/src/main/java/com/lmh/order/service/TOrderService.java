package com.lmh.order.service;

import com.lmh.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String courseId, String memberIdByJwtToken);

    Boolean isBoughtCourseId(String courseId, String memberId);

    List<TOrder> selectByMemberId(String memberId);

    boolean removeById(String orderId, String memberId);
}
