package com.test.api_0822_sh.exceptions.users;

import java.util.Set;

public class UserUnsupportedFieldException extends RuntimeException {

    public UserUnsupportedFieldException(Set<String> keys) {
        super("Field " + keys.toString() + "is not allowed");
    }
}
