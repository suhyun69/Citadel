package com.portfolio.citadel.service;

import com.portfolio.citadel.domain.Building;
import com.portfolio.citadel.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;

    public Queue<Building> getBuildingCard() {

        List<Building> buildingList = new ArrayList<>();
        buildingRepository.findAll().stream()
                .forEach(b -> {
                    for(int i=0; i<b.getCount(); i++) {
                        buildingList.add(new Building(b));
                    }
                });
        Collections.shuffle(buildingList);

        return new LinkedList<>(buildingList);
    }
}
