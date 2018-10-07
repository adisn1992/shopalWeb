package main.java.api;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import main.java.model.Product;
import org.mortbay.util.ajax.JSONObjectConvertor;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.nio.charset.StandardCharsets;

/**
 * Created by user on 05/05/2018.
 */

@Path("/shopal")
public class Controller{

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


