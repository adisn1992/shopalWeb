package main.java.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import static jdk.nashorn.internal.runtime.Source.sourceFor;
/**
 * Created by user on 05/05/2018.
 */

@Path("/utils")
public class Utils {



    public Utils() {
        // Connection to mongoDB
        //MongoClient mongoClient = new MongoClient("Localhost", 27017);
        // Access to shopalDB


        // EXP
        //productScan("1", "1");
        //users.connectUser("abc");
        //stocks.updateProduct("1", "5", "3", "99");
        //stocks.addProduct("1", "33");
        // END EXP
    }

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/users")
        public String users(){
            return "hello user adi";
        }

        @GET
        @Path("/product/{barcode}")
        @Produces(MediaType.TEXT_PLAIN)
        public String GetProductFromSuperGetAPI(@PathParam("barcode") long barcode) throws IOException{
            String url = "https://api.superget.co.il/";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            // con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept", "application/json, text/plain, */*");
            con.setRequestProperty("Accept-Language","en-US,en;q=0.9,he;q=0.8");
            con.setRequestProperty( "charset", "utf-8");
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("action=GetProductsByBarCode&api_key=a4f63497f3b3d2d1d8d1644265f9cf4a18f950ac");
            urlParameters.append("&product_barcode="  + barcode);
            urlParameters.append("&limit=1");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters.toString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
            return response.toString();
        }

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/testConnectionProductsAPI")
        public void ConnectToSuperGetAPI() throws IOException {
            String url = "https://api.superget.co.il/";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            // con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "action=TestFunction&api_key=a4f63497f3b3d2d1d8d1644265f9cf4a18f950ac";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        }
    }


