package org.abigballofmud.apimonitor.infra.aspect;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.abigballofmud.apimonitor.app.service.ApiMonitorRecordService;
import org.abigballofmud.apimonitor.domain.entity.CallerInfo;
import org.abigballofmud.apimonitor.infra.constant.CallerInfoConstants;
import org.abigballofmud.apimonitor.infra.utils.ApiMonitorUtil;
import org.abigballofmud.apimonitor.infra.utils.CallerInfoContextHolder;
import org.abigballofmud.apimonitor.infra.utils.IpUtil;
import org.abigballofmud.apimonitor.infra.utils.TimeUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>AOP实现</p>
 *
 * @author isacc 2019-8-19 14:04:55
 * @since 1.0
 */
@Aspect
@Slf4j
public class ApiMonitorAspect {

    private ApiMonitorRecordService apiMonitorRecordService;

    public ApiMonitorAspect(ApiMonitorRecordService apiMonitorRecordService) {
        this.apiMonitorRecordService = apiMonitorRecordService;
    }

    private ThreadLocal<ApiMonitorRecordDTO> apiMonitorRecordDTO = new InheritableThreadLocal<>();

    @Pointcut("@annotation(org.abigballofmud.apimonitor.infra.annotation.ApiMonitor)")
    public void pointcut() {
        // ignore
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        ApiMonitorRecordDTO dto = ApiMonitorRecordDTO.builder().build();
        apiMonitorRecordDTO.set(dto);
        // request_time
        dto.setRequestTime(LocalDateTime.now());
        // request_id
        dto.setRequestId(UUID.randomUUID().toString());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // request_header request_param
            ApiMonitorUtil.requestHeaderAndParamHandler(dto, request);
            // user_agent
            dto.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
            // referer
            dto.setReferer(request.getParameter(HttpHeaders.REFERER));
            // request_url
            dto.setRequestUrl(request.getRequestURL().toString());
            // request_method
            dto.setRequestMethod(request.getMethod());
            // ip
            dto.setIp(IpUtil.getRealIpAddr(request));
            // class_method
            dto.setClassMethod(String.format("%s.%s()",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName()));
        }
    }

    @After("pointcut()")
    public void after() {
        ApiMonitorRecordDTO dto = apiMonitorRecordDTO.get();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletResponse response = attributes.getResponse();
            if (response != null) {
                String responseCode = String.valueOf(response.getStatus());
                // response_code
                dto.setResponseCode(responseCode);
                // response_status
                if (HttpStatus.valueOf(response.getStatus()).is2xxSuccessful()) {
                    dto.setResponseStatus("SUCCESS");
                } else {
                    dto.setResponseStatus("FAIL");
                }
            }
        }
        // response_time
        dto.setResponseTime(LocalDateTime.now());
        // invoke_cost
        dto.setInvokeCost(Duration.between(dto.getRequestTime(), dto.getResponseTime()).toMillis());
        // invoke_cost_format
        dto.setInvokeCostFormat(TimeUtil.timestamp2String(Duration.between(dto.getRequestTime(), dto.getResponseTime()).toMillis()));
    }

    @AfterReturning(pointcut = "pointcut()", returning = "object")
    public void afterReturning(Object object) {
        // response_entity
        ApiMonitorRecordDTO dto = apiMonitorRecordDTO.get();
        dto.setResponseEntity(object.toString());
        this.callInfoHandler(dto);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "exception")
    public void afterThrowing(Exception exception) {
        // 抛异常时重写返回信息
        ApiMonitorRecordDTO dto = apiMonitorRecordDTO.get();
        // response_code
        dto.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        // response_status
        dto.setResponseStatus("FAIL");
        // response_entity
        dto.setResponseEntity(exception.toString());
        this.callInfoHandler(dto);
    }

    private void callInfoHandler(ApiMonitorRecordDTO dto) {
        // 返回信息必须含有调用方信息 serviceId tenantId userId clientId
        CallerInfo callerInfo = CallerInfoContextHolder.current();
        dto.setServiceId(Optional.ofNullable(callerInfo.getServiceId()).orElse(CallerInfoConstants.DEFAULT_SERVICE_ID));
        dto.setTenantId(Optional.ofNullable(callerInfo.getTenantId()).orElse(CallerInfoConstants.DEFAULT_TENANT_ID));
        dto.setUserId(Optional.ofNullable(callerInfo.getUserId()).orElse(CallerInfoConstants.DEFAULT_USER_ID));
        dto.setClientId(Optional.ofNullable(callerInfo.getClientId()).orElse(CallerInfoConstants.DEFAULT_CLIENT_ID));
        dto.setRoleId(Optional.ofNullable(callerInfo.getRoleId()).orElse(CallerInfoConstants.DEFAULT_ROLE_ID));
        log.debug("ApiMonitorRecordDTO: {}", dto);
        // 新增API监控记录
        apiMonitorRecordService.insert(dto);
        apiMonitorRecordDTO.remove();
        CallerInfoContextHolder.clear();
    }
}
