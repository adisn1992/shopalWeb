package main.java.model;

import com.mongodb.*;

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

    public static Product getInstanceClass() {
        return ourInstance;
    }

    public String getImg(Long productId) throws ValidationException {
        validationOfProduct(productId);
        return getImgByProduct(productId);
    }

    public List<List<String>> getImagesAndNames(String stockId) throws ValidationException {
        List<Long> productsId = stockClass.getAllProductsId(stockId);
        return  getImagesAndNamesByListOfProducts(productsId);
    }

    public List<List<String>>getImagesAndNames_shoppingList(String stockId) throws ValidationException {

        List<Long> productsId = stockClass.getAllProductsId_shoppingList(stockId);
        return  getImagesAndNamesByListOfProducts(productsId);
    }

    public void addProduct(Long productId){
        if(!is_productId_existInDB(productId)){
            setProduct(productId);
        }
    }
    
/** Private: **/
    public void setProduct(Long productId){
        BasicDBObject doc = new BasicDBObject("barcode", productId).append("name", "").append("img", "");
        products.insert(doc);
    }

    private void validationOfProduct(Long productId) throws ValidationException{
        if (!is_productId_existInDB(productId)) {
            throw new ValidationException("Invalid product"); // error: stock not exist in DB
        }
    }

    private boolean is_productId_existInDB(Long productId){
        DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
        return (cursor.count() >= 1 ) ? true : false;
    }

    private List<List<String>> getImagesAndNamesByListOfProducts(List<Long> productsId) {
        List<List<String>> res = new ArrayList<>();
        List<String> images = new ArrayList<>();
        List<String> names = new ArrayList<>();


        for(Long id: productsId){
            if(is_productId_existInDB(id)) {
                // add img to images
                String img = getImgByProduct(id);
                images.add(img);
                // add name to names
                String name = getNameByProduct(id);
                names.add(name);
            }
        }

        res.add(images);
        res.add(names);

        return res;
    }

    private String getImgByProduct(Long productId) {
        String Img = null;
        try{
            validationOfProduct(productId);
            DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
            BasicDBObject curr = (BasicDBObject) cursor.next();
            Img  = curr.get("img").toString();
        }
        catch(ValidationException e){
            Img = "NOT_EXISTS_IN_PRODUCTS_COLLECTION";
        }

        return Img;
    }

    private String getNameByProduct(Long productId) {
        String name = null;
        try{
            validationOfProduct(productId);
            DBCursor cursor = products.find(new BasicDBObject("barcode", productId));
            BasicDBObject curr = (BasicDBObject) cursor.next();
            name  = curr.get("name").toString();
        }
        catch(ValidationException e){
            name = "NOT_EXISTS_IN_PRODUCTS_COLLECTION";
        }

        return name;
    }
}


