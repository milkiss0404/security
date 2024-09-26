package com.example.security.service;

import com.example.security.dto.JoinDTO;
import com.example.security.entity.UserEntity;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void joinProcess(JoinDTO joinDTO) {
        //db에 이미 동일한 username을 가진회원이 존재하는지 (검증) valid

        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if(isUser){
            return;
        }


        String hashcode =  bCryptPasswordEncoder.encode(joinDTO.getPassword());//비밀번호 암호화
        UserEntity userEntity = joinDTO.DtoToEntity();
        userEntity.updatePassword(hashcode);
        userEntity.updateRole("ROLE_ADMIN");

        userRepository.save(userEntity);
    }

}
