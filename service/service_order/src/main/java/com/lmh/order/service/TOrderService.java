package com.lmh.order.service;

import com.lmh.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
