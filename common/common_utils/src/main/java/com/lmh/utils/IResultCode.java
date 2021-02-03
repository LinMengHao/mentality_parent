package com.lmh.utils;

/**
 * 返回请求状态和信息
 */
public interface IResultCode {
    /**
     * 获取错误时状态码
     * @return
     */
    Integer getCode();

    /**
     * 获取错误时信息
     * @return
     */
    String getMessage();
}
