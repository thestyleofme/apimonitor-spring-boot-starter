package org.abigballofmud.apimonitor.infra.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/08/22 10:53
 * @since 1.0
 */
@Slf4j
public class ApiMonitorUtil {

    private ApiMonitorUtil() {
        throw new IllegalStateException("util class!");
    }

    public static ApiMonitorRecordDTO generateErrorApiMonitorRecordDTO(HttpServletRequest request) {
        ApiMonitorRecordDTO dto = ApiMonitorRecordDTO.builder().build();
        // request_time
        dto.setRequestTime(LocalDateTime.now());
        // request_id
        dto.setRequestId(UUID.randomUUID().toString());
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
        // request_header request_param
        ApiMonitorUtil.requestHeaderAndParamHandler(dto, request);
        // response_code
        dto.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        // response_status
        dto.setResponseStatus("FAIL");
        // response_time
        dto.setResponseTime(LocalDateTime.now());
        // invoke_cost
        dto.setInvokeCost(Duration.between(dto.getRequestTime(), dto.getResponseTime()).toMillis());
        // invoke_cost_format
        dto.setInvokeCostFormat(TimeUtil.timestamp2String(Duration.between(dto.getRequestTime(), dto.getResponseTime()).toMillis()));
        return dto;
    }

    public static void requestHeaderAndParamHandler(ApiMonitorRecordDTO dto, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        HashMap<String, Object> map = new HashMap<>(16);
        while (headerNames.hasMoreElements()) {
            String element = headerNames.nextElement();
            map.put(element, request.getHeader(element));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // request_header
            dto.setRequestHeader(objectMapper.writeValueAsString(map));
            // request_param
            dto.setRequestParam(objectMapper.writeValueAsString(request.getParameterMap()));
        } catch (JsonProcessingException e) {
            log.error("request header[Map -> String] error!");
        }
    }


}
