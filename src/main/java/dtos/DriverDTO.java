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
 *
 * @author tha
 */
public class DriverDTO {
    private long id;
    String dummy;

    public DriverDTO(Driver driver) {
        if(driver.getId() != null)
            this.id = driver.getId();
        this.dummy = driver.getDummy();
    }

    public static List<DriverDTO> getDTOs(List<Driver> drivers){
        List<DriverDTO> driverDTOS = new ArrayList();
        drivers.forEach(driver -> driverDTOS.add(new DriverDTO(driver)));
        return driverDTOS;
    }

    public long getId() {
        return id;
    }

    public String getDummy() {
        return dummy;
    }
}
