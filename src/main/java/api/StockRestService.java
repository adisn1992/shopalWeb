package main.java.api;

import main.java.model.Product;
import main.java.model.Stock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;

@Path("/stock")
public class StockRestService{
    private Stock stock = Stock.getInstanceClass();
    private Product product = Product.getInstanceClass();

    @GET
    @Path("/get/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getStock(@PathParam("stockId") String stockId) throws Exception{
        try {
            return stock.getStock(stockId);
        }
        catch(ValidationException e){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/getShopList/{stockId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getShopList(@PathParam("stockId") String stockId) throws Exception{
        try {
            return stock.getShoppingList(stockId);
        }
        catch(ValidationException e){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("/update/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public void updateProductsQuantities(String dataStr){
        JSONParser parser = new JSONParser();
        JSONObject data;
        JSONArray products;

        try {
            data = (JSONObject) parser.parse(dataStr);
            products = (JSONArray) parser.parse(data.get("products").toString());
            stock.updateProducts(data.get("stockId").toString(), products);
        }
        catch(Exception e){
            if(e instanceof ValidationException){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            else if(e instanceof ParseException){
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }
        }
    }

    @PUT
    @Path("/purchase/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public void purchaseProducts(String dataStr){
        JSONParser parser = new JSONParser();
        JSONObject data;
        JSONArray products;

        try {
            data = (JSONObject) parser.parse(dataStr);
            products = (JSONArray) parser.parse(data.get("products").toString());
            stock.purchaseProducts(data.get("stockId").toString(), products);
        }
        catch(Exception e){
            if(e instanceof ValidationException){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            else if(e instanceof ParseException){
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }
        }
    }

    @PUT
    @Path("/remove/")
    @Produces(MediaType.TEXT_PLAIN)
    public void removeProduct(String dataStr){
        JSONParser parser = new JSONParser();
        JSONObject data;

        try {
            data = (JSONObject) parser.parse(dataStr);
            String stockId = data.get("stockId").toString();
            Long productId = Long.parseLong(data.get("productId").toString());

            stock.removeProductById(stockId, productId);
        }
        catch(Exception e){
            if(e instanceof ValidationException){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            else if(e instanceof ParseException){
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }
        }
    }

    @POST
    @Path("/addProduct/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public void addProduct(String dataStr){
        JSONParser parser = new JSONParser();
        JSONObject data;

        try {
            data = (JSONObject) parser.parse(dataStr);
            String stockId = data.get("stockId").toString();
            Long productId = Long.parseLong(data.get("productId").toString());
            Integer limit = Integer.parseInt(data.get("limit").toString());
            Integer available = Integer.parseInt(data.get("available").toString());
            Integer toPurchase = ((limit - available) <0) ? 0 : (limit - available);

            stock.createOrupdateProduct(stockId, productId, available, limit, toPurchase);
            product.addProduct(productId);
            }
        catch(Exception e){
            if(e instanceof ValidationException){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        }
    }

    @PUT
    @Path("/productToTrash/")
    @Produces(MediaType.TEXT_PLAIN)
    public void productToTrash(String dataStr){
        JSONParser parser = new JSONParser();
        JSONObject data;

        try {
            data = (JSONObject) parser.parse(dataStr);
            String stockId = data.get("stockId").toString();
            Long productId = Long.parseLong(data.get("productId").toString());

            stock.updateAfterScan(stockId, productId);
        }
        catch(Exception e){
            if(e instanceof ValidationException){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            else if(e instanceof ParseException){
                throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
            }
        }
    }

    @GET
    @Path("/isProductExistInStock/{stockId}/{productId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String isProductExistInStock(@PathParam("stockId") String stockId, @PathParam("productId") Long productId) {
        try {
            return (stock.checkIfProductExistInStock(stockId, productId)) ? "true" : "false";
        }
        catch(ValidationException e){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}