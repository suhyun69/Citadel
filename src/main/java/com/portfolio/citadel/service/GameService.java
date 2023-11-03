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
    BuildingRepository buildingRepository;

    private final static int memberCount  = 6;

    private static List<Job> jobList = new ArrayList<>();
    private static Queue<Building> buildingDeck = new LinkedList<>();

    public void play() {

        jobList = jobRepository.findAll().stream().map(Job::new).collect(Collectors.toList());
        List<Building> buildingList = new ArrayList<>();
        buildingRepository.findAll().stream()
                .forEach(b -> {
                    for(int i=0; i<b.getCount(); i++) {
                        buildingList.add(new Building(b));
                    }
                });
        Collections.shuffle(buildingList);
        buildingDeck = new LinkedList<>(buildingList);
        PlayerWrapper pw = new PlayerWrapper(memberCount);

        for(Player p : pw.getPlayerList()) {
            p.setMoney(2);
            for(int i=0; i<4; i++) {
                p.addHand(buildingDeck.poll());
            }
        }

        boolean isFinalRound = false;
        int round = 1;

        while(!isFinalRound) {
            log.info(String.format("[Round %d]", round));

            pw.chooseJob(jobList);
            for(Job job : jobList) {
                if(pw.getPlayerList().stream().filter(p -> p.getJob().getNo() == job.getNo()).findAny().isPresent()) {

                    Player player = pw.getPlayerList().stream().filter(p -> p.getJob().getNo() == job.getNo()).findAny().get();

                    log.info(String.format("%s의 차례입니다. Player%d가 행동합니다. (%d/%d/%d)", player.getJob().getName(), player.getNo(), player.getMoney(), player.getHands().size(), player.getBuildings().size()));
                    if(player.getJob().isKing()) {
                        log.info(String.format("왕관을 가져옵니다."));
                        pw.getPlayerList().stream().filter(p_-> p_.isCrown()).findAny().get().setCrown(false);
                        player.setCrown(true);
                    }

                    player.getMoneyFromBuilding();

                    if((int)(Math.random() * 2) == 1) {
                        player.getMoneyFromBank();
                    }
                    else {
                        List<Building> draw = new ArrayList<>();
                        IntStream.range(0, 2).forEach(i -> draw.add(buildingDeck.poll()));
                        buildingDeck.offer(player.getBuildingCard(draw));
                    }

                    player.build();

                    log.info(String.format("턴 종료. (%d/%d/%d)", player.getMoney(), player.getHands().size(), player.getBuildings().size()));

                    if(!isFinalRound && pw.getPlayerList().stream().filter(p -> p.getBuildings().size() == 7).findAny().isPresent()) {
                        isFinalRound = true;
                        player.setEndPlayer(true);
                        log.info(String.format("건물을 7채 지었습니다. 이번 라운드를 마지막으로 게임을 종료합니다."));
                    }
                }
            }

            log.info(String.format("[Round %d end]", round));
            round++;

            if(isFinalRound) {
                Player winner = pw.getWinner();
                log.info(String.format("Player%d가 총점 %d점으로 승리했습니다", winner.getNo(), winner.getTotalScore()));
            }
        }
    }
}
