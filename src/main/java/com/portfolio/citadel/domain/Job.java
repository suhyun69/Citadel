package com.portfolio.citadel.domain;

import lombok.Getter;

@Getter
public class Job {
    private int no;

    public Job(com.portfolio.citadel.entity.Job entity) {
        this.no = entity.getNo();
    }

    public boolean isKing() {
        return this.no == 4;
    }
}
