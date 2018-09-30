package main.java.model;

import com.mongodb.*;
import main.java.api.Utils;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;
/*

/**
 * Created by user on 12/04/2018.
 */
public class ShoppingList {
    /*
    // should be one instance of ShoppingList class and stocks collection
    private static ShoppingList ourInstance = new ShoppingList();
    private static DBCollection shopList;
    private static Stock stockClass =  Stock.getInstanceClass();
*/


/** Public: **/
/*
    public ShoppingList()
    {
        // connect to mongo
        MongoClient mongoClient = new MongoClient("Localhost", 27017);
        // get DB
        DB database = mongoClient.getDB("shopal");
        // get collection
        shopList = database.getCollection("shopList");
    }

    public void reset(String shoppingListId) throws Exception{
        validationOfShoppingList(shoppingListId);
        JSONObject shoppingList = stockClass.getShoppingList(getStockId(shoppingListId));
        JSONArray products = Utils.getArrayJsonFromJsonByField(shoppingList, "products");

        for(Object item: products){
            if ( item instanceof JSONObject) {
                JSONObject product =  (JSONObject)item;
                try {
                    // get args
                    Integer productId = Integer.parseInt(product.get("productId").toString());
                    Integer available = Integer.parseInt(product.get("available").toString());
                    Integer limit = Integer.parseInt(product.get("limit").toString());
                    Integer quantity = ((limit - available) <0 ) ? 0 : (limit - available);

                    // send args to method
                    createOrupdateProduct(shoppingListId, productId, quantity);
                }
                catch(Exception e) {
                    int y = 7;
                }
            }
            else{
                int x =5;
            }
        }
    }

    public static ShoppingList getInstanceClass() {
        return ourInstance;
    }

    public static DBCollection getInstanceCollection() {
        return ourInstance.shopList;
    }

    public String getShoppingList(String shoppingListId) throws Exception {
        validationOfShoppingList(shoppingListId);
        BasicDBObject shoppingList = (BasicDBObject) (shopList.find(new BasicDBObject("_id", new ObjectId(shoppingListId)))).next();
        return shoppingList.toString();
    }

    // adi: check this one
    public List<Integer> getAllProductsId(String shoppingListId) throws Exception {
        validationOfShoppingList(shoppingListId);
        return Utils.getProductsId(shoppingListId, shopList);
    }

    // adi: check this one
    public void purchaseListOfProducts(String shoppingListId, JSONArray products) throws Exception {
        validationOfShoppingList(shoppingListId);
        updateListOfProducts(shoppingListId, products);
        stockClass.purchaseProducts(getStockId(shoppingListId), products);
    }
    // adi: check this one
    public void updateListOfProducts(String shoppingListId, JSONArray products) throws Exception {
        validationOfShoppingList(shoppingListId);

        // iterate products and update quantities
        for(Object item: products){
            if ( item instanceof JSONObject) {
                JSONObject product =  (JSONObject)item;
                try {
                    // get args
                    Integer productId = Integer.parseInt(product.get("productId").toString());
                    Integer quantity = Integer.parseInt(product.get("quantity").toString());

                    // send args to method
                    createOrupdateProduct(shoppingListId, productId, quantity);
                }
                catch(Exception e) {
                    int y = 7;
                }
            }
            else{
                int x =5;
            }
        }
    }
    // adi: check this one
    public void createOrupdateProduct(String shoppingListId, Integer productId, Integer quantity) throws Exception {
        validationOfShoppingList(shoppingListId);

        if (!is_productId_existInDB(shoppingListId, productId)) {
            createProduct(shoppingListId, productId, 0);
        }
        else {
            updateProduct(shoppingListId, productId, quantity);
        }
        // adi: return
    }
*/
/** Private: **/
/*
    private void validationOfShoppingList(String shoppingListId) throws Exception{
        if (!is_shoppingListId_existInDB(shoppingListId)) {
            throw new Exception("Invalid stock"); // error: stock not exist in DB
        }
    }

    private boolean is_shoppingListId_existInDB(String shoppingListId) {
        DBCursor cursor = shopList.find(new BasicDBObject("_id", new ObjectId(shoppingListId)));
        return (cursor.count() >= 1 );
    }

    private boolean is_productId_existInDB(String shoppingListId, Integer productId){
        DBCursor cursor = shopList.find(new BasicDBObject("_id", new ObjectId(shoppingListId)).append("items.productId", productId),
                new BasicDBObject("items", 1));
        return (cursor.count() >= 1 );
    }
    // adi: check this one
    private void createProduct(String shoppingListId, Integer productId, Integer quantity){
        DBObject listItem = new BasicDBObject("items",
                new BasicDBObject("productId", productId)
                        .append("quantity", quantity));

        DBObject query = new BasicDBObject("_id", new ObjectId(shoppingListId));

        shopList.update(query, new BasicDBObject("$push", listItem));
    }
    // adi: check this one
    private void updateProduct(String shoppingListId, Integer productId, Integer quantity) throws Exception{
        WriteResult res = shopList.update(new BasicDBObject("_id", new ObjectId(shoppingListId)).append("items.productId", productId),
                new BasicDBObject("$set", new BasicDBObject("items.$.quantity", quantity)));

        if(res.getN() == 0 ){
            throw new Exception("updateProduct failed");
        }
    }
    // adi: check this one
    private String getStockId(String shoppingListId){
        BasicDBObject currShoppingList = (BasicDBObject) (shopList.find(new BasicDBObject("_id", new ObjectId(shoppingListId)))).next();
        return currShoppingList.getString("stockId");
    }
*/
}


