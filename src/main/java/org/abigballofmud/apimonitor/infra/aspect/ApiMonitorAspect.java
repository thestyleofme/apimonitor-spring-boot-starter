package org.abigballofmud.apimonitor.infra.aspect;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.abigballofmud.apimonitor.app.service.ApiMonitorRecordService;
import org.abigballofmud.apimonitor.domain.entity.CallerInfo;
import org.abigballofmud.apimonitor.infra.constant.CallerInfoConstants;
import org.abigballofmud.apimonitor.infra.converter.CallerInfoConvertMapper;
import org.abigballofmud.apimonitor.infra.utils.IpUtil;
import org.abigballofmud.apimonitor.infra.utils.TimeUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpHeaders;
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

    private static final String SUCCESS_CODE_START = "2";

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
            // request_header
            dto.setRequestHeader(request.getHeaderNames().toString());
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
            dto.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            // request_param
            dto.setRequestParam(Arrays.toString(joinPoint.getArgs()));
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
                if (responseCode.startsWith(SUCCESS_CODE_START)) {
                    dto.setResponseStatus("SUCCESS");
                } else {
                    dto.setResponseStatus("FAIL");
                }
            }
        }
        // response_time
        dto.setResponseTime(LocalDateTime.now());
        // invoke_cost
        dto.setInvokeCost(TimeUtil.timestamp2String(Duration.between(dto.getRequestTime(), dto.getResponseTime()).toMillis()));
    }

    @AfterReturning(pointcut = "pointcut()", returning = "object")
    public void getAfterReturn(Object object) {
        // response_entity
        ApiMonitorRecordDTO dto = apiMonitorRecordDTO.get();
        dto.setResponseEntity(object.toString());
        // 返回信息必须含有调用方信息 service_id tenant_id user_id client_id
        CallerInfo callerInfo;
        if (object instanceof CallerInfo) {
            // 方法只返回CallerInfo
            callerInfo = (CallerInfo) object;
        } else {
            // 默认方法里都会返回CallerInfo
            callerInfo = CallerInfoConvertMapper.INSTANCE.objectToCallerInfo(object);
        }
        dto.setServiceId(Optional.ofNullable(callerInfo.getServiceId()).orElse(CallerInfoConstants.DEFAULT_SERVICE_ID));
        dto.setTenantId(Optional.ofNullable(callerInfo.getTenantId()).orElse(CallerInfoConstants.DEFAULT_TENANT_ID));
        dto.setUserId(Optional.ofNullable(callerInfo.getUserId()).orElse(CallerInfoConstants.DEFAULT_USER_ID));
        dto.setClientId(Optional.ofNullable(callerInfo.getClientId()).orElse(CallerInfoConstants.DEFAULT_CLIENT_ID));
        log.debug("ApiMonitorRecordDTO: {}", dto);
        // 新增API监控记录
        apiMonitorRecordService.insert(dto);
        apiMonitorRecordDTO.remove();
    }

}
