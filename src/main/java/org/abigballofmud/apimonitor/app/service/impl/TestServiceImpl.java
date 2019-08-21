package org.abigballofmud.apimonitor.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.abigballofmud.apimonitor.app.service.TestService;
import org.abigballofmud.apimonitor.domain.entity.CallerInfo;
import org.abigballofmud.apimonitor.infra.constant.CallerInfoConstants;
import org.abigballofmud.apimonitor.infra.utils.CallerInfoContextHolder;
import org.springframework.stereotype.Service;


/**
 * description
 *
 * @author isacc 2019/08/19 15:36
 * @since 1.0
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Override
    public CallerInfo hello() {
        log.debug("=======hello()=======");
        CallerInfo callerInfo = CallerInfo.builder().
                serviceId(CallerInfoConstants.DEFAULT_SERVICE_ID).
                tenantId(CallerInfoConstants.DEFAULT_TENANT_ID).
                userId(CallerInfoConstants.DEFAULT_USER_ID).
                clientId(CallerInfoConstants.DEFAULT_CLIENT_ID).build();
        CallerInfoContextHolder.setCallerInfo(callerInfo);
        // throw new IllegalArgumentException();
        return callerInfo;
    }

}
