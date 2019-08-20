package org.abigballofmud.apimonitor.infra.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * API接口监控注解
 * </p>
 *
 * @author isacc 2019-8-19 13:58:14
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMonitor {

}
