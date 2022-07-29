package com.test.api_0822_sh.services;

import com.test.api_0822_sh.models.User;

public interface IUserService {

    User findById(Long id);

    void create(User newUser);
}
