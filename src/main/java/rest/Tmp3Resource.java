package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Tmp3DTO;
import facades.Tmp3Facade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("tmp3")
public class Tmp3Resource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final Tmp3Facade FACADE =  Tmp3Facade.getTmp3Facade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "{\"msg\":\"Hello from tmp3\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public String getAll() {
        List<Tmp3DTO> tmp3DTOs = FACADE.getAll();
        return GSON.toJson(tmp3DTOs);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public String getTmp3ById(@PathParam("id") long id) {
        Tmp3DTO tmp3DTO = FACADE.getTmp3ById(id);
        return GSON.toJson(tmp3DTO);
    }



}
