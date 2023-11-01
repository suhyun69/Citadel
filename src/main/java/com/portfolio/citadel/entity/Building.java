package com.portfolio.citadel.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "BUILDING")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUILDING_SEQ", updatable = false)
    private Long seq;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COST")
    private int cost;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "COUNT")
    private int count;

    @Column(name = "DESCRIPTION")
    private String description;
}
