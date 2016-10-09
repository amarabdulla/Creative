package com.slidingimages;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.slidingimages.app.AppStatus;
import com.slidingimages.cart.LoginEmptyCartActivity;
import com.slidingimages.cart.LoginItemCartActivity;
import com.slidingimages.customViews.ScrimInsetsFrameLayout;
import com.slidingimages.utils.UtilsDevice;
import com.slidingimages.utils.UtilsMiscellaneous;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Creative on 04-Sep-16.
 */
public class ProfilePage extends ActionBarActivity implements View.OnClickListener {
    private ImageView menu_icon;
//    private TextView tc,privacy,my_order;
    private static final String name = "name";
    String namepref;
    private AHBottomNavigation bottomNavigation;
    private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
    private TextView textViewPhone_txt,textViewEmail_txt,textViewCity_txt;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private CustomProgressDialog mCustomProgressDialog;
    String userid="2";
    private String url = "";
    public static final String KEY_USERID = "user_id";
    FloatingActionButton floatingActionButton;
    private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
    private TextView textViewUsername;
    private String username,surname,email,city,address1,address2,phone,zip;
    ListView list;
    String[] menu_item = {
            "My Orders",
            "Terms and Condition",
            "Privacy Policy" };
    Integer[] imageId = {
            R.drawable.my_order_icon,
            R.drawable.terms_icon,
            R.drawable.privacy_icon
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_test);
        final String[] colors = {"#a41c9a"};
        list=(ListView)findViewById(R.id.list);
        textViewUsername=(TextView)findViewById(R.id.username);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        SharedPreferences prefs = getSharedPreferences(Activity_Login.MY_PREFS_NAME, MODE_PRIVATE);
        String name_pref= prefs.getString("username", "null");
        String userid_pref= prefs.getString("userid", "null");
        if (name_pref.equals("") || userid_pref.equals("")|| name_pref.equals("null")|| userid_pref.equals("null")){
            url = HomePage.FIRSTPART + "profileView?user_id=" + Activity_Login.userId;
        }else {
            url = HomePage.FIRSTPART + "profileView?user_id=" +userid_pref;
        }
        textViewEmail_txt=(TextView)findViewById(R.id.email_txt);
        textViewPhone_txt=(TextView)findViewById(R.id.phone);
        textViewCity_txt=(TextView)findViewById(R.id.city_txt);
        if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){
            textViewEmail_txt.setVisibility(View.GONE);
            textViewCity_txt.setVisibility(View.GONE);
        }else {

            textViewEmail_txt.setVisibility(View.VISIBLE);
            textViewCity_txt.setVisibility(View.VISIBLE);
            if ((name_pref.equals("") ||name_pref.equals("null"))){
//                textViewNameBanner.setText(Activity_Login.username);
            }else {
//                textViewNameBanner.setText(name_pref);
            }

        }
        ProfileViewCustomListAdapter adapter = new
                ProfileViewCustomListAdapter(ProfilePage.this, menu_item, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position){
                    case 0:
                        Intent intent11= new Intent(ProfilePage.this,OrderList.class);
                        startActivity(intent11);
                        break;
                    case 1:
                        Intent intent= new Intent(ProfilePage.this,TermsAndCondition.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent2= new Intent(ProfilePage.this,PrivacyPolicy.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        mCustomProgressDialog = new CustomProgressDialog(ProfilePage.this);
        menuLayoutOne=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_one);
        menuLayoutTwo=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_two);
        menuLayoutThree=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_three);
        menuLayoutFour=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_four);
        menuLayoutFive=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_five);
        menuLayoutSix=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_six);
        menu_icon=(ImageView) findViewById(R.id.menu_icon) ;
        menu_icon.setClickable(true);
        navigation_username=(TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
        menuLayoutOne_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_one);
        menuLayoutTwo_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_two);
        menuLayoutThree_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_help_and_three);
        menuLayoutFour_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_four);
        menuLayoutFive_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_five);
        menuLayoutSix_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_six);
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        menuLayoutOne_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutTwo_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutThree_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutFour_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutFive_header.setTypeface(tf, Typeface.NORMAL);
        menuLayoutSix_header.setTypeface(tf, Typeface.NORMAL);
        namepref = prefs.getString("username", "null");
        if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){
            navigation_username.setText("Welcome "+"Guest");
            navigation_username.setTypeface(tf);
        }else {
            if (name.equals("null") || name.equals("")){
                navigation_username.setText(Activity_Login.username);
            }else {
                navigation_username.setText(namepref);
            }
            navigation_username.setTypeface(tf);
        }

        init_navigator();

        if (AppStatus.getInstance(this).isOnline()) {
            ProgressTask progressTask=new ProgressTask();
            progressTask.execute();
        }else {
            new SweetAlertDialog(ProfilePage.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Please check your network")
                    .show();
        }



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ProfilePage.this,Activity_Login.class);
                startActivity(intent);
                SharedPreferences.Editor editor = getSharedPreferences(Activity_Login.MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                ShoppingCart.product_names.clear();
                ShoppingCart.product_ids.clear();
                ShoppingCart.product_images.clear();
                ShoppingCart.purchase_prices.clear();
                ShoppingCart.sale_prices.clear();
                ShoppingCart.designer_names.clear();
                ShoppingCart.qtyArray.clear();
                ShoppingCart.avaliablilityArray.clear();
                ProfilePage.this.finish();
            }
        });

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

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.home), R.drawable.home_icon, Color.parseColor(colors[0]));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.product_lowcase), R.drawable.category_icon, Color.parseColor(colors[0]));
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.search), R.drawable.search_icon, Color.parseColor(colors[0]));
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.shopping_cart_lowcase), R.drawable.cart_icon, Color.parseColor(colors[0]));
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(getString(R.string.my_profile), R.drawable.user_icon, Color.parseColor(colors[0]));

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#a41c9a"));

        bottomNavigation.setAccentColor(Color.parseColor("#FFFFFF"));
        bottomNavigation.setInactiveColor(Color.parseColor("#FFFFFF"));

        //  Enables Reveal effect
//        bottomNavigation.setColored(true);

        bottomNavigation.setCurrentItem(4);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:
                        Intent intent= new Intent(ProfilePage.this,HomePage.class);
                        ProfilePage.this.finish();
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1= new Intent(ProfilePage.this,ProductPage.class);
                        startActivity(intent1);
                        ProfilePage.this.finish();
                        break;
                    case 2:
                        Intent intent2= new Intent(ProfilePage.this,SearchActivity.class);
                        startActivity(intent2);
                        ProfilePage.this.finish();
                        break;
                    case 3:
                        if (ShoppingCart.product_names.isEmpty()){
                            Intent intent3= new Intent(ProfilePage.this,LoginEmptyCartActivity
                                    .class);
                            startActivity(intent3);
                            ProfilePage.this.finish();
                        }else {
                            Intent intent3 = new Intent(ProfilePage.this, LoginItemCartActivity.class);
                            startActivity(intent3);
                        }
                        break;
                    case 4:
                        break;
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
                break;

            case R.id.navigation_drawer_items_list_linearLayout_two:
                Intent dlist = new Intent(this, SpecialOfferPage.class);
                this.startActivity(dlist);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_three:
                Intent scart = new Intent(this, ShoppingCart.class);
                this.startActivity(scart);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_four:
                Intent faq = new Intent(this, Faq.class);
                this.startActivity(faq);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_five:
                Intent about = new Intent(this, AboutUs.class);
                this.startActivity(about);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_six:
                Intent cont = new Intent(this, ContactUs.class);
                this.startActivity(cont);
                break;
        }
    }
    @Override
    protected void onResume() {
        bottomNavigation.setCurrentItem(4);
        super.onResume();
    }
    private void init_navigator(){
        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);



        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
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
        @Override
        protected Boolean doInBackground(final String... args) {

            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
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
                                    username = jsonObjectUser.getString("firstname");
                                    surname = jsonObjectUser.getString("lastname");
                                    email = jsonObjectUser.getString("email");
                                    phone = jsonObjectUser.getString("phone");
                                    address1 = jsonObjectUser.getString("address1");
                                    address2 = jsonObjectUser.getString("address2");
                                    city=jsonObjectUser.getString("city");
                                    zip=jsonObjectUser.getString("zip");

                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textViewUsername.setText(username);
                                        textViewCity_txt.setText(city);
                                        textViewEmail_txt.setText(email);
                                        textViewPhone_txt.setText(phone);
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
                            new SweetAlertDialog(ProfilePage.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Please try again")
                                    .show();
                        }
                    }
            );


            RequestQueue requestQueue = Volley.newRequestQueue(ProfilePage.this);
            requestQueue.add(getRequest);


            return null;
        }
    }
}
