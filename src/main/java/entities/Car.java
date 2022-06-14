package entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;
import java.util.List;


@Entity
@NamedQuery(name = "car.deleteAllRows", query = "DELETE from Car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    String brand;
    String make;
    String year;
    String sponsor;
    String color;

    @OneToMany (mappedBy = "car")
    List<Driver> drivers = new ArrayList<>();

    @ManyToMany
    List<Race> races = new ArrayList<>();

    public Car() {
    }

    public Car(String name, String brand, String make, String year, String sponsor, String color, List<Driver> drivers, List<Race> races) {
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.sponsor = sponsor;
        this.color = color;
        this.drivers = drivers;
        this.races = races;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void addDriver(Driver driver) {
        this.getDrivers().add(driver);
    }
    public void removeDriver(Driver driver) {
        this.getDrivers().remove(driver);
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    public void addRace(Race race) {
        this.getRaces().add(race);
    }
    public void removeRace(Race race) {
        this.getRaces().remove(race);
    }



}
