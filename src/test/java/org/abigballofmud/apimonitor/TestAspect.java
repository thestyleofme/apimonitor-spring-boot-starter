package org.abigballofmud.apimonitor;

import org.abigballofmud.apimonitor.api.v1.controller.TestController;
import org.abigballofmud.apimonitor.domain.entity.CallerInfo;
import org.abigballofmud.apimonitor.infra.config.ApiMonitorConfiguration;
import org.abigballofmud.apimonitor.infra.config.MybatisPlusConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/08/21 9:26
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ApiMonitorConfiguration.class,
        DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class,
        MybatisPlusConfig.class
})
public class TestAspect {

    @Autowired
    TestController testController;

    @Test
    public void test() {
        CallerInfo callerInfo = testController.hello();
        Assert.assertNotNull(callerInfo);
    }

}

