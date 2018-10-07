package main.java.model;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//import javax.xml.bind.ValidationException;
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
    public String getImg(Long productId) throws /*ValidationException*/Exception {
        validationOfProduct(productId);
        return getImgByProduct(productId);
    }

    public List<String> getImages(String stockId) throws /*ValidationException*/Exception {
        List<Long> productsId = stockClass.getAllProductsId(stockId);
        return  getImagesByListOfProducts(productsId);
    }


/** Private: **/

    public void setProduct(){
        // TODO
    }

    private void validationOfProduct(Long productId) throws /*ValidationException*/Exception{
        if (!is_productId_existInDB(productId)) {
            throw new /*ValidationException*/Exception("Invalid product"); // error: stock not exist in DB
        }
    }

    private boolean is_productId_existInDB(Long productId){
        DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
        return (cursor.count() >= 1 ) ? true : false;
    }

    private List<String> getImagesByListOfProducts(List<Long> productsId) {
        List<String> images = new ArrayList<String>();

        for(Long id: productsId){
            if(is_productId_existInDB(id)) {
                String img = getImgByProduct(id);
                images.add(img);
            }
        }

        return images;
    }

    private String getImgByProduct(Long productId) {
        String Img = null;
        try{
            validationOfProduct(productId);
            DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
            BasicDBObject curr = (BasicDBObject) cursor.next();
            Img  = curr.get("img").toString();
        }
        catch(/*ValidationException*/Exception e){
            // adi: if (Img == null) -> default pic

        }

        return Img;
    }
}


