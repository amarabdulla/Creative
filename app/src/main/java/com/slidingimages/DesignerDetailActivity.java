package com.slidingimages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.slidingimages.cart.LoginEmptyCartActivity;
import com.slidingimages.cart.LoginItemCartActivity;
import com.slidingimages.customViews.ScrimInsetsFrameLayout;
import com.slidingimages.utils.UtilsDevice;
import com.slidingimages.utils.UtilsMiscellaneous;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Creative on 19-Sep-16.
 */
public class DesignerDetailActivity extends ActionBarActivity implements View.OnClickListener {
    private static String url ;
    private FrameLayout menuLayoutOne, menuLayoutTwo, menuLayoutThree, menuLayoutFour, menuLayoutFive, menuLayoutSix;
    private ArrayList<String> imagesParseArray = new ArrayList<String>();
    private ArrayList<String> titleParseArray = new ArrayList<String>();
    private ArrayList<String> sale_priceParseArray = new ArrayList<String>();
    private ArrayList<String> purchase_priceParseArray = new ArrayList<String>();
    private ArrayList<String> designer_nameParseArray = new ArrayList<String>();
    private ArrayList<String> avaliablilityParseArray = new ArrayList<String>();
    private ArrayList<String> discountParseArray = new ArrayList<String>();
    private ArrayList<String> qtyArray = new ArrayList<String>();
    private ArrayList<String> productIdArray = new ArrayList<>();
    private ImageView menu_icon;
    private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
    String title;
    String id;
    private AHBottomNavigation bottomNavigation;
    private TextView header;
    private GridView gridView;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private CustomProgressDialog mCustomProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.designer_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("designerName");
            id = extras.getString("designerId");
        }
        navigation_username=(TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
        menuLayoutOne_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_one);
        menuLayoutTwo_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_two);
        menuLayoutThree_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_help_and_three);
        menuLayoutFour_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_four);
        menuLayoutFive_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_five);
        menuLayoutSix_header=(TextView) findViewById(R.id.navigation_drawer_items_textView_six);
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        menuLayoutOne_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutTwo_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutThree_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutFour_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutFive_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutSix_header.setTypeface(tf, Typeface.BOLD);


        if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){
            navigation_username.setText("Welcome "+"Guest");
            navigation_username.setTypeface(tf);
        }else {
            navigation_username.setText("Welcome "+Activity_Login.username);
            navigation_username.setTypeface(tf);
        }

        url =HomePage.FIRSTPART+"designerProfile?designer_id="+id;
        gridView = (GridView) findViewById(R.id.gridView);
        header=(TextView)findViewById(R.id.header_text);
        menuLayoutOne = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_one);
        menuLayoutTwo = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_two);
        menuLayoutThree = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_three);
        menuLayoutFour = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_four);
        menuLayoutFive = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_five);
        menuLayoutSix = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_six);
        menu_icon = (ImageView) findViewById(R.id.menu_icon);
        menu_icon.setClickable(true);
        mCustomProgressDialog = new CustomProgressDialog(DesignerDetailActivity.this);

        header.setText(title);
        ProgressTask progressTask = new ProgressTask();
        progressTask.execute();

        for (int i = 0; i < titleParseArray.size(); i++) {
            qtyArray.add("1");
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
    }
    private void init_navigator() {
        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);


        if (getSupportActionBar() != null) {
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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.navigation_drawer_items_list_linearLayout_one:
                Intent intent = new Intent(this, DesignerList.class);
                this.startActivity(intent);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_two:
                Intent dlist = new Intent(this, SpecialOfferPage.class);
                this.startActivity(dlist);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_three:
                if (!ShoppingCart.product_names.isEmpty()) {
                    Intent scart = new Intent(this, LoginItemCartActivity.class);
                    this.startActivity(scart);
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    Intent scart = new Intent(this, LoginEmptyCartActivity.class);
                    this.startActivity(scart);
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }
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
    private class ProgressTask extends AsyncTask<String, Void, Boolean> {


        protected void onPreExecute() {
            mCustomProgressDialog.show("");

//            dialog.setMessage("Progress start");
//            dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (mCustomProgressDialog.isShowing() || mCustomProgressDialog != null) {
                mCustomProgressDialog.dismiss("");
            }


            for (int i = 0; i < titleParseArray.size(); i++) {
                qtyArray.add("1");
            }
            //Creating GridViewAdapter Object
            GridViewAdapter gridViewAdapter = new GridViewAdapter(DesignerDetailActivity.this, imagesParseArray, titleParseArray,
                    sale_priceParseArray, purchase_priceParseArray, designer_nameParseArray,
                    avaliablilityParseArray, qtyArray, discountParseArray, productIdArray);

            //Adding adapter to gridview
            gridView.setAdapter(gridViewAdapter);

        }

        protected Boolean doInBackground(final String... args) {
            String serverData = null;// String object to store fetched data from server
            // Http Request Code start
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                serverData = EntityUtils.toString(httpEntity);
                Log.d("response", serverData);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                JSONObject jsonObject = new JSONObject(serverData);
                JSONArray jsonArray = jsonObject.getJSONArray("designer_products");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                    String title = jsonObjectUser.getString("title");
                    String url = jsonObjectUser.getString("product_image");
                    String sale_price = jsonObjectUser.getString("sale_price");
                    String purchase_price = jsonObjectUser.getString("discount_price");
                    String designer_name = jsonObjectUser.getString("designer_name");
                    String availability = jsonObjectUser.getString("availability");
                    String discount = jsonObjectUser.getString("discount");
                    String product_id = jsonObjectUser.getString("product_id");


                    titleParseArray.add(title);
                    imagesParseArray.add(url);
                    sale_priceParseArray.add(sale_price);
                    purchase_priceParseArray.add(purchase_price);
                    designer_nameParseArray.add(designer_name);
                    avaliablilityParseArray.add(availability);
                    discountParseArray.add(discount);
                    productIdArray.add(product_id);

                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
            return null;
        }
    }
}
