package com.portfolio.citadel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class Player {
    private int no;
    private Job job;
    private boolean crown;
    private int money;
    private List<Building> hands = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private boolean isEndPlayer;
    private int totalScore;

    public Player(int no, boolean isCrown) {
        this.no = no;
        this.crown = isCrown;
    }

    public void setCrown() {
        this.setCrown(true);
        log.info(String.format("왕관을 가져옵니다."));
    }

    // 금화 2개 받기
    public void getMoneyFromBank() {
        this.setMoney(this.getMoney() + 2);
        log.info("은행으로부터 금화 2개를 받았습니다.");
    }

    public boolean isBuildable() {
        return !this.getBuildableList().isEmpty();
    }

    private List<Building> getBuildableList() {
        return this.hands.stream()
                .filter(b -> this.isBuildable(b))
                .collect(Collectors.toList());
    }

    private boolean isBuildable(Building building) {

        if(building.getCost() > this.getMoney()) return false;

        if(this.getBuildings().stream().anyMatch(b -> b.getName().equals(building.getName()))) {
            if(this.getBuildings().stream().noneMatch(b -> b.getName().equals("채석장"))) {
                return false;
            }
        }

        // 비밀 금고는 절대로 도시에 건설할 수 없습니다. 게임이 종료되었을 때, 손에 든 비밀 금고를 공개하고 추가로 3점을 받습니다.
        if(building.getName().equals("비밀 금고")) return false;

        return true;
    }

    // 건물 짓기
    public void build() {
        List<Building> buildableList = this.getBuildableList();
        if(!buildableList.isEmpty()) {
            Building building = buildableList.get(this.getRandomIndex(buildableList.size()));

            this.setMoney(this.getMoney() - building.getCost());
            this.getHands().remove(building);
            this.getBuildings().add(building);

            log.info(String.format("[%s(%d)]을 지었습니다.", building.getName(), building.getCost()));
        }
        else {
            if(this.getHands().size() == 0) {
                log.info("건물카드가 없습니다.");
            }
            else {
                log.info(String.format("%s%s", "지을 수 있는 건물이 없습니다."
                        , String.format(" - %s", this.getHands().stream()
                                .map(h -> String.format("%s(%s)", h.getName(), h.getCost()))
                                .collect(Collectors.joining(", ")))));
            }
        }
    }

    // 세금 받기
    public void getMoneyFromBuilding() {
        int tax = 0;
        switch (this.job.getNo()) {
            case 4:
                tax = (int)this.buildings.stream().filter(b -> b.getType().equals("N")).count();
                if(tax > 0) {
                    this.money += tax;
                    log.info(String.format("귀족 건물로부터 세금으로 금화 %d개를 받았습니다.", tax));
                }
                break;
            case 5:
                tax = (int)this.buildings.stream().filter(b -> b.getType().equals("R")).count();
                if(tax > 0) {
                    this.money += tax;
                    log.info(String.format("종교 건물로부터 세금으로 금화 %d개를 받았습니다.", tax));
                }
                break;
            case 6:
                tax = (int)this.buildings.stream().filter(b -> b.getType().equals("C")).count();
                if(tax > 0) {
                    this.money += tax;
                    log.info(String.format("상업 건물로부터 세금으로 금화 %d개를 받았습니다.", tax));
                }
                break;
            case 8:
                tax = (int)this.buildings.stream().filter(b -> b.getType().equals("A")).count();
                if(tax > 0) {
                    this.money += tax;
                    log.info(String.format("군사 건물로부터 세금으로 금화 %d개를 받았습니다.", tax));
                }
                break;
        }
    }

    // 건물 카드 받기
    public Building getBuildingCard(List<Building> buildingsFromDeck) {

        buildingsFromDeck.stream()
                .forEach(b -> this.hands.add(b));

        Building building = this.hands.get(this.getRandomIndex(this.getHands().size()));
        this.hands.remove(building);

        log.info(String.format("건물 카드를 받았습니다."));

        return building;
    }

    private int getRandomIndex(int size) {
        return (int)(Math.random() * size);
    }
}
