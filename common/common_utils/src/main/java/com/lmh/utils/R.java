package com.lmh.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
  @ApiModelProperty(value = "是否成功")
  private Boolean success;

  @ApiModelProperty(value = "返回状态码")
  private Integer code;

  @ApiModelProperty(value = "返回消息")
  private String message;

  @ApiModelProperty(value = "返回数据")
  private Map<String, Object> data=new HashMap<>();

    /**
     * 构造方法私有，只能使用静态方法，达到保护属性的目的
     */
    private R(){};
//链式编程

    public static R ok(){
        R r=new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        return r;
    }
    public static R error(){
        R r=new R();
        r.setSuccess(false);
        r.setCode(ResultCode.COMMON_FAIL.getCode());
        r.setMessage(ResultCode.COMMON_FAIL.getMessage());
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R data(Map<String, Object>map){
        this.setData(map);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

}
