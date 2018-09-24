package main.java.model;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


/**
 * Created by user on 12/04/2018.
 */
public class ShoppingList {
    // should be one instance of ShoppingList class and stocks collection
    private static ShoppingList ourInstance = new ShoppingList();
    private static DBCollection shopList;

    /** Public: **/
    public ShoppingList()
    {
        // connect to mongo
        MongoClient mongoClient = new MongoClient("Localhost", 27017);
        // get DB
        DB database = mongoClient.getDB("shopal");
        // get collection
        shopList = database.getCollection("shopList");
    }

    public static ShoppingList getInstanceClass() {
        return ourInstance;
    }

    public static DBCollection getInstanceCollection() {
        return ourInstance.shopList;
    }

    /** Private: **/



}

    /*
    List<Product> shoppingList = new ArrayList<>();


    //TODO: MOVE THIS TO BE IN ANDROID STUDIO
    public void addProductToList(Product product){
        //TODO: check if already exists
        //if so ask the user what to do
        shoppingList.add(product);
    }

    public void deleteProduct(Product product){
        for (Iterator<Product> iter = shoppingList.listIterator(); iter.hasNext(); ) {
            Product productInList = iter.next();
            if (productInList.equals(product)) {
                iter.remove();
            }
        }
    }

    public void deleteList(){
        shoppingList = new ArrayList<>();
    }

    public void createNewList(){
        shoppingList = new ArrayList<>();
    }

    public void changeAmountOfProduct(Product product, int count)
    {
        for (Iterator<Product> iter = shoppingList.listIterator(); iter.hasNext(); ) {
            Product productInList = iter.next();
            if (productInList.equals(product)) {
                productInList.setCount(count);
            }
        }
    }


}
*/