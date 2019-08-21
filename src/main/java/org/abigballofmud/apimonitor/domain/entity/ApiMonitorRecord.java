package org.abigballofmud.apimonitor.domain.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
@TableName(value = "xsvc_api_monitor_record")
public class ApiMonitorRecord {

    public static final String FIELD_MONITOR_ID = "monitorId";
    public static final String FIELD_REQUEST_ID = "requestId";
    public static final String FIELD_SERVICE_ID = "serviceId";
    public static final String FIELD_IP = "ip";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_CLIENT_ID = "clientId";
    public static final String FIELD_REQUEST_URL = "requestUrl";
    public static final String FIELD_REQUEST_METHOD = "requestMethod";
    public static final String FIELD_REQUEST_HEADER = "requestHeader";
    public static final String FIELD_USER_AGENT = "userAgent";
    public static final String FIELD_CLASS_METHOD = "classMethod";
    public static final String FIELD_REQUEST_PARAM = "requestParam";
    private static final String FIELD_REFERER ="referer" ;
    public static final String FIELD_REQUEST_TIME = "requestTime";
    public static final String FIELD_RESPONSE_TIME = "responseTime";
    public static final String FIELD_INVOKE_COST = "invokeCost";
    public static final String FIELD_RESPONSE_CODE = "responseCode";
    public static final String FIELD_RESPONSE_STATUS = "responseStatus";
    public static final String FIELD_RESPONSE_ENTITY = "responseEntity";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_CREATION_DATE = "creationDate";
    public static final String FIELD_CREATED_BY = "createdBy";
    public static final String FIELD_LAST_UPDATE_DATE = "lastUpdateDate";
    public static final String FIELD_LAST_UPDATED_BY = "lastUpdatedBy";
    public static final String FIELD_OBJECT_VERSION_NUMBER = "objectVersionNumber";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    @TableId(type = IdType.AUTO)
    private Long monitorId;

    private String requestId;

    private Long serviceId;

    private String ip;

    private Long userId;

    private Long clientId;

    private String requestUrl;

    private String requestMethod;

    private String requestHeader;

    private String userAgent;

    private String referer;

    private String classMethod;

    private String requestParam;

    private LocalDateTime requestTime;

    private LocalDateTime responseTime;

    private String invokeCost;

    private String responseCode;

    private String responseStatus;

    private String responseEntity;

    private Long tenantId;

    private LocalDateTime creationDate;

    private Long createdBy;

    private LocalDateTime lastUpdateDate;

    private Long lastUpdatedBy;

    @Version
    private Long objectVersionNumber;

}