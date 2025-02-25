package com.quizapp.result.repository;

import com.quizapp.result.entity.ScoreEntity;
import com.quizapp.result.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {
    Optional<ScoreEntity> findByUserAndSessionKey(UserEntity user, String sessionKey);
    List<ScoreEntity> findBySessionKey(String sessionKey);
}
