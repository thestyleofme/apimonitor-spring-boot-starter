package org.abigballofmud.apimonitor.infra.config;

import lombok.EqualsAndHashCode;
import org.abigballofmud.apimonitor.app.service.ApiMonitorRecordService;
import org.abigballofmud.apimonitor.infra.aspect.ApiMonitorAspect;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan({
        "org.abigballofmud.**.mapper"
})
public class ApiMonitorConfiguration {

    @Bean
    public ApiMonitorAspect apiMonitorAspect(ApiMonitorRecordService apiMonitorRecordService) {
        return new ApiMonitorAspect(apiMonitorRecordService);
    }

}
