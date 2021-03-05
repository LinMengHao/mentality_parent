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
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
@Api(tags = "支付订单")
//@CrossOrigin
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
    @ApiOperation("判断课程是否被购买")
    @GetMapping("isBought/{courseId}")
    public R isBuyByCourseId(@PathVariable String courseId,HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBought=orderService.isBoughtCourseId(courseId,memberId);
        return R.ok().data("isBought",isBought);
    }
    //获取当前用户订单列表
    @ApiOperation(value = "获取当前用户订单列表")
    @GetMapping("orderList")
    public R list(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        List<TOrder> list = orderService.selectByMemberId(memberId);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "删除订单")
    @DeleteMapping("removeOrder/{orderId}")
    public R remove(@PathVariable String orderId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = orderService.removeById(orderId, memberId);
        if(result){
            return R.ok().message("删除订单成功");
        }else{
            return R.error().message("数据不存在");
        }
    }
}

