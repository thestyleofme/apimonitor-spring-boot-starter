package org.abigballofmud.apimonitor.infra.utils;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/08/20 15:50
 * @since 1.0
 */
public class TimeUtil {

    private TimeUtil() {
        throw new IllegalStateException("util class!");
    }

    /**
     * 时间戳转为时分秒
     *
     * @param milliseconds 毫秒
     * @return java.lang.String
     * @author isacc 2019-8-20 15:50:48
     */
    public static String timestamp2String(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hour = seconds / 3600;
        long minute = (seconds - hour * 3600) / 60;
        long second = (seconds - hour * 3600 - minute * 60);

        StringBuilder sb = new StringBuilder();
        if (hour > 0) {
            sb.append(hour).append("h ");
        }
        if (minute > 0) {
            sb.append(minute).append("m ");
        }
        if (second > 0) {
            sb.append(second).append("s");
        }
        if (second == 0) {
            sb.append("<1s");
        }
        return sb.toString();
    }

}
