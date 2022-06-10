package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Tmp2DTO;
import facades.Tmp2Facade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("tmp2")
public class Tmp2Resource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final Tmp2Facade FACADE =  Tmp2Facade.getTmp2Facade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String hello() {
        return "{\"msg\":\"Hello from tmp2\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/all")
    public String getAll() {
        List<Tmp2DTO> tmp2DTOs = FACADE.getAll();
        return GSON.toJson(tmp2DTOs);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public String getTmp2ById(@PathParam("id") long id) {
        Tmp2DTO tmp2DTO = FACADE.getTmp2ById(id);
        return GSON.toJson(tmp2DTO);
    }



}
