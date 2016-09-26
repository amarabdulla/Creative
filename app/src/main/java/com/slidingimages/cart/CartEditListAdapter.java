package com.slidingimages.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;


import com.slidingimages.ImageLoader;
import com.slidingimages.R;
import com.slidingimages.ShoppingCart;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CartEditListAdapter extends BaseAdapter {
	private List<String> imgList = new ArrayList<String>();
	private List<String> nameList = new ArrayList<String>();
	private List<String> stockList = new ArrayList<String>();
	private List<String> priceList = new ArrayList<String>();
	private Context context;
	private String userName;
	private int newQty = 1;
	private List<String> updateQtyList = new ArrayList<String>();
	private List<String> updateQtyNameList = new ArrayList<String>();
	private List<String> updateNewStockList = new ArrayList<String>();
	private List<List<String>> qtyList = new ArrayList<List<String>>();
	private List<String> delNameList = new ArrayList<String>();
	private List<String> delQtyList = new ArrayList<String>();
	private List<List<String>> delList = new ArrayList<List<String>>();
	private ArrayList<String> avaliablilityParseArray = new ArrayList<String>();
	private ArrayList<String> qtyArray = new ArrayList<String>();
	private ArrayList<String> images;
	private ArrayList<String> product_names;
	private ArrayList<String> sale_prices;
	private ArrayList<String> designer_names;
	private ArrayList<String> purchase_prices;
	private ImageLoader imageLoader;
	
	public CartEditListAdapter(Context context, ArrayList<String> images, ArrayList<String> product_names,ArrayList<String> sale_prices
			,ArrayList<String> purchase_prices,ArrayList<String> designer_names,ArrayList<String> avaliablilityParseArray ,ArrayList<String> qtyArray,String userName) {
		this.context = context;
		this.images = images;
		this.product_names = product_names;
		this.sale_prices = sale_prices;
		this.purchase_prices = purchase_prices;
		this.designer_names = designer_names;
		this.avaliablilityParseArray = avaliablilityParseArray;
		this.qtyArray = qtyArray;
		this.userName = userName;
		imageLoader = new ImageLoader(context.getApplicationContext());
	}

	@Override
	public int getCount() {
		return product_names.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public List<List<String>> getQtyData(){
		return qtyList;
	}
	
	public List<List<String>> getDelData(){
		return delList;
		
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		ViewHolder holder = null;
		if(v == null){
			v = LayoutInflater.from(context).inflate(R.layout.cart_edit_listview_item, null);
			holder = new ViewHolder();
			holder.img = (ImageView)v.findViewById(R.id.edit_img);
			holder.tv_name =(TextView)v.findViewById(R.id.edit_name);
			holder.tv_price = (TextView)v.findViewById(R.id.edit_price);
			holder.tv_qty = (TextView)v.findViewById(R.id.edit_qty);
			holder.btn_del = (TextView)v.findViewById(R.id.edit_del);
			v.setTag(holder);
		}else{
			holder = (ViewHolder)v.getTag();
		}
		
		holder.tv_name.setText(product_names.get(position));
		holder.tv_price.setText("AED " + sale_prices.get(position));
		holder.tv_qty.setText("Qty:" + ShoppingCart.qtyArray.get(position));
		//img
		imageLoader.DisplayImage(images.get(position), holder.img);
		
		
		
		holder.tv_qty.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String stock = avaliablilityParseArray.get(position);

				final NumberPicker mPicker = new NumberPicker(context);
				mPicker.setMinValue(1);

				int a = Integer.valueOf(stock);
				mPicker.setMaxValue(a);
				
                
                mPicker.setOnValueChangedListener(new OnValueChangeListener() {
					

					@Override
					public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
						newQty = newVal;	
					}				
				});       

                AlertDialog mAlertDialog = new AlertDialog.Builder(context).
                		setView(mPicker).
                		setTitle("Set Quantity").
                		setPositiveButton("OK", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ShoppingCart.qtyArray.add(position, String.valueOf(newQty));//update text
								notifyDataSetChanged();	
								//reset qty
								newQty = 1;
							}


						}).create();
                
                mAlertDialog.show();
                
			}
		});
		
		qtyList.add(updateQtyNameList);
		qtyList.add(updateQtyList);
		qtyList.add(updateNewStockList);
		
		holder.btn_del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Snackbar.make(v,product_names.get(position)+" removed from cart",Snackbar.LENGTH_SHORT).show();
				product_names.remove(product_names.get(position));
				sale_prices.remove(sale_prices.get(position));
				images.remove(images.get(position));
				purchase_prices.remove(purchase_prices.get(position));
				avaliablilityParseArray.remove(avaliablilityParseArray.get(position));
				designer_names.remove(designer_names.get(position));
				notifyDataSetChanged();
			}
		});
		return v;
	}
	
	final static class ViewHolder{
		ImageView img;
		TextView tv_name;
		TextView tv_price;
		TextView tv_qty;
		TextView btn_del;
	}

}
