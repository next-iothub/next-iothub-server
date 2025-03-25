package com.wangsl.common.exception;

import lombok.Getter;

/**
 * 全局异常枚举
 */
@Getter
public enum IothubExceptionEnum implements IothubExceptionMap {

    ERROR_USER_NOT_EXIST("100", "用户不存在"),
    ERROR_USERNAME_EXIST("101", "用户名已存在"),
    USER_DISABLED("1002", "您的账号已被停用"),
    ERROR_PARAM("400", "请求参数有误"),
    ERROR_UNAUTHORIZED("401", "用户未授权"),
    ERROR_TOKEN_EXPIRED("402", "token过期"),
    ERROR_FORBIDDEN("403", "资源被禁止访问"),
    ERROR_SERVER("500", "系统异常"),
    ERROR_TIME_OUT("502", "超时操作"),
    ERROR_VERIFY_CODE_WRONG("600", "验证码不正确"),
    ERROR_NOT_LOGIN_TO_COMMENT("601", "请先登录"),
    ERROR_USER_STATE_NOT_VALID("602", "账户异常，请联系管理员"),
    ERROR_UNABLE_GET_USER("603", "无法获取用户"),
    ERROR_GAIN_RESOURCE("604", "获取资源信息异常"),
    ERROR_CAN_NOT_DELETE_RESOURCE("900", "默认资源无法删除"),
    ERROR_IN_BLACKLIST("901", "检测到你进行非法操作，已被列入黑名单!"),
    ERROR_DEVICE_STATUS("800", "设备状态实时推送失败"),
    ERROR_PRODUCT_KEY_INVALID("801", "产品 key 无效，产品不存在"),
    ERROR_PRODUCT_NAME_EXIST("802", "产品名称已存在"),
    ERROR_DEVICE_NAME_EXIST("810", "设备名已经存在"),
    ERROR_PRODUCT_PROPERTY_EXIST("900", "该属性已经存在"),
    ERROR_PRODUCT_EVENT_EXIST("901", "该事件已经存在"),
    ERROR_PRODUCT_SERVICE_EXIST("902", "该服务已经存在");


    private final String code;

    private final String message;

    IothubExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
