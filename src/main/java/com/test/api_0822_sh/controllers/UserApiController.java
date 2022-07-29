package com.test.api_0822_sh.controllers;

import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserApiController {

    private IUserService service;

    @GetMapping("/users/{id}")
    User findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    void saveUser(@RequestBody User newUser) {
        service.create(newUser);
    }

}
