package org.abigballofmud.apimonitor.api.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p></p>
 *
 * @author isacc 2019-08-19 20:38:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel("API监控记录表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiMonitorRecordDTO {

    @ApiModelProperty("主键")
    private Long monitorId;

    @ApiModelProperty(value = "请求id UUID")
    @NotBlank
    @Size(max = 36)
    private String requestId;

    @ApiModelProperty(value = "服务ID")
    @NotNull
    private Long serviceId;

    @ApiModelProperty(value = "请求ip地址")
    @NotBlank
    @Size(max = 16)
    private String ip;

    @ApiModelProperty(value = "用户id")
    @NotNull
    private Long userId;

    @ApiModelProperty(value = "客户端登录时不为空")
    private Long clientId;

    @ApiModelProperty(value = "请求url")
    @NotBlank
    @Size(max = 511)
    private String requestUrl;

    @ApiModelProperty(value = "请求方法GET/POST等")
    @NotBlank
    @Size(max = 10)
    private String requestMethod;

    @ApiModelProperty(value = "请求头")
    @NotBlank
    private String requestHeader;

    @ApiModelProperty(value = "referer")
    private String referer;

    @ApiModelProperty(value = "User-Agent")
    @NotBlank
    @Size(max = 511)
    private String userAgent;

    @ApiModelProperty(value = "请求的具体方法名  格式：类.方法")
    @NotBlank
    @Size(max = 255)
    private String classMethod;

    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    @ApiModelProperty(value = "请求时间")
    @NotNull
    private LocalDateTime requestTime;

    @ApiModelProperty(value = "返回时间")
    @NotNull
    private LocalDateTime responseTime;

    @ApiModelProperty(value = "调用耗时")
    @NotBlank
    @Size(max = 63)
    private String invokeCost;

    @ApiModelProperty(value = "返回状态码")
    @NotBlank
    @Size(max = 10)
    private String responseCode;

    @ApiModelProperty(value = "返回状态 SUCCESS FAIL")
    @NotBlank
    @Size(max = 15)
    private String responseStatus;

    @ApiModelProperty(value = "返回结果")
    private String responseEntity;

    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    @ApiModelProperty(hidden = true)
    private LocalDateTime creationDate;

    @ApiModelProperty(hidden = true)
    private Long createdBy;

    @ApiModelProperty(hidden = true)
    private LocalDateTime lastUpdateDate;

    @ApiModelProperty(hidden = true)
    private Long lastUpdatedBy;

    @ApiModelProperty(hidden = true)
    private Long objectVersionNumber;

}
