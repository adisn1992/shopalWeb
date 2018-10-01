package main.java.model;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.java.api.Utils;
import org.json.*;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Stock{
    // should be one instance of stock class and stocks collection
    public static final int NO_CHANGE = -1;
    private static Stock ourInstance = new Stock();
    private static DBCollection stocks;


/** Public: **/
    // listActivity = shopping list activity
    public Stock()
    {
        // connect to mongo
        MongoClient mongoClient = new MongoClient("Localhost", 27017);
        // get DB
        DB database = mongoClient.getDB("shopal");
        // get collection
        stocks = database.getCollection("stocks");
    }

    public static Stock getInstanceClass() {
        return ourInstance;
    }

    public String getStock(String stockId) throws Exception {
        validationOfStock(stockId);
        // reset toPurchase
        resetShoppingList(stockId);

        BasicDBObject curr = (BasicDBObject) (stocks.find(new BasicDBObject("_id", new ObjectId(stockId)))).next();
        return curr.toString();
    }

    public String getShoppingList(String stockId) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject stock = (JSONObject) parser.parse(getStock(stockId));

        JSONArray products = (JSONArray) parser.parse(stock.get("items").toString());
        Iterator<JSONObject> iter = products.iterator();

        // iterate over products and removes all products that (toPurchase == 0)
        while (iter.hasNext()) {
            JSONObject product = iter.next();
            if(Integer.parseInt(product.get("toPurchase").toString()) == 0){
                iter.remove();
            }
        }

        stock.remove("items");
        stock.put("items", products);

        return stock.toString();
    }

    public void removeProductById(String stockId, Integer productId) throws Exception {
        validationOfStock(stockId);
        removeProduct(stockId, productId);
    }

    public void updateListOfProducts_stockActivity(String stockId, JSONArray products) throws Exception{
        validationOfStock(stockId);
        // iterate products and update quantities
        for(Object item: products){
            if ( item instanceof JSONObject) {
                JSONObject product =  (JSONObject)item;
                try {
                    Integer productId = Integer.parseInt(product.get("productId").toString());
                    updateProductValue(stockId, productId, "available", Integer.parseInt(product.get("available").toString()));
                    updateProductValue(stockId, productId, "limit", Integer.parseInt(product.get("limit").toString()));
                }
                catch(Exception e) {
                    int y = 7;
                }
            }
            else{
                int x =5;
            }
        }

        resetShoppingList(stockId);
    }

    public List<Integer> getAllProductsId_stockActivity(String stockId) throws Exception{
        validationOfStock(stockId);
        return Utils.getProductsId(stockId, stocks);
    }

    public List<Integer> getAllProductsId_listActivity(String stockId) throws Exception {
        validationOfStock(stockId);
        List<Integer> products = getAllProductsId_stockActivity(stockId);

        Iterator<Integer> iter = products.iterator();
        while (iter.hasNext()) {
            Integer product = iter.next(); // must be called before you can call i.remove()
            if(!is_productId_existInShopList(stockId, (Integer)product)){
                iter.remove();
            }
        }

        return products;
    }

    // adi: increase available
    public void purchaseProducts(String stockId , JSONArray products) throws Exception {
        validationOfStock(stockId);

        for(Object item: products){
            if ( item instanceof JSONObject) {
                JSONObject product =  (JSONObject)item;
                try {
                    // get args
                    Integer productId = Integer.parseInt(product.get("productId").toString());
                    Integer purchased = Integer.parseInt(product.get("purchased").toString());

                    Integer available = getValueOfProduct(stockId, productId, "available");

                    updateProductValue(stockId, productId, "available", (available + purchased));
                    resetToPurchase(stockId, productId);
                }
                catch(Exception e) {
                    int y = 7;
                }
            }

        }
        int x=5;
    }



    public static DBCollection getInstanceCollection() {
        return ourInstance.stocks;
    }

    public String newStock(String name){
        return createStock(name);
    }

    public void deleteStockById(String stockId) throws Exception {
        validationOfStock(stockId);
        deleteStock(stockId);
    }

    // update exist product after we scan it (throwing to can) -> available = available -1
    public void updateAfterScan(String stockId, Integer productId) throws Exception {
        validationOfStock(stockId);

        if (!is_productId_exist(stockId, productId)) {
            throw new Exception("product doesn't exist in stock"); // productId not exist in DB
        }

        // getting the available quantity of products:
        Integer available = getValueOfProduct(stockId, productId, "available");
        Integer limit = getValueOfProduct(stockId, productId, "limit");

        if (available == 0) {
            throw new Exception("Product doesn't exist in stock"); // product not exist in stock
        }

        // update product :
        available = ((available-1) < 0) ? 0 : (available-1);

        updateProductValue(stockId, productId, "available", available);
        resetToPurchase(stockId, productId);
    }

    // update or create product with wanted quantities -> available, limit
    // for new product it should be available = 1, limit = 0;
    public void createOrupdateProduct(String stockId, Integer productId, Integer available, Integer limit, Integer toPurchase) throws Exception {
        validationOfStock(stockId);

        if (!is_productId_exist(stockId, productId)) {
            createProduct(stockId, productId, 1, 0);
        }
        else {
            if(available != NO_CHANGE){
                updateProductValue(stockId, productId, "available", available);
            }

            if(limit != NO_CHANGE){
                updateProductValue(stockId, productId, "limit", limit);
            }

            if(toPurchase != NO_CHANGE){
                updateProductValue(stockId, productId, "toPurchase", toPurchase);
            }
        }
        // adi: return
    }


/** Private: **/
    // TODO: to check method
    private String createStock(String name){
        BasicDBObject doc = new BasicDBObject("name", name).append("items", null);
        stocks.insert(doc);
        ObjectId id = (ObjectId)doc.get( "_id" );

        return id.toString();
    }
    // TODO: to check method
    private void validationOfStock(String stockId) throws Exception{
        if (!is_stockId_existInDB(stockId)) {
            throw new Exception("Invalid stock"); // error: stock not exist in DB
        }
    }
    // TODO: to check method
    private void deleteStock(String stockId){
        stocks.remove(new BasicDBObject("_id", new ObjectId(stockId)));
    }

    private void removeProduct(String stockId, Integer productId){
        BasicDBObject query = new BasicDBObject("_id", new ObjectId(stockId));

        BasicDBObject fields = new BasicDBObject("items",
                new BasicDBObject( "productId", productId));

        BasicDBObject update = new BasicDBObject("$pull",fields);

        stocks.update( query, update );
    }

    private void createProduct(String stockId, Integer productId, Integer available, Integer limit){
        Integer toPurchase = ((limit-available) < 0) ? 0 : (limit-available);

        DBObject listItem = new BasicDBObject("items",
                new BasicDBObject("productId", productId)
                        .append("available", available)
                        .append("limit", limit)
                        .append("toPurchase", toPurchase));

        DBObject query = new BasicDBObject("_id", new ObjectId(stockId));

        stocks.update(query, new BasicDBObject("$push", listItem));
    }

    private boolean is_stockId_existInDB(String stockId) {
        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)));
        return (cursor.count() >= 1);
    }

    private boolean is_productId_exist(String stockId, Integer productId){
        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)).append("items.productId", productId),
                new BasicDBObject("items", 1));
        return (cursor.count() >= 1 );
    }

    private boolean is_productId_existInShopList(String stockId, Integer productId) throws Exception {
        return (getValueOfProduct(stockId, productId, "toPurchase") > 0 );
    }

    private Integer getValueOfProduct(String stockId, Integer productId, String fieldName) throws Exception {
        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)).append("items.productId", productId),
                new BasicDBObject("items", 1));

        // get cursor (next = curr)
        BasicDBObject curr = (BasicDBObject) cursor.next();
        // get list array as json
        JSONParser parser = new JSONParser();
        JSONArray queryArray = (JSONArray) parser.parse(curr.getString("items"));

        // iterate queryArray and return the value
        for(Object item: queryArray){
            if ( item instanceof JSONObject) {
                JSONObject product =  (JSONObject)item;
                try {
                    if (Integer.parseInt(product.get("productId").toString()) == productId){
                        return Integer.parseInt(product.get(fieldName).toString());
                    }
                }
                catch(Exception e) {
                    throw new Exception("01_getValueOfProduct failed");
                }
            }
        }

        throw new Exception("02_getValueOfProduct failed");
    }

    private void updateProductValue(String stockId, Integer productId, String field, Integer value) throws Exception{
        WriteResult res = stocks.update(new BasicDBObject("_id", new ObjectId(stockId)).append("items.productId", productId),
                new BasicDBObject("$set", new BasicDBObject(("items.$." + field), value)));

        if(res.getN() == 0 ){ throw new Exception("updateProduct failed:" + field); }
    }

    private void resetShoppingList(String stockId) throws Exception {
        validationOfStock(stockId);
        List<Integer> products = getAllProductsId_stockActivity(stockId);

        for(Integer product : products){
            resetToPurchase(stockId, product);
        }
    }

    private void resetToPurchase(String stockId, Integer productId) throws Exception {
        Integer limit = getValueOfProduct(stockId, productId, "limit");
        Integer available = getValueOfProduct(stockId, productId, "available");

        Integer toPurchase = ((limit-available) < 0) ? 0 : (limit-available);

        updateProductValue(stockId, productId, "toPurchase", toPurchase);
    }

}