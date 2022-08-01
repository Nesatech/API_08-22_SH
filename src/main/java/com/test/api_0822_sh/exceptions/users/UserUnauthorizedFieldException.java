package com.test.api_0822_sh.exceptions.users;

public class UserUnauthorizedFieldException extends RuntimeException {

    /**
     * Exception lifted when some fields are unauthorized when a user registers
     *
     * @param message custom error details
     */
    public UserUnauthorizedFieldException(String message) {
        super(message);
    }
}
