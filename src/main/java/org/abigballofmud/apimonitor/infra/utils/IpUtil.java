package org.abigballofmud.apimonitor.infra.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * IP工具类
 *
 * @author isacc 2019/08/19 16:04
 * @since 1.0
 */
@Slf4j
public class IpUtil {

    private IpUtil() {
        throw new IllegalStateException("util class!");
    }

    private static final String UNKNOWN = "UNKNOWN";
    private static final String COMMA = ",";
    private static final String LOCAL_HOST_1 = "127.0.0.1";
    private static final String LOCAL_HOST_2 = "0:0:0:0:0:0:0:1";
    private static final int IP_LENGTH = 15;

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     */
    public static String getRealIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip) && ip.contains(COMMA)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = ip.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return getLocalRealIp(ip);
    }

    private static String getLocalRealIp(String ip) {
        if (LOCAL_HOST_1.equals(ip) || LOCAL_HOST_2.equals(ip)) {
            // 根据网卡取本机配置的IP
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ip = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                log.error("UnknownHostException", e);
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length() = 15
            if (ip != null && ip.length() > IP_LENGTH && ip.contains(COMMA)) {
                ip = ip.substring(0, ip.indexOf(COMMA));
            }
        }
        return ip;
    }

}
