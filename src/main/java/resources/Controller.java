package main.java.resources;

import main.java.model.Engine;
import main.java.model.Product;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * Created by user on 05/05/2018.
 */
@Path("/shopal")
public class Controller {

    @GET
    @Path("/product/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductByBarcode(@PathParam("barcode") int barcode){

        // 1. Make engine singleton
        // 2. Create new method in engine (input -> barcode | output -> product)
        //return new Engine().getProductByBarcode(barcode);

        /** START - Need to replace this code with actual call to engine */
        Product product = new Product();
        product.setCount(1);
        product.setDate(new Date());
        product.setProductName("example product");
        product.setSerialNumber(barcode);
        return product;
        /** END - Need to replace this code with actual call to engine */
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/users")
    public String users(){
        return "hello user adi";
    }

}
