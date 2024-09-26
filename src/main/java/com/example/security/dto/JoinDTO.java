package com.example.security.dto;

import com.example.security.entity.UserEntity;
import lombok.*;
import org.apache.catalina.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinDTO {
    private int id;
    private String username;
    private String password;


    public UserEntity DtoToEntity() {
        return UserEntity.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
