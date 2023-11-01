package com.portfolio.citadel.domain;

import lombok.Getter;

@Getter
public class Building {

    private String name;
    private int cost;
    private String type;

    public Building(com.portfolio.citadel.entity.Building entity) {
        this.name = entity.getName();
        this.cost = entity.getCost();
        this.type = entity.getType();
    }
}
