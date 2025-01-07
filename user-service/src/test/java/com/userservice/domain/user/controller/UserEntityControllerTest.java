package com.userservice.domain.user.controller;

import com.userservice.domain.user.controller.dto.request.CreateUserDTO;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.entity.UserEntity;
import com.userservice.domain.user.service.usecase.CreateUserUseCase;
import com.userservice.domain.user.service.usecase.GetUserUseCase;
import com.userservice.global.utils.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class UserEntityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GetUserUseCase getUserUseCase;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO("username", "email@example.com", "password");
        UserResponse userResponse = UserResponse.builder().id("id").username("username").email("email@example.com").build();

        when(createUserUseCase.createUser(any(UserEntity.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\",\"email\":\"email@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("id"))
                .andExpect(jsonPath("$.userName").value("username"))
                .andExpect(jsonPath("$.email").value("email@example.com"));
    }

    @Test
    void testGetUser() throws Exception {
        UserResponse userResponse = UserResponse.builder().id("id").username("username").email("email@example.com").build();

        when(jwtProvider.getUserId(any(HttpServletRequest.class))).thenReturn("id");
        when(getUserUseCase.findUserById(anyString())).thenReturn(userResponse);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id"))
                .andExpect(jsonPath("$.userName").value("username"))
                .andExpect(jsonPath("$.email").value("email@example.com"));
    }

    @Test
    void testGetUserByUserId() throws Exception {
        UserResponse userResponse = UserResponse.builder().id("id").username("username").email("email@example.com").build();

        when(getUserUseCase.findUserById(anyString())).thenReturn(userResponse);

        mockMvc.perform(get("/users/{userId}", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id"))
                .andExpect(jsonPath("$.userName").value("username"))
                .andExpect(jsonPath("$.email").value("email@example.com"));
    }
}