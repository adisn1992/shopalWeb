package main.java.api;

import main.java.model.Stock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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


    @GET
    @Path("/get/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getStock(@PathParam("stockId") String stockId) throws Exception{
        return stock.getStock(stockId);
        //stock.createOrupdateProduct(stockId, productId, 1, 0 );
        //return "adi";
        // adi: return
    }

    @PUT
    @Path("/update/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateProductsQuantities(String dataStr){
        JSONParser parser = new JSONParser();
        JSONObject data;
        JSONArray products;

        try {
            data = (JSONObject) parser.parse(dataStr);
            products = (JSONArray) parser.parse(data.get("products").toString());
            stock.updateListOfProducts(data.get("stockId").toString(), products);
        }
        catch(Exception e){
            // adi: handle exception
        }

        return "adi";
    }

    @PUT
    @Path("/remove/")
    @Produces(MediaType.TEXT_PLAIN)
    public String removeProduct(String dataStr){
        JSONParser parser = new JSONParser();
        JSONObject data;
        String response= null;

        try {
            data = (JSONObject) parser.parse(dataStr);
            String stockId = data.get("stockId").toString();
            Integer productId = Integer.parseInt(data.get("productId").toString());

            stock.removeProductById(stockId, productId);
            response = productId.toString();
        }
        catch(Exception e){
            // adi: handle exception
            response = null;
        }

        return response;
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