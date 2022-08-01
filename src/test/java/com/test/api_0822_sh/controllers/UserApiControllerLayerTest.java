package com.test.api_0822_sh.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.api_0822_sh.exceptions.users.UserNotFoundException;
import com.test.api_0822_sh.exceptions.users.UserUnauthorizedFieldException;
import com.test.api_0822_sh.models.User;
import com.test.api_0822_sh.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserApiController.class})
public class UserApiControllerLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

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
    public void givenUsers_whenFindAll_thenReturnJsonArray() throws Exception {
        given(userService.findAll()).willReturn(users);

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
    public void givenGetUser_whenFindById_thenReturnJsonArray() throws Exception {
        given(userService.findById(1L)).willReturn(users.get(0));

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
    public void givenGetWrongId_whenFindById_then400() throws Exception {
        given(userService.findById(1L)).willThrow(new UserNotFoundException(""));

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound())
        ;
    }

    @DisplayName("PostUser_Success")
    @Test
    public void givenPostUser_whenSaveUser_thenCreateUserStatus201() throws Exception {
        given(userService.create(any(User.class))).willReturn(users.get(0));

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

    @DisplayName("PostUnauthorizedUser_Error")
    @Test
    public void givenPostUnauthorizedUser_whenSaveUser_thenStatus403() throws Exception {
        given(userService.create(any(User.class))).willThrow(new UserUnauthorizedFieldException(""));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(users.get(1))))

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
