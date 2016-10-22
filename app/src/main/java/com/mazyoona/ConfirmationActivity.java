package com.mazyoona;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mazyoona.cart.LoginEmptyCartActivity;
import com.mazyoona.customViews.ScrimInsetsFrameLayout;
import com.mazyoona.utils.UtilsDevice;
import com.mazyoona.utils.UtilsMiscellaneous;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmationActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String name = "name";
    String namepref;
    private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private AHBottomNavigation bottomNavigation;
    private ImageView menu_icon;
    TextView textViewId;
    TextView textViewStatus,textViewAmount;
    String amt;
    private RelativeLayout navigation_banner_layout;
    Button home;
    private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
    private ImageView profile_pic;
    private ImageLoader imageLoader;
    private String image_pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        textViewId = (TextView) findViewById(R.id.paymentId);
        textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        textViewAmount = (TextView) findViewById(R.id.paymentAmount);
        home=(Button)findViewById(R.id.home) ;
//        textViewAddress = (TextView) findViewById(R.id.shippingaddress);
        navigation_banner_layout=(RelativeLayout)findViewById(R.id.navigation_drawer_account_section);
        menuLayoutOne=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_one);
        menuLayoutTwo=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_two);
        menuLayoutThree=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_three);
        menuLayoutFour=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_four);
        menuLayoutFive=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_five);
        menuLayoutSix=(FrameLayout)findViewById(R.id.navigation_drawer_items_list_linearLayout_six);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        menu_icon=(ImageView) findViewById(R.id.menu_icon) ;
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
        namepref = prefs.getString("username", "null");
        image_pref = prefs.getString("profile_image", "null");
        if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){
            navigation_username.setText("Welcome "+"Guest");
            navigation_username.setTypeface(tf);
        }else {
            if (name.equals("null") || name.equals("")){
                navigation_username.setText(Activity_Login.username);
                imageLoader.DisplayImage(Activity_Login.profile_image,profile_pic);
            }else {
                navigation_username.setText(namepref);
                imageLoader.DisplayImage(image_pref,profile_pic);
            }
            navigation_username.setTypeface(tf);
        }
        final String[] colors = {"#a41c9a"};
//
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

        bottomNavigation.setCurrentItem(3);

        //Navigation menu item click listener
        menuLayoutOne.setOnClickListener(this);
        menuLayoutTwo.setOnClickListener(this);
        menuLayoutThree.setOnClickListener(this);
        menuLayoutFour.setOnClickListener(this);
        menuLayoutFive.setOnClickListener(this);
        menuLayoutSix.setOnClickListener(this);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        Intent intent= new Intent(ConfirmationActivity.this,HomePage.class);
                        startActivity(intent);
                        ConfirmationActivity.this.finish();
                        break;
                    case 1:
                        break;
                    case 2:
                        Intent intent2= new Intent(ConfirmationActivity.this,SearchActivityServer.class);
                        startActivity(intent2);
                        ConfirmationActivity.this.finish();
                        break;
                    case 3:
                        if (!ShoppingCart.product_names.isEmpty()){
                            Intent scart = new Intent(ConfirmationActivity.this, ShoppingCart.class);
                            startActivity(scart);
                            mDrawerLayout.closeDrawer(GravityCompat.END);
                        }else {
                            Intent scart = new Intent(ConfirmationActivity.this, LoginEmptyCartActivity.class);
                            startActivity(scart);
                            mDrawerLayout.closeDrawer(GravityCompat.END);
                        }
                        break;
                    case 4:
                        Intent intent4= new Intent(ConfirmationActivity.this,ProfilePage.class);
                        startActivity(intent4);
                        ConfirmationActivity.this.finish();
                        break;
                }

            }
        });

        init_navigator();

        navigation_banner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ConfirmationActivity.this,HomePage.class);
                startActivity(intent);
                ConfirmationActivity.this.finish();
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
        //Getting Intent
        final Intent intent = getIntent();

        //Clearing all items from cart
        ShoppingCart.product_names.clear();
        ShoppingCart.product_ids.clear();
        ShoppingCart.purchase_prices.clear();
        ShoppingCart.sale_prices.clear();
        ShoppingCart.avaliablilityArray.clear();
        ShoppingCart.qtyArray.clear();
        ShoppingCart.designer_names.clear();
        ShoppingCart.product_images.clear();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ConfirmationActivity.this,HomePage.class);
                startActivity(intent1);
                ConfirmationActivity.this.finish();
            }
        });

        try {
            if (!intent.getStringExtra("PaymentDetails").equalsIgnoreCase("cash on delivery")) {
                JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

                //Displaying payment details
                showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
                amt=intent.getStringExtra("PaymentAmount");
            }else {
                amt=intent.getStringExtra("PaymentAmount");
                textViewId.setText("cod");
                textViewStatus.setText("Cash On Delivery");
                textViewAmount.setText(amt);
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.navigation_drawer_items_list_linearLayout_one:
                Intent intent = new Intent(this, DesignerList.class);
                this.startActivity(intent);
                ConfirmationActivity.this.finish();
                break;

            case R.id.navigation_drawer_items_list_linearLayout_two:
                Intent dlist = new Intent(this, SpecialOfferPage.class);
                this.startActivity(dlist);
                ConfirmationActivity.this.finish();
                break;

            case R.id.navigation_drawer_items_list_linearLayout_three:
                if (!ShoppingCart.product_names.isEmpty()) {
                    Intent scart = new Intent(this, ShoppingCart.class);
                    this.startActivity(scart);
                    ConfirmationActivity.this.finish();
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    Intent scart = new Intent(this, LoginEmptyCartActivity.class);
                    this.startActivity(scart);
                    ConfirmationActivity.this.finish();
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }
                break;

            case R.id.navigation_drawer_items_list_linearLayout_four:
                Intent faq = new Intent(this, Faq.class);
                this.startActivity(faq);
                ConfirmationActivity.this.finish();
                break;

            case R.id.navigation_drawer_items_list_linearLayout_five:
                Intent about = new Intent(this, AboutUs.class);
                this.startActivity(about);
                ConfirmationActivity.this.finish();
                break;

            case R.id.navigation_drawer_items_list_linearLayout_six:
                Intent cont = new Intent(this, ContactUs.class);
                this.startActivity(cont);
                ConfirmationActivity.this.finish();
                break;
        }
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views


        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" USD");
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
}
