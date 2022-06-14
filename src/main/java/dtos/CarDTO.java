/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Car;
import entities.Driver;
import entities.Race;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class CarDTO {
    private long id;
    String name;
    String brand;
    String make;
    String year;
    String sponsor;
    String color;
    List<Long> driversIds;
    List<Long> racesIds;

    public CarDTO(Car car) {
        if(car.getId() != null)
            this.id = car.getId();
        this.name = car.getName();
        this.brand = car.getBrand();
        this.make = car.getMake();
        this.year = car.getYear();
        this.sponsor = car.getSponsor();
        this.color = car.getColor();
        this.driversIds = getDrivers(car.getDrivers());
        this.racesIds = getRaces(car.getRaces());
    }

    public static List<CarDTO> getDTOs(List<Car> cars){
        List<CarDTO> carDTOs = new ArrayList<>();
        cars.forEach(car -> carDTOs.add(new CarDTO(car)));
        return carDTOs;
    }

    private List<Long> getDrivers(List<Driver> drivers){
        List<Long> driverIds = new ArrayList<>();
        for (Driver driver : drivers)
        {
            driverIds.add(driver.getId());
        }
        return driverIds;
    }

    private List<Long> getRaces(List<Race> races){
        List<Long> driverIds = new ArrayList<>();
        for (Race race : races)
        {
            driverIds.add(race.getId());
        }
        return driverIds;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getMake() {
        return make;
    }

    public String getYear() {
        return year;
    }

    public String getSponsor() {
        return sponsor;
    }

    public String getColor() {
        return color;
    }

    public List<Long> getDriversIds() {
        return driversIds;
    }

}
