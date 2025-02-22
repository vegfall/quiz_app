package com.quizapp.result.controller;

import com.quizapp.result.entity.UserEntity;
import com.quizapp.result.service.SingleplayerResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/result")
public class ResultController {
    private final SingleplayerResultService resultService;

    public ResultController(SingleplayerResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> checkConnection() {
        return new ResponseEntity<>("Result responding OK :)", HttpStatus.OK);
    }

    @GetMapping("/db/{id}")
    public ResponseEntity<UserEntity> getUserEntityTest(@PathVariable Long id) {
        return new ResponseEntity<>(resultService.getUserEntity(id), HttpStatus.OK);
    }

    @PostMapping("/db/{username}")
    public ResponseEntity<String> postUserEntityTest(@PathVariable String username) {
        resultService.saveUserEntity(username);
        String returnValue = "UserEntity with username " + username + " created";

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }
}
