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


    @PUT
    @Path("/validation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String bbb(String data){
        String x = data;
        int b = 5;
        return "adi";
    }


}


