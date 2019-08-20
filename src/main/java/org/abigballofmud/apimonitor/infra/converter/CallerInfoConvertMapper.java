package org.abigballofmud.apimonitor.infra.converter;

import org.abigballofmud.apimonitor.api.dto.ApiMonitorRecordDTO;
import org.abigballofmud.apimonitor.domain.entity.ApiMonitorRecord;
import org.abigballofmud.apimonitor.domain.entity.CallerInfo;
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
public interface CallerInfoConvertMapper {

    CallerInfoConvertMapper INSTANCE = Mappers.getMapper(CallerInfoConvertMapper.class);

    CallerInfo objectToCallerInfo(Object object);

}
