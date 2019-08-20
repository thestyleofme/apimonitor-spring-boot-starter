package org.abigballofmud.apimonitor.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.abigballofmud.apimonitor.domain.entity.ApiMonitorRecord;

/**
 * description
 *
 * @author isacc 2019/08/19 21:24
 * @since 1.0
 */
public interface ApiMonitorRecordService {

    /**
     * 分页查询API监控记录
     *
     * @param apiMonitorRecordPage Page<ApiMonitorRecord>
     * @param apiMonitorRecordDTO  ApiMonitorRecordDTO
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO>
     * @author isacc 2019/8/19 22:13
     */
    IPage<ApiMonitorRecordDTO> list(Page<ApiMonitorRecord> apiMonitorRecordPage, ApiMonitorRecordDTO apiMonitorRecordDTO);

    /**
     * 新增API监控
     *
     * @param apiMonitorRecordDTO ApiMonitorRecordDTO
     * @return org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO
     * @author isacc 2019/8/20 14:35
     */
    ApiMonitorRecordDTO insert(ApiMonitorRecordDTO apiMonitorRecordDTO);

    /**
     * 更新API监控记录
     *
     * @param apiMonitorRecordDTO ApiMonitorRecordDTO
     * @return org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO
     * @author isacc 2019/8/20 14:49
     */
    ApiMonitorRecordDTO update(ApiMonitorRecordDTO apiMonitorRecordDTO);

    /**
     * 删除API监控记录
     *
     * @param apiMonitorRecordDTO ApiMonitorRecordDTO
     * @author isacc 2019/8/20 14:56
     */
    void delete(ApiMonitorRecordDTO apiMonitorRecordDTO);
}
