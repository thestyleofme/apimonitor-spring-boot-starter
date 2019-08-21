package org.abigballofmud.apimonitor.infra.converter;

import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.abigballofmud.apimonitor.domain.entity.ApiMonitorRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/08/19 21:42
 * @since 1.0
 */
@Mapper
public interface ApiMonitorConvertMapper {

    ApiMonitorConvertMapper INSTANCE = Mappers.getMapper(ApiMonitorConvertMapper.class);

    /**
     * entityToDto
     *
     * @param apiMonitorRecord ApiMonitorRecord
     * @return org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO
     * @author isacc 2019/8/19 21:44
     */
    ApiMonitorRecordDTO entityToDto(ApiMonitorRecord apiMonitorRecord);

    /**
     * DtoToEntity
     *
     * @param apiMonitorRecordDTO ApiMonitorRecordDTO
     * @return org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO
     * @author isacc 2019/8/19 21:44
     */
    ApiMonitorRecord dtoToEntity(ApiMonitorRecordDTO apiMonitorRecordDTO);

}
