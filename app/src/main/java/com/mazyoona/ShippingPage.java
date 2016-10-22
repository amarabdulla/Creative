package com.mazyoona;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mazyoona.cart.LoginEmptyCartActivity;
import com.mazyoona.customViews.ScrimInsetsFrameLayout;
import com.mazyoona.utils.UtilsDevice;
import com.mazyoona.utils.UtilsMiscellaneous;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Creative on 04-Sep-16.
 */
public class ShippingPage extends Activity implements View.OnClickListener{
    private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
    private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
    private Button next,btnShipping;
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
    String userid_pref,image_pref;
    private ImageView profile_pic;
    private static final String name = "name";
    private ImageLoader imageLoader;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_address_page);
        next=(Button)findViewById(R.id.btn_next);
        fname=(EditText)findViewById(R.id.fname);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        lname=(EditText)findViewById(R.id.lname);
        btnShipping=(Button)findViewById(R.id.btnShipping);
        address_one=(EditText)findViewById(R.id.address_1);
        address_two=(EditText)findViewById(R.id.address_2);
        menuLayoutOne=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_one);
        menuLayoutTwo=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_two);
        menuLayoutThree=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_three);
        menuLayoutFour=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_four);
        menuLayoutFive=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_five);
        menuLayoutSix=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_six);
        navigation_username=(TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
        menuLayoutOne_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_one);
        menuLayoutTwo_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_two);
        menuLayoutThree_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_help_and_three);
        menuLayoutFour_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_four);
        menuLayoutFive_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_five);
        menuLayoutSix_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_six);
        profile_pic=(ImageView)findViewById(R.id.profile_picture_navigation);
        imageLoader=new ImageLoader(getApplicationContext());
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        mCustomProgressDialog = new CustomProgressDialog(ShippingPage.this);
        zip=(EditText)findViewById(R.id.zip);
        String fontPath = "fonts/arial.ttf";
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        next.setTypeface(tf);
        btnShipping.setTypeface(tf);
        city=(EditText) findViewById(R.id.city);
        SharedPreferences prefs = getSharedPreferences(Activity_Login.MY_PREFS_NAME, MODE_PRIVATE);
        String name_pref= prefs.getString("username", "null");
        userid_pref= prefs.getString("userid", "null");
        image_pref = prefs.getString("profile_image", "null");
        if (name_pref.equals("") || userid_pref.equals("")|| name_pref.equals("null")|| userid_pref.equals("null")){
            urlprofileView = HomePage.FIRSTPART + "profileView?user_id=" + Activity_Login.userId;
        }else {
            urlprofileView = HomePage.FIRSTPART + "profileView?user_id=" +userid_pref;
        }
        if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){
            navigation_username.setText("Welcome "+"Guest");
            navigation_username.setTypeface(tf);
            profile_pic.setBackgroundResource(R.drawable.user_icon_female);
        }else {
            if (name.equals("null") || name.equals("")){
                navigation_username.setText(Activity_Login.username);
                imageLoader.DisplayImage(Activity_Login.profile_image,profile_pic);

            }else {
                navigation_username.setText(name_pref);
                imageLoader.DisplayImage(image_pref,profile_pic);

            }
            navigation_username.setTypeface(tf);
        }
        ProgressTask progressTask= new ProgressTask();
        progressTask.execute();
        init_navigator();

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });


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
        //Navigation menu item click listener
        menuLayoutOne.setOnClickListener(this);
        menuLayoutTwo.setOnClickListener(this);
        menuLayoutThree.setOnClickListener(this);
        menuLayoutFour.setOnClickListener(this);
        menuLayoutFive.setOnClickListener(this);
        menuLayoutSix.setOnClickListener(this);
    }
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.navigation_drawer_items_list_linearLayout_one:
                Intent intent = new Intent(this, DesignerList.class);
                this.startActivity(intent);

                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_two:
                Intent dlist = new Intent(this, SpecialOfferPage.class);
                this.startActivity(dlist);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_three:

                if (!ShoppingCart.product_names.isEmpty()){
                    Intent scart = new Intent(this, ShoppingCart.class);
                    this.startActivity(scart);
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }else {
                    Intent scart = new Intent(this, LoginEmptyCartActivity.class);
                    this.startActivity(scart);
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }

                break;

            case R.id.navigation_drawer_items_list_linearLayout_four:
                Intent faq = new Intent(this, Faq.class);
                this.startActivity(faq);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_five:
                Intent about = new Intent(this, AboutUs.class);
                this.startActivity(about);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_six:
                Intent cont = new Intent(this, ContactUs.class);
                this.startActivity(cont);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;
        }
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
    private void init_navigator(){
        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);





        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
    }

}
