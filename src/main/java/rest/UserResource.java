package rest;

import com.google.gson.Gson;
import dtos.UserDTO;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import facades.UserFacade;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class UserResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;
    Gson GSON = new Gson();
    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is set up
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {
       List<UserDTO> userDTOs = FACADE.getAll();
       return GSON.toJson(userDTOs);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed({"user"})
    public String getFromUser() {
        String thisUser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisUser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisUser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisUser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("userinfo")
    @RolesAllowed({"user", "admin"})

    public String getUserName() {
        String thisUser = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        User currentUser = em.find(User.class, thisUser);
        UserDTO userDTO = new UserDTO(currentUser);
        return GSON.toJson(userDTO);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("newuser")
    public String addNewUser(String data) {
        System.out.println("data" + data);
        UserDTO userDTO = GSON.fromJson(data, UserDTO.class);
        User user = userDTO.toUser();

        User user1 = UserFacade.getUserFacade(EMF).registerNewUser(user);
        return GSON.toJson(user1);
    }

}