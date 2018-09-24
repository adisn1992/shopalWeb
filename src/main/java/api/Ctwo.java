package main.java.api;

import com.sun.istack.internal.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mabat")
public class Ctwo {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/users")
    public void users() {}//return "hello user adiiiiiii";

    @POST
    @Path("/adi")
    @Consumes("text/plain")
    public void postClichedMessage(String message) {
        // Store the message
    }

    @GET
    @Path("/product/{barcode}")
    @Produces({"application/json"})
    public int getProductByBarcode(@PathParam("barcode") int barcode) {
        int x = 5;
        return x;
    }

/*

    @POST
    @Path(value = "/validation")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({//
            @ApiResponse(code = 204, message = "All validations pass"), //
            @ApiResponse(code = 400, message = "Found violations in validation", responseContainer = "Set",
                    response = Violation.class)//
    })
    default Response validate(@NotNull final T obj) {
        final Set<ConstraintViolation<T>> constraintViolations = getValidator().validate(obj, AllValidations.class);

        if (constraintViolations.isEmpty()) {
            return Response.noContent().build();
        }

        throw new ConstraintViolationException(constraintViolations);
    }
*/

}


