package main.java.model;

import com.mongodb.*;
import javax.xml.bind.ValidationException;

/**
 * Created by user on 12/04/2018.
 */

// email is a unique key for user
public class User {
    private static User ourInstance = new User();
    private static DBCollection users;
    private static Stock stockClass =  Stock.getInstanceClass();

/** Public: **/
    public User()
    {
        // connect to mongo
        MongoClient mongoClient = new MongoClient("Localhost", 27017);
        // get DB
        DB database = mongoClient.getDB("shopal");
        // get collection
        users = database.getCollection("users");
    }

    public static User getInstanceClass() {
        return ourInstance;
    }

    public String addUserAndGetStockId(String firstName, String lastName, String email) throws ValidationException {
        if(is_user_existInDB(email)){
            throw new ValidationException("User already exists");
        }
        String stockId =  stockClass.newStock(email);
        setUser(email, firstName, lastName, stockId);

        return stockId;
    }

    public String getValue(String email, String fieldName) throws ValidationException {
        if (!is_user_existInDB(email)) {
            throw new ValidationException("Invalid user");
        }

        return getValueFromUser(email, fieldName);
    }

/** Private: **/
    private void setUser(String email, String firstName, String lastName, String stockId){
        BasicDBObject doc = new BasicDBObject("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("stockId", stockId);

        users.insert(doc);
    }

    private boolean is_user_existInDB(String email) {
        DBCursor cursor = users.find(new BasicDBObject("email", email));
        return (cursor.count() >= 1);
    }

    private String getValueFromUser(String email, String fieldName){
        DBCursor cursor = users.find(new BasicDBObject("email", email));
        BasicDBObject user = (BasicDBObject) cursor.next();

        return user.getString(fieldName);
    }
}










