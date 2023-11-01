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
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class GameService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    BuildingRepository buildingRepository;

    private final static int memberCount  = 6;
    private final static int round = 8;

    public void play() {

        PlayerWrapper pw = new PlayerWrapper();
        List<Job> jobList = jobRepository.findAll().stream().map(Job::new).collect(Collectors.toList());

        List<Building> buildingDeck = new ArrayList<>();
        buildingRepository.findAll().stream()
                .forEach(b -> {
                    for(int i=0; i<b.getCount(); i++) {
                        buildingDeck.add(new Building(b));
                    }
                });
        Collections.shuffle(buildingDeck);

        for(int i=1; i<=round; i++) {
            log.info(String.format("[Round %d]", i));

            pw.chooseJob(jobList);
            pw.doAction(jobList);

            log.info(String.format("[Round %d end]", i));
        }
    }
}
