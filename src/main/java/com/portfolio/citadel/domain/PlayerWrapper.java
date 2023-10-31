package com.portfolio.citadel.domain;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class PlayerWrapper {

    private List<Player> playerList;

    public PlayerWrapper() {
        this.playerList = new ArrayList<>();
        for(int i=1; i<=6; i++) {
            this.playerList.add(new Player(i));
        }
    }

    public PlayerWrapper(int memberCount) {
        this.playerList = new ArrayList<>();
        for(int i=1; i<=memberCount; i++) {
            this.playerList.add(new Player(i));
        }
    }

    public List<Player> getPlayerList() {
        return this.playerList;
    }

    public void sortByKing() {

        // 기본적으로 PlayerNo 기준 정렬
        this.playerList = this.playerList.stream().sorted(Comparator.comparingInt(Player::getNo)).collect(Collectors.toList());
        if(this.playerList.stream().filter(p -> p.isCrown()).findAny().isPresent()) {
            // 왕관을 가진 플레이어가 있는 경우, 왕관을 가진 플레이어부터 정렬
            Queue<Player> playerQueue = new LinkedList<>(this.playerList);
            while(!playerQueue.peek().isCrown()) {
                playerQueue.offer(playerQueue.poll());
            }
            this.playerList = playerQueue.stream().toList();
        }
    }

    public void chooseJob(List<Job> jobList) {

        List<Job> jobListCopied = new ArrayList<>(jobList);
        // Collections.copy(jobListCopied, jobList);

        for(Player p : this.playerList) {
            int jobIndex = (int)(Math.random() * jobListCopied.size());

            Job selected = jobListCopied.get(jobIndex);
            p.setJob(selected);
            p.setCrown(selected.isKing());
            jobListCopied.remove(selected);

            log.info(String.format("Player%d가 Job %d를 선택했습니다", p.getNo(), p.getJob().getNo()));
        }
    }

    public void sortByJob() {
        this.playerList = this.playerList.stream().sorted(Comparator.comparingInt(p -> p.getJob().getNo())).collect(Collectors.toList());
    }

    public void doAction() {
        for(Player p : this.playerList) {
            log.info(String.format("Job %d, Player%d가 행동합니다.", p.getJob().getNo(), p.getNo()));
        }
    }
}
