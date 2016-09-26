package com.slidingimages.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.slidingimages.ImageLoader;
import com.slidingimages.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CartItemListAdapter extends BaseAdapter {

	private ArrayList<String> images;
	private ArrayList<String> product_names;
	private ArrayList<String> sale_prices;
	private ArrayList<String> designer_names;
	private ArrayList<String> purchase_prices;
	private ArrayList<String> qtyArray;
	private ImageLoader imageLoader;
	private Context context;

	public CartItemListAdapter(Context context,ArrayList<String> images, ArrayList<String> product_names,ArrayList<String> sale_prices
			,ArrayList<String> purchase_prices,ArrayList<String> designer_names,ArrayList<String> qtyArray) {
		this.context = context;
		this.images = images;
		this.sale_prices = sale_prices;
		this.designer_names = designer_names;
		this.purchase_prices = purchase_prices;
		this.product_names = product_names;
		this.qtyArray = qtyArray;
		imageLoader = new ImageLoader(context.getApplicationContext());
	}

	@Override
	public int getCount() {
		return product_names.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		v = LayoutInflater.from(context).inflate(R.layout.cart_listview_item, null);
		ImageView img = (ImageView)v.findViewById(R.id.item_img);
		TextView tv_name = (TextView)v.findViewById(R.id.item_name);
		TextView tv_price = (TextView)v.findViewById(R.id.item_price);
		TextView tv_qty = (TextView)v.findViewById(R.id.item_qty);
		
		tv_name.setText(product_names.get(position));
		tv_price.setText("AED " + sale_prices.get(position));
		tv_qty.setText("Qty:" + qtyArray.get(position));
		//img
		imageLoader.DisplayImage(images.get(position), img);
		
		return v;
	}

}
