package main.java.model;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Collections;

import static com.mongodb.client.model.Filters.eq;

//import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Created by user on 14/04/2018.
 */
public class User {
    public static final String CLIENT_ID = "901407414554-dtnv2raqv7f2o5d2v4585moboaem5c55.apps.googleusercontent.com";
    private static final HttpTransport transport = new NetHttpTransport();
    private static final JacksonFactory jsonFactory = new JacksonFactory();
    GoogleIdTokenVerifier verifier;
    private MongoCollection<Document> users;

    /**
     * Public:
     **/
    public User(MongoDatabase database) {
        // connect to DB collection: users
        users = database.getCollection("users");
        // Create google idToken verifier
        verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID)) //** check the correct client_id
                .build();
    }

    public void connectUser(String idTokenString) {  // TODO (Receive idTokenString by HTTPS POST)
        try {
            // verify and create Payload
            GoogleIdToken idToken = verifier.verify(idTokenString);
            Payload payload = idToken.getPayload();
            String userId = payload.getUserId();

            if (!is_userId_existInDB(userId)) {
                addUser(payload); // new user
            }

        } catch (Exception e) {
            System.out.println("Invalid idToken :   " + e.getMessage());
        }
    }

    /**
     * Private:
     **/

    private void addUser(Payload payload) {
        // Get profile information from payload
        String email = payload.getEmail();
        String userId = payload.getUserId();
        //// boolean emailVerified = Boolean.valueOf(payload.getEmailVerified()); // TODO check for what

        String name = (String) payload.get("name");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
        String pictureUrl = (String) payload.get("picture");

        users.insertOne(new Document("userId", userId)
                .append("email", email)
                .append("name", name)
                .append("familyName", familyName)
                .append("givenName", givenName)
                .append("pictureUrl", pictureUrl)
                .append("stockId", "NO STOCKS")
        );

    }


    private boolean is_userId_existInDB(String userId) {
        boolean res = true;
        Document doc = users.find(eq("userId", userId)).first();

        if (doc == null) {
            res = false;
        }

        return res;
    }


    /**
     * public String getUserName() {
     * return userName;
     * }
     * <p>
     * public void setUserName(String userName) {
     * this.userName = userName;
     * }
     * <p>
     * public String getEmailAdress() {
     * <p>
     * return emailAdress;
     * }
     * <p>
     * public void setEmailAdress(String emailAdress) {
     * this.emailAdress = emailAdress;
     * }
     */

}
