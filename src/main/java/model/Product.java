package main.java.model;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/04/2018.
 */

// pay attention: productId = barcode although there is _id field
public class Product {
    // should be one instance of Product class and stocks collection
    private static Product ourInstance = new Product();
    private static DBCollection products;
    private static Stock stockClass =  Stock.getInstanceClass();

/** Public: **/
    public Product()
    {
        // connect to mongo
        MongoClient mongoClient = new MongoClient("Localhost", 27017);
        // get DB
        DB database = mongoClient.getDB("shopal");
        // get collection
        products = database.getCollection("products");
    }

    public static Product getInstanceClass() {
        return ourInstance;
    }

    public static DBCollection getInstanceCollection() {
        return ourInstance.products;
    }

    public String getImg(Integer productId) throws Exception {
        validationOfProduct(productId);
        return getImgByProduct(productId);
    }

    public List<String> getImages(String stockId) throws Exception {
        List<Integer> productsId = stockClass.getAllProductsId_stockActivity(stockId);
        return  getImagesByListOfProducts(productsId);
    }


/** Private: **/

    public void setProduct(){
        // TODO
    }

    private void validationOfProduct(Integer productId) throws Exception{
        if (!is_productId_existInDB(productId)) {
            throw new Exception("Invalid product"); // error: stock not exist in DB
        }
    }

    private boolean is_productId_existInDB(Integer productId){
        DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
        return (cursor.count() >= 1 ) ? true : false;
    }

    private List<String> getImagesByListOfProducts(List<Integer> productsId) throws Exception{
        List<String> imgs = new ArrayList<String>();

        for(Integer id: productsId){
            if(is_productId_existInDB(id)) {
                String img = getImgByProduct(id);
                imgs.add(img);
            }
        }

        return imgs;
    }

    private String getImgByProduct(Integer productId) throws Exception{
        DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
        BasicDBObject curr = (BasicDBObject) cursor.next();
        String Img  = curr.get("img").toString();
        return Img;
    }


}


