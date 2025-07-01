package tdr.pet.authorization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tdr.pet.authorization.model.dto.UserDto;
import tdr.pet.authorization.model.entity.CustomUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDto(CustomUser user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    CustomUser toEntity(UserDto userDto);
}
