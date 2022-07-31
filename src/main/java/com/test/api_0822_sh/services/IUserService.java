package com.test.api_0822_sh.services;

import com.test.api_0822_sh.models.User;

import java.util.List;

public interface IUserService {

    User findById(Long id);

    User create(User newUser);

    List<User> findAll();
}
