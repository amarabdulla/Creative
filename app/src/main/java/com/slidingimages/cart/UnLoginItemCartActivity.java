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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.slidingimages.ProductsDetailActivity;
import com.slidingimages.R;
import com.slidingimages.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class UnLoginItemCartActivity extends Activity {

	private CartItemListAdapter mAdapter;
	private List<List<String>> itemList = new ArrayList<List<String>>();
	private String userName = "temp";

//	private String categoryName;
//	private String productName;
	private Bundle extras;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_unlogin_item);
		
		getIntentTxt();

		initView();
	
	}
	
	
	private void getIntentTxt() {
		// TODO Auto-generated method stub
		extras = getIntent().getExtras();

	}







	private void initView() {
		TextView tv_subPrice = (TextView)findViewById(R.id.unlogin_item_subtotal_price);
		Button btn_checkout = (Button)findViewById(R.id.unlogin_item_checkout);
		Button btn_sign = (Button)findViewById(R.id.unlgoin_item_signin);
		Button btn_editCart = (Button)findViewById(R.id.unlogin_item_edit);
		ListView list = (ListView)findViewById(R.id.unlogin_item_listview);


		tv_subPrice.setText("$" + "subPrice here");
		mAdapter = new CartItemListAdapter(this, ShoppingCart.product_images, ShoppingCart.product_names,
				ShoppingCart.sale_prices,ShoppingCart.purchase_prices,ShoppingCart.designer_names,ShoppingCart.qtyArray);
		list.setAdapter(mAdapter);
		//adjust listView height
		new Utility().setListViewHeightBasedOnChildren(list);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
//				TextView tv_name  = (TextView)view.findViewById(R.id.item_name);
//				String name = tv_name.getText().toString();
//				Intent intent = new Intent(UnLoginItemCartActivity.this,ProductsDetailActivity.class);
//				intent.putExtra("productName", name);
//				startActivity(intent);
			}
		});
		
		btn_checkout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(mainActivity == true){
//					Intent intent = new Intent(UnLoginItemCartActivity.this, SignInActivity.class);
//					String tag = "cartWithItem";
//					intent.putExtra("cart_item", tag);
//					intent.putExtra("cart_empty", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					startActivity(intent);
//				}else if(prodcutsMainActivity == true){
//					categoryName = extras.getString("categoryName");
//					Intent intent = new Intent(UnLoginItemCartActivity.this, SignInActivity.class);
//					String tag = "cartWithItem";
//					intent.putExtra("cart_item", tag);
//					intent.putExtra("cart_empty", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					intent.putExtra("categoryName", categoryName);
//					startActivity(intent);
//				}else if(prodcutsDetailActivity == true){
//					productName = extras.getString("productName");
//					Intent intent = new Intent(UnLoginItemCartActivity.this, SignInActivity.class);
//					String tag = "cartWithItem";
//					intent.putExtra("cart_item", tag);
//					intent.putExtra("cart_empty", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					intent.putExtra("productName", productName);
//					startActivity(intent);
//				}
			}
		});
		
		btn_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(mainActivity == true){
//					Intent intent = new Intent(UnLoginItemCartActivity.this, SignInActivity.class);
//					String tag = "cartWithItem";
//					intent.putExtra("cart_item", tag);
//					intent.putExtra("cart_empty", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					startActivity(intent);
//				}else if(prodcutsMainActivity == true){
//					categoryName = extras.getString("categoryName");
//					Intent intent = new Intent(UnLoginItemCartActivity.this, SignInActivity.class);
//					String tag = "cartWithItem";
//					intent.putExtra("cart_item", tag);
//					intent.putExtra("cart_empty", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					intent.putExtra("categoryName", categoryName);
//					startActivity(intent);
//				}else if(prodcutsDetailActivity == true){
//					productName = extras.getString("productName");
//					Intent intent = new Intent(UnLoginItemCartActivity.this, SignInActivity.class);
//					String tag = "cartWithItem";
//					intent.putExtra("cart_item", tag);
//					intent.putExtra("cart_empty", "none");
//					intent.putExtra("wish", "none");
//					intent.putExtra("productDetail", "none");
//					intent.putExtra("productList", "none");
//					intent.putExtra("productName", productName);
//					startActivity(intent);
//				}
				
			}
		});
		
		btn_editCart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				categoryName = extras.getString("categoryName");
//				productName = extras.getString("productName");
				//send userName
				Intent intent = new Intent(UnLoginItemCartActivity.this, CartEditActivity.class);
				intent.putExtra("userName", userName);
//				intent.putExtra("categoryName", categoryName);
//				intent.putExtra("productName", productName);

				startActivity(intent);
			}
		});

	}


	//adjust listview when under scrollview
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
