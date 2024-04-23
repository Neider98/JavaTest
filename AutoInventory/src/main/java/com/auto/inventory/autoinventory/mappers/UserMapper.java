package com.auto.inventory.autoinventory.mappers;

import com.auto.inventory.autoinventory.models.dtos.UserDTO;
import com.auto.inventory.autoinventory.models.entities.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "entryDate", source = "entryDate")
    @Mapping(target = "position", source = "position")
    UserDTO userToUserDTO(User user);

    UserDTO userDTOToUser(UserDTO userDTO);

    List<UserDTO> mapToDtoList(List<User> entities);

    default Optional<UserDTO> mapToDtoOptional(Optional<User> entityOptional) {
        return entityOptional.map(this::userToUserDTO);
    }

}
