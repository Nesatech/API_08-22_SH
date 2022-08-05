package com.test.api_0822_sh.controllers;

import com.test.api_0822_sh.dtos.UserDTO;
import com.test.api_0822_sh.mapper.UserMapper;
import com.test.api_0822_sh.services.IUserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for User
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Api(value = "User API")
public class UserApiController {

    private UserMapper userMapper;
    private IUserService service;

    /**
     * Endpoint to find all users
     *
     * @return List of all users in DB
     */
    @GetMapping()
    @ApiOperation(value = "Find all Users in the System", response = UserDTO.class, tags = "findAll")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK")
    })
    public List<UserDTO> findAll() {
        return userMapper.usersToUsersDto(service.findAll());
    }

    /**
     * Endpoint to find a user by its id
     *
     * @param id id of user to be found
     * @return user DTO if found or 404
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a User by its id", response = UserDTO.class, tags = "findById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public UserDTO findById(@PathVariable("id") @ApiParam(name = "id", value = "User id", example = "1") Long id) {
        return userMapper.userToUserDto(service.findById(id));
    }

    /**
     * Endpoint to save a user in DB
     *
     * @param newUser user to be saved
     * @return user DTO if created
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save a user in the System", response = UserDTO.class, tags = "saveUser")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public UserDTO saveUser(@RequestBody UserDTO newUser) {
        return userMapper.userToUserDto(service.create(userMapper.userDtoToUser(newUser)));
    }

}
