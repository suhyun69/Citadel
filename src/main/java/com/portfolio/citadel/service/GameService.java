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

    private static List<Job> jobList = new ArrayList<>();
    private static Queue<Building> buildingDeck = new LinkedList<>();

    public void play() {

        // List<Job> jobList = jobRepository.findAll().stream().map(Job::new).collect(Collectors.toList());
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

        this.init(pw.getPlayerList(), buildingDeck);

        boolean isFinalRound = false;
        int round = 1;
        while(!isFinalRound) {
            log.info(String.format("[Round %d]", round));

            pw.chooseJob(jobList);
            this.doAction(pw.getPlayerList());

            log.info(String.format("[Round %d end]", round));
            round++;

            Optional<Player> SevenBuildingOwner = pw.getPlayerList().stream()
                    .filter(p -> p.getBuildings().size() == 7)
                    .findAny();
            if(SevenBuildingOwner.isPresent()) isFinalRound = true;
        }
    }

    private void init(List<Player> playerList, Queue<Building> buildingDeck) {

        for(Player p : playerList) {
            p.setMoney(2);
            for(int i=0; i<4; i++) {
                p.addHand(buildingDeck.poll());
            }
        }
    }

    private void doAction(List<Player> playerList) {

        for(Job job : jobList) {
            Optional<Player> player = playerList.stream().filter(p -> p.getJob().getNo() == job.getNo()).findAny();
            if(player.isPresent()) {
                log.info(String.format("%s의 차례입니다. Player%d가 행동합니다. (%d/%d/%d)", player.get().getJob().getName(), player.get().getNo(), player.get().getMoney(), player.get().getHands().size(), player.get().getBuildings().size()));
                if(player.get().getJob().isKing()) {
                    log.info(String.format("왕관을 가져옵니다."));
                    playerList.stream().filter(p_-> p_.isCrown()).findAny().get().setCrown(false);
                    player.get().setCrown(true);
                }

                this.getAsset(player.get());
                this.build(player.get());

                log.info(String.format("턴 종료. (%d/%d/%d)", player.get().getMoney(), player.get().getHands().size(), player.get().getBuildings().size()));
            }
        }
    }

    private void getAsset(Player player) {
        if(this.yesOrNo()) {
            // 은행으로부터 금화 2개 받기
            player.setMoney(2);
            log.info(String.format("Player%d가 금화 2개를 받았습니다. (총 %d개)", player.getNo(), player.getMoney()));
        }
        else {
            // 건물 카드 가져오기
            player.getHands().add(buildingDeck.poll());
            player.getHands().add(buildingDeck.poll());

            Random random = new Random();
            int index = random.nextInt(player.getHands().size());
            Building building = player.getHands().get(index);
            player.getHands().remove(building);
            buildingDeck.offer(building);
            log.info(String.format("Player%d가 건물 카드를 받았습니다.", player.getNo()));
        }
    }

    private void build(Player player) {

        // 건물 짓기
        List<Building> buildable = player.getHands().stream()
                .filter(b -> b.getCost() <= player.getMoney())
                .collect(Collectors.toList());

        if(buildable.size() > 0) {
            Random random = new Random();
            int index = random.nextInt(buildable.size());

            Building building = buildable.get(index);
            player.setMoney(-1 * building.getCost());
            player.getHands().remove(building);
            player.getBuildings().add(building);
            log.info(String.format("Player%d가 [%s(%d)]을 지었습니다. (총 %d개)", player.getNo(), building.getName(), building.getCost(), player.getBuildings().size()));
        }
    }

    private boolean yesOrNo() {
        return (int)(Math.random() * 2) == 1;
    }
}
