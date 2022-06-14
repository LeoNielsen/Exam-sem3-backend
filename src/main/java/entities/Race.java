package entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;
import java.util.List;


@Entity
@NamedQuery(name = "race.deleteAllRows", query = "DELETE from Race")
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    String location;
    String startDate;
    String duration;

    @ManyToMany
    List<Car> cars = new ArrayList<>();
    
    public Race() {
    }

    public Race(String name, String location, String startDate, String duration, List<Car> cars) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.duration = duration;
        this.cars = cars;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        this.getCars().add(car);
    }
    public void removeCar(Car car) {
        this.getCars().remove(car);
    }
}
