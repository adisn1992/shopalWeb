package main.java.resources;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import main.java.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by user on 05/05/2018.
 */

@Path("/shopal")
public class Controller {



    public Controller() {
        // Connection to mongoDB
        //MongoClient mongoClient = new MongoClient("Localhost", 27017);
        // Access to shopalDB


        // EXP
        //productScan("1", "1");
        //users.connectUser("abc");
        //stocks.updateProduct("1", "5", "3", "99");
        //stocks.addProduct("1", "33");
        // END EXP
    }

    @GET
    @Path("/product/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public int getProductByBarcode(@PathParam("barcode") int barcode){

        // 1. Make engine singleton
        // 2. Create new method in engine (input -> barcode | output -> product)
        //return new Engine().getProductByBarcode(barcode);

        /** START - Need to replace this code with actual call to engine */

        int x = 5;
        return x;

        /*
        Product product = new Product();
        product.setCount(1);
        product.setDate(new Date());
        product.setProductName("example product");
        product.setSerialNumber(barcode);
        return product;
        */
        /** END - Need to replace this code with actual call to engine */

    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/users")
    public String users(){
        return "hello user adi";
    }


}


