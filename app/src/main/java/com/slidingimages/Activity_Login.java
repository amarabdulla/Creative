package com.slidingimages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.slidingimages.app.AppStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Activity_Login extends Activity implements OnClickListener {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button login, register;
    private TextView fpass;
    private String email;
    private TextView skip_login,title_text;
    private String password;
    private String result="null";
    public static String userId="null";
    public static String username="null";
    public static String profile_image="null";
    private CustomProgressDialog mCustomProgressDialog;

    private static final String REGISTER_URL = HomePage.FIRSTPART+"login";
    public static final String MY_PREFS_NAME="login_pref";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        skip_login=(TextView)findViewById(R.id.skip_login);
        fpass=(TextView) findViewById(R.id.fpassword);
        title_text=(TextView) findViewById(R.id.title_text);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        mCustomProgressDialog = new CustomProgressDialog(Activity_Login.this);
        login = (Button) findViewById(R.id.btnLogin);

        register = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        login.setTypeface(tf);
        register.setTypeface(tf);
        fpass.setTypeface(tf);
        skip_login.setTypeface(tf);
        title_text.setTypeface(tf);
        editTextEmail.setTypeface(tf);
        editTextPassword.setTypeface(tf);
        login.setOnClickListener(this);
        fpass.setOnClickListener(this);
        register.setOnClickListener(this);
        skip_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnLogin:
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                if (AppStatus.getInstance(this).isOnline()) {

                    if (!email.equals("") || !password.equals("")) {
                        if (isValidEmail(email)) {
                            ProgressTask progressTask = new ProgressTask();
                            progressTask.execute();
                        }else {
                            new SweetAlertDialog(Activity_Login.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Please check your email")
                                    .show();
                        }
                    }else {
                        new SweetAlertDialog(Activity_Login.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Please fill your credential")
                                .show();
                    }

                } else {
                    new SweetAlertDialog(Activity_Login.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Please check your network")
                            .show();
//                    Log.v("Home", "############################You are not online!!!!");
                }



//                Intent i = new Intent(Activity_Login.this, HomePage.class);
//                startActivity(i);
                break;
            case R.id.btnLinkToRegisterScreen:
                Intent ii = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(ii);
                break;

            case R.id.fpassword:
                Intent intent = new Intent(Activity_Login.this, ForgotPassword.class);
                startActivity(intent);
                break;
            case R.id.skip_login:
                userId="0";
                username="temp";
                Intent intent1 = new Intent(Activity_Login.this, HomePage.class);
                startActivity(intent1);
                break;

            default:
                break;
        }
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
//                                Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject mainObject = new JSONObject(response.toString());
                                    System.out.println(response.toString());
                                    result = mainObject.getString("status");

                                     userId = mainObject.getString("user_id");
                                     username = mainObject.getString("name");
                                     profile_image=mainObject.getString("profile_image");

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (result.equals("true")) {
//                                                Toast.makeText(Activity_Login.this, "successful", Toast.LENGTH_LONG).show();
                                                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                editor.putString("username", username);
                                                editor.putString("userid", userId);
                                                editor.putString("profile_image", profile_image);
                                                editor.commit();
                                                Intent intent = new Intent(Activity_Login.this, HomePage.class);
                                                startActivity(intent);
                                                Activity_Login.this.finish();
                                            }
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(response+"response");
                                Intent intent= new Intent(Activity_Login.this,HomePage.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Activity_Login.this,error.toString(),Toast.LENGTH_LONG).show();
                              result="failed";
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!result.equals("true")){
//                                            Toast.makeText(Activity_Login.this,"successful",Toast.LENGTH_LONG).show();
                                            new SweetAlertDialog(Activity_Login.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Incorrect username/password")
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
                        params.put(KEY_PASSWORD,password);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(Activity_Login.this);
                requestQueue.add(stringRequest);

            return null;
        }
    }

}