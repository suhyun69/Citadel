package com.portfolio.citadel.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
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

    public void setCrown(boolean crown) {
        this.crown = crown;
    }

    public void setMoney(int money) {
        this.money += money;
    }

    public void addHand(Building building) {
        this.getHands().add(building);
    }
}
