/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Car;
import entities.Race;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class RaceDTO {
    private long id;
    String name;
    String location;
    String startDate;
    String duration;
    List<Long> carsId;

    public RaceDTO(Race race) {
        if(race.getId() != null)
            this.id = race.getId();
        this.name = race.getName();
        this.location = race.getLocation();
        this.startDate = race.getStartDate();
        this.duration = race.getDuration();
        this.carsId = getCars(race.getCars());
    }

    public static List<RaceDTO> getDTOs(List<Race> races){
        List<RaceDTO> raceDTOS = new ArrayList<>();
        races.forEach(race -> raceDTOS.add(new RaceDTO(race)));
        return raceDTOS;
    }

    private List<Long> getCars(List<Car> cars){
        List<Long> driverIds = new ArrayList<>();
        for (Car car : cars)
        {
            driverIds.add(car.getId());
        }
        return driverIds;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDuration() {
        return duration;
    }

    public List<Long> getCarsId() {
        return carsId;
    }

}
