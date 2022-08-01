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
public class UserServiceImpl implements IUserService {

    private UserRepository repository;

    /**
     * Find a user specified by its id
     *
     * @param id id of the user
     * @return user
     * throws UserNotFoundException
     */
    @TrackExecutionTime
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found for id: " + id));
    }

    /**
     * After checks, save a new user in DB
     *
     * @param newUser user to be registered
     * @return registered user
     */
    @TrackExecutionTime
    public User create(User newUser) {
        if (isUserAdult(newUser) && isUserFrench(newUser)) {
            return repository.save(newUser);
        } else {
            throw new UserUnauthorizedFieldException("User must be above 18 years old and French to register");
        }
    }

    /**
     * Check if a user's country is equal to france
     *
     * @param newUser user to be registered
     * @return boolean userFrench
     */
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

    /**
     * Check if a user is above 18 years old
     *
     * @param newUser user to be registered
     * @return boolean userAdult
     */
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

    /**
     * Find all users in DB
     *
     * @return List of all users in DB
     */
    @TrackExecutionTime
    public List<User> findAll() {
        return repository.findAll();
    }
}
