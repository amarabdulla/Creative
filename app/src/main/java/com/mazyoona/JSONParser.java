package com.mazyoona;

/**
 * Created by Amar on 8/30/2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
    static InputStream iStream = null;
    static JSONArray jarray = null;
    static String json = "";
    static InputStream is = null;
    static JSONObject jObj = null;

    public JSONParser() {
    }


    public JSONObject getJSONFromUrl(String url) {

        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();//default implementation of HTTP client
            HttpPost httpPost = new HttpPost(url);//extending a database through appending; other functions also
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();	// put it into the inputstream

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);//buffered reader for speed;"iso-8859-1" is a standard;8 is size for the reader
            StringBuilder sb = new StringBuilder();//for string modifications
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");//appends the contents of the mentioned string
            }
            is.close();
            json = sb.toString();
            System.out.println(json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}
