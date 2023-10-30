package com.portfolio.citadel.service;

import com.portfolio.citadel.domain.Job;
import com.portfolio.citadel.domain.Player;
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

    private final static int memberCount  = 6;
    private final static int round = 8;

    public void play() {

        List<Player> playerList = new ArrayList<>();
        for(int i=1; i<=memberCount; i++) {
            playerList.add(new Player(i));
        }
        List<Job> jobList = jobRepository.findAll().stream().map(Job::new).collect(Collectors.toList());

        for(int i=1; i<=round; i++) {
            log.info(String.format("[Round %d]", i));

            if(i != 1) playerList = this.sortByKing(playerList);
            selectJob(playerList, jobList);

            playerList = this.sortByJob(playerList);
            for(Player p : playerList) {
                log.info(String.format("JobNo %d, PlayerNo%d의 차례입니다", p.getJob().getNo(), p.getNo()));
            }
            log.info(String.format("[Round %d end]", i));
        }
    }

    private void selectJob(List<Player> playerList, List<Job> jobList) {

        Collections.shuffle(jobList); // jobList 섞기

        for(int i=0; i<memberCount; i++) {
            playerList.get(i).setJob(jobList.get(i));
            if(jobList.get(i).isKing()) playerList.get(i).setCrown(true);
            log.info(String.format("PlayerNo%d이 Job %d를 골랐습니다.",  playerList.get(i).getNo(),  playerList.get(i).getJob().getNo()));
        }
    }

    private List<Player> sortByJob(List<Player> playerList) {
        return playerList.stream().sorted(Comparator.comparingInt(p -> p.getJob().getNo())).collect(Collectors.toList());
    }

    private List<Player> sortByKing(List<Player> playerList) {

        List<Player> sorted = new ArrayList<>();

        int kingIndex = playerList.stream().filter(p -> p.getCrown()).findAny().get().getNo();
        Optional<Player> king = playerList.stream().filter(p -> p.getJob().isKing()).findAny();
        if(king.isPresent()) {
            kingIndex = king.get().getNo();
            for(Player p : playerList) {
                p.setCrown(p.getNo() == kingIndex);
            }
        }

        for(int i=kingIndex; i<=memberCount; i++) {
            int finalI = i;
            Optional<Player> player = playerList.stream().filter(p -> p.getNo() == finalI).findAny();
            if(player.isPresent()) {
                sorted.add(player.get());
            }
        }

        for(int i=1; i<kingIndex; i++) {
            int finalI = i;
            Optional<Player> player = playerList.stream().filter(p -> p.getNo() == finalI).findAny();
            if(player.isPresent()) {
                sorted.add(player.get());
            }
        }

        return sorted;
    }
}
