package com.quizapp.quiz.repository;

import com.quizapp.quiz.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    Optional<SessionEntity> findBySessionKey(String sessionKey);
}
