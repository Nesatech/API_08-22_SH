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

    @GetMapping()
    public List<UserDTO> findAll() {
        return userMapper.usersToUsersDto(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public UserDTO findById(@PathVariable("id") Long id) {
        return userMapper.userToUserDto(service.findById(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO saveUser(@RequestBody User newUser) {
        return userMapper.userToUserDto(service.create(newUser));
    }

}
