package com.slidingimages;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingimages.cart.LoginEmptyCartActivity;
import com.slidingimages.cart.LoginItemCartActivity;
import com.slidingimages.customViews.ScrimInsetsFrameLayout;
import com.slidingimages.utils.UtilsDevice;
import com.slidingimages.utils.UtilsMiscellaneous;

/**
 * Created by Creative on 18-Sep-16.
 */
public class PrivacyPolicy extends ActionBarActivity implements View.OnClickListener {
    private static final String name = "name";
    String namepref;
    private WebView webview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;
    private static String URL=HomePage.FIRSTPART+"page/privacy_policy";
    private ImageView menu_icon;
    private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);
        menuLayoutOne = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_one);
        menuLayoutTwo = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_two);
        menuLayoutThree = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_three);
        menuLayoutFour = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_four);
        menuLayoutFive = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_five);
        menuLayoutSix = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_six);
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
        menu_icon = (ImageView) findViewById(R.id.menu_icon);
        menu_icon.setClickable(true);
        this.webview = (WebView) findViewById(R.id.web_view);

        menuLayoutOne.setOnClickListener(this);
        menuLayoutTwo.setOnClickListener(this);
        menuLayoutThree.setOnClickListener(this);
        menuLayoutFour.setOnClickListener(this);
        menuLayoutFive.setOnClickListener(this);
        menuLayoutSix.setOnClickListener(this);
        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setTitle(null);
        setSupportActionBar(topToolBar);

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
        init_navigator();

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
//        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(PrivacyPolicy.this, "", "Please wait..");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
//                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("No network found");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        webview.loadUrl(URL);
    }
    @Override
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
                mDrawerLayout.closeDrawer(GravityCompat.END);
                break;
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
}
