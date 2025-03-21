package com.wangsl.common.exception;

/**
 * description: 全局异常抛出
 */
public class ExceptionUtil {

    private ExceptionUtil() {}

    /**
     * 抛异常
     */
    public static void throwEx(IothubExceptionMap iothubExceptionMap) {
        throw new IothubException(iothubExceptionMap, true);
    }

    /**
     * 抛异常
     */
    public static void throwEx(String code, String message) {
        throw new IothubException(code, message);
    }

    /**
     * 抛异常到异常页面
     */
    public static void throwExToPage(IothubExceptionMap iothubExceptionMap) {
        throw new IothubException(iothubExceptionMap, false);
    }

}
