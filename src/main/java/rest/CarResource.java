package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.DriverDTO;
import facades.CarFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("car")
public class CarResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final CarFacade FACADE =  CarFacade.getCarFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "{\"msg\":\"Hello from car\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public String getAll() {
        List<CarDTO> carDTOs = FACADE.getAll();
        return GSON.toJson(carDTOs);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getdrivers/{id}")
    public String getDrivers(@PathParam("id") long id) {
        List<DriverDTO> driverDTOS = FACADE.getDrivers(id);
        return GSON.toJson(driverDTOS);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    @Path("/{id}")
    public String getCarById(@PathParam("id") long id) {
        CarDTO carDTO = FACADE.getCarById(id);
        return GSON.toJson(carDTO);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    @Path("/create")
    public String createCar(String data) {
        CarDTO carDTO = GSON.fromJson(data, CarDTO.class);
        carDTO = FACADE.createCar(carDTO);
        return GSON.toJson(carDTO);
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    @Path("/update/{id}")
    public String updateCar(@PathParam("id") long id, String data) {
        CarDTO carDTO = GSON.fromJson(data, CarDTO.class);
        carDTO = FACADE.updateCar(id, carDTO);
        return GSON.toJson(carDTO);
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    @Path("/delete/{id}")
    public String deleteCar(@PathParam("id") long id) {
        CarDTO carDTO = FACADE.deleteCar(id);
        return GSON.toJson(carDTO);
    }


}
