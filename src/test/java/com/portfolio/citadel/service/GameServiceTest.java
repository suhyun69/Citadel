package com.portfolio.citadel.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameServiceTest {

    @Autowired
    GameService gameService;

    @DisplayName("log test")
    @Test
    public void logTest() throws Exception {

        gameService.play();

        assertThat(true).isEqualTo(true);
    }

}