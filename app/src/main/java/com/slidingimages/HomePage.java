package com.slidingimages;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.slidingimages.cart.LoginEmptyCartActivity;
import com.slidingimages.cart.LoginItemCartActivity;
import com.slidingimages.customViews.ScrimInsetsFrameLayout;
import com.slidingimages.utils.UtilsDevice;
import com.slidingimages.utils.UtilsMiscellaneous;
import com.viewpagerindicator.CirclePageIndicator;

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
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends ActionBarActivity implements View.OnClickListener{
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
//    private static String url = "http://192.168.0.109/creative/mazyoona/index.php/rest/api/products";
    public static final String FIRSTPART="http://52.210.59.100/project/mazyoona/index.php/rest/api/";
    private static String url = FIRSTPART+"featured";
    private static final String companyName = "companyName";
    private static final String name = "name";
    private static final String urls = "url";
    private Button product,search,cart,profile;
    private GridView gridView;
    private ImageView menu_icon;
    private Integer currentMenuPosition=0;
    private TextView seeAll;
    private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
    private ArrayList<String> bannerImageArray = new ArrayList<>();
    private ArrayList<String> imagesParseArray = new ArrayList<String>();
    private ArrayList<String> titleParseArray = new ArrayList<String>();
    private ArrayList<String> sale_priceParseArray = new ArrayList<String>();
    private ArrayList<String> purchase_priceParseArray = new ArrayList<String>();
    private ArrayList<String> designer_nameParseArray = new ArrayList<String>();
    private ArrayList<String> avaliablilityParseArray = new ArrayList<String>();
    private ArrayList<String> discountParseArray = new ArrayList<String>();
    private ArrayList<String> qtyArray = new ArrayList<String>();
    private ArrayList<String> productIdArray= new ArrayList<>();
    private ArrayList<String> descArray= new ArrayList<>();
    private AHBottomNavigation bottomNavigation;
    private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private CustomProgressDialog mCustomProgressDialog;
    String useridpref,namepref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        gridView = (GridView) findViewById(R.id.gridView);
        seeAll=(TextView)findViewById(R.id.see_all) ;
        menu_icon=(ImageView) findViewById(R.id.menu_icon) ;
        menu_icon.setClickable(true);
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
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        menuLayoutOne_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutTwo_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutThree_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutFour_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutFive_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutSix_header.setTypeface(tf, Typeface.BOLD);
        SharedPreferences prefs = getSharedPreferences(Activity_Login.MY_PREFS_NAME, MODE_PRIVATE);
        namepref = prefs.getString("username", "null");//"No name defined" is the default value.
        useridpref = prefs.getString("userid", "null");
        System.out.println("name in pref"+name);

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




        mCustomProgressDialog = new CustomProgressDialog(HomePage.this);

        final String[] colors = {"#a41c9a"};
         bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

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

        bottomNavigation.setAccentColor(Color.parseColor("#a41c9a"));
        bottomNavigation.setInactiveColor(Color.parseColor("#a41c9a"));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        Intent intent1= new Intent(HomePage.this,ProductPage.class);
                        startActivity(intent1);
                        HomePage.this.finish();
                        break;
                    case 2:
                        Intent intent2= new Intent(HomePage.this,SearchActivity.class);
                        startActivity(intent2);
                        HomePage.this.finish();
                        break;
                    case 3:
                        if (ShoppingCart.product_names.isEmpty()){
                            Intent intent3= new Intent(HomePage.this,LoginEmptyCartActivity
                                    .class);
                            startActivity(intent3);
                            HomePage.this.finish();
                        }else {
                            Intent intent3 = new Intent(HomePage.this, LoginItemCartActivity
                                    .class);
                            startActivity(intent3);
                        }

                        break;
                    case 4:
                        Intent intent4= new Intent(HomePage.this,ProfilePage.class);
                        startActivity(intent4);

                        HomePage.this.finish();
                        break;
                }

            }
        });

         setTitle(null);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
//        topToolBar.setLogo(R.drawable.ic_drawer);
        topToolBar.setLogoDescription(getResources().getString(R.string.app_name));

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


        bannerImageArray.add("http://www.latestasianfashions.com/wp-content/uploads/2012/10/Bokitta-New-Head-Scarves-Winter-Collection-2012-Banner-www.latestasianfashions.jpg");
        bannerImageArray.add("http://sobyexports.com/blog/wp-content/uploads/2015/08/2349092452176d38a07a112ab1a37bd53a3a32ad_abayas.jpg");
        bannerImageArray.add("http://usabaya.com/wp-content/uploads/2015/06/Botika-Latest-Fashion-Matching-Scarves-Collection-2012-Banner-www.Fashionhuntworld.Blogspot.com_.jpg");



        init();

        ProgressTask progressTask=new ProgressTask();
        progressTask.execute();




        //Navigation menu item click listener
        menuLayoutOne.setOnClickListener(this);
        menuLayoutTwo.setOnClickListener(this);
        menuLayoutThree.setOnClickListener(this);
        menuLayoutFour.setOnClickListener(this);
        menuLayoutFive.setOnClickListener(this);
        menuLayoutSix.setOnClickListener(this);
        seeAll.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(HomePage.this,ProductDescriptionPage.class);
                startActivity(intent);
            }
        });

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
                    Intent scart = new Intent(this, LoginItemCartActivity.class);
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
            case R.id.see_all:
                Intent pro = new Intent(this, ProductPage.class);
                this.startActivity(pro);
                break;
        }
    }


    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        BannerViewPager_Adapter bannerViewPager_adapter = new BannerViewPager_Adapter(HomePage.this, bannerImageArray);
        mPager.setAdapter(bannerViewPager_adapter);
        bannerViewPager_adapter.notifyDataSetChanged();

        mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomePage.this,ProductDescriptionPage.class);
                startActivity(intent);
            }
        });

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);

        NUM_PAGES = bannerImageArray.size();


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

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

            for (int i=0;i<titleParseArray.size();i++){
                qtyArray.add("1");
            }



            //Creating GridViewAdapter Object
            GridViewAdapter gridViewAdapter = new GridViewAdapter(HomePage.this, imagesParseArray, titleParseArray,
                    sale_priceParseArray,
                    purchase_priceParseArray,designer_nameParseArray,avaliablilityParseArray,qtyArray,discountParseArray,productIdArray,descArray);

            //Adding adapter to gridview
//            gridView.setAdapter(gridViewAdapter);

            ExpandableHeightGridView gridView = (ExpandableHeightGridView) findViewById(R.id.gridView);
            gridView.setAdapter(gridViewAdapter);
            gridView.setExpanded(true);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent= new Intent(HomePage.this,ProductDescriptionPage.class);
                    startActivity(intent);

                }
            });

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
                    JSONArray jsonArray = jsonObject.getJSONArray("featured");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                        String name = jsonObjectUser.getString("title");
                        String url = jsonObjectUser.getString("product_image");
                        String sale_price = jsonObjectUser.getString("sale_price");
                        String purchase_price = jsonObjectUser.getString("discount_price");
                        String designer_name = jsonObjectUser.getString("designer_name");
                        String availability = jsonObjectUser.getString("availability");
                        String discount=jsonObjectUser.getString("discount");
                        String product_id=jsonObjectUser.getString("product_id");
                        String desc=jsonObjectUser.getString("description");

                        titleParseArray.add(name);
                        imagesParseArray.add(url);
                        sale_priceParseArray.add(sale_price);
                        purchase_priceParseArray.add(purchase_price);
                        designer_nameParseArray.add(designer_name);
                        avaliablilityParseArray.add(availability);
                        discountParseArray.add(discount);
                        productIdArray.add(product_id);
                        descArray.add(desc);
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



        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

//        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
    }

    @Override
    protected void onResume() {
        bottomNavigation.setCurrentItem(0);
        super.onResume();
    }
}
