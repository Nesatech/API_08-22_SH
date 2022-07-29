package com.test.api_0822_sh.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate birthday;
    private String country;
    private String phoneNumber;
    private char gender;

}
