package com.test.api_0822_sh.services;

import com.test.api_0822_sh.exceptions.users.UserUnauthorizedFieldException;
import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceLayerTest {
    
    @Mock
    private UserRepository userRepository;

    IUserService userService;

    private List<User> users;
    
    @BeforeEach
    void initUseCase() {
        userService = new UserServiceImpl(userRepository);
        users = Arrays.asList(
                new User("Dylan", LocalDate.of(1976, 6, 13), "France", "0214758963", 'M'),
                new User("Laura", LocalDate.of(1989, 11, 20), "France", "0235869742", 'F'),
                new User("Florian", LocalDate.of(1979, 7, 8), "France"),
                new User("Christopher", LocalDate.of(1986, 4, 18), "Belgique"),
                new User("Benjamin", LocalDate.of(2012, 5, 28), "France")
        );
    }

    @DisplayName("FindById_Success")
    @Test
    public void givenIdDoesExist_whenUserInfoIsRetrieved_thenUserInfoIsReceived() {
        User user = users.get(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        User savedUser = userService.findById(1L);

        assertThat(savedUser).isNotNull();

        assertThat(savedUser.getId()).isEqualTo(user.getId());
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(savedUser.getCountry()).isEqualTo(user.getCountry());
        assertThat(savedUser.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(savedUser.getGender()).isEqualTo(user.getGender());
    }

    @DisplayName("FindAllUsers_Success")
    @Test
    public void givenFindAllUsers_whenUserInfoIsRetrieved_thenAllUsersAreReceived() {
        when(userRepository.findAll()).thenReturn(users);

        List<User> savedUsers = userService.findAll();

        assertThat(savedUsers).isNotNull();
        assertThat(savedUsers).isNotEmpty();

        assertThat(savedUsers.get(0).getId()).isEqualTo(users.get(0).getId());
        assertThat(savedUsers.get(0).getName()).isEqualTo(users.get(0).getName());
        assertThat(savedUsers.get(0).getBirthday()).isEqualTo(users.get(0).getBirthday());
        assertThat(savedUsers.get(0).getCountry()).isEqualTo(users.get(0).getCountry());
        assertThat(savedUsers.get(0).getPhoneNumber()).isEqualTo(users.get(0).getPhoneNumber());
        assertThat(savedUsers.get(0).getGender()).isEqualTo(users.get(0).getGender());

        assertThat(savedUsers.get(1).getId()).isEqualTo(users.get(1).getId());
        assertThat(savedUsers.get(1).getName()).isEqualTo(users.get(1).getName());
        assertThat(savedUsers.get(1).getBirthday()).isEqualTo(users.get(1).getBirthday());
        assertThat(savedUsers.get(1).getCountry()).isEqualTo(users.get(1).getCountry());
        assertThat(savedUsers.get(1).getPhoneNumber()).isEqualTo(users.get(1).getPhoneNumber());
        assertThat(savedUsers.get(1).getGender()).isEqualTo(users.get(1).getGender());

        assertThat(savedUsers.get(2).getId()).isEqualTo(users.get(2).getId());
        assertThat(savedUsers.get(2).getName()).isEqualTo(users.get(2).getName());
        assertThat(savedUsers.get(2).getBirthday()).isEqualTo(users.get(2).getBirthday());
        assertThat(savedUsers.get(2).getCountry()).isEqualTo(users.get(2).getCountry());
    }

    @DisplayName("NoOptionalFieldUser_Success")
    @Test
    public void givenUserWithNoOptionalFields_whenUserIsCreated_thenUserInfoIsReceived() {
        User user = users.get(2);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.create(user);

        assertThat(savedUser).isNotNull();

        assertThat(savedUser.getId()).isEqualTo(user.getId());
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(savedUser.getCountry()).isEqualTo(user.getCountry());
    }

    @DisplayName("UserNotFrench_Error")
    @Test
    public void givenUserNotFrench_whenUserIsCreated_thenThrowUserUnauthorizedFieldException() {
        User user = users.get(3);
        assertThatThrownBy(() -> userService.create(user)).isInstanceOf(UserUnauthorizedFieldException.class);
        assertThatThrownBy(() -> userService.create(user)).hasMessage("User must be above 18 years old and French to register");
    }

    @DisplayName("UserNotAdult_Error")
    @Test
    public void givenUserNotAdult_whenUserIsCreated_thenThrowUserUnauthorizedFieldException() {
        User user = users.get(4);
        assertThatThrownBy(() -> userService.create(user)).isInstanceOf(UserUnauthorizedFieldException.class);
        assertThatThrownBy(() -> userService.create(user)).hasMessage("User must be above 18 years old and French to register");
    }

}
