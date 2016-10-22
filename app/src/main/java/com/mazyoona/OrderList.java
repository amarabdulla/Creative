package com.mazyoona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.mazyoona.app.AppStatus;
import com.mazyoona.cart.LoginEmptyCartActivity;
import com.mazyoona.customViews.ScrimInsetsFrameLayout;
import com.mazyoona.utils.UtilsDevice;
import com.mazyoona.utils.UtilsMiscellaneous;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Creative on 20-Sep-16.
 */
public class OrderList extends ActionBarActivity implements View.OnClickListener {
    private static final String name = "name";
    String name_pref;
    private DrawerLayout mDrawerLayout;
    private AHBottomNavigation bottomNavigation;
    private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private ProgressDialog dialog;
    private ListView listView;
    private ImageView menu_icon;
    private String result;
    private ArrayList<String> imagesParseArray = new ArrayList<String>();
    private ArrayList<String> titleParseArray = new ArrayList<String>();
    private ArrayList<String> companyParseArray = new ArrayList<String>();
    private ArrayList<String> emailParseArray = new ArrayList<String>();
    private ArrayList<String> addressParseArray = new ArrayList<String>();
    private ArrayList<String> designerIdParseArray = new ArrayList<String>();
    private static String url = "";
    private static String TAG_USERID="user_id";
    private CustomProgressDialog mCustomProgressDialog;
    private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
    private ImageView profile_pic;
    private ImageLoader imageLoader;
    private String image_pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list
        );
        final String[] colors = {"#a41c9a"};

        menuLayoutOne=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_one);
        menuLayoutTwo=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_two);
        menuLayoutThree=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_three);
        menuLayoutFour=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_four);
        menuLayoutFive=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_five);
        menuLayoutSix=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_six);
        listView=(ListView)findViewById(R.id.listview);
        mCustomProgressDialog = new CustomProgressDialog(OrderList.this);
        menu_icon=(ImageView) findViewById(R.id.menu_icon) ;
        menu_icon.setClickable(true);
        dialog= new ProgressDialog(this);
        navigation_username=(TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
        menuLayoutOne_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_one);
        menuLayoutTwo_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_two);
        menuLayoutThree_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_help_and_three);
        menuLayoutFour_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_four);
        menuLayoutFive_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_five);
        menuLayoutSix_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_six);
        profile_pic=(ImageView)findViewById(R.id.profile_picture_navigation);
        imageLoader=new ImageLoader(getApplicationContext());
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        menuLayoutOne_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutTwo_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutThree_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutFour_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutFive_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutSix_header.setTypeface(tf, Typeface.NORMAL);
        SharedPreferences prefs = getSharedPreferences(Activity_Login.MY_PREFS_NAME, MODE_PRIVATE);
        name_pref= prefs.getString("username", "null");
        image_pref = prefs.getString("profile_image", "null");
        String userid_pref= prefs.getString("userid", "null");
        if (name_pref.equals("") || userid_pref.equals("")|| name_pref.equals("null")|| userid_pref.equals("null")){
            url=HomePage.FIRSTPART+"orderDetails?user_id="+Activity_Login.userId;
        }else {
            url = HomePage.FIRSTPART + "orderDetails?user_id=" +userid_pref;
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


        //Navigation menu item click listener
        menuLayoutOne.setOnClickListener(this);
        menuLayoutTwo.setOnClickListener(this);
        menuLayoutThree.setOnClickListener(this);
        menuLayoutFour.setOnClickListener(this);
        menuLayoutFive.setOnClickListener(this);
        menuLayoutSix.setOnClickListener(this);


        init_navigator();

        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
        if (AppStatus.getInstance(this).isOnline()) {
            ProgressTask progressTask=new ProgressTask();
            progressTask.execute();
        }else {
            new SweetAlertDialog(OrderList.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Please check your network")
                    .show();
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
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.navigation_drawer_items_list_linearLayout_one:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
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

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                                result="success";
//                            Toast.makeText(OrderList.this,response,Toast.LENGTH_LONG).show();
                            try {

                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    System.out.println(response.toString());
                                    JSONArray jsonArray = jsonObject.getJSONArray("orders");


                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
//                                        String title = jsonObjectUser.getString("name");
//                                        String url = jsonObjectUser.getString("image");
//                                        String company = jsonObjectUser.getString("company");
//                                        String email = jsonObjectUser.getString("email");
                                        String product_subtotal = jsonObjectUser.getString("order_id");
                                        String payment_type = jsonObjectUser.getString("payment_type");
                                        JSONArray jsonArrayProducts = jsonObjectUser.getJSONArray("products");
                                        for (int j = 0; j < jsonArrayProducts.length(); j++) {
                                            JSONObject jsonObjectProducts = jsonArrayProducts.getJSONObject(j);
                                            String product_name = jsonObjectProducts.getString("name");
                                            String product_image = jsonObjectProducts.getString("image");
                                            String product_price = jsonObjectProducts.getString("price");
                                            String product_salecode = jsonObjectProducts.getString("sale_code");
                                            String product_qty = jsonObjectProducts.getString("qty");

                                            titleParseArray.add(product_name);
                                            imagesParseArray.add(product_image);
                                            companyParseArray.add(product_price);
                                            emailParseArray.add(product_qty);
                                            addressParseArray.add(product_subtotal);

                                        }

//                                        addressParseArray.add(payment_type);

                                    }

                                    System.out.println(imagesParseArray +"img");

                                }
                                catch (JSONException e) {
                                    e.printStackTrace();

                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        Toast.makeText(OrderList.this,"successful",Toast.LENGTH_LONG).show();

                                        CustomOrderListAdapter  customOrderListAdapter= new CustomOrderListAdapter(OrderList.this,imagesParseArray,titleParseArray,
                                                companyParseArray,emailParseArray,addressParseArray,designerIdParseArray);
                                        listView.setAdapter(customOrderListAdapter);

                                    }
                                });

                            }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                Toast.makeText(OrderList.this,error.toString(),Toast.LENGTH_LONG).show();
//                              result="failed";
//                            new SweetAlertDialog(OrderList.this, SweetAlertDialog.ERROR_TYPE)
//                                    .setTitleText("Email already used")
//                                    .show();
                        }
                    }){
//                @Override
//                protected Map<String,String> getParams(){
//                    Map<String,String> params = new HashMap<String, String>();
//                    params.put(TAG_USERID, Activity_Login.userId);
//                    return params;
//                }

            };





            RequestQueue requestQueue = Volley.newRequestQueue(OrderList.this);
            requestQueue.add(stringRequest);

            return null;
        }
    }

}
