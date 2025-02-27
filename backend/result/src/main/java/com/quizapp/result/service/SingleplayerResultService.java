package com.quizapp.result.service;

import com.quizapp.result.dto.ResultDTO;
import com.quizapp.result.dto.ScoreDTO;
import com.quizapp.result.dto.SessionScoreDTO;
import com.quizapp.result.dto.request.GetResultRequest;
import com.quizapp.result.entity.ScoreEntity;
import com.quizapp.result.mapper.ResultMapper;
import com.quizapp.result.repository.ScoreRepository;
import com.quizapp.result.repository.UserRepository;
import com.quizapp.result.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SingleplayerResultService implements ResultService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final ResultMapper resultMapper;

    public SingleplayerResultService(ScoreRepository scoreRepository, UserRepository userRepository, ResultMapper resultMapper) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
        this.resultMapper = resultMapper;
    }

    private UserEntity getOrCreateUser(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity(null, username);

                    return userRepository.save(newUser);
                });
    }

    @Override
    public void resetScore(String sessionKey, String username) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return;
        }

        ScoreEntity scoreEntity = scoreRepository.findByUserAndSessionKey(user, sessionKey).orElse(null);
        if (scoreEntity == null) {
            return;
        }

        log.info("Resetting score for {} in session {}", username, sessionKey);

        scoreEntity.setTotalScore(0);
        scoreEntity.setChosenAlternatives("");
        scoreRepository.save(scoreEntity);
    }

    @Override
    public ResultDTO postAnswer(GetResultRequest request) {
        log.info("Receiving in ResultService postAnswer: " + request.getNumberOfQuestions());

        ResultDTO result;
        UserEntity user = getOrCreateUser(request.getUsername());
        String alternatives;
        ScoreEntity scoreEntity = scoreRepository.findByUserAndSessionKey(user, request.getSessionKey())
                .orElseGet(() -> {
                    ScoreEntity newScore = new ScoreEntity();
                    newScore.setUser(user);
                    newScore.setSessionKey(request.getSessionKey());
                    newScore.setTotalScore(0);
                    newScore.setChosenAlternatives("");
                    newScore.setNumberOfQuestions(request.getNumberOfQuestions());
                    return newScore;
                });

        if (request.getCorrectAlternativeKey() == request.getSelectedAlternativeKey()) {
            scoreEntity.setTotalScore(scoreEntity.getTotalScore() + 1);
        }

        alternatives = (scoreEntity.getChosenAlternatives() != null ? scoreEntity.getChosenAlternatives() : "") + request.getSelectedAlternativeKey();

        scoreEntity.setChosenAlternatives(alternatives);
        scoreRepository.save(scoreEntity);

        log.info("Saving in ResultService postAnswer: " + scoreEntity.getNumberOfQuestions());

        result = new ResultDTO(request.getCorrectAlternativeKey(), request.getExplanation(), scoreEntity.getTotalScore());

        return result;
    }

    @Override
    public ScoreDTO getScore(String sessionKey, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User " + username + " not found."));

        ScoreEntity scoreEntity = scoreRepository.findByUserAndSessionKey(user, sessionKey)
                .orElseThrow(() -> new RuntimeException("Score not found for sessionKey: " + sessionKey + " and username: " + username));

        return resultMapper.toDTO(resultMapper.toModel(scoreEntity));
    }

    @Override
    public List<SessionScoreDTO> getScoresForSession(String sessionKey) {
        List<ScoreEntity> scoreEntities = scoreRepository.findBySessionKey(sessionKey);
        List<SessionScoreDTO> sessionScoreDTOs = new ArrayList<>();

        for (ScoreEntity score : scoreEntities) {
            sessionScoreDTOs.add(resultMapper.toSessionScoreDTO(score));
            log.info("Returning in ResultService getScoresForSession: " + score.getNumberOfQuestions());
        }

        return sessionScoreDTOs;
    }
}
