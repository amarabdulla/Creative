package com.mazyoona;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.mazyoona.cart.LoginEmptyCartActivity;
import com.mazyoona.customViews.BounceInterpolator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductsDetailActivity extends Activity {
	private String productName;
	private String productId;
	private String productImg;
	private String productAvail;
	private String sale_price;
	private String purchase_price;
	private String designer_name;
	private String discount;
	private Bundle extras;
	private Button menu_icon;
	private Toolbar toolbar;
	private TextView out_of_stock;
	private TextView badge_notification;
	private ImageLoader imageLoader;
	private String qty_str,desc;
	private Spinner qty;
	private String spinner_qty_text="1";
	private List<Integer> list = new ArrayList<Integer>();
	private Menu mToolbarMenu;
	private TextView textViewdiscount;
	private AHBottomNavigation bottomNavigation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail_activity);
		imageLoader=new ImageLoader(ProductsDetailActivity.this);
		toolbar=(Toolbar)findViewById(R.id.toolbar) ;
		badge_notification=(TextView)findViewById(R.id.badge_notification) ;
		menu_icon=(Button)findViewById(R.id.menu_icon);
		menu_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!ShoppingCart.product_names.isEmpty()){
					Intent intent= new Intent(ProductsDetailActivity.this,ShoppingCart.class);
					startActivity(intent);
					ProductsDetailActivity.this.finish();
				}else {
					Intent intent= new Intent(ProductsDetailActivity.this,LoginEmptyCartActivity.class);
					startActivity(intent);
				}

			}
		});

//		final String[] colors = {"#a41c9a"};
//		bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
//
//		AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.home), R.drawable.home_icon, Color.parseColor(colors[0]));
//		AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.product_lowcase), R.drawable.category_icon, Color.parseColor(colors[0]));
//		AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.search), R.drawable.search_icon, Color.parseColor(colors[0]));
//		AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.shopping_cart_lowcase), R.drawable.cart_icon, Color.parseColor(colors[0]));
//		AHBottomNavigationItem item5 = new AHBottomNavigationItem(getString(R.string.my_profile), R.drawable.user_icon, Color.parseColor(colors[0]));
//
//		bottomNavigation.addItem(item1);
//		bottomNavigation.addItem(item2);
//		bottomNavigation.addItem(item3);
//		bottomNavigation.addItem(item4);
//		bottomNavigation.addItem(item5);
//
//		bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#a41c9a"));
//
//		bottomNavigation.setAccentColor(Color.parseColor("#FFFFFF"));
//		bottomNavigation.setInactiveColor(Color.parseColor("#FFFFFF"));
//
//		//  Enables Reveal effect
////        bottomNavigation.setColored(true);
//
//		bottomNavigation.setCurrentItem(0);
//
//		bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
//			@Override
//			public void onTabSelected(int position, boolean wasSelected) {
//				// Do something cool here...
//				switch (position) {
//					case 0:
//						break;
//					case 1:
//						Intent intent1= new Intent(ProductsDetailActivity.this,ProductPage.class);
//						startActivity(intent1);
//						ProductsDetailActivity.this.finish();
//						break;
//
//						break;
//					case 3:
//						if (ShoppingCart.product_names.isEmpty()){
//							Intent intent3= new Intent(ProductsDetailActivity.this,LoginEmptyCartActivity
//									.class);
//							startActivity(intent3);
//							ProductsDetailActivity.this.finish();
//						}else {
//							Intent intent3 = new Intent(ProductsDetailActivity.this, ShoppingCart
//									.class);
//							startActivity(intent3);
//						}
//
//						break;
//					case 4:
//						if (Activity_Login.username.equals("") || Activity_Login.username.equals("temp")){
//							initAlertDialog();
//						}else {
//							Intent intent4 = new Intent(ProductsDetailActivity.this, ProfilePage.class);
//							startActivity(intent4);
//							ProductsDetailActivity.this.finish();
//						}
//						break;
//				}
//
//			}
//		});

		setTitle(null);

		if (!ShoppingCart.product_images.isEmpty()){
			badge_notification.setVisibility(View.VISIBLE);
			badge_notification.setText(ShoppingCart.product_names.size()+"");
		}else {
			badge_notification.setVisibility(View.INVISIBLE);
		}

		getIntentTxt();
		initView();

		toolbar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ShoppingCart.product_names.isEmpty()){
					Intent intent = new Intent(ProductsDetailActivity.this, LoginEmptyCartActivity.class);
					startActivity(intent);
				}else {
					Intent intent = new Intent(ProductsDetailActivity.this, ShoppingCart.class);
					startActivity(intent);
				}
			}
		});

//		ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,list);
//
//		dataAdapter.setDropDownViewResource
//				(android.R.layout.simple_spinner_dropdown_item);
//
//		qty.setAdapter(dataAdapter);
	}
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		getMenuInflater().inflate(R.menu.main, paramMenu);
		return super.onCreateOptionsMenu(paramMenu);
	}

	private void getIntentTxt() {
		//get intent
		extras = getIntent().getExtras();
		productId= extras.getString("productId");
		productName = extras.getString("productName");
		productImg = extras.getString("productImage");
		sale_price = extras.getString("sale_price");
		discount= extras.getString("discount");
		purchase_price = extras.getString("purchase_price");
		designer_name = extras.getString("designer_name");
		productAvail = extras.getString("available");
		desc= extras.getString("desc");
	}


	private void initView() {

		TextView tv_model = (TextView)findViewById(R.id.detail_model);
		final TextView tv_avail = (TextView)findViewById(R.id.detail_avail);
		TextView tv_price = (TextView)findViewById(R.id.detail_price);
		TextView tv_tax = (TextView)findViewById(R.id.detail_tax);
		TextView tv_des = (TextView)findViewById(R.id.detail_des);
		ImageButton btn_add = (ImageButton)findViewById(R.id.detail_add);
//		final Button btn_buy = (Button)findViewById(R.id.detail_buy);
//		qty = (Spinner) findViewById(R.id.detail_edt_qty);
		textViewdiscount=(TextView) findViewById(R.id.discount_detail_page);
		ImageView detail_img = (ImageView)findViewById(R.id.detail_img);
		out_of_stock=(TextView)findViewById(R.id.outofstock_detail_page);

		for (int i=1;i<Integer.parseInt(productAvail)+1;i++){
			list.add(i);
		}


		tv_model.setText(productName);
		tv_avail.setText("Availability: " + productAvail);
		tv_price.setText("Price: " + "AED " + purchase_price);

		if (!discount.equals("") && !discount.equals("0")){
			textViewdiscount.setVisibility(View.VISIBLE);
			textViewdiscount.setText(discount+"% OFF");

			textViewdiscount.setBackgroundResource(R.color.green);
//			textViewdiscount.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
			textViewdiscount.setGravity(Gravity.CENTER);
		}else {
			textViewdiscount.setVisibility(View.GONE);
		}
		if (productAvail.equals("0")){
			out_of_stock.setVisibility(View.VISIBLE);
			out_of_stock.setText("Out of Stock");

			out_of_stock.setBackgroundResource(R.color.red);
//			textViewdiscount.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
			out_of_stock.setGravity(Gravity.CENTER);
		}else {
			out_of_stock.setVisibility(View.GONE);
		}

		if (sale_price.equals(purchase_price)){
			tv_tax.setText("");
		}else {
			tv_tax.setText(sale_price+" AED");
			tv_tax.setPaintFlags(tv_tax.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}

		tv_des.setText(Html.fromHtml(desc));
//		imageLoader.DisplayImage(productImg, detail_img);
		Picasso.with(ProductsDetailActivity.this).load(productImg).placeholder(R.drawable.detailview_stub_loading).error(R.drawable.detailview_stub_error).into(detail_img);
//		Glide.with(ProductsDetailActivity.this)
//				.load(productImg)
//				.placeholder(R.drawable.detailview_stub_loading)
//				.error(R.drawable.detailview_stub_error)
//				.crossFade()
//				.into(detail_img);

		detail_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				GridItem item = (GridItem) parent.getItemAtPosition(position);

				Intent intent = new Intent(ProductsDetailActivity.this, DetailsActivity.class);
				ImageView imageView = (ImageView) v.findViewById(R.id.detail_img);


				// Interesting data to pass across are the thumbnail size/location, the
				// resourceId of the source bitmap, the picture description, and the
				// orientation (to avoid returning back to an obsolete configuration if
				// the device rotates again in the meantime)

				int[] screenLocation = new int[2];
				imageView.getLocationOnScreen(screenLocation);

				//Pass the image title and url to DetailsActivity
				intent.putExtra("left", screenLocation[0]).
						putExtra("top", screenLocation[1]).
						putExtra("width", imageView.getWidth()).
						putExtra("height", imageView.getHeight()).
//						putExtra("title", item.getTitle()).
						putExtra("image", productImg);

				//Start details activity
				startActivity(intent);
			}
		});


		
	    btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {


				if (!productAvail.equalsIgnoreCase("0")) {
					if (ShoppingCart.product_names.contains(productName)) {
						new SweetAlertDialog(ProductsDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
								.setTitleText("Already added to Cart")
								.show();
					} else {



//						spinner_qty_text = qty.getSelectedItem().toString();
						ShoppingCart.product_ids.add(productId);
						ShoppingCart.product_names.add(productName);
						ShoppingCart.sale_prices.add(sale_price);
						ShoppingCart.purchase_prices.add(purchase_price);
						ShoppingCart.designer_names.add(designer_name);
						ShoppingCart.product_images.add(productImg);
						ShoppingCart.avaliablilityArray.add(productAvail);
						ShoppingCart.qtyArray.add(1);
						badge_notification.setVisibility(View.VISIBLE);
						badge_notification.setText(ShoppingCart.product_names.size()+"");

						final Animation myAnim = AnimationUtils.loadAnimation(ProductsDetailActivity.this, R.anim.bounce);
						BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
						myAnim.setInterpolator(interpolator);
//						menu_icon.startAnimation(myAnim);
						badge_notification.startAnimation(myAnim);

//						new SweetAlertDialog(ProductsDetailActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//								.setTitleText("Added to Cart!")
//								.show();
					}
				}else {
					new SweetAlertDialog(ProductsDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
							.setTitleText("Out of Stock!")
							.show();
				}
			}

		});
		
		

	
	}

	public class Animations {
		public Animation fromAtoB(float fromX, float fromY, float toX, float toY, Animation.AnimationListener l, int speed){


			Animation fromAtoB = new TranslateAnimation(
					Animation.ABSOLUTE, //from xType
					fromX,
					Animation.ABSOLUTE, //to xType
					toX,
					Animation.ABSOLUTE, //from yType
					fromY,
					Animation.ABSOLUTE, //to yType
					toY
			);

			fromAtoB.setDuration(speed);
			fromAtoB.setInterpolator(new AnticipateOvershootInterpolator(1.0f));


			if(l != null)
				fromAtoB.setAnimationListener(l);
			return fromAtoB;
		}
	}
	protected void initAlertDialog() {
		Dialog alertDialog = new AlertDialog.Builder(this).
				setTitle("Save failed").
				setMessage("You need to login before save item").
				setIcon(R.drawable.ic_launcher).
				setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String none = "none";
						Intent intent = new Intent(ProductsDetailActivity.this,HomePage.class);
						intent.putExtra("cart_empty", none);
						intent.putExtra("cart_item", none);
						intent.putExtra("wish", none);
						intent.putExtra("productDetail", "productDetail");
						intent.putExtra("productName", productName);
						intent.putExtra("productList", none);
						startActivity(intent);
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create();
		
		alertDialog.show();
		
	}


	



}
