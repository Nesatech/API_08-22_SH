package com.test.api_0822_sh.services;

import com.test.api_0822_sh.exceptions.users.UserNotFoundException;
import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository repository;

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found for id: " + id));
    }

    public User create(User newUser) {
        return repository.save(newUser);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
