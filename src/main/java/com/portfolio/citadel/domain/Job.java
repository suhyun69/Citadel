package com.portfolio.citadel.domain;

import lombok.Getter;

@Getter
public class Job {
    private int no;
    private String name;

    public Job(com.portfolio.citadel.entity.Job entity) {
        this.no = entity.getNo();
        this.name = entity.getName();
    }

    public boolean isKing() {
        return this.no == 4;
    }
}
