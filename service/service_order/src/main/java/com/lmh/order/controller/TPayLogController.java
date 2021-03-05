package com.lmh.order.controller;


import com.lmh.order.service.TPayLogService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
@Api(tags = "订单支付记录")
//@CrossOrigin
@RestController
@RequestMapping("/eduOrder/t-pay-log")
public class TPayLogController {
    @Autowired
    private TPayLogService payLogService;
    @ApiOperation("生成微信支付二维码接口")
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map=payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @ApiOperation("查询订单支付的状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String, String>map=payLogService.queryPayStatus(orderNo);
        if(map==null){
            return R.error().message("支付出错");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(2012).message("支付中");
    }
}

