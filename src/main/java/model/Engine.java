// TODO: marked as //**

package main.java.model;

import com.mongodb.MongoClient;
import com.mongodb.client.*;

/**
 * Created by user on 12/04/2018.
 **/

//////////////////////////////////////////////// TODO GOOGLE CONNECTION
//    public boolean signIn(String emailAddress) {
//        boolean res = true; //// to integrate with google
//        //TODO CHECK RETURN THE RESPOND FROM GOOGLE API
//        if(res){
//            updateUserInfoInDb();
//        }
//        return true;
//    }
//
//    //TODO
//    private void updateUserInfoInDb() {
//    }
//    //TODO CHECK HOW TO DO SO
//    public void stayLogIn(){
//
//    }
//    public void CreateNewUser() {
//        //adi marsiano
//        //s;ss
//    }
//////////////////////////////////////////////////

public class Engine {
    MongoDatabase database;
    Stock stocks;
    User users;

    public Engine() {
        // Connection to mongoDB
        MongoClient mongoClient = new MongoClient("Localhost", 27017);
        // Access to shopalDB
        database = mongoClient.getDatabase("shopal");
        stocks = new Stock(database);
        users = new User(database);


        // EXP
        //productScan("1", "1");
        //users.connectUser("abc");
        //stocks.updateProduct("1", "5", "3", "99");
        //stocks.addProduct("1", "33");
        // END EXP
    }


//    private void productScan(String barcode, String stockId) {
//        //** Str productId = getProductId_Barcode(barcode); // working with barcode API
//        try {
//            //   stocks.updateProductAfterScan(stockId, productId);
//        } catch (Exception e) {
//            System.out.println(e.getMessage()); //** massage to client
//        }
//    }
//
//    // user search for product (by name) in order to add it to "stock"
//    private Product findProductDetails(String search, String stockId){
//        String name = "Milk";
//        Product product = new Product();
//        product.setSerialNumber(serialNumber);
//        // product.setDate(11-00-1992);
//        product.setProductName(name);
//        return product;
//
//        //return json with all products options
//    }


//////////////////////////////////////////////// TODO SCAN ACTIVITY CONNECTION
    //** user update stock amounts
//    public void scanProductWithUpdate() {
//        int serialNumber = Barcode.scanBarcode(new Object());
//        int bar;
//        Product product = findProductDetails(serialNumber);
//        printProductDetailsFromBarcode(product); //TODO TALK ABOUT THE OPTIONS
//        //TODO RETURN JSON TO THE UI
//    }
//
//    public void continuityScan() {
//        int serialNumber = Barcode.scanBarcode(new Object());
//        Product product = findProductDetails(serialNumber);
//        //TODO CREATE JSON (OR UPDATE AN EXISTING)
//    }

//    public void printProductDetailsFromBarcode(Product product) {
//        System.out.println("Serial Number: " + product.getSerialNumber());
//        System.out.println("Product Name: " + product.getProductName());
//        System.out.println("Product Date: " + product.getDate());
//    }
//    public void updateUi() {
//        //TODO RETURN LIST OF JSONS TO UI
//    }
//    public boolean checkIsItOutOfDate(Date date) throws ParseException {
//        Date todayDate;
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//        todayDate = dateFormatter.parse(dateFormatter.format(new Date() ));
//        return dateFormatter.format(todayDate).equals(dateFormatter.format(date));
//    }
//////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////////////
//    public void increaseAmount() {
//
//    }
//
//    public void deleteList(ShoppingList shoppingList) {
//
//    }
//
//    public void updateList(ShoppingList shoppingList) {
//
//    }
//
//    public void logOut() {
//
//    }
/////////////////////////////////////////////////////////////////////////////////////


}