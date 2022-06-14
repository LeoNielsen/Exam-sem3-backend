/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tha
 */
public class DriverDTO {
    private long id;
    String name;
    String birthYear;
    String experience;
    String gender;
    String user;
    Long carId;

    public DriverDTO(Driver driver) {
        if (driver.getId() != null)
            this.id = driver.getId();
        this.name = driver.getName();
        this.birthYear = driver.getBirthYear();
        this.experience = driver.getExperience();
        this.gender = driver.getGender();
        this.user = driver.getUser().getUserName();
        if (driver.getCar() != null)
            this.carId = driver.getCar().getId();
    }

    public static List<DriverDTO> getDTOs(List<Driver> drivers) {
        List<DriverDTO> driverDTOS = new ArrayList<>();
        drivers.forEach(driver -> driverDTOS.add(new DriverDTO(driver)));
        return driverDTOS;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getExperience() {
        return experience;
    }

    public String getGender() {
        return gender;
    }

    public String getUser() {
        return user;
    }

    public Long getCarId() {
        return carId;
    }
}
