package org.abigballofmud.apimonitor.api.v1.controller;

import io.swagger.annotations.ApiOperation;
import org.abigballofmud.apimonitor.app.service.TestService;
import org.abigballofmud.apimonitor.domain.entity.CallerInfo;
import org.abigballofmud.apimonitor.infra.annotation.ApiMonitor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author isacc 2019/08/19 15:06
 * @since 1.0
 */
@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @ApiOperation(value = "测试接口")
    @GetMapping("/hello")
    @ApiMonitor
    public CallerInfo hello() {
        return testService.hello();
    }

}
