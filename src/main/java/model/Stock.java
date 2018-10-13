package main.java.model;

import main.java.api.Utils;

import com.mongodb.*;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Stock{
    // should be one instance of stock class and stocks collection
    private static Stock ourInstance = new Stock();
    private static DBCollection stocks;

    public static final int NO_CHANGE = -1;

/** Public: **/
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

    public String getStock(String stockId) throws ValidationException, ParseException  {
        validationOfStock(stockId);
        refreshShoppingList(stockId);

        BasicDBObject curr = (BasicDBObject) (stocks.find(new BasicDBObject("_id", new ObjectId(stockId)))).next();
        return curr.toString();
    }

    public String getShoppingList(String stockId) throws ValidationException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject stock = null;
        JSONArray products = null;

        stock = (JSONObject) parser.parse(getStock(stockId));
        products = (JSONArray) parser.parse(stock.get("items").toString());

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

    public void removeProductById(String stockId, Long productId) throws ValidationException {
        validationOfStock(stockId);
        removeProduct(stockId, productId);
    }

    public void updateProducts(String stockId, JSONArray products) throws ValidationException, ParseException {
        validationOfStock(stockId);

        // iterate products and update quantities
        for(Object item: products){
            JSONObject product =  (JSONObject)item;

            Long productId = Long.parseLong(product.get("productId").toString());
            updateProductValue(stockId, productId, "available", Integer.parseInt(product.get("available").toString()));
            updateProductValue(stockId, productId, "limit", Integer.parseInt(product.get("limit").toString()));
        }

        refreshShoppingList(stockId);
    }

    public List<Long> getAllProductsId(String stockId) throws ValidationException{
        validationOfStock(stockId);
        return Utils.getProductsId(stockId, stocks);
    }

    public void purchaseProducts(String stockId , JSONArray products) throws ValidationException, ParseException  {
        validationOfStock(stockId);

        for(Object item: products){
            JSONObject product =  (JSONObject)item;

            // get args
            Long productId = Long.parseLong(product.get("productId").toString());
            Integer purchased = Integer.parseInt(product.get("purchased").toString());

            Integer available = getValueOfProduct(stockId, productId, "available");

            updateProductValue(stockId, productId, "available", (available + purchased));
            resetToPurchase(stockId, productId);
        }
    }

    public String newStock(String name){
        return createStock(name);
    }

    // update exist product after we scan it (throwing to can) -> available = available -1
    public void updateAfterScan(String stockId, Long productId) throws ValidationException ,ParseException  {
        validationOfStock(stockId);

        if (!is_productId_exist(stockId, productId)) {
            throw new ValidationException("product doesn't exist in stock"); // error: stock not exist in DB
        }

        // get available quantity
        Integer available = getValueOfProduct(stockId, productId, "available");
        // available = available - 1
        available = ((available-1) < 0) ? 0 : (available-1);
        // update available
        updateProductValue(stockId, productId, "available", available);
        // reset shopping list
        resetToPurchase(stockId, productId);
    }

    // update or create product with wanted quantities -> available, limit
    // for new product it should be available = 1, limit = 0;
    public void createOrupdateProduct(String stockId, Long productId, Integer available, Integer limit, Integer toPurchase) throws ValidationException {
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
    }

    public boolean checkIfProductExistInStock(String stockId, Long productId) throws ValidationException{
        validationOfStock(stockId);

        return is_productId_exist(stockId, productId);
    }

/** Private: **/
    private void validationOfStock(String stockId) throws ValidationException {
        if (!is_stockId_existInDB(stockId)) {
            throw new ValidationException("Invalid stock");
        }
    }

    private void createProduct(String stockId, Long productId, Integer available, Integer limit){
        Integer toPurchase = ((limit-available) < 0) ? 0 : (limit-available);

        DBObject listItem = new BasicDBObject("items",
                new BasicDBObject("productId", productId)
                        .append("available", available)
                        .append("limit", limit)
                        .append("toPurchase", toPurchase));

        DBObject query = new BasicDBObject("_id", new ObjectId(stockId));

        stocks.update(query, new BasicDBObject("$push", listItem));
    }

    private void removeProduct(String stockId, Long productId){
        BasicDBObject query = new BasicDBObject("_id", new ObjectId(stockId));

        BasicDBObject fields = new BasicDBObject("items",
                new BasicDBObject( "productId", productId));

        BasicDBObject update = new BasicDBObject("$pull",fields);

        stocks.update( query, update );
    }

    private void updateProductValue(String stockId, Long productId, String field, Integer value){
        stocks.update(new BasicDBObject("_id", new ObjectId(stockId)).append("items.productId", productId),
                new BasicDBObject("$set", new BasicDBObject(("items.$." + field), value)));
    }

    private void refreshShoppingList(String stockId) throws ValidationException, ParseException {
        List<Long> products = getAllProductsId(stockId);

        for(Long product : products){
            resetToPurchase(stockId, product);
        }
    }

    private void resetToPurchase(String stockId, Long productId) throws ValidationException,ParseException {
        Integer limit = getValueOfProduct(stockId, productId, "limit");
        Integer available = getValueOfProduct(stockId, productId, "available");

        Integer toPurchase = ((limit-available) < 0) ? 0 : (limit-available);

        updateProductValue(stockId, productId, "toPurchase", toPurchase);
    }

    private boolean is_stockId_existInDB(String stockId) {
        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)));
        return (cursor.count() >= 1);
    }

    private boolean is_productId_exist(String stockId, Long productId){
        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)).append("items.productId", productId),
                new BasicDBObject("items", 1));
        return (cursor.count() >= 1 );
    }

    private String createStock(String name) {
        BasicDBObject doc = new BasicDBObject("name", name).append("items", new ArrayList<>());
        stocks.insert(doc);
        ObjectId id = (ObjectId)doc.get( "_id" );

        return id.toString();
    }

    private Integer getValueOfProduct(String stockId, Long productId, String fieldName) throws ValidationException, ParseException {
        if (!is_productId_exist(stockId, productId)) {
            throw new ValidationException("Invalid product");
        }

        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)).append("items.productId", productId),
                new BasicDBObject("items", 1));

        // get cursor (next = curr)
        BasicDBObject curr = (BasicDBObject) cursor.next();
        // get list array as json
        JSONParser parser = new JSONParser();
        JSONArray queryArray = null;

        queryArray = (JSONArray) parser.parse(curr.getString("items"));


        // iterate queryArray and return the value
        for (Object item : queryArray) {
            JSONObject product = (JSONObject) item;
            if (Long.parseLong(product.get("productId").toString()) == productId) {
                return Integer.parseInt(product.get(fieldName).toString());
            }
        }

        throw new ValidationException("Invalid product");
    }
}




