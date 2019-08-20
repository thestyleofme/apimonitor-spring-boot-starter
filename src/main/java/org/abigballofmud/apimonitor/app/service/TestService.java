package org.abigballofmud.apimonitor.app.service;

import org.abigballofmud.apimonitor.domain.entity.CallerInfo;

/**
 * description
 *
 * @author isacc 2019/08/19 15:35
 * @since 1.0
 */
public interface TestService {

    /**
     * 测试接口监控
     *
     * @return org.abigballofmud.apimonitor.domain.entity.CallerInfo
     * @author isacc 2019/8/20 16:49
     */
    CallerInfo hello();
}
