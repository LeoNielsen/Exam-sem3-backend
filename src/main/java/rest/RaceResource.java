package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.RaceDTO;
import utils.EMF_Creator;
import facades.RaceFacade;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("race")
public class RaceResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final RaceFacade FACADE =  RaceFacade.getRaceFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "{\"msg\":\"Hello from race\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public String getAll() {
        List<RaceDTO> raceDTOS = FACADE.getAll();
        return GSON.toJson(raceDTOS);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getcars/{id}")
    public String getCars(@PathParam("id") long id) {
        List<CarDTO> carDTOS = FACADE.getCars(id);
        return GSON.toJson(carDTOS);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    @Path("/{id}")
    public String getRaceById(@PathParam("id") long id) {
        RaceDTO raceDTO = FACADE.getRaceById(id);
        return GSON.toJson(raceDTO);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/create")
    @RolesAllowed("admin")
    public String createRace(String data) {
        RaceDTO raceDTO = GSON.fromJson(data, RaceDTO.class);
        raceDTO = FACADE.createRace(raceDTO);
        return GSON.toJson(raceDTO);
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/update/{id}")
    @RolesAllowed("admin")
    public String updateRace(@PathParam("id") long id, String data) {
        RaceDTO raceDTO = GSON.fromJson(data, RaceDTO.class);
        raceDTO = FACADE.updateRace(id, raceDTO);
        return GSON.toJson(raceDTO);
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/delete/{id}")
    @RolesAllowed("admin")
    public String deleteRace(@PathParam("id") long id) {
        RaceDTO raceDTO = FACADE.deleteRace(id);
        return GSON.toJson(raceDTO);
    }
}
