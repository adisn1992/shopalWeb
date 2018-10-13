package main.java.api;

import main.java.model.User;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.ValidationException;


@Path("/user")
public class UserRestService {
    private User user = User.getInstanceClass();

    @GET
    @Path("/addUser/{firstName}/{lastName}/{email}/")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(@PathParam("firstName") String firstName, @PathParam("lastName") String lastName , @PathParam("email") String email) throws ValidationException {
        String userEmail = email;

        try {
            return user.addUserAndGetStockId(firstName, lastName, email);
        } catch (ValidationException e) {
            return user.getValue(userEmail, "stockId");
        }
    }
}

