package main.java.model;

import com.mongodb.MongoClient;
import com.mongodb.client.*;

/**
 * Created by user on 12/04/2018.
 **/

// test!!!!

public class Engine {
    MongoDatabase a;
    Stock stocks;
    User users;

    public Engine() {
        MongoClient mongoClient = new MongoClient("Localhost", 27017);
        a = mongoClient.getDatabase("shopal");
        stocks = new Stock();
        users = new User();

    }
}