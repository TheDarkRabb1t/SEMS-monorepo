package model.mapper;

import model.dto.AuthContextDto;
import model.dto.EnrichedLogEventDto;
import model.dto.GeoLocationDto;
import model.dto.UserAgentInfoDto;
import model.entity.AuthContext;
import model.entity.EnrichedLogEvent;
import model.entity.GeoLocation;
import model.entity.UserAgentInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrichedLogEventMapper {

    EnrichedLogEvent toEntity(EnrichedLogEventDto dto);

    EnrichedLogEventDto toDto(EnrichedLogEvent entity);

    GeoLocationDto geoToDto(GeoLocation geo);

    GeoLocation geoToEntity(GeoLocationDto dto);

    UserAgentInfoDto userAgentToDto(UserAgentInfo info);

    UserAgentInfo userAgentToEntity(UserAgentInfoDto dto);

    AuthContextDto authToDto(AuthContext auth);

    AuthContext authToEntity(AuthContextDto dto);
}
