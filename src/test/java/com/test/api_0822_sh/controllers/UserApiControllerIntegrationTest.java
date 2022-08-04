package com.test.api_0822_sh.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    private List<User> users;

    @BeforeEach
    void initUseCase() {
        users = Arrays.asList(
                new User("Brigitte", LocalDate.of(1989, 11, 20), "France", "0235869742", 'F'),
                new User("Serge", LocalDate.of(1979, 1, 26), "France", "0214758696", 'M')
        );
    }

    @DisplayName("FindAll_Success")
    @Test
    public void findAllUser_thenReturnJsonArray() throws Exception {
        when(userRepository.findAll()).thenReturn(users);

        MvcResult mvcResult = mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].id", is(users.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(users.get(0).getName())))
                .andExpect(jsonPath("$[0].birthday", is(users.get(0).getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))))
                .andExpect(jsonPath("$[0].country", is(users.get(0).getCountry())))
                .andExpect(jsonPath("$[0].phoneNumber", is(users.get(0).getPhoneNumber())))
                .andExpect(jsonPath("$[0].gender", is(users.get(0).getGender().toString())))

                .andExpect(jsonPath("$[1].id", is(users.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(users.get(1).getName())))
                .andExpect(jsonPath("$[1].birthday", is(users.get(1).getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))))
                .andExpect(jsonPath("$[1].country", is(users.get(1).getCountry())))
                .andExpect(jsonPath("$[1].phoneNumber", is(users.get(1).getPhoneNumber())))
                .andExpect(jsonPath("$[1].gender", is(users.get(1).getGender().toString())))

                .andReturn();

        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @DisplayName("FindById_Success")
    @Test
    public void findById_thenReturnJsonArray() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(users.get(0)));

        MvcResult mvcResult = mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id", is(users.get(0).getId())))
                .andExpect(jsonPath("$.name", is(users.get(0).getName())))
                .andExpect(jsonPath("$.birthday", is(users.get(0).getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))))
                .andExpect(jsonPath("$.country", is(users.get(0).getCountry())))
                .andExpect(jsonPath("$.phoneNumber", is(users.get(0).getPhoneNumber())))
                .andExpect(jsonPath("$.gender", is(users.get(0).getGender().toString())))
                .andReturn();

        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @DisplayName("FindById_Error")
    @Test
    public void getWrongId_whenFindById_then400() throws Exception {
        mockMvc.perform(get("/users/3")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound())
        ;
    }

    @DisplayName("PostUser_Success")
    @Test
    public void saveUser_thenCreateUserStatus201() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(users.get(0));

        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(users.get(0))))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id", is(users.get(0).getId())))
                .andExpect(jsonPath("$.name", is(users.get(0).getName())))
                .andExpect(jsonPath("$.birthday", is(users.get(0).getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))))
                .andExpect(jsonPath("$.country", is(users.get(0).getCountry())))
                .andExpect(jsonPath("$.phoneNumber", is(users.get(0).getPhoneNumber())))
                .andExpect(jsonPath("$.gender", is(users.get(0).getGender().toString())))
                .andReturn()
        ;

        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @DisplayName("PostNotFrenchUser_Error")
    @Test
    public void postNotFrenchUser_whenSaveUser_thenStatus403() throws Exception {
        User NotFrenchUser = new User("Jean-Claude", LocalDate.of(1974, 5, 2), "Belgique");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(NotFrenchUser)))

                .andExpect(status().isForbidden())
        ;
    }

    @DisplayName("PostNotAdultUser_Error")
    @Test
    public void postNotAdultUser_whenSaveUser_thenStatus403() throws Exception {
        User NotFrenchUser = new User("Vanessa", LocalDate.of(2006, 5, 2), "France");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(NotFrenchUser)))

                .andExpect(status().isForbidden())
        ;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
