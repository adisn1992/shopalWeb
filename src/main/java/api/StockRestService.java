package main.java.api;

import main.java.model.Stock;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/stock")
//adi: add getters - stockid, products in stocks, etc..
public class StockRestService{

    private Stock stock = Stock.getInstanceClass();
    //private MongoCollection<Document> stocks = stock.getStocksCollection();

    /** Public: **/
    // adi: exp
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/users")
    public String users(){
        return "hello from stocks";
    }


    //adi: should be post
    @GET
    @Path("/newProduct/{stockId}/{productId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addProduct(@PathParam("stockId") String stockId, @PathParam("productId") Integer productId) throws Exception{
        stock.createOrupdateProduct(stockId, productId, 1, 0 );
        return "adi";
        // adi: return
    }

    //adi: should be post
    @GET
    @Path("/updateOrCreateProduct/{stockId}/{productId}/{available}/{limit}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addProduct(@PathParam("stockId") String stockId, @PathParam("productId") Integer productId,
                             @PathParam("available") Integer available,@PathParam("limit") Integer limit) throws Exception{
        stock.createOrupdateProduct(stockId, productId, available, limit );
        return "adi";
        // adi: return
    }
}