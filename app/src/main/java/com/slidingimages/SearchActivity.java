package com.slidingimages;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.slidingimages.app.AppStatus;
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
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SearchActivity extends ActionBarActivity implements View.OnClickListener{

	// Declare Variables
	GridView gridView;
	GridViewAdapterSearch adapter;
	private FrameLayout menuLayoutOne,menuLayoutTwo,menuLayoutThree,menuLayoutFour,menuLayoutFive,menuLayoutSix;
	EditText editsearch;
	private static final String name = "name";
	String namepref;
	private ImageView menu_icon;
	private ProgressDialog dialog;
	private Button search_close;
	private ArrayList<String> imagesParseArray = new ArrayList<String>();
	private ArrayList<String> titleParseArray = new ArrayList<String>();
	private ArrayList<String> sale_priceParseArray = new ArrayList<String>();
	private ArrayList<String> purchase_priceParseArray = new ArrayList<String>();
	private ArrayList<String> designer_nameParseArray = new ArrayList<String>();
	private AHBottomNavigation bottomNavigation;
	private DrawerLayout mDrawerLayout;
	private CustomProgressDialog mCustomProgressDialog;
	private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
	private TextView navigation_username,menuLayoutOne_header,menuLayoutTwo_header,menuLayoutThree_header,menuLayoutFour_header,menuLayoutFive_header,menuLayoutSix_header;
//	private static String FIRSTPART="http://52.210.59.100/project/mazyoona/index.php/rest/api/";
	private static String url = HomePage.FIRSTPART+"products";
//	private static String url = "http://192.168.0.109/creative/mazyoona/index.php/rest/api/products";
	ArrayList<ProductModelClass> arraylist = new ArrayList<ProductModelClass>();
	private ArrayList<String> avaliablilityParseArray = new ArrayList<String>();
	private ArrayList<String> discountParseArray = new ArrayList<String>();
	private ArrayList<String> qtyArray = new ArrayList<String>();
	private ArrayList<String> productIdArray= new ArrayList<>();
	private ArrayList<String> descArray= new ArrayList<>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		menu_icon=(ImageView) findViewById(R.id.menu_icon) ;
		menu_icon.setClickable(true);
		search_close=(Button)findViewById(R.id.delete);
		mCustomProgressDialog = new CustomProgressDialog(SearchActivity.this);
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
		namepref = prefs.getString("username", "null");
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
		final String[] colors = {"#a41c9a"};

		// Locate the ListView in listview_main.xml
		gridView = (GridView) findViewById(R.id.gridView);
		bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
		dialog= new ProgressDialog(this);

		if (AppStatus.getInstance(this).isOnline()) {
			ProgressTask progressTask=new ProgressTask();
			progressTask.execute();
		}else {
			new SweetAlertDialog(SearchActivity.this, SweetAlertDialog.WARNING_TYPE)
					.setTitleText("Please check your network")
					.show();
		}


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
//		bottomNavigation.setColored(true);

		bottomNavigation.setCurrentItem(2);

		bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position, boolean wasSelected) {
				// Do something cool here...
				switch (position) {
					case 0:
                        Intent intent= new Intent(SearchActivity.this,HomePage.class);
						SearchActivity.this.finish();
                        startActivity(intent);
						break;
					case 1:
						Intent intent1= new Intent(SearchActivity.this,ProductPage.class);
						startActivity(intent1);
						SearchActivity.this.finish();
						break;
					case 2:

						break;
					case 3:
						if (!ShoppingCart.product_names.isEmpty()){
							Intent scart = new Intent(SearchActivity.this, ShoppingCart.class);
							startActivity(scart);
							mDrawerLayout.closeDrawer(GravityCompat.END);
						}else {
							Intent scart = new Intent(SearchActivity.this, LoginEmptyCartActivity.class);
							startActivity(scart);
							mDrawerLayout.closeDrawer(GravityCompat.END);
						}
						break;
					case 4:
						if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){
							initAlertDialog();
						}else {
							Intent intent4 = new Intent(SearchActivity.this, ProfilePage.class);
							startActivity(intent4);
							SearchActivity.this.finish();
						}
						break;
				}

			}
		});

		init_navigator();
		//Navigation menu item click listener
		menuLayoutOne.setOnClickListener(this);
		menuLayoutTwo.setOnClickListener(this);
		menuLayoutThree.setOnClickListener(this);
		menuLayoutFour.setOnClickListener(this);
		menuLayoutFive.setOnClickListener(this);
		menuLayoutSix.setOnClickListener(this);


		// Locate the EditText in listview_main.xml
		editsearch = (EditText) findViewById(R.id.search);

		// Capture Text in EditText
		editsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
				adapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
		search_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editsearch.setText("");
			}
		});
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
	protected void initAlertDialog() {
		Dialog alertDialog = new AlertDialog.Builder(this).
				setTitle("Sign in required").
				setMessage("To continue please sign in").
				setIcon(R.drawable.ic_launcher).
				setPositiveButton("Sign in", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String none = "none";
						Intent intent = new Intent(SearchActivity.this,Activity_Login.class);
						startActivity(intent);
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				bottomNavigation.setCurrentItem(2);
			}
		}).create();

		alertDialog.show();

	}
	private class ProgressTask extends AsyncTask<String, Void, Boolean> {

		protected void onPreExecute() {
//			dialog.setMessage("Progress start");
//			dialog.show();
			mCustomProgressDialog.show("");
		}

		@Override
		protected void onPostExecute(final Boolean success) {
//			if (dialog.isShowing()) {
//				dialog.dismiss();
//			}
			if (mCustomProgressDialog.isShowing()|| mCustomProgressDialog!=null) {
				mCustomProgressDialog.dismiss("");
			}

			for (int i=0;i<titleParseArray.size();i++){
				qtyArray.add("1");
			}

			for (int i = 0; i < titleParseArray.size(); i++)
			{

				ProductModelClass wp = new ProductModelClass(sale_priceParseArray.get(i), titleParseArray.get(i),
						purchase_priceParseArray.get(i), imagesParseArray.get(i),designer_nameParseArray.get(i),avaliablilityParseArray.get(i),qtyArray.get(i)
						,discountParseArray.get(i),productIdArray.get(i),descArray.get(i));
				// Binds all strings into an array
				arraylist.add(wp);
			}

//			adapter = new GridViewAdapterSearch(SearchActivity.this,arraylist imagesParseArray, titleParseArray,
//					sale_priceParseArray,purchase_priceParseArray,designer_nameParseArray,
//					avaliablilityParseArray,qtyArray,discountParseArray,productIdArray,descArray);

			adapter = new GridViewAdapterSearch(SearchActivity.this, arraylist);

			// Binds the Adapter to the ListView
			gridView.setAdapter(adapter);


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

	@Override
	protected void onResume() {
		bottomNavigation.setCurrentItem(2);
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

//        mActionBarDrawerToggle.syncState();

		// Navigation Drawer layout width
		int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
				UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
		int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

		mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
	}
}
