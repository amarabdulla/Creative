package com.slidingimages.cart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.slidingimages.AboutUs;
import com.slidingimages.Activity_Login;
import com.slidingimages.ContactUs;
import com.slidingimages.DesignerList;
import com.slidingimages.Faq;
import com.slidingimages.HomePage;
import com.slidingimages.ProductPage;
import com.slidingimages.ProfilePage;
import com.slidingimages.R;
import com.slidingimages.SearchActivity;
import com.slidingimages.ShoppingCart;
import com.slidingimages.SpecialOfferPage;
import com.slidingimages.customViews.ScrimInsetsFrameLayout;
import com.slidingimages.utils.UtilsDevice;
import com.slidingimages.utils.UtilsMiscellaneous;

public class LoginEmptyCartActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageButton menu_icon;
    private FrameLayout menuLayoutOne, menuLayoutTwo, menuLayoutThree, menuLayoutFour, menuLayoutFive, menuLayoutSix;
    private Bundle extras;
    private AHBottomNavigation bottomNavigation;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private TextView navigation_username, menuLayoutOne_header, menuLayoutTwo_header, menuLayoutThree_header, menuLayoutFour_header, menuLayoutFive_header, menuLayoutSix_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_login_empty);
        menu_icon = (ImageButton) findViewById(R.id.menu_icon);
        menu_icon.setClickable(true);
        menuLayoutOne = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_one);
        menuLayoutTwo = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_two);
        menuLayoutThree = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_three);
        menuLayoutFour = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_four);
        menuLayoutFive = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_five);
        menuLayoutSix = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_six);
        navigation_username = (TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
        menuLayoutOne_header = (TextView) findViewById(R.id.navigation_drawer_items_textView_one);
        menuLayoutTwo_header = (TextView) findViewById(R.id.navigation_drawer_items_textView_two);
        menuLayoutThree_header = (TextView) findViewById(R.id.navigation_drawer_items_textView_help_and_three);
        menuLayoutFour_header = (TextView) findViewById(R.id.navigation_drawer_items_textView_four);
        menuLayoutFive_header = (TextView) findViewById(R.id.navigation_drawer_items_textView_five);
        menuLayoutSix_header = (TextView) findViewById(R.id.navigation_drawer_items_textView_six);
        String fontPath = "fonts/arial.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        menuLayoutOne_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutTwo_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutThree_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutFour_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutFive_header.setTypeface(tf, Typeface.BOLD);
        menuLayoutSix_header.setTypeface(tf, Typeface.BOLD);
        if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")) {
            navigation_username.setText("Welcome " + "Guest");
            navigation_username.setTypeface(tf);
        } else {
            navigation_username.setText("Welcome " + Activity_Login.username);
            navigation_username.setTypeface(tf);
        }
        getIntentTxt();
        initBtnView();

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

        bottomNavigation.setCurrentItem(3);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:
                        Intent intent = new Intent(LoginEmptyCartActivity.this, HomePage.class);
                        startActivity(intent);
                        LoginEmptyCartActivity.this.finish();
                        break;
                    case 1:
                        Intent intent1 = new Intent(LoginEmptyCartActivity.this, ProductPage.class);
                        startActivity(intent1);
                        LoginEmptyCartActivity.this.finish();
                        break;
                    case 2:
                        Intent intent2 = new Intent(LoginEmptyCartActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        LoginEmptyCartActivity.this.finish();
                        break;
                    case 3:

                        break;
                    case 4:
                        if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){

                        }else {
                            Intent intent4 = new Intent(LoginEmptyCartActivity.this, ProfilePage.class);
                            startActivity(intent4);
                            LoginEmptyCartActivity.this.finish();
                        }
                        break;
                }

            }
        });

        setTitle(null);

        //Navigation menu item click listener
        menuLayoutOne.setOnClickListener(this);
        menuLayoutTwo.setOnClickListener(this);
        menuLayoutThree.setOnClickListener(this);
        menuLayoutFour.setOnClickListener(this);
        menuLayoutFive.setOnClickListener(this);
        menuLayoutSix.setOnClickListener(this);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
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
    }

    private void getIntentTxt() {
        // TODO Auto-generated method stub
        extras = getIntent().getExtras();
    }
    protected void initAlertDialog() {
        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("Sign in required").
                setMessage("To continue please sign in").
                setIcon(R.drawable.ic_launcher).
                setPositiveButton("Sign in", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String none = "none";
                        Intent intent = new Intent(LoginEmptyCartActivity.this,Activity_Login.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();

        alertDialog.show();


    }
    public void onClick(View v) {

        switch (v.getId()) {

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

    private void initBtnView() {

        Button btn_shopNow = (Button) findViewById(R.id.login_empty_shopnow);

        btn_shopNow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginEmptyCartActivity.this, HomePage.class);
                startActivity(intent);
                LoginEmptyCartActivity.this.finish();
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

//        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
    }


}
