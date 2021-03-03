package com.lmh.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.lmh.base.handler.LmhException;
import com.lmh.order.entity.TOrder;
import com.lmh.order.entity.TPayLog;
import com.lmh.order.mapper.TPayLogMapper;
import com.lmh.order.service.TOrderService;
import com.lmh.order.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.order.utils.ConstantWxUtils;
import com.lmh.order.utils.HttpClient;
import com.lmh.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {
    @Autowired
    private TOrderService orderService;

    @Override
    public Map createNative(String orderNo) {
        try{
            QueryWrapper<TOrder>wrapper=new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            TOrder order = orderService.getOne(wrapper);
            Map m=new HashMap();
            //m.put("appid","wx74862e0dfcf69954");
            m.put("appid", ConstantWxUtils.WX_PAY_APPID);
            //m.put("mch_id", "1558950191");
            m.put("mch_id", ConstantWxUtils.WX_PAY_PARTNER);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            //m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("notify_url", ConstantWxUtils.WX_PAY_NOTIFYURL);
            m.put("trade_type", "NATIVE");
            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            //client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setXmlParam(WXPayUtil.generateSignedXml(m,ConstantWxUtils.WX_PAY_PARTNERKEY));
            client.setHttps(true);
            //执行post请求发送
            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        //二维码地址

            return map;
        }catch (Exception e){
            throw new LmhException(ResultCode.WX_CREATE_ERROR.getCode(),ResultCode.WX_CREATE_ERROR.getMessage());
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", ConstantWxUtils.WX_PAY_APPID);
            //m.put("appid", "wx74862e0dfcf69954");
            //m.put("mch_id", "1558950191");
            m.put("mch_id", ConstantWxUtils.WX_PAY_PARTNER);
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,ConstantWxUtils.WX_PAY_PARTNERKEY));
            //client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3 得到请求返回内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map再返回
            return resultMap;
        }catch(Exception e) {
            return null;
        }
    }

    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        String out_trade_no = map.get("out_trade_no");
        QueryWrapper<TOrder>wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",out_trade_no);
        TOrder order = orderService.getOne(wrapper);
        //更新订单表里的状态
        if(order.getStatus().intValue()==1){
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);
        //向支付表里面添加支付记录
        TPayLog payLog = new TPayLog();
        payLog.setOrderNo(out_trade_no);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }
}
