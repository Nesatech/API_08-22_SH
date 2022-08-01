package com.test.api_0822_sh.exceptions.users;

public class UserNotFoundException extends RuntimeException {

    /**
     * Exception lifted when user is not found
     *
     * @param message custom error details
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
