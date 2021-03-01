package com.lmh.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.order.entity.TOrder;
import com.lmh.order.service.TOrderService;
import com.lmh.utils.JwtUtils;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
@Api(tags = "支付订单")
@CrossOrigin
@RestController
@RequestMapping("/eduOrder/t-order")
public class TOrderController {
    @Autowired
    private TOrderService orderService;
    @ApiOperation("生成订单")
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String tOrderId=orderService.createOrders(courseId,memberIdByJwtToken);
        return R.ok().data("orderId",tOrderId);
    }
    @ApiOperation("根据订单ID查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<TOrder>wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }
}

