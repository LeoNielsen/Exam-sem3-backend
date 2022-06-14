package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DriverDTO;
import facades.DriverFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("driver")
public class DriverResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final DriverFacade FACADE =  DriverFacade.getDriverFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "{\"msg\":\"Hello from driver\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public String getAll() {
        List<DriverDTO> driverDTOS = FACADE.getAll();
        return GSON.toJson(driverDTOS);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    @Path("/{id}")
    public String getDriverById(@PathParam("id") long id) {
        DriverDTO driverDTO = FACADE.getDriverById(id);
        return GSON.toJson(driverDTO);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    @Path("/create")
    public String createDriver(String data) {
        DriverDTO driverDTO = GSON.fromJson(data, DriverDTO.class);
        driverDTO = FACADE.createDriver(driverDTO);
        return GSON.toJson(driverDTO);
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    @Path("/update/{id}")
    public String updateDriver(@PathParam("id") long id, String data) {
        DriverDTO driverDTO = GSON.fromJson(data, DriverDTO.class);
        driverDTO = FACADE.updateDriver(id, driverDTO);
        return GSON.toJson(driverDTO);
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    @Path("/delete/{id}")
    public String deleteDriver(@PathParam("id") long id) {
        DriverDTO driverDTO = FACADE.deleteDriver(id);
        return GSON.toJson(driverDTO);
    }

}
