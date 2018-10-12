package main.java.api;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 05/05/2018.
 */


public class Utils {

    public static List<Long> getProductsId(String id, DBCollection collection){

        List<Long> productsId = new ArrayList<Long>();

        DBCursor cursor = collection.find(new BasicDBObject("_id", new ObjectId(id)), new BasicDBObject("items", 1));
        BasicDBObject stock = (BasicDBObject) cursor.next();

        // get list of products
        JSONParser parser = new JSONParser();
        JSONArray products = null;
        try {
            products = (JSONArray) parser.parse(stock.getString("items"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // iterate list of products and gets id of each product
        for(Object item: products){
            if ( item instanceof JSONObject) {
                JSONObject product =  (JSONObject)item;
                productsId.add(Long.parseLong(product.get("productId").toString()));
            }
        }

        return productsId;
    }

    public static String getImgsAndNeamedJson(List<List<String>> imgsAndNames){
        JSONArray data = new JSONArray();

        Iterator<String> itr_images = imgsAndNames.get(0).iterator();
        Iterator<String> itr_names = imgsAndNames.get(1).iterator();

        while (itr_names.hasNext() && itr_images.hasNext()) {
            String img = itr_images.next();
            String name = itr_names.next();

            JSONObject productData = new JSONObject();
            productData.put("productImg", img);
            productData.put("productName", name);

            data.add(productData);
        }

        return data.toJSONString();
    }

    public static JSONArray getArrayJsonFromJsonByField(JSONObject  jsonObject, String field){
        JSONParser parser = new JSONParser();
        JSONArray products = null;

        try {
            products = (JSONArray) parser.parse(jsonObject.get(field).toString());
        }
        catch(Exception e){
            // adi: handle exception
        }

        return products;
    }

    public static JSONObject stringToJson(String str){
        JSONParser parser = new JSONParser();
        JSONObject json = null;

        try {
            json = (JSONObject) parser.parse(str);
        }
        catch(Exception e){
            // adi: handle exception
        }

        return json;
    }


}


