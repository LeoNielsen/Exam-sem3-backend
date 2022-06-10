package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Tmp1DTO;
import utils.EMF_Creator;
import facades.Tmp1Facade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("tmp1")
public class Tmp1Resource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final Tmp1Facade FACADE =  Tmp1Facade.getTmp1Facade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "{\"msg\":\"Hello from tmp1\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public String getAll() {
        List<Tmp1DTO> tmp1DTOs = FACADE.getAll();
        return GSON.toJson(tmp1DTOs);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public String getTmp1ById(@PathParam("id") long id) {
        Tmp1DTO tmp1DTO = FACADE.getTmp1ById(id);
        return GSON.toJson(tmp1DTO);
    }



}
