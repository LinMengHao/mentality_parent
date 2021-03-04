package com.lmh.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.base.handler.LmhException;
import com.lmh.ucenter.entity.Member;
import com.lmh.ucenter.entity.vo.RegisterVo;
import com.lmh.ucenter.mapper.MemberMapper;
import com.lmh.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.utils.JwtUtils;
import com.lmh.utils.MD5;
import com.lmh.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-02-23
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new LmhException(ResultCode.PARAM_IS_BLANK.getCode(),ResultCode.PARAM_IS_BLANK.getMessage());
        }
        //判断手机号是否正确
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member member1 = baseMapper.selectOne(wrapper);
        //判断对象是否为空
        if(member1==null){
            throw new LmhException(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode(),ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        //判断密码时，先加密在对比
        if(!MD5.encrypt(password).equals(member1.getPassword())){
            throw new LmhException(ResultCode.USER_CREDENTIALS_ERROR.getCode(),ResultCode.USER_CREDENTIALS_ERROR.getMessage());
        }
        //判断用户是否被禁用
        if(member1.getIsDisabled()==1){
            throw new LmhException(ResultCode.USER_ACCOUNT_LOCKED.getCode(),ResultCode.USER_ACCOUNT_LOCKED.getMessage());
        }

        //登录成功，生成一个token
        String token = JwtUtils.getJwtToken(member1.getId(), member1.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
//获取数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw new LmhException(ResultCode.PARAM_IS_BLANK.getCode(),ResultCode.PARAM_IS_BLANK.getMessage());
        }

        //判断验证码，由于redis存入了验证码
        //获取
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!redisCode.equals(code)){
            throw new LmhException(ResultCode.CODE_ERROR.getCode(),ResultCode.CODE_ERROR.getMessage());
        }

        //判断手机号不可重复
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(wrapper);
        if(integer>0){
            throw new LmhException(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode(),ResultCode.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }

        //添加到数据库
        Member member=new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//加密密码
        member.setIsDisabled(0);
        member.setAvatar("https://edu-manager-lmh.oss-cn-beijing.aliyuncs.com/u%3D2561659095%2C299912888%26fm%3D26%26gp%3D0.jpg");
        baseMapper.insert(member);
    }

    @Override
    public Member getOpenIdMember(String openid) {
        if(!StringUtils.isEmpty(openid)){
            QueryWrapper<Member> wrapper=new QueryWrapper<>();
            wrapper.eq("openid",openid);
            Member member = baseMapper.selectOne(wrapper);
            return member;
        }else {
            return null;
        }
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
