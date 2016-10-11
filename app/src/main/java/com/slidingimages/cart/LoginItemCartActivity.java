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


import com.slidingimages.Activity_Login;
import com.slidingimages.ProductsDetailActivity;
import com.slidingimages.R;
import com.slidingimages.ShippingPage;
import com.slidingimages.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginItemCartActivity extends Activity {
	private CartItemListAdapter mAdapter;
	private List<List<String>> itemList = new ArrayList<List<String>>();
	private String userName;
	private String subPrice;
	private Boolean mainActivity;
	private Boolean prodcutsMainActivity;
	private Boolean prodcutsDetailActivity;
	private String categoryName;
	private String productName;
	private Bundle extras;
	private double sum=0;
	private ArrayList<Double> sumArray= new ArrayList<>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_login_item);

//		initActionBar();
//		getIntentTxt();
		initView();
	}
	



	private void initView() {
		TextView tv_subPrice = (TextView)findViewById(R.id.login_item_subtotal_price);
		Button btn_checkout = (Button)findViewById(R.id.login_item_checkout);
		Button btn_editCart = (Button)findViewById(R.id.login_item_edit);
		ListView list = (ListView)findViewById(R.id.login_item_listview);

		if (!ShoppingCart.purchase_prices.isEmpty() || !ShoppingCart.qtyArray.isEmpty()){
			for (int i=0;i <ShoppingCart.purchase_prices.size();i++){
//				 sum=(int)Double.parseDouble(ShoppingCart.purchase_prices.get(i)) *(int)Double.parseDouble(ShoppingCart.qtyArray.get(i));
				 sumArray.add(sum);
			}
		}
		tv_subPrice.setText("AED " + (int) total_sale_price());
//		mAdapter = new CartItemListAdapter(this, ShoppingCart.product_images, ShoppingCart.product_names,
//				ShoppingCart.sale_prices,ShoppingCart.purchase_prices,ShoppingCart.designer_names,ShoppingCart.qtyArray);
		list.setAdapter(mAdapter);
		//make listView display normally
		new Utility().setListViewHeightBasedOnChildren(list);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
//				TextView tv_name  = (TextView)view.findViewById(R.id.item_name);
//				String name = tv_name.getText().toString();
//				Intent intent = new Intent(LoginItemCartActivity.this,ProductsDetailActivity.class);
//				//send productName to productDetail page
//				intent.putExtra("productName", name);
//				startActivity(intent);
			}
		});
		
		btn_checkout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if (!Activity_Login.username.equals("temp")) {
					if (!ShoppingCart.product_images.isEmpty()){
						Intent intent = new Intent(LoginItemCartActivity.this, ShippingPage.class);
						startActivity(intent);
						LoginItemCartActivity.this.finish();
					}else {
						new SweetAlertDialog(LoginItemCartActivity.this, SweetAlertDialog.NORMAL_TYPE)
								.setTitleText("Please add items")
								.show();
					}

				}else {
					new SweetAlertDialog(LoginItemCartActivity.this, SweetAlertDialog.ERROR_TYPE)
							.setTitleText("Please login to continue")
							.setConfirmText("OK")
							.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								@Override
								public void onClick(SweetAlertDialog sDialog) {
									Intent intent= new Intent(LoginItemCartActivity.this,Activity_Login.class);
									startActivity(intent);
									LoginItemCartActivity.this.finish();
								}
							})
							.show();

				}
			}
		});
		
		btn_editCart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				categoryName = extras.getString("categoryName");
//				productName = extras.getString("productName");

				//send userName
				Intent intent = new Intent(LoginItemCartActivity.this, CartEditActivity.class);
//				intent.putExtra("userName", userName);
//				intent.putExtra("categoryName", categoryName);
//				intent.putExtra("productName", productName);


				startActivity(intent);
				LoginItemCartActivity.this.finish();
				
			}
		});

	}
	public double total_sale_price()
	{
		double sum = 0;
		for(int i = 0; i < sumArray.size(); i++)
		{
			sum += sumArray.get(i);
		}
		return sum;
	}

	//adjust listView when under scrollView
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
                      totalHeight += listItem.getMeasuredHeight();  //calculate listView whole height
               }

               ViewGroup.LayoutParams params = listView.getLayoutParams();
               params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
               //listView.getDividerHeight()
               //params.height
               listView.setLayoutParams(params);
        }
    }


   

}
