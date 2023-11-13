package com.portfolio.citadel.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@Slf4j
public class PlayerWrapper {

    private static int initMember = 6;
    private static int initMoney = 2;
    private static int initCard = 4;

    private int finalRoundBuilding = 7;
    private List<Player> playerList = new ArrayList<>();
    private Queue<Building> buildingDeck = new LinkedList<>();

    public PlayerWrapper(Queue<Building> buildingDeck) {

        this.setBuildingDeck(buildingDeck);

        for(int i=1; i<=initMember; i++) {
            this.playerList.add(new Player(i, i==1));
        }

        for(Player p : this.getPlayerList()) {
            p.setMoney(initMoney);
            IntStream.range(0, initCard).forEach(i -> {
                p.getHands().add(this.getBuildingDeck().poll());
            });
        }
    }

    // PlayerNo 기준으로 정렬하되, 왕관을 가진 플레이어부터 정렬
    private void sortByCrown() {

        Queue<Player> playerQueue = new LinkedList<>(this.getPlayerList().stream().sorted(Comparator.comparingInt(Player::getNo)).collect(Collectors.toList()));
        while(!playerQueue.peek().isCrown()) playerQueue.offer(playerQueue.poll());

        this.setPlayerList(playerQueue.stream().toList());
    }

    public void selectJob(List<Job> jobList) {

        List<Job> jobListCopied = new ArrayList<>(jobList);

        this.sortByCrown();
        for(Player p : this.playerList) {

            Job selected = jobListCopied.get(this.getRandomIndex(jobListCopied.size()));
            p.setJob(selected);
            jobListCopied.remove(selected);

            log.info(String.format("Player%d가 %s를 선택했습니다", p.getNo(), p.getJob().getName()));
        }
    }

    public void before(Player player) {
        log.info(String.format("%s의 차례입니다. Player%d가 행동합니다. (%d/%d/%d)", player.getJob().getName(), player.getNo(), player.getMoney(), player.getHands().size(), player.getBuildings().size()));
        if(player.getJob().isKing()) this.moveCrown(player);
    }

    public void moveCrown(Player player) {
        this.getPlayerList().stream().filter(p_ -> p_.isCrown()).findAny().get().setCrown(false);
        player.setCrown();
        log.info(String.format("왕관을 가져옵니다."));
    }

    public void getAsset(Player player) {

        if(this.getRandomIndex(2) == 0) {
            this.getMoneyFromBank(player);
        }
        else {
            this.getBuildingCard(player);
        }
    }

    private void getMoneyFromBank(Player player) {
        player.getMoneyFromBank();
    }

    private void getBuildingCard(Player player) {
        Building backCard = player.getBuildingCard(IntStream.range(0,2)
                .mapToObj(i -> this.getBuildingDeck().poll())
                .collect(Collectors.toList()));
        this.getBuildingDeck().offer(backCard);
    }

    public void build(Player player) {
        player.build();
    }

    public boolean after(Player player, boolean isFinalRound) {

        if(!isFinalRound && player.getBuildings().size() == this.finalRoundBuilding) {
            isFinalRound = true;
            player.setEndPlayer(true);
            log.info(String.format("건물을 %s채 지었습니다. 이번 라운드를 마지막으로 게임을 종료합니다.", this.finalRoundBuilding));
        }
        log.info(String.format("턴 종료. (%d/%d/%d)", player.getMoney(), player.getHands().size(), player.getBuildings().size()));

        return isFinalRound;
    }

    public Player getWinner() {
        this.playerList.stream()
                .forEach(p -> p.setTotalScore(this.getTotalScore(p)));

        return this.playerList.stream()
                .max(Comparator.comparing(Player::getTotalScore))
                .get();
    }

    private int getTotalScore(Player player) {

        int totalScore = 0;

        totalScore += player.getBuildings().stream().map(b -> b.getCost()).reduce((b1, b2) -> b1 + b2).orElse(0); // 자신의 도시에 있는 건물 카드들의 건설비용만큼 점수를 받습니다.

        Set<Map.Entry<String, List<Building>>> entrySet = player.getBuildings().stream()
                .collect(Collectors.groupingBy(b -> b.getType()))
                .entrySet();

        // 자신의 도시에 건물 카드가 5가지 종류별로 하나씩 건설되었다면 3점을 받습니다.
        if(entrySet.size() == 5) {
            totalScore += 3;
        }
        else {
            // 게임이 종료되면 유령 지구를 원하는 종류의 건물로 간주합니다.
            if(entrySet.size() == 4 && entrySet.stream().anyMatch(e -> e.getKey().equals("S") && e.getValue().size() >= 2 && e.getValue().stream().anyMatch(b -> b.getName().equals("유령 지구")))) {
                totalScore += 3;
            }
        }

        // 의사당
        if(player.getBuildings().stream().anyMatch(b -> b.getName().equals("비밀 금고"))) {
            if(entrySet.stream().anyMatch(e -> e.getValue().size() >= 3)) {
                totalScore += 3;
                // 게임이 종료되었을 때 자기 도시에 같은 종류의 건물이 최소 3채 건설되어 있다면 추가로 3점을 받습니다.
            }
        }

        // 가장 먼저 도시를 완성시킨 플레이어는 4점을 받습니다.
        // 도시를 완성시킨 나머지 플레이어는 2점을 받습니다.
        if(player.getBuildings().size() == 7) {
            totalScore += player.isEndPlayer() ? 4 : 2;
        }

        // 특수카드 효과

        // 동상
        if(player.getBuildings().stream().anyMatch(b -> b.getName().equals("동상"))) {
            if(player.isCrown()) {
                totalScore += 5; // 게임이 종료되었을 때 왕관을 가지고 있다면 추가로 5점을 받습니다.
            }
        }

        // 바실리카
        if(player.getBuildings().stream().anyMatch(b -> b.getName().equals("바실리카"))) {
            totalScore += (int)player.getBuildings().stream().filter(b -> (b.getCost() % 2) == 1).count();
            // 게임이 종료되었을 때, 자기 도시의 건물 중 건설비용이 홀수인 건물당 추가로 1점씩을 받습니다.
        }

        // 상아탑
        if(player.getBuildings().stream().anyMatch(b -> b.getName().equals("상아탑"))) {
            totalScore += (player.getBuildings().stream().filter(b -> b.getType().equals("S")).count() == 1L ? 5: 0);
            // 게임이 종료되었을 때 자기 도시에 특수 건물이 상아탑뿐이라면 추가로 5점을 받습니다.
        }

        // 소원의 우물
        if(player.getBuildings().stream().anyMatch(b -> b.getName().equals("소원의 우물"))) {
            totalScore += (int)player.getBuildings().stream().filter(b -> b.getType().equals("S")).count();
            // 게임이 종료되면 자기 도시에 건설된 특수 건물 1채당(소원의 우물 포함) 추가로 1점씩을 받습니다.
        }

        // 지도 보관실
        if(player.getBuildings().stream().anyMatch(b -> b.getName().equals("지도 보관실"))) {
            totalScore += player.getHands().size();
            // 게임이 종료되면 손에 든 건물 카드 1장당 1점씩을 추가로 받습니다.
        }

        // 드래곤 게이트
        if(player.getBuildings().stream().anyMatch(b -> b.getName().equals("드래곤 게이트"))) {
            totalScore += 2;
            // 게임이 종료되면 추가로 2점을 받습니다.
        }

        // 제국 보고
        if(player.getBuildings().stream().anyMatch(b -> b.getName().equals("제국 보고"))) {
            totalScore += player.getMoney();
            // 게임이 종료되면 개인 금고에 남아있는 금화 1닢당 1점씩을 추가로 받습니다.
        }

        // 비밀 금고
        if(player.getHands().stream().anyMatch(b -> b.getName().equals("비밀 금고"))) {
            totalScore += 3;
            // 비밀 금고는 절대로 도시에 건설할 수 없습니다. 게임이 종료되었을 때, 손에 든 비밀 금고를 공개하고 추가로 3점을 받습니다.
        }

        return totalScore;
    }

    private int getRandomIndex(int size) {
        return (int)(Math.random() * size);
    }
}
