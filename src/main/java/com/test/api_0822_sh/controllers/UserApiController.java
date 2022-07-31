package com.test.api_0822_sh.controllers;

import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private IUserService service;

    @GetMapping()
    List<User> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    User findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    User saveUser(@RequestBody User newUser) {
        return service.create(newUser);
    }

}
