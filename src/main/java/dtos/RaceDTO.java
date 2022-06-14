/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Race;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class RaceDTO {
    private long id;
    String dummy;

    public RaceDTO(Race race) {
        if(race.getId() != null)
            this.id = race.getId();
        this.dummy = race.getDummy();
    }

    public static List<RaceDTO> getDTOs(List<Race> races){
        List<RaceDTO> raceDTOS = new ArrayList();
        races.forEach(race -> raceDTOS.add(new RaceDTO(race)));
        return raceDTOS;
    }

    public long getId() {
        return id;
    }

    public String getDummy() {
        return dummy;
    }
}
