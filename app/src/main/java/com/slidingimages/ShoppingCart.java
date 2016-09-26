package com.slidingimages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;

/**
 * Created by Amar on 9/2/2016.
 */
public class ShoppingCart extends Activity {
    private ListView listView;
    private Button apply,checkout;
    private EditText promocode;
    TextView empty,total_quanity,total;
    private AHBottomNavigation bottomNavigation;

    CustomListShoppingCart customListShoppingCart;
    public static ArrayList<String> product_names = new ArrayList<>();
    public static ArrayList<String> product_images =  new ArrayList<>();
    public static ArrayList<String> sale_prices =  new ArrayList<>();
    public static ArrayList<String> purchase_prices =  new ArrayList<>();
    public static ArrayList<String> designer_names =  new ArrayList<>();
    public static ArrayList<String> avaliablilityArray =  new ArrayList<>();
    public static ArrayList<String> qtyArray =  new ArrayList<>();
    public static ArrayList<String> product_ids =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        final String[] colors = {"#a41c9a"};

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
                        Intent intent2= new Intent(ShoppingCart.this,SearchActivity.class);
                        startActivity(intent2);
                        ShoppingCart.this.finish();
                        break;
                    case 3:
                        break;
                    case 4:
                        Intent intent4= new Intent(ShoppingCart.this,ProfilePage.class);
                        startActivity(intent4);
                        ShoppingCart.this.finish();
                        break;
                }

            }
        });


        Bundle bundle = getIntent().getExtras();
        if( getIntent().getExtras() != null)
        {
            String p_name = bundle.getString("FILES_TO_SEND_1");
            String s_price= bundle.getString("FILES_TO_SEND_2");
            String p_price = bundle.getString("FILES_TO_SEND_3");
            String image = bundle.getString("FILES_TO_SEND_5");
            String d_name = bundle.getString("FILES_TO_SEND_4");
            String avaliablity = bundle.getString("FILES_TO_SEND_6");
            product_names.add(p_name);
            product_images.add(image);
            sale_prices.add(s_price);
            purchase_prices.add(p_price);
            designer_names.add(d_name);
            avaliablilityArray.add(avaliablity);
        }



        apply=(Button)findViewById(R.id.apply);
        checkout=(Button)findViewById(R.id.check_out);
        promocode=(EditText) findViewById(R.id.promo_code);
        empty=(TextView)findViewById(R.id.empty);
        total_quanity=(TextView)findViewById(R.id.total_quanity) ;
        total=(TextView)findViewById(R.id.subtotal) ;
        listView = (ListView) findViewById(R.id.listView);

        if (product_names.size()!=0 || !product_names.isEmpty()){
            customListShoppingCart = new CustomListShoppingCart(this, product_names, product_images, sale_prices);
            listView.setAdapter(customListShoppingCart);
            total_quanity.setText(product_names.size()+"");
            if (sale_prices.size()!=0 || !sale_prices.isEmpty()){
                total.setText((int) total_sale_price()+" AED");
            }else {
                total.setText("0");
            }
        }else {
            empty.setText("No items!");
            total_quanity.setText("0");
        }


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShoppingCart.this,ShippingPage.class);
                startActivity(intent);
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (promocode.getText().equals("")){
                  Toast.makeText(getApplicationContext(),"Please enter your code",Toast.LENGTH_SHORT);
              }
            }
        });
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
            sum += (int)Double.parseDouble(purchase_prices.get(i));
        }
        return sum;
    }
}
