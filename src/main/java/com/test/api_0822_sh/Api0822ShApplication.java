package com.test.api_0822_sh;

import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class Api0822ShApplication {

    public static void main(String[] args) {
        SpringApplication.run(Api0822ShApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> repository.save(new User("test_name", LocalDate.parse("1901-01-02"), "test_country", "test_phoneNumber", 'M'));
    }
}
