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
        return product.getImgUrl(productId);
    }

    @GET
    @Path("/getImgs_stock/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getImgs_ActivityStock(@PathParam("stockId") String stockId) throws Exception{
        return Utils.getImgsJson( product.getImgs_stock(stockId));
    }

    @GET
    @Path("/getImgs_shoppingList/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getImgs_ActivityShoppingList(@PathParam("stockId") String stockId) throws Exception{
        return Utils.getImgsJson(product.getImgs_shoppingList(stockId));
    }
}
