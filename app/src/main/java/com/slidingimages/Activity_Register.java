package com.slidingimages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.slidingimages.app.AppStatus;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Activity_Register extends Activity  {
    private EditText editTextUsername, editTextEmail, editTextPass, editTextPhone, editTextCity,editTextLname;
    private Button btnRegister;
    private String fname;
    private String password;
    private String email;
    private String phone;
    private String city;
    private String lname;
    private String user_id;
    private CustomProgressDialog mCustomProgressDialog;

    String result = null;

    private static String REGISTER_URL = HomePage.FIRSTPART+"register";


    private static final String TAG_FNAME = "name";

//    private static final String TAG_LNAME = "lastname";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_CITY = "city";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mCustomProgressDialog = new CustomProgressDialog(Activity_Register.this);
        editTextUsername = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPass = (EditText) findViewById(R.id.password);
        editTextPhone = (EditText) findViewById(R.id.phone);
        editTextCity = (EditText) findViewById(R.id.city);
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
//        editTextLname = (EditText) findViewById(R.id.lname);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setTypeface(tf);


        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(Activity_Register.this).isOnline()) {
                    if (editTextUsername.getText().toString().equals("") || editTextEmail.getText().toString().equals("") || editTextPass.getText().toString().equals("")
                            || editTextPhone.getText().toString().equals("") || editTextCity.getText().toString().equals("")) {
                        new SweetAlertDialog(Activity_Register.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Please fill all fields")
                                .show();

                    } else {
                        if (!isValidEmail(editTextEmail.getText().toString())){
                            new SweetAlertDialog(Activity_Register.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Please check your email")
                                    .show();
                        }else {
                            fname = editTextUsername.getText().toString().trim();
                            email = editTextEmail.getText().toString().trim();
                            password = editTextPass.getText().toString().trim();
                            phone = editTextPhone.getText().toString().trim();
                            city = editTextCity.getText().toString().trim();

                            ProgressTask progressTask = new ProgressTask();
                            progressTask.execute();
                        }

                    }

                }else {
                        new SweetAlertDialog(Activity_Register.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Please check your network")
                                .show();
                }
            }

        });

    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
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

        protected Boolean doInBackground(final String... args) {



            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                                result="success";
                            Toast.makeText(Activity_Register.this,response,Toast.LENGTH_LONG).show();
                            try {
                                JSONObject mainObject = new JSONObject(response.toString());
                                result = mainObject.getString("status");
                                user_id = mainObject.getString("user_id");
                                String username = mainObject.getString("name");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                            Toast.makeText(Activity_Register.this,"successful",Toast.LENGTH_LONG).show();
                                            Intent intent= new Intent(Activity_Register.this,Activity_Login.class);
                                            startActivity(intent);
                                            Activity_Register.this.finish();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
//                              result="failed";
                            new SweetAlertDialog(Activity_Register.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Email already used")
                                    .show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(TAG_FNAME, fname);
                    params.put(TAG_EMAIL, email);
                    params.put(TAG_PHONE, phone);
                    params.put(TAG_CITY, city);
                    params.put(TAG_PASSWORD,password);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(Activity_Register.this);
            requestQueue.add(stringRequest);

            return null;
        }
    }




}