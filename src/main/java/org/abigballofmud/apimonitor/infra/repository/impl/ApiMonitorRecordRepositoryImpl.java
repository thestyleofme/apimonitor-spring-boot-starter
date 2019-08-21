package org.abigballofmud.apimonitor.infra.repository.impl;

import java.util.ArrayList;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.abigballofmud.apimonitor.domain.entity.ApiMonitorRecord;
import org.abigballofmud.apimonitor.domain.repository.ApiMonitorRecordRepository;
import org.abigballofmud.apimonitor.infra.converter.ApiMonitorConvertMapper;
import org.abigballofmud.apimonitor.infra.mapper.ApiMonitorRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author isacc 2019/08/19 21:21
 * @since 1.0
 */
@Component
public class ApiMonitorRecordRepositoryImpl implements ApiMonitorRecordRepository {

    private final ApiMonitorRecordMapper apiMonitorRecordMapper;

    public ApiMonitorRecordRepositoryImpl(ApiMonitorRecordMapper apiMonitorRecordMapper) {
        this.apiMonitorRecordMapper = apiMonitorRecordMapper;
    }

    @Override
    public IPage<ApiMonitorRecordDTO> list(Page<ApiMonitorRecord> apiMonitorRecordPage, ApiMonitorRecordDTO apiMonitorRecordDTO) {
        QueryWrapper<ApiMonitorRecord> queryWrapper = commonQueryWrapper(apiMonitorRecordDTO);
        final IPage<ApiMonitorRecord> entityPage = apiMonitorRecordMapper.selectPage(apiMonitorRecordPage, queryWrapper);
        final ArrayList<ApiMonitorRecordDTO> dtoList = new ArrayList<>();
        entityPage.getRecords().forEach(entity -> dtoList.add(ApiMonitorConvertMapper.INSTANCE.entityToDto(entity)));
        final Page<ApiMonitorRecordDTO> dtoPage = new Page<>();
        BeanUtils.copyProperties(entityPage, dtoPage);
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    @Override
    public ApiMonitorRecordDTO insert(ApiMonitorRecordDTO apiMonitorRecordDTO) {
        ApiMonitorRecord entity = ApiMonitorConvertMapper.INSTANCE.dtoToEntity(apiMonitorRecordDTO);
        apiMonitorRecordMapper.insert(entity);
        return ApiMonitorConvertMapper.INSTANCE.entityToDto(entity);
    }

    @Override
    public ApiMonitorRecordDTO update(ApiMonitorRecordDTO apiMonitorRecordDTO) {
        ApiMonitorRecord entity = ApiMonitorConvertMapper.INSTANCE.dtoToEntity(apiMonitorRecordDTO);
        apiMonitorRecordMapper.updateById(entity);
        return ApiMonitorConvertMapper.INSTANCE.entityToDto(entity);
    }

    @Override
    public void delete(ApiMonitorRecordDTO apiMonitorRecordDTO) {
        apiMonitorRecordMapper.delete(commonQueryWrapper(apiMonitorRecordDTO));
    }

    @Override
    public ApiMonitorRecordDTO detail(Long tenantId, Long monitorId) {
        ApiMonitorRecord entity = apiMonitorRecordMapper.selectOne(detailWrapper(tenantId, monitorId));
        return ApiMonitorConvertMapper.INSTANCE.entityToDto(entity);
    }

    private QueryWrapper<ApiMonitorRecord> commonQueryWrapper(ApiMonitorRecordDTO apiMonitorRecordDTO) {
        final QueryWrapper<ApiMonitorRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.or().eq("MONITOR_ID", apiMonitorRecordDTO.getMonitorId());
        queryWrapper.or().eq("TENANT_ID", apiMonitorRecordDTO.getTenantId());
        queryWrapper.or().eq("SERVICE_ID", apiMonitorRecordDTO.getServiceId());
        queryWrapper.or().eq("SERVICE_ID", apiMonitorRecordDTO.getServiceId());
        queryWrapper.or().like("CLASS_METHOD", apiMonitorRecordDTO.getClassMethod());
        return queryWrapper;
    }

    private QueryWrapper<ApiMonitorRecord> detailWrapper(Long tenantId, Long monitorId) {
        final QueryWrapper<ApiMonitorRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TENANT_ID", tenantId);
        queryWrapper.eq("MONITOR_ID", monitorId);
        return queryWrapper;
    }

}
