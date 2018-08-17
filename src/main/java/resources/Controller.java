package main.java.resources;

/**
 * Created by user on 05/05/2018.
 */

/*

@Path("/shopal")
public class Controller {

    @GET
    @Path("/product/{barcode}")
    @Produces(PageAttributes.MediaType.APPLICATION_JSON)
    public Product getProductByBarcode(@PathParam("barcode") int barcode){

        // 1. Make engine singleton
        // 2. Create new method in engine (input -> barcode | output -> product)
        //return new Engine().getProductByBarcode(barcode);

        /** START - Need to replace this code with actual call to engine */

/*
        Product product = new Product();
        product.setCount(1);
        product.setDate(new Date());
        product.setProductName("example product");
        product.setSerialNumber(barcode);
        return product;
        /** END - Need to replace this code with actual call to engine */

//    }

  //  @GET
    //@Produces(PageAttributes.MediaType.TEXT_PLAIN)
    //@Path("/users")
    //public String users(){
     //   return "hello user adi";
   // }


//}

