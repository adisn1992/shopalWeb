package main.java.model;

import javax.ws.rs.Path;

/**
 * Created by user on 12/04/2018.
 */
@Path("/product")
public class ShoppingList {



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