package main.java.api;

import main.java.model.Stock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/stock")
public class StockRestService{
    private Stock stock = Stock.getInstanceClass();

    @GET
    @Path("/get/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getStock(@PathParam("stockId") String stockId) throws Exception{
        return stock.getStock(stockId);
    }

    @GET
    @Path("/getShopList/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getShopList(@PathParam("stockId") String stockId) throws Exception{
        return stock.getShoppingList(stockId);
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
            stock.updateListOfProducts_stockActivity(data.get("stockId").toString(), products);
        }
        catch(Exception e){
            // adi: handle exception
        }

        return "adi";
    }

    @PUT
    @Path("/purchase/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String purchaseProducts(String dataStr){
        JSONParser parser = new JSONParser();
        JSONObject data;
        JSONArray products;

        try {
            data = (JSONObject) parser.parse(dataStr);
            products = (JSONArray) parser.parse(data.get("products").toString());
            stock.purchaseProducts(data.get("stockId").toString(), products);
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

}





    /*
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
    */