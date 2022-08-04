package com.test.api_0822_sh.services;

import com.test.api_0822_sh.models.User;

import java.util.List;

/**
 * User service interface
 */
public interface IUserService {

    User findById(Long id);

    User create(User newUser);

    List<User> findAll();
}
