package com.quizapp.result.service;

import com.quizapp.result.repository.UserRepository;
import com.quizapp.result.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SingleplayerResultService implements ResultService {
    private final UserRepository userRepository;

    public SingleplayerResultService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserEntity(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveUserEntity(String username) {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);

        log.info("UserEntity with username {} created!", username);

        userRepository.save(entity);
    }
}
