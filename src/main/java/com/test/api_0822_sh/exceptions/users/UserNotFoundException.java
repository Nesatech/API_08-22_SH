package com.test.api_0822_sh.exceptions.users;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User id is not found: " + id);
    }
}
