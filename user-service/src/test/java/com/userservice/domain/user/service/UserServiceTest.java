package com.userservice.domain.user.service;

import com.userservice.domain.user.entity.IdGenerator;
import com.userservice.domain.user.entity.User;
import com.userservice.domain.user.exception.UserAlreadyExistException;
import com.userservice.domain.user.repository.UserJpaRepository;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

        @Mock
        private UserJpaRepository userJpaRepository;

        @Mock
        private IdGenerator idGenerator;

        @Mock
        private PasswordEncoder passwordEncoder;

        @InjectMocks
        private UserService userService;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        @DisplayName("Create User Test")
        void createUser() {
                User user = User.builder().email("test@example.com").build();
                when(userJpaRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
                when(userJpaRepository.save(any(User.class))).thenReturn(user);

                UserResponse response = userService.createUser(user);

                assertThat(response.getEmail()).isEqualTo(user.getEmail());
                verify(userJpaRepository, times(1)).findUserByEmail(user.getEmail());
                verify(userJpaRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Create User Already Exists Test")
        void createUserAlreadyExists() {
                User user = User.builder().email("test@example.com").build();
                when(userJpaRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

                assertThrows(UserAlreadyExistException.class, () -> userService.createUser(user));
                verify(userJpaRepository, times(1)).findUserByEmail(user.getEmail());
                verify(userJpaRepository, times(0)).save(user);
        }

        @Test
        @DisplayName("Modify User Test")
        void modifyUser() {
                // Implement the test for modifyUser method
        }

        @Test
        @DisplayName("Remove User Test")
        void removeUser() {
                // Implement the test for removeUser method
        }

        @Test
        @DisplayName("Load User By Username Test")
        void loadUserByUsername() {
                User user = User.builder().email("test@example.com").password("password").build();
                when(userJpaRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

                UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

                assertThat(userDetails.getUsername()).isEqualTo(user.getId());
                verify(userJpaRepository, times(1)).findUserByEmail(user.getEmail());
        }

        @Test
        @DisplayName("Load User By Username Not Found Test")
        void loadUserByUsernameNotFound() {
                when(userJpaRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());

                assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test@example.com"));
                verify(userJpaRepository, times(1)).findUserByEmail("test@example.com");
        }

        @Test
        @DisplayName("Find User By Email Test")
        void findUserByEmail() {
                User user = User.builder().email("test@example.com").build();
                when(userJpaRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

                UserResponse response = userService.findUserByEmail(user.getEmail());

                assertThat(response.getEmail()).isEqualTo(user.getEmail());
                verify(userJpaRepository, times(1)).findUserByEmail(user.getEmail());
        }

        @Test
        @DisplayName("Find User By Email Not Found Test")
        void findUserByEmailNotFound() {
                when(userJpaRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());

                assertThrows(UsernameNotFoundException.class, () -> userService.findUserByEmail("test@example.com"));
                verify(userJpaRepository, times(1)).findUserByEmail("test@example.com");
        }

        @Test
        @DisplayName("Find User By Id Test")
        void findUserById() {
                User user = User.builder().id("123").build();
                when(userJpaRepository.findById(user.getId())).thenReturn(Optional.of(user));

                UserResponse response = userService.findUserById(user.getId());

                assertThat(response.getId()).isEqualTo(user.getId());
                verify(userJpaRepository, times(1)).findById(user.getId());
        }

        @Test
        @DisplayName("Find User By Id Not Found Test")
        void findUserByIdNotFound() {
                when(userJpaRepository.findById("123")).thenReturn(Optional.empty());

                assertThrows(UsernameNotFoundException.class, () -> userService.findUserById("123"));
                verify(userJpaRepository, times(1)).findById("123");
        }
}