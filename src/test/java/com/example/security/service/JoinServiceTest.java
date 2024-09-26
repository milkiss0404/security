package com.example.security.service;

import com.example.security.dto.JoinDTO;
import com.example.security.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // Spring 컨텍스트를 로드합니다.
class JoinServiceTest {

    @Test
    public void joinProcessTest() {
        // given
        JoinDTO dto = JoinDTO.builder()
                .id(1)
                .username("asd")
                .password("asd")
                .build();

        // when
        UserEntity userEntity = dto.DtoToEntity(); // 수정된 메서드 호출

        // then
        Assertions.assertThat(userEntity.getUsername()).isEqualTo(dto.getUsername());
        Assertions.assertThat(userEntity.getPassword()).isEqualTo(dto.getPassword());
    }
}
