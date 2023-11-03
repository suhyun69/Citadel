package com.portfolio.citadel.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class Player {
    private int no;
    private Job job;
    private boolean crown;
    private int money;
    private List<Building> hands = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();

    public Player(int no, boolean isCrown) {
        this.no = no;
        this.crown = isCrown;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    public void setMoney(int money) { this.money = money; }
    public void setCrown(boolean crown) {
        this.crown = crown;
    }

    public void addHand(Building building) {
        this.getHands().add(building);
    }

    // 금화 2개 받기
    public void getMoneyFromBank() {
        this.money += 2;
        log.info(String.format("은행으로부터 금화 2개를 받았습니다."));
    }

    // 건물 카드 받기
    public Building getBuildingCard(List<Building> buildingsFromDeck) {

        buildingsFromDeck.stream().forEach(b -> this.hands.add(b));

        Random random = new Random();
        int index = random.nextInt(this.hands.size());

        Building building = this.hands.get(index);
        this.hands.remove(building);

        log.info(String.format("건물 카드를 받았습니다."));

        return building;
    }

    // 건물 짓기
    public void build() {

        if(this.hands.stream().anyMatch(b -> b.getCost() <= this.money)) {
            List<Building> buildable = this.hands.stream().filter(b -> b.getCost() <= this.money).collect(Collectors.toList());

            Random random = new Random();
            int index = random.nextInt(buildable.size());

            Building building = buildable.get(index);
            this.money -= building.getCost();
            this.hands.remove(building);
            this.buildings.add(building);

            log.info(String.format("[%s(%d)]을 지었습니다.", building.getName(), building.getCost()));
        }
    }
}
