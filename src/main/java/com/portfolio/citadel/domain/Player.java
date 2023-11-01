package com.portfolio.citadel.domain;

import lombok.Getter;

@Getter
public class Player {
    private int no;
    private Job job;
    private boolean crown;

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
}
