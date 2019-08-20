package org.abigballofmud.apimonitor.api.v1.controller;

import java.util.ArrayList;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.abigballofmud.apimonitor.app.service.ApiMonitorRecordService;
import org.abigballofmud.apimonitor.domain.entity.ApiMonitorRecord;
import org.abigballofmud.apimonitor.domain.repository.ApiMonitorRecordRepository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * ApiMonitorRecord应用服务
 * </p>
 *
 * @author isacc 2019/08/19 21:22
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/{tenantId}/api-monitor-records")
public class ApiMonitorRecordController {

    private final ApiMonitorRecordService apiMonitorRecordService;
    private final ApiMonitorRecordRepository apiMonitorRecordRepository;

    public ApiMonitorRecordController(ApiMonitorRecordService apiMonitorRecordService, ApiMonitorRecordRepository apiMonitorRecordRepository) {
        this.apiMonitorRecordService = apiMonitorRecordService;
        this.apiMonitorRecordRepository = apiMonitorRecordRepository;
    }

    @ApiOperation(value = "API监控列表")
    @GetMapping
    public IPage<ApiMonitorRecordDTO> list(@PathVariable Long tenantId,
                                           Page<ApiMonitorRecord> apiMonitorRecordPage,
                                           ApiMonitorRecordDTO apiMonitorRecordDTO) {
        apiMonitorRecordDTO.setTenantId(tenantId);
        if (CollectionUtils.isEmpty(apiMonitorRecordPage.getOrders())) {
            // 默认设置主键降序排序
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(OrderItem.desc("MONITOR_ID"));
            apiMonitorRecordPage.setOrders(orderItems);
        }
        return apiMonitorRecordService.list(apiMonitorRecordPage, apiMonitorRecordDTO);
    }

    @ApiOperation(value = "新增API监控")
    @PostMapping
    public ApiMonitorRecordDTO insert(@PathVariable Long tenantId,
                                      ApiMonitorRecordDTO apiMonitorRecordDTO) {
        apiMonitorRecordDTO.setTenantId(tenantId);
        return apiMonitorRecordService.insert(apiMonitorRecordDTO);
    }

    @ApiOperation(value = "API监控记录详情")
    @GetMapping("/{monitorId}")
    public ApiMonitorRecordDTO detail(@PathVariable Long tenantId,
                                      @PathVariable Long monitorId) {
        return apiMonitorRecordRepository.detail(tenantId, monitorId);
    }

    @ApiOperation(value = "修改API监控记录")
    @PutMapping
    public ApiMonitorRecordDTO update(@PathVariable Long tenantId,
                                      @RequestBody ApiMonitorRecordDTO apiMonitorRecordDTO) {
        apiMonitorRecordDTO.setTenantId(tenantId);
        return apiMonitorRecordService.update(apiMonitorRecordDTO);
    }

    @ApiOperation(value = "删除API监控记录")
    @DeleteMapping
    public void remove(@PathVariable Long tenantId,
                       @RequestBody ApiMonitorRecordDTO apiMonitorRecordDTO) {
        apiMonitorRecordDTO.setTenantId(tenantId);
        apiMonitorRecordService.delete(apiMonitorRecordDTO);
    }

}
