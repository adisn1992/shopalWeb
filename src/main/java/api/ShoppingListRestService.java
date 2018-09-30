package main.java.api;

import main.java.model.ShoppingList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//@Path("/shoppingList")
public class ShoppingListRestService {
    /*
    private ShoppingList shopList = ShoppingList.getInstanceClass();

    @PUT
    @Path("/update/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateProductsQuantities(String dataStr){
        JSONObject data = Utils.stringToJson(dataStr);
        JSONArray products = Utils.getArrayJsonFromJsonByField(data, "products");
        String shoppingListId = data.get("shoppingListId").toString();

        try {
            shopList.updateListOfProducts(shoppingListId, products);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "adii";
    }

    @PUT
    @Path("/purchase/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String purchaseProducts(String dataStr){
        JSONObject data = Utils.stringToJson(dataStr);
        JSONArray products = Utils.getArrayJsonFromJsonByField(data, "products");
        String shoppingListId = data.get("shoppingListId").toString();

        try {
            shopList.purchaseListOfProducts(shoppingListId, products);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "adi";
    }
*/
}
