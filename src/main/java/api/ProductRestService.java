package main.java.api;

import main.java.model.Product;
import javax.ws.rs.Path;

@Path("/product")
public class ProductRestService {
    private Product product = Product.getInstanceClass();


}
