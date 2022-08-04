package com.test.api_0822_sh.controllers;

import com.test.api_0822_sh.dtos.UserDTO;
import com.test.api_0822_sh.mapper.UserMapper;
import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.services.IUserService;
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
public class UserApiController {

    private UserMapper userMapper;
    private IUserService service;

    /**
     * Endpoint to find all users
     *
     * @return List of all users in DB
     */
    @GetMapping()
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
    public UserDTO findById(@PathVariable("id") Long id) {
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
    public UserDTO saveUser(@RequestBody User newUser) {
        return userMapper.userToUserDto(service.create(newUser));
    }

}
