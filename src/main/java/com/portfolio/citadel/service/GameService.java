package com.portfolio.citadel.service;

import com.portfolio.citadel.domain.Building;
import com.portfolio.citadel.domain.Job;
import com.portfolio.citadel.domain.Player;
import com.portfolio.citadel.domain.PlayerWrapper;
import com.portfolio.citadel.repository.BuildingRepository;
import com.portfolio.citadel.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@Slf4j
public class GameService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    BuildingService buildingService;

    private static List<Job> jobList = new ArrayList<>();
    private static Queue<Building> buildingDeck = new LinkedList<>();
    private static boolean isFinalRound = false;
    private static int round = 1;

    public void play() {

        jobList = jobRepository.findAll().stream().map(Job::new).collect(Collectors.toList());
        buildingDeck = buildingService.getBuildingCard();

        PlayerWrapper pw = new PlayerWrapper(buildingDeck);

        while(!isFinalRound) {
            log.info(String.format("[Round %d]", round));

            pw.selectJob(jobList);

            for(Job job : jobList) {
                if(pw.getPlayerList().stream().anyMatch(p -> p.getJob().getNo() == job.getNo())) {

                    Player player = pw.getPlayerList().stream().filter(p -> p.getJob().getNo() == job.getNo()).findAny().get();

                    pw.before(player);

                    // 자원얻기
                    pw.getAsset(player);

                    // 건물짓기
                    pw.build(player);

                    isFinalRound = pw.after(player, isFinalRound);
                }
            }

            log.info(String.format("[Round %d end]", round));
            round++;

            if(isFinalRound) {
                pw.getWinner();
            }
        }
    }
}
