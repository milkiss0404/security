package com.example.security.entity;

import com.example.security.dto.JoinDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String password;

    private String role;

    public void updateRole(String role){
        this.role = role;
    }

    public void updatePassword(String hash) {
        this.password = hash;
    }

    public JoinDTO EntityToDTO(){
        return JoinDTO.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .build();
    }
}


