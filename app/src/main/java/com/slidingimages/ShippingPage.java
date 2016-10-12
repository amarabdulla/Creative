package com.slidingimages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Creative on 04-Sep-16.
 */
public class ShippingPage extends Activity {
    private Button next;
    private EditText city;
    private EditText fname,phone,email,address_one,address_two,state,zip,lname;
    private CustomProgressDialog mCustomProgressDialog;
    private String result;
    public static String f_name_str,l_name_str,email_str,address_one_str,address_two_str,zip_str,city_str,phone_str;
    private String urlprofileView ="";
    private String urlprofileUpdate = HomePage.FIRSTPART+"profileUpdate";
    private static final String TAG_FNAME = "firstname";
    private static final String TAG_LNAME = "lastname";
    private static final String TAG_ADDRESS1 = "address1";
    private static final String TAG_ADDRESS2 = "address2";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_CITY = "city";
    private static final String TAG_USERID = "user_id";
    private static final String TAG_ZIP = "zip";
    String userid_pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_address_page);
        next=(Button)findViewById(R.id.btn_next);
        fname=(EditText)findViewById(R.id.fname);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        lname=(EditText)findViewById(R.id.lname);
        address_one=(EditText)findViewById(R.id.address_1);
        address_two=(EditText)findViewById(R.id.address_2);
        mCustomProgressDialog = new CustomProgressDialog(ShippingPage.this);
        zip=(EditText)findViewById(R.id.zip);
        city=(EditText) findViewById(R.id.city);
        SharedPreferences prefs = getSharedPreferences(Activity_Login.MY_PREFS_NAME, MODE_PRIVATE);
        String name_pref= prefs.getString("username", "null");
        userid_pref= prefs.getString("userid", "null");
        if (name_pref.equals("") || userid_pref.equals("")|| name_pref.equals("null")|| userid_pref.equals("null")){
            urlprofileView = HomePage.FIRSTPART + "profileView?user_id=" + Activity_Login.userId;
        }else {
            urlprofileView = HomePage.FIRSTPART + "profileView?user_id=" +userid_pref;
        }
//        List<String> list = new ArrayList<String>();
//        list.add("Dubai");
//        list.add("Abu Dhabi");
//        list.add("Ras al-Khaimah");
//        list.add("Sharjah");
//        list.add("Ajman");
//        list.add("Umm al-Quwain");
//        list.add("Fujairah");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
//
//        dataAdapter.setDropDownViewResource
//                (android.R.layout.simple_spinner_dropdown_item);
//
//        city.setAdapter(dataAdapter);
//
//        // Spinner item selection Listener
//        addListenerOnSpinnerItemSelection();


//        if (!Activity_Login.username.equals("temp")){
//
//        }
        ProgressTask progressTask= new ProgressTask();
        progressTask.execute();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().equals("") ||lname.getText().toString().equals("")||address_one.getText().toString().equals("")
                        ||address_two.getText().toString().equals("")|| city.getText().toString().equals("")||zip.getText().toString().equals("")){
                    new SweetAlertDialog(ShippingPage.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Please fill all the fields")
                            .show();
                }else   if (fname.getText().toString().equals("null") ||lname.getText().toString().equals("null")||address_one.getText().toString().equals("null")
                        ||address_two.getText().toString().equals("null")|| city.getText().toString().equals("null")||zip.getText().toString().equals("null")) {
                    new SweetAlertDialog(ShippingPage.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("null is not a valid entry")
                            .show();
                }
                else {

                    f_name_str=fname.getText().toString();
                    l_name_str=lname.getText().toString();
                    address_one_str=address_one.getText().toString();
                    address_two_str=address_two.getText().toString();
                    phone_str=phone.getText().toString();
                    city_str=city.getText().toString();
                    zip_str=zip.getText().toString();

                    ProgressTask2 progressTask2= new ProgressTask2();
                    progressTask2.execute();


                }

            }
        });
    }
    // Add spinner data

//    public void addListenerOnSpinnerItemSelection(){
//
//        city.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//    }
    private class ProgressTask extends AsyncTask<String, Void, Boolean> {

        protected void onPreExecute() {
            mCustomProgressDialog.show("");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (mCustomProgressDialog.isShowing()|| mCustomProgressDialog!=null) {
                mCustomProgressDialog.dismiss("");
            }



        }
        @Override
        protected Boolean doInBackground(final String... args) {

            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, urlprofileView, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("user_info");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                                    f_name_str = jsonObjectUser.getString("firstname");
                                    l_name_str= jsonObjectUser.getString("lastname");
                                    phone_str = jsonObjectUser.getString("phone");
                                    address_one_str = jsonObjectUser.getString("address1");
                                    address_two_str = jsonObjectUser.getString("address2");
                                    city_str=jsonObjectUser.getString("city");
                                    zip_str=jsonObjectUser.getString("zip");

                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (f_name_str.equals("")||city_str.equals("")|| address_one.equals("")){
                                            new SweetAlertDialog(ShippingPage.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Please enter details")
                                                    .show();

                                        }else {
                                            fname.setText(f_name_str);
                                            city.setText(city_str);
                                            if (lname.equals("")){
                                                lname.getText().clear();
                                            }else {
                                                lname.setText(l_name_str);
                                            }
                                            phone.setText(phone_str);
                                            if (address_one.equals("")){

                                                address_one.setText("");
                                            }else {
                                                address_one.setText(address_one_str);
                                            }
                                            if (address_two.equals("")){
                                                address_two.getText().clear();
                                            }else {
                                                address_two.setText(address_two_str);
                                            }
                                            if (zip.equals("")){
                                                zip.setText("");
                                            }else {
                                                zip.setText(zip_str);
                                            }

                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Log.d("Error.Response", error);
                            new SweetAlertDialog(ShippingPage.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("No data found")
                                    .show();
                        }
                    }
            );


            RequestQueue requestQueue = Volley.newRequestQueue(ShippingPage.this);
            requestQueue.add(getRequest);


            return null;
        }
    }
    private class ProgressTask2 extends AsyncTask<String, Void, Boolean> {

        protected void onPreExecute() {
            mCustomProgressDialog.show("");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (mCustomProgressDialog.isShowing()|| mCustomProgressDialog!=null) {
                mCustomProgressDialog.dismiss("");
            }



        }
        @Override
        protected Boolean doInBackground(final String... args) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlprofileUpdate,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                                result="success";
//                            Toast.makeText(ShippingPage.this,response,Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(ShippingPage.this,ChangeShippingAddressPage.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ShippingPage.this,error.toString(),Toast.LENGTH_LONG).show();
//                              result="failed";
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(TAG_USERID, userid_pref);
                    params.put(TAG_FNAME, f_name_str);
                    params.put(TAG_LNAME, l_name_str);
                    params.put(TAG_ADDRESS1, address_one_str);
                    params.put(TAG_ADDRESS2, address_two_str);
                    params.put(TAG_PHONE, phone_str);
                    params.put(TAG_CITY, city_str);
                    params.put(TAG_ZIP, zip_str);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(ShippingPage.this);
            requestQueue.add(stringRequest);

            return null;
        }
    }

}
