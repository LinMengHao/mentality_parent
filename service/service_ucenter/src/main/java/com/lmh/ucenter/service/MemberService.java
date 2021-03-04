package com.lmh.ucenter.service;

import com.lmh.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-02-23
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
