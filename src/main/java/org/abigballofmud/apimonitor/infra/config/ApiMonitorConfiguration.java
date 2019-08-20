package org.abigballofmud.apimonitor.infra.config;

import lombok.EqualsAndHashCode;
import org.abigballofmud.apimonitor.app.service.ApiMonitorRecordService;
import org.abigballofmud.apimonitor.infra.aspect.ApiMonitorAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/06/11 2019-8-19 15:17:30
 */
@EqualsAndHashCode(callSuper = false)
@Configuration
@ComponentScan("org.abigballofmud.apimonitor")
public class ApiMonitorConfiguration {

    @Bean
    public ApiMonitorAspect apiMonitorAspect(ApiMonitorRecordService apiMonitorRecordService) {
        return new ApiMonitorAspect(apiMonitorRecordService);
    }

}
