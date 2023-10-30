package com.portfolio.citadel.domain;

import lombok.Getter;

@Getter
public class Player {
    private int no;
    private Job job;
    private Boolean crown;

    public Player(int no) {
        this.no = no;
        this.crown = false;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setCrown(boolean crown) {
        this.crown = crown;
    }
}
