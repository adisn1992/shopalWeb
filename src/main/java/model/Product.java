package main.java.model;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.bind.ValidationException;
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

    public static DBCollection getInstanceCollection() {
        return ourInstance.products;
    }

    public static Product getInstanceClass() {
        return ourInstance;
    }

    //public void addProduct(Integer)
    public String getImg(Integer productId) throws ValidationException {
        validationOfProduct(productId);
        return getImgByProduct(productId);
    }

    public List<String> getImages(String stockId) throws ValidationException {
        List<Integer> productsId = stockClass.getAllProductsId(stockId);
        return  getImagesByListOfProducts(productsId);
    }


/** Private: **/

    public void setProduct(){
        // TODO
    }

    private void validationOfProduct(Integer productId) throws ValidationException{
        if (!is_productId_existInDB(productId)) {
            throw new ValidationException("Invalid product"); // error: stock not exist in DB
        }
    }

    private boolean is_productId_existInDB(Integer productId){
        DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
        return (cursor.count() >= 1 ) ? true : false;
    }

    private List<String> getImagesByListOfProducts(List<Integer> productsId) {
        List<String> images = new ArrayList<String>();

        for(Integer id: productsId){
            if(is_productId_existInDB(id)) {
                String img = getImgByProduct(id);
                images.add(img);
            }
        }

        return images;
    }

    private String getImgByProduct(Integer productId) {
        String Img = null;
        try{
            validationOfProduct(productId);
            DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
            BasicDBObject curr = (BasicDBObject) cursor.next();
            Img  = curr.get("img").toString();
        }
        catch(ValidationException e){
            // adi: if (Img == null) -> default pic

        }

        return Img;
    }
}


