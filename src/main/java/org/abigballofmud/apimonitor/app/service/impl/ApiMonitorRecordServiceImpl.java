package org.abigballofmud.apimonitor.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.abigballofmud.apimonitor.app.service.ApiMonitorRecordService;
import org.abigballofmud.apimonitor.domain.entity.ApiMonitorRecord;
import org.abigballofmud.apimonitor.domain.repository.ApiMonitorRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author isacc 2019/08/19 21:24
 * @since 1.0
 */
@Service
public class ApiMonitorRecordServiceImpl implements ApiMonitorRecordService {

    private final ApiMonitorRecordRepository apiMonitorRecordRepository;

    public ApiMonitorRecordServiceImpl(ApiMonitorRecordRepository apiMonitorRecordRepository) {
        this.apiMonitorRecordRepository = apiMonitorRecordRepository;
    }

    @Override
    public IPage<ApiMonitorRecordDTO> list(Page<ApiMonitorRecord> apiMonitorRecordPage, ApiMonitorRecordDTO apiMonitorRecordDTO) {
        return apiMonitorRecordRepository.list(apiMonitorRecordPage,apiMonitorRecordDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiMonitorRecordDTO insert(ApiMonitorRecordDTO apiMonitorRecordDTO) {
        return apiMonitorRecordRepository.insert(apiMonitorRecordDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiMonitorRecordDTO update(ApiMonitorRecordDTO apiMonitorRecordDTO) {
        return apiMonitorRecordRepository.update(apiMonitorRecordDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ApiMonitorRecordDTO apiMonitorRecordDTO) {
        apiMonitorRecordRepository.delete(apiMonitorRecordDTO);
    }
}
