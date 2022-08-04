package com.test.api_0822_sh.mapper;

import com.test.api_0822_sh.dtos.UserDTO;
import com.test.api_0822_sh.models.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * User entity to User DTO mapper
 */
@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    UserDTO userToUserDto(User user);

    List<UserDTO> usersToUsersDto(List<User> users);

}
