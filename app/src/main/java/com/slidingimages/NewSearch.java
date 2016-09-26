package com.slidingimages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Creative on 03-Sep-16.
 */
import android.content.Context;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

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

public class NewSearch extends ActionBarActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private ProgressDialog dialog;
    private GridView gridView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private ArrayList<String> imagesParseArray = new ArrayList<String>();
    private ArrayList<String> titleParseArray = new ArrayList<String>();
    private ArrayList<String> sale_priceParseArray = new ArrayList<String>();
    private ArrayList<String> purchase_priceParseArray = new ArrayList<String>();
    private ArrayList<String> designer_nameParseArray = new ArrayList<String>();
    private ArrayList<String> avaliablilityParseArray = new ArrayList<String>();
    private ArrayList<String> qtyArray = new ArrayList<String>();

    private static String url = "http://192.168.0.109/creative/mazyoona/index.php/rest/api/products";
    private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
    private Button product,home,cart,profile;
    GridViewAdapter gridViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        product=(Button)findViewById(R.id.product) ;
        home=(Button)findViewById(R.id.home) ;
        cart=(Button)findViewById(R.id.cart) ;
        profile=(Button)findViewById(R.id.profile) ;
        gridView = (GridView) findViewById(R.id.gridView);
        menuLayoutOne=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_one);
        menuLayoutTwo=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_two);
        menuLayoutThree=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_three);
        menuLayoutFour=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_four);
        menuLayoutFive=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_five);
        menuLayoutSix=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_six);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NewSearch.this,HomePage.class);
                startActivity(intent);
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NewSearch.this,ProductPage.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NewSearch.this,ShoppingCart.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NewSearch.this,ProfilePage.class);
                startActivity(intent);
            }
        });
        init_navigator();
        dialog= new ProgressDialog(this);

        ProgressTask progressTask=new ProgressTask();
        progressTask.execute();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.navigation_drawer_items_list_linearLayout_one: /** Start a new Activity MyCards.java */
                Intent intent = new Intent(this, DesignerList.class);
                this.startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_two: /** AlerDialog when click on Exit */
                Intent dlist = new Intent(this, SpecialOfferPage.class);
                this.startActivity(dlist);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_three: /** AlerDialog when click on Exit */
                Intent scart = new Intent(this, ShoppingCart.class);
                this.startActivity(scart);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_four: /** AlerDialog when click on Exit */
                Intent faq = new Intent(this, Faq.class);
                this.startActivity(faq);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_five: /** AlerDialog when click on Exit */
                Intent about = new Intent(this, AboutUs.class);
                this.startActivity(about);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.navigation_drawer_items_list_linearLayout_six: /** AlerDialog when click on Exit */
                Intent cont = new Intent(this, ContactUs.class);
                this.startActivity(cont);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }



    private class ProgressTask extends AsyncTask<String, Void, Boolean> {

        protected void onPreExecute() {
            dialog.setMessage("Progress start");
            dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            for (int i=0;i<titleParseArray.size();i++){
                qtyArray.add("1");
            }

//            //Creating GridViewAdapter Object
//            gridViewAdapter = new GridViewAdapter(NewSearch.this, imagesParseArray, titleParseArray,
//                    sale_priceParseArray,purchase_priceParseArray,designer_nameParseArray,avaliablilityParseArray,qtyArray,d);
//
//            //Adding adapter to gridview
//            gridView.setAdapter(gridViewAdapter);
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    Intent intent= new Intent(NewSearch.this,ProductDescriptionPage.class);
//                    startActivity(intent);
//
//                }
//            });

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
                JSONArray jsonArray = jsonObject.getJSONArray("products");
//                    JSONArray jsonArray = jsonObject.getJSONArray("worldpopulation");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                    String name = jsonObjectUser.getString("title");
                    String url = jsonObjectUser.getString("product_image");
                    String sale_price = jsonObjectUser.getString("sale_price");
                    String purchase_price = jsonObjectUser.getString("discount_price");
                    String designer_name = jsonObjectUser.getString("designer_name");
                    String availability = jsonObjectUser.getString("availability");

                    titleParseArray.add(name);
                    imagesParseArray.add(url);
                    sale_priceParseArray.add(sale_price);
                    purchase_priceParseArray.add(purchase_price);
                    designer_nameParseArray.add(designer_name);
                    avaliablilityParseArray.add(availability);
                }


            }
            catch (JSONException e) {
                e.printStackTrace();

            }
            return null;
        }
    }


    private void init_navigator(){
        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);



        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
        // Set the first item as selected for the first time
//        getSupportActionBar().setTitle(R.string.toolbar_title_home);


    }
}
