package main.java.model;
import org.json.*;
import com.mongodb.*;
import org.bson.types.ObjectId;


public class Stock{
    // should be one instance of stock class and stocks collection
    private static Stock ourInstance = new Stock();
    private static DBCollection stocks;


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

    public String newStock(String name){
        return createStock(name);
    }

    public void deleteStockById(String stockId) throws Exception {
        validationOfStock(stockId);
        deleteStock(stockId);
    }

    public static DBCollection getInstanceCollection() {
        return ourInstance.stocks;
    }

    // update or create product with wanted quantities -> available, limit
    // for new product it should be available = 1, limit = 0;
    public void createOrupdateProduct(String stockId, Integer productId, Integer available, Integer limit) throws Exception {
        validationOfStock(stockId);

        if (!is_productId_existInDB(stockId, productId)) {
            createProduct(stockId, productId, 1, 0);
        }
        else {
            updateProduct(stockId, productId, available, limit);
        }
        // adi: return
    }

    // update exist product after we scan it (throwing to can) -> available = available -1
    public void updateAfterScan(String stockId, Integer productId) throws Exception {
        Integer available;
        Integer limit;

        validationOfStock(stockId);

        if (!is_productId_existInDB(stockId, productId)) {
            throw new Exception("product doesn't exist in stock"); // productId not exist in DB
        }

        // getting the available quantity of products:
        available = getValueOfProduct(stockId, productId, "available");
        limit = getValueOfProduct(stockId, productId, "limit");

        if (available == 0) {
            throw new Exception("Product doesn't exist in stock"); // product not exist in stock
        }

        // update product : available = available - 1
        if(--available < 0){
            available = 0;
        }

        updateProduct(stockId, productId, available, limit);
        // adi: return
    }


    /** Private: **/
    private String createStock(String name){
        BasicDBObject doc = new BasicDBObject("name", name).append("list", null);
        stocks.insert(doc);
        ObjectId id = (ObjectId)doc.get( "_id" );

        return id.toString();
    }

    private void validationOfStock(String stockId) throws Exception{
        if (!is_stockId_existInDB(stockId)) {
            throw new Exception("Invalid stock"); // error: stock not exist in DB
        }
    }

    private void deleteStock(String stockId){
        stocks.remove(new BasicDBObject("_id", new ObjectId(stockId)));
    }

    private void createProduct(String stockId, Integer productId, Integer available, Integer limit){
        DBObject listItem = new BasicDBObject("list",
                                new BasicDBObject("productId", productId)
                                        .append("available", available)
                                        .append("limit", limit));

        DBObject query = new BasicDBObject("_id", new ObjectId(stockId));

        stocks.update(query, new BasicDBObject("$push", listItem));
    }

    private void updateProduct(String stockId, Integer productId, Integer available, Integer limit) throws Exception{
        WriteResult res = stocks.update(new BasicDBObject("_id", new ObjectId(stockId)).append("list.productId", productId),
                new BasicDBObject("$set", new BasicDBObject("list.$.available", available).append("list.$.limit", limit)));

        if(res.getN() == 0 ){
            throw new Exception("updateProduct failed");
        }
    }

    private boolean is_stockId_existInDB(String stockId) {
        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)));
        return (cursor.count() >= 1 ) ? true : false;
    }

    private boolean is_productId_existInDB(String stockId, Integer productId){
        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)).append("list.productId", productId),
                new BasicDBObject("list", 1));
        return (cursor.count() >= 1 ) ? true : false;
    }

    private Integer getValueOfProduct(String stockId, Integer productId, String fieldName) throws Exception {
        DBCursor cursor = stocks.find(new BasicDBObject("_id", new ObjectId(stockId)).append("list.productId", productId),
                new BasicDBObject("list", 1));

        // get cursor (next = curr)
        BasicDBObject curr = (BasicDBObject) cursor.next();
        // get list array as json
        JSONArray queryArray = new JSONArray(curr.getString("list"));

        // iterate queryArray and return the value
        for (int i = 0; i < queryArray.length(); i++) {
            JSONObject item  = queryArray.getJSONObject(i);
            if ((Integer) item.get("productId") == productId){
                return (Integer) item.get(fieldName);
            }
        }

        throw new Exception("getValueOfProduct failed");
    }
}



//public:
// don't think we need this
    /*
    // Add a new product to user's "stock" | UI: no need to update shopList (limit = 0)
    public String addProduct(String stockId, Integer productId) throws Exception{
        validationOfStock(stockId);

        if (is_productId_existInDB(stockId, productId)) {
            throw new Exception("product already exist in stock"); // productId exist in DB
        }

        // update stock with new productId -> default: available = 1, limit = 0
        updateProduct(stockId, productId,1, 0);

        // adi: need to return something else
        // adi: return
        return "post";
    }
    */