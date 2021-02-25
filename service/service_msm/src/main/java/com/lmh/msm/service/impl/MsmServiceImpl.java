package com.lmh.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.lmh.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone))return false;
        DefaultProfile profile=
                DefaultProfile.getProfile("default", "LTAI4G9noLphDjqeDXQw6V2L", "MH3eOMahfADQxPmlX2gfyX5gAVFDeq");
        IAcsClient client=new DefaultAcsClient(profile);

        //设置相关参数  固定的
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", "双木林在线教育平台"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", "SMS_201723691"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递
        //最终发送
        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            boolean success = commonResponse.getHttpResponse().isSuccess();
            return success;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
