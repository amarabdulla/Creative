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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.slidingimages.Activity_Login;
import com.slidingimages.R;
import com.slidingimages.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class CartEditActivity extends Activity {
	CartEditListAdapter mAdapter;
	List<List<String>> itemList = new ArrayList<List<String>>();
	String userName;
	private String categoryName;
	private String productName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_edit_activity);
		
//		initActionBar();
//		getIntentTxt();
		
		initView();
		
	}
	
//	private void initActionBar() {
//		ActionBar actionBar = getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setTitle("Edit Cart");
//	}

//	private void getIntentTxt() {
//		//get userName
//		Bundle extras = getIntent().getExtras();
//		userName = extras.getString("userName");
//		categoryName = extras.getString("categoryName");
//		productName = extras.getString("productName");
//	}

	private void initView() {
		ListView list = (ListView)findViewById(R.id.edit_listview);
		Button btn_save = (Button)findViewById(R.id.edit_save);
		Button btn_cancel = (Button)findViewById(R.id.edit_cancel);
//
		userName="test";
//
		mAdapter = new CartEditListAdapter(this, ShoppingCart.product_images, ShoppingCart.product_names,
				ShoppingCart.sale_prices,ShoppingCart.purchase_prices,ShoppingCart.designer_names,ShoppingCart.avaliablilityArray,ShoppingCart.qtyArray, userName);
		list.setAdapter(mAdapter);
//
		new Utility().setListViewHeightBasedOnChildren(list);
		
		btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
							
//				processItemInDB();

                String pName = "pname";
//				if(userName.equals("temp")){
//					if(pName == null){
//						Intent intent = new Intent(CartEditActivity.this,UnLoginEmptyCartActivity.class);
//						intent.putExtra("categoryName", categoryName);
//						intent.putExtra("productName", productName);
//						startActivity(intent);
//					}else{
//						Intent intent = new Intent(CartEditActivity.this,UnLoginItemCartActivity.class);
//						intent.putExtra("userName", userName);
//						intent.putExtra("categoryName", categoryName);
//						intent.putExtra("productName", productName);
//
//						startActivity(intent);
//					}
//				}else{
					if(pName == null){
						Intent intent = new Intent(CartEditActivity.this,Activity_Login.class);
//						intent.putExtra("categoryName", categoryName);
//						intent.putExtra("productName", productName);
						startActivity(intent);
					}else{
						Intent intent = new Intent(CartEditActivity.this,LoginItemCartActivity.class);
//						intent.putExtra("userName", userName);
//						intent.putExtra("categoryName", categoryName);
//						intent.putExtra("productName", productName);
						
						startActivity(intent);
						CartEditActivity.this.finish();
					}
//				}
			}

		});

		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(userName.equals("temp")){
//					Intent intent = new Intent(CartEditActivity.this,UnLoginItemCartActivity.class);
//					intent.putExtra("categoryName", categoryName);
//					intent.putExtra("productName", productName);
//
//
//					startActivity(intent);
//				}else{
					Intent intent = new Intent(CartEditActivity.this,LoginItemCartActivity.class);
					intent.putExtra("userName", userName);
					intent.putExtra("categoryName", categoryName);
					intent.putExtra("productName", productName);

					startActivity(intent);
					CartEditActivity.this.finish();
//				}
				
				
			}
		});		
	}

	public class Utility {
		public  void setListViewHeightBasedOnChildren(ListView listView) {
			//get list adapter
			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				return;
			}

			int totalHeight = 0;
			for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);  //calculate item height
				totalHeight += listItem.getMeasuredHeight();  //calculate whole height
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			//listView.getDividerHeight()
			//params.height
			listView.setLayoutParams(params);
		}
	}


}
