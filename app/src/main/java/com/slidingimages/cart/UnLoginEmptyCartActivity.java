package com.slidingimages.cart;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.slidingimages.Activity_Login;
import com.slidingimages.R;


public class UnLoginEmptyCartActivity extends Activity {

	private String categoryName;
	private String productName;
	private Bundle extras;

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_unlogin_empty);
		
		initActionBar();
		getIntentTxt();
		initBtnView();
					
	}

	

	private void getIntentTxt() {
		// TODO Auto-generated method stub
		extras = getIntent().getExtras();


	}



	private void initActionBar() {
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Shopping Cart");
		actionBar.setDisplayHomeAsUpEnabled(true);		
	}
	
	private void initBtnView() {
		Button btn_sign = (Button)findViewById(R.id.cart_signin);
		
		btn_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(mainActivity == true){
//					Intent intent = new Intent(UnLoginEmptyCartActivity.this, Activity_Login.class);
//					String tag = "cartEmpty";
//					//send self tag to make signIn activity know which activity should go next after sign in
//					intent.putExtra("cart_empty", tag);
//					//other tag, if not set will cause nullPointer error
//					intent.putExtra("cart_item", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					startActivity(intent);
//				}else if(prodcutsMainActivity==true){
//					categoryName = extras.getString("categoryName");
//					Intent intent = new Intent(UnLoginEmptyCartActivity.this, Activity_Login.class);
//					String tag = "cartEmpty";
//					//send self tag to make signIn activity know which activity should go next after sign in
//					intent.putExtra("cart_empty", tag);
//					//other tag, if not set will cause nullPointer error
//					intent.putExtra("cart_item", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					intent.putExtra("categoryName", categoryName);
//					startActivity(intent);
//				}else if(prodcutsDetailActivity==true){
//					productName = extras.getString("productName");
//					Intent intent = new Intent(UnLoginEmptyCartActivity.this, Activity_Login.class);
//					String tag = "cartEmpty";
//					//send self tag to make signIn activity know which activity should go next after sign in
//					intent.putExtra("cart_empty", tag);
//					//other tag, if not set will cause nullPointer error
//					intent.putExtra("cart_item", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					intent.putExtra("productName", productName);
//					startActivity(intent);
//				}
				
							
			}
		});		
	}
	


}
