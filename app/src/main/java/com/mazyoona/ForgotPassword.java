package com.mazyoona;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mazyoona.app.AppStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Amar on 9/16/2016.
 */
public class ForgotPassword extends Activity {
    private EditText editTextEmail;
    private Button submit;
    private String email,msg;
    private String result="null";
    private CustomProgressDialog mCustomProgressDialog;
    private static final String REGISTER_URL = HomePage.FIRSTPART+"forget";

    public static final String KEY_EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        editTextEmail=(EditText)findViewById(R.id.email);
        submit=(Button)findViewById(R.id.submit);
        mCustomProgressDialog = new CustomProgressDialog(ForgotPassword.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppStatus.getInstance(ForgotPassword.this).isOnline()) {
                    if (editTextEmail.getText().toString().equals("")) {
                        new SweetAlertDialog(ForgotPassword.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Please enter your email")
                                .show();
                    } else {
                        email = editTextEmail.getText().toString().trim();
                        ProgressTask progressTask = new ProgressTask();
                        progressTask.execute();
                    }
                }else {
                    new SweetAlertDialog(ForgotPassword.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Please check your network")
                            .show();
                }
            }
        });
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
                                result="success";
                            Toast.makeText(ForgotPassword.this,response,Toast.LENGTH_LONG).show();
                            try {
                                JSONObject mainObject = new JSONObject(response.toString());
                                result = mainObject.getString("status");
                                msg = mainObject.getString("message");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("true")) {
                                        Toast.makeText(ForgotPassword.this, "successful", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ForgotPassword.this, Activity_Login.class);
                                        startActivity(intent);
                                        ForgotPassword.this.finish();
                                    }
                                }
                            });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                                result = "failed";
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!result.equals("true")) {
                                        new SweetAlertDialog(ForgotPassword.this, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Email not found")
                                                .show();
                                    }
                                }
                            });

                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(KEY_EMAIL, email);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(ForgotPassword.this);
            requestQueue.add(stringRequest);

            return null;
        }
    }

}
