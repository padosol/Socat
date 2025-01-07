package com.userservice.domain.user.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserEntityTest {

    @Test
    @DisplayName("유저 생성 테스트")
    void createUser() {
        // given
        UserEntity user = UserEntity.builder().build();

        // when
        FakeIdGenerator idGenerator = new FakeIdGenerator();
        user.createUser(idGenerator);

        // then
        Assertions.assertThat(idGenerator.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void encoderPassword() {

        // given
        String password = "testtest";
        UserEntity user = UserEntity.builder().password(password).build();

        // when
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.encoderPassword(passwordEncoder);

        // then
        Assertions.assertThat(passwordEncoder.matches(password, user.getPassword())).isTrue();
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void deleteUser() {
        // given
        UserEntity user = UserEntity.builder().build();
        FakeIdGenerator idGenerator = new FakeIdGenerator();
        user.createUser(idGenerator);

        // when
        user.deleteUser();

        // then
        Assertions.assertThat(user.getState()).isEqualTo(UserState.DELETE);
    }
}