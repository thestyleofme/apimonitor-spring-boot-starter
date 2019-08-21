package org.abigballofmud.apimonitor.infra.utils;

import org.abigballofmud.apimonitor.domain.entity.CallerInfo;

/**
 * <p>
 * 调用接口方使用此类返回调用方信息CallerInfo
 * </p>
 *
 * @author isacc 2019/08/21 11:52
 * @since 1.0
 */
public class CallerInfoContextHolder {

    private CallerInfoContextHolder() {
        throw new IllegalStateException("context class!");
    }

    private static final ThreadLocal<CallerInfo> CALLER_INFO_THREAD_LOCAL = new InheritableThreadLocal<>();

    public static CallerInfo current() {
        return CALLER_INFO_THREAD_LOCAL.get();
    }

    public static void setCallerInfo(CallerInfo callerInfo) {
        CALLER_INFO_THREAD_LOCAL.set(callerInfo);
    }

    public static void clear() {
        CALLER_INFO_THREAD_LOCAL.remove();
    }

}
