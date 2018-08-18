package main.java.model;

//import org.codehaus.jackson.annotate.JsonProperty;

//import javax.ws.rs.Produces;
//import javax.xml.bind.annotation.XmlRootElement;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ws.rs.Path;

/**
 * Created by user on 12/04/2018.
 */

@Path("/product")
public class Product {
    private MongoCollection<Document> products;

    public Product(MongoDatabase database) {
        // connect to DB collection: users
        products = database.getCollection("products");

    }

    public void getProduct(int barcode){
        // if not in DB search in out api
        // return product
    }

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
