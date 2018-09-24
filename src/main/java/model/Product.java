package main.java.model;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * Created by user on 12/04/2018.
 */


public class Product {
    // should be one instance of Product class and stocks collection
    private static Product ourInstance = new Product();
    private static DBCollection products;

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

    /** Private: **/

    // adi:
    public void getProduct(int barcode){
        // if not in DB search in out api
        // return product
    }

    // adi:
    public void setProduct(int jsonProduct){
        // get json and store it in DB
        // json not int **

    }

}


// check if we need something from here
/*
    //@JsonProperty
    private int serialNumber;
    //@JsonProperty
    private String productName;
    //@JsonProperty
    private Date date;
    //@JsonProperty
    private int count = 1;

    public int getCount() {
        return count;
    }

    public setProduct(int jsonProduct){
        // get json and store it in DB
        // json not int **

    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getProductName() {
        return productName;
    }

    public Date getDate() {
        return date;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setDate(Date date) {
        this.date = date;
    }


*/
