package main.java.api;

import main.java.model.Product;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;


@Path("/product")
public class ProductRestService {
    private Product product = Product.getInstanceClass();

    @GET
    @Path("/getImg/{productId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getImg(@PathParam("productId") Long productId) {
        try{
            return product.getImg(productId);
        }
        catch(ValidationException e){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/getImgs/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getImages(@PathParam("stockId") String stockId) {
        try{
            return Utils.getImgsAndNeamedJson(product.getImagesAndNames(stockId));
        }
        catch(ValidationException e){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
