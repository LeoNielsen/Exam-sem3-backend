/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Car;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class CarDTO {
    private long id;
    String dummy;

    public CarDTO(Car car) {
        if(car.getId() != null)
            this.id = car.getId();
        this.dummy = car.getDummy();
    }

    public static List<CarDTO> getDTOs(List<Car> cars){
        List<CarDTO> carDTOs = new ArrayList();
        cars.forEach(car -> carDTOs.add(new CarDTO(car)));
        return carDTOs;
    }

    public long getId() {
        return id;
    }

    public String getDummy() {
        return dummy;
    }
}
