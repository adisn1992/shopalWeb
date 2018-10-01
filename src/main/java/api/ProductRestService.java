package main.java.api;

import main.java.model.Product;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/product")
public class ProductRestService {
    private Product product = Product.getInstanceClass();

    @GET
    @Path("/getImg/{productId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getImg(@PathParam("productId") Integer productId) throws Exception{
        return product.getImg(productId);
    }

    @GET
    @Path("/getImgs/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getImages(@PathParam("stockId") String stockId) throws Exception{
        return Utils.getImgsJson(product.getImages(stockId));
    }
}
