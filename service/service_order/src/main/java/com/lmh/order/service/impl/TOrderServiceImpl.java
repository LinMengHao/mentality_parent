package com.lmh.order.service.impl;

import com.lmh.order.client.EduClient;
import com.lmh.order.client.UcenterClient;
import com.lmh.order.entity.TOrder;
import com.lmh.order.mapper.TOrderMapper;
import com.lmh.order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.order.utils.OrderNoUtil;
import com.lmh.orderVo.CourseWebVoOrder;
import com.lmh.orderVo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberIdByJwtToken);

        //通过远程调用根据课程id获取课信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象，向order对象里面设置需要数据
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setPsychologistName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);

        //更新课程销量
        eduClient.updateBuyCountById(order.getCourseId());

        //返回订单号
        return order.getOrderNo();
    }
}
