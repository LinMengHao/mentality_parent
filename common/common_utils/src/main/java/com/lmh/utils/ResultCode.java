package com.lmh.utils;

/**
 * 返回数据和状态的枚举
 * 规定:
 * #200表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * #后面对什么的操作自己在这里注明就行了
 */
public enum ResultCode implements IResultCode {

    /* 成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    /*错误*/
    DORMITORY_NOT_DELETE(3007, "有入住人，不可删除，请先移除住户"),
    DORMITORY_ALREADY_EXIST(3008, "宿舍房间名已存在"),
    STUDENT_ID_EXIST(3009,"学号已经存在"),

    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),

    /*运行时异常*/
    ARITHMETIC_EXCEPTION(9001,"算数异常"),
    SAVE_ERROR(9002,"保存失败"),
    START_TIMEOUT(9003,"连接超时"),

    /*文件错误*/
    FILE_EMPTY(4001,"文件数据为空"),
    /*课程信息错误*/
    COURSE_NOT_SAVE(5001,"课程信息添加失败"),
    COURSE_NOT_UPDATE(5002,"课程信息修改失败"),
    COURSE_NOT_DELETE(5003,"课程删除失败"),
    /*章节错误*/
    CHAPTER_NOT_DELETE(6001,"章节删除失败，请先删除小节"),

    /*视频错误*/
    VIDEO_NOT_UPLOAD(7001,"视频失效"),
    VIDEO_NOT_DELETE(7002,"视频删除失败"),
    VIDEO_NOT_GET(7003,"获取视频凭证失败"),
    ;

    private Integer code;
    private String message;

    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
