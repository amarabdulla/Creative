package com.slidingimages;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.slidingimages.cart.LoginItemCartActivity;

import java.lang.reflect.Field;
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
	private String userName;
	private Bundle extras;
	private ImageLoader imageLoader;
	private String qty_str;
	private Spinner qty;
	private List<Integer> list = new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail_activity);
		imageLoader=new ImageLoader(ProductsDetailActivity.this);

		getIntentTxt();
		initView();
		ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,list);

		dataAdapter.setDropDownViewResource
				(android.R.layout.simple_spinner_dropdown_item);

		qty.setAdapter(dataAdapter);
	}

	private void getIntentTxt() {
		//get intent
		extras = getIntent().getExtras();
		productId= extras.getString("productId");
		productName = extras.getString("productName");
		productImg = extras.getString("productImage");
		sale_price = extras.getString("sale_price");
		purchase_price = extras.getString("purchase_price");
		designer_name = extras.getString("designer_name");
		productAvail = extras.getString("available");
		qty_str= extras.getString("qty");
	}


	private void initView() {

		TextView tv_model = (TextView)findViewById(R.id.detail_model);
		final TextView tv_avail = (TextView)findViewById(R.id.detail_avail);
		TextView tv_price = (TextView)findViewById(R.id.detail_price);
		TextView tv_tax = (TextView)findViewById(R.id.detail_tax);
		TextView tv_des = (TextView)findViewById(R.id.detail_des);
		Button btn_add = (Button)findViewById(R.id.detail_add);
		final Button btn_buy = (Button)findViewById(R.id.detail_buy);
		qty = (Spinner) findViewById(R.id.detail_edt_qty);
		ImageView detail_img = (ImageView)findViewById(R.id.detail_img);

		for (int i=1;i<Integer.parseInt(productAvail)+1;i++){
			list.add(i);
		}


		tv_model.setText(productName);
		tv_avail.setText("Availability: " + productAvail);
		tv_price.setText("Price: " + "$" + purchase_price);
		tv_tax.setText(sale_price);
		tv_tax.setPaintFlags(tv_tax.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		tv_des.setText("Test description is here designer clothes collection is the new way of industry buy now the collection");
		imageLoader.DisplayImage(productImg, detail_img);


		
	    btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

			if (ShoppingCart.product_names.contains(productName)){
				new SweetAlertDialog(ProductsDetailActivity.this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("Already added to Cart")
						.show();
			}else {
//                    ShoppingCart.qtyArray.add(position,"1");
				ShoppingCart.product_ids.add(productId);
				ShoppingCart.product_names.add(productName);
				ShoppingCart.sale_prices.add(sale_price);
				ShoppingCart.purchase_prices.add(purchase_price);
				ShoppingCart.designer_names.add(designer_name);
				ShoppingCart.product_images.add(productImg);
				ShoppingCart.avaliablilityArray.add(productAvail);
				ShoppingCart.qtyArray.add(qty_str);

				new SweetAlertDialog(ProductsDetailActivity.this, SweetAlertDialog.SUCCESS_TYPE)
						.setTitleText("Added to Cart!")
						.show();
			}
			}
		});
		
		
		btn_buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShoppingCart.product_ids.add(productId);
				ShoppingCart.product_names.add(productName);
				ShoppingCart.sale_prices.add(sale_price);
				ShoppingCart.purchase_prices.add(purchase_price);
				ShoppingCart.designer_names.add(designer_name);
				ShoppingCart.product_images.add(productImg);
				ShoppingCart.avaliablilityArray.add(productAvail);
				ShoppingCart.qtyArray.add(qty_str);
				Intent intent= new Intent(ProductsDetailActivity.this, LoginItemCartActivity.class);
				startActivity(intent);
			}
		});
	
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	



}
