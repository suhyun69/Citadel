package com.portfolio.citadel.domain;

import lombok.Getter;

@Getter
public class Building {

    private String name;
    private int cost;
    private BuildingType type;

    public Building(com.portfolio.citadel.entity.Building entity) {
        this.name = entity.getName();
        this.cost = entity.getCost();

        switch (entity.getType()) {
            case "N":
                this.type = BuildingType.Noble;
                break;
            case "C":
                this.type = BuildingType.Commerce;
                break;
            case "R":
                this.type = BuildingType.Religion;
                break;
            case "M":
                this.type = BuildingType.Military;
                break;
            case "S":
                this.type = BuildingType.Special;
                break;
        }
    }

    public void setType(BuildingType type) {
        this.type = type;
    }
}
