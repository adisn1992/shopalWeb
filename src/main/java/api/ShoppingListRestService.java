package main.java.api;

import main.java.model.ShoppingList;
import javax.ws.rs.Path;

@Path("/shoppingList")
public class ShoppingListRestService {
    private ShoppingList shopList = ShoppingList.getInstanceClass();



}
