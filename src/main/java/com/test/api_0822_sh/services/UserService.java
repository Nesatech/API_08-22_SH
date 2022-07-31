package com.test.api_0822_sh.services;

import com.test.api_0822_sh.exceptions.users.UserNotFoundException;
import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository repository;

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void create(User newUser) {
        repository.save(newUser);
    }
}
