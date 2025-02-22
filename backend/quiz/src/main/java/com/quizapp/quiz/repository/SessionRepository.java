package com.quizapp.quiz.repository;

import com.quizapp.quiz.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

}
