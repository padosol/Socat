package com.userservice.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.domain.user.controller.dto.request.CreateUserDTO;
import com.userservice.domain.user.controller.dto.request.ModifyUserDTO;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.entity.UserEntity;
import com.userservice.domain.user.service.usecase.CreateUserUseCase;
import com.userservice.domain.user.service.usecase.GetUserUseCase;
import com.userservice.domain.user.service.usecase.ModifyUserUserCase;
import com.userservice.global.config.security.SecurityConfig;
import com.userservice.global.utils.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = UserController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private GetUserUseCase getUserUseCase;

    @MockBean
    private ModifyUserUserCase modifyUserUserCase;

    @MockBean
    private JwtProvider jwtProvider;

    private CreateUserDTO createUserDTO;
    private ModifyUserDTO modifyUserDTO;
    private UserResponse userResponse;
    private String userId;

    @BeforeEach
    void setUp() {
        userId = "test-user-id";
        
        createUserDTO = new CreateUserDTO("testUser", "test@example.com", "password123");

        modifyUserDTO = new ModifyUserDTO("modifiedUser", "modified@example.com", "newpassword123");

        userResponse = UserResponse.builder()
                .id(userId)
                .username("testUser")
                .email("test@example.com")
                .build();
    }

    @Test
    @DisplayName("유저 생성 성공 테스트")
    void createUserSuccess() throws Exception {
        // Given
        when(createUserUseCase.createUser(any(UserEntity.class)))
                .thenReturn(userResponse);

        // When & Then
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @DisplayName("현재 유저 정보 조회 성공 테스트")
    void getUserSuccess() throws Exception {
        // Given
        when(jwtProvider.getUserId(any())).thenReturn(userId);
        when(getUserUseCase.findUserById(userId)).thenReturn(userResponse);

        // When & Then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @DisplayName("특정 유저 정보 조회 성공 테스트")
    void getUserByUserIdSuccess() throws Exception {
        // Given
        when(getUserUseCase.findUserById(userId)).thenReturn(userResponse);

        // When & Then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(userId))
                .andExpect(jsonPath("$.data.username").value("testUser"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"));
    }

    @Test
    @DisplayName("유저 정보 수정 성공 테스트")
    void modifyUserSuccess() throws Exception {
        // Given
        UserResponse modifiedResponse = UserResponse.builder()
                                                    .id(userId)
                                                    .username("modifiedUser")
                                                    .email("modified@example.com")
                                                    .build();

        when(modifyUserUserCase.modifyUser(any(UserEntity.class)))
                .thenReturn(modifiedResponse);

        // When & Then
        mockMvc.perform(put("/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(userId))
                .andExpect(jsonPath("$.data.username").value("modifiedUser"))
                .andExpect(jsonPath("$.data.email").value("modified@example.com"));
    }

    @Test
    @DisplayName("유저 생성 시 유효성 검사 실패 테스트")
    void createUserValidationFail() throws Exception {
        // Given
        CreateUserDTO invalidDTO = new CreateUserDTO();
        // 필수 필드를 비워둠

        // When & Then
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    @DisplayName("유저 수정 시 유효성 검사 실패 테스트")
//    void modifyUserValidationFail() throws Exception {
//        // Given
//        ModifyUserDTO invalidDTO = new ModifyUserDTO();
//        // 필수 필드를 비워둠
//
//        // When & Then
//        mockMvc.perform(put("/users/{userId}", userId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(invalidDTO)))
//                .andExpect(status().isBadRequest());
//    }
}