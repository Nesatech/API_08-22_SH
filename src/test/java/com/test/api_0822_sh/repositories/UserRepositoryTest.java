package com.test.api_0822_sh.repositories;

import com.test.api_0822_sh.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private List<User> users;

    @BeforeEach
    void initUseCase() {
        users = Arrays.asList(
                new User("Tommy", LocalDate.of(1985, 12, 5),  "France", "0245857694", 'M'),
                new User("Rachel", LocalDate.of(1983, 9, 15),  "France", "0145357857", 'F')
        );
    }

    @DisplayName("SaveUsers_FindAll_Success")
    @Test
    public void givenSaveUsers_whenFindAll_thenReceivedAllUsers() {
        userRepository.saveAll(users);

        List<User> savedUsers = userRepository.findAll();

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
    }

    @DisplayName("SaveUser_FindById_Success")
    @Test
    public void givenSaveUser_whenFindById_thenReceivedUserInfo() {
        User user = users.get(1);

        userRepository.save(user);

        Optional<User> userById = userRepository.findById(3L);

        assertThat(userById).isPresent();

        assertThat(userById.get().getName()).isEqualTo(user.getName());
        assertThat(userById.get().getBirthday()).isEqualTo(user.getBirthday());
        assertThat(userById.get().getCountry()).isEqualTo(user.getCountry());
        assertThat(userById.get().getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(userById.get().getGender()).isEqualTo(user.getGender());
    }

}
