package com.mazyoona;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mazyoona.app.Api;
import com.mazyoona.cart.LoginEmptyCartActivity;
import com.mazyoona.customViews.ScrimInsetsFrameLayout;
import com.mazyoona.utils.UtilsDevice;
import com.mazyoona.utils.UtilsMiscellaneous;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Amar on 9/2/2016.
 */
public class ShoppingCart extends Activity implements View.OnClickListener{
    private ListView listView;
    private Button apply,checkout;
    private EditText promocode;
    private String promo_code_text;
    public static Integer promo_amount=0;
    TextView empty,total_quanity,total;
    private AHBottomNavigation bottomNavigation;
    private CustomProgressDialog mCustomProgressDialog;
    CustomListShoppingCart customListShoppingCart;
    public static ArrayList<String> product_names = new ArrayList<>();
    public static ArrayList<String> product_images =  new ArrayList<>();
    public static ArrayList<String> sale_prices =  new ArrayList<>();
    public static ArrayList<String> purchase_prices =  new ArrayList<>();
    public static ArrayList<String> designer_names =  new ArrayList<>();
    public static ArrayList<String> avaliablilityArray =  new ArrayList<>();
    public static ArrayList<Integer> qtyArray =  new ArrayList<>();
    public static ArrayList<String> product_ids =  new ArrayList<>();
    Typeface tf;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    String userid_pref,image_pref;
    private String urlprofileView ="";
    private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
    private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
    private ImageView profile_pic;
    ImageLoader imageLoader;
    private static final String name = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);
        mCustomProgressDialog = new CustomProgressDialog(ShoppingCart.this);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
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
        final String[] colors = {"#a41c9a"};
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        String fontPath = "fonts/arial.ttf";
        tf = Typeface.createFromAsset(getAssets(), fontPath);

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

        bottomNavigation.setCurrentItem(3);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        Intent intent= new Intent(ShoppingCart.this,HomePage.class);
                        ShoppingCart.this.finish();
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1= new Intent(ShoppingCart.this,ProductPage.class);
                        startActivity(intent1);
                        ShoppingCart.this.finish();
                        break;
                    case 2:
                        Intent intent2= new Intent(ShoppingCart.this,SearchActivityServer.class);
                        startActivity(intent2);
                        ShoppingCart.this.finish();
                        break;
                    case 3:
                        break;
                    case 4:
                        if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){
//                            initAlertDialog();
                            new SweetAlertDialog(ShoppingCart.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Please login to continue")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent intent = new Intent(ShoppingCart.this,Activity_Login.class);
                                            startActivity(intent);
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .setCancelText("Cancel")
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            bottomNavigation.setCurrentItem(0);
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .show();
                        }else {
                            Intent intent4 = new Intent(ShoppingCart.this, ProfilePage.class);
                            startActivity(intent4);
                            ShoppingCart.this.finish();
                        }
                        break;
                }

            }
        });

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

        //Navigation menu item click listener
        menuLayoutOne.setOnClickListener(this);
        menuLayoutTwo.setOnClickListener(this);
        menuLayoutThree.setOnClickListener(this);
        menuLayoutFour.setOnClickListener(this);
        menuLayoutFive.setOnClickListener(this);
        menuLayoutSix.setOnClickListener(this);

        apply=(Button)findViewById(R.id.apply);
        checkout=(Button)findViewById(R.id.check_out);
        promocode=(EditText) findViewById(R.id.promo_code);
        checkout.setTypeface(tf);
        apply.setTypeface(tf);
//        empty=(TextView)findViewById(R.id.empty);
        total_quanity=(TextView)findViewById(R.id.total_quanity) ;
        total=(TextView)findViewById(R.id.subtotal) ;
        listView = (ListView) findViewById(R.id.listView);

        if (product_names.size()!=0 || !product_names.isEmpty()){
            customListShoppingCart = new CustomListShoppingCart(this, product_names, product_images, purchase_prices);
            listView.setAdapter(customListShoppingCart);
            total_quanity.setText(total_qty()+"");
            if (purchase_prices.size()!=0 || !purchase_prices.isEmpty()){
                total.setText((int) total_sale_price()+" AED");
            }else {
                total.setText("0");
            }
        }else {
//            empty.setText("No items!");
            total_quanity.setText("0");
        }
        customListShoppingCart.setOnDataChangeListener(new CustomListShoppingCart.OnDataChangeListener(){
            public void onDataChanged(int size){
                total_quanity.setText(total_qty()+"");
                total.setText((int) total_sale_price()+" AED");
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Activity_Login.username.equals("temp")) {
                if (!ShoppingCart.product_names.isEmpty()) {
                    Intent intent = new Intent(ShoppingCart.this, ShippingPage.class);
                    startActivity(intent);
                }else {
                    new SweetAlertDialog(ShoppingCart.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("No items in your cart")
                            .show();
                }
                }else {
                    new SweetAlertDialog(ShoppingCart.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Please login to continue")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Intent intent= new Intent(ShoppingCart.this,Activity_Login.class);
                                    startActivity(intent);
                                    ShoppingCart.this.finish();
                                }
                            })
                            .show();

                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (promocode.getText().toString().equals("")){
                  Toast.makeText(getApplicationContext(),"Please enter your code",Toast.LENGTH_SHORT).show();
              }else {
                  promo_code_text=promocode.getText().toString();
                  ProgressTask progressTask=new ProgressTask();
                  progressTask.execute();
              }
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

            String url= Api.PROMO_CODE+"?code="+promo_code_text;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                                result="success";
//                                Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                            try {
                                JSONObject mainObject = new JSONObject(response.toString());
                                System.out.println(response.toString());
                                String result = mainObject.getString("status");
                                 promo_amount = mainObject.getInt("couponPrice");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        total.setText((int) total_sale_price()-promo_amount+" AED");
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            new SweetAlertDialog(ShoppingCart.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("You have saved"+" "+promo_amount+" AED")
                                    .show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Activity_Login.this,error.toString(),Toast.LENGTH_LONG).show();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                            Toast.makeText(Activity_Login.this,"successful",Toast.LENGTH_LONG).show();
                                        new SweetAlertDialog(ShoppingCart.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Promo code invalid")
                                                .show();
                                }
                            });
                        }
                    }){


            };

            RequestQueue requestQueue = Volley.newRequestQueue(ShoppingCart.this);
            requestQueue.add(stringRequest);

            return null;
        }
    }

    @Override
    protected void onResume() {
        bottomNavigation.setCurrentItem(3);
        super.onResume();
    }
    public double total_sale_price()
    {
        double sum = 0;
        for(int i = 0; i < purchase_prices.size(); i++)
        {
            sum += (int)Double.parseDouble(purchase_prices.get(i))*qtyArray.get(i);
        }
        return sum;
    }
    public double total_qty()
    {
        Integer sum = 0;
        for(int i = 0; i < qtyArray.size(); i++)
        {
            sum += Integer.parseInt(String.valueOf(qtyArray.get(i)));
        }
        return sum;
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
