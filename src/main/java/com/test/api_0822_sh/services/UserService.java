package com.test.api_0822_sh.services;

import com.test.api_0822_sh.exceptions.users.UserNotFoundException;
import com.test.api_0822_sh.exceptions.users.UserUnauthorizedFieldException;
import com.test.api_0822_sh.logging.executionTime.TrackExecutionTime;
import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository repository;

    @TrackExecutionTime
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found for id: " + id));
    }

    @TrackExecutionTime
    public User create(User newUser) {
        if (isUserAdult(newUser) && isUserFrench(newUser)) {
            return repository.save(newUser);
        } else {
            throw new UserUnauthorizedFieldException("User must be above 18 years old and French to register");
        }
    }

    private boolean isUserFrench(User newUser) {
        boolean userFrench = false;
        if (newUser != null) {
            Set<String> authorizedCountries = new HashSet<>();
            authorizedCountries.add("france");
            if (authorizedCountries.contains(newUser.getCountry().toLowerCase())) {
                userFrench = true;
            }
        }
        return userFrench;
    }

    private boolean isUserAdult(User newUser) {
        boolean userAdult = false;
        if (newUser != null) {
            Period period = Period.between(newUser.getBirthday(), LocalDate.now());
            if (period.getYears() >= 18) {
                userAdult = true;
            }
        }
        return userAdult;
    }

    @TrackExecutionTime
    public List<User> findAll() {
        return repository.findAll();
    }
}
