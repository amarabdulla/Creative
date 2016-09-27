package com.slidingimages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.slidingimages.cart.LoginItemCartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GridViewAdapterSearch extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<ProductModelClass> worldpopulationlist = null;
	private ArrayList<ProductModelClass> arraylist;

	private ImageLoader imageLoader;

	public GridViewAdapterSearch(Context context,
								 List<ProductModelClass> worldpopulationlist) {
		mContext = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<ProductModelClass>();
		this.arraylist.addAll(worldpopulationlist);
		imageLoader = new ImageLoader(context.getApplicationContext());
	}

	public class ViewHolder {
		TextView rank;
		TextView country;
		TextView population;
		ImageView flag;
		TextView designer_name;
//		Button buy_now,add_to_cart;
	}

	@Override
	public int getCount() {
		return worldpopulationlist.size();
	}

	@Override
	public ProductModelClass getItem(int position) {
		return worldpopulationlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {

			holder = new ViewHolder();
			view = inflater.inflate(R.layout.grid, null);
			holder.rank = (TextView) view.findViewById(R.id.sale_price);
			holder.country = (TextView) view.findViewById(R.id.product_name);
//			holder.buy_now =(Button) view.findViewById(R.id.buy_now);
//			holder.add_to_cart =(Button) view.findViewById(R.id.add_to_cart);
			holder.population = (TextView) view.findViewById(R.id.purchase_price);
			holder.designer_name = (TextView) view.findViewById(R.id.designer_name);
			holder.flag = (ImageView) view.findViewById(R.id.imageView1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.rank.setText(worldpopulationlist.get(position).getSale_price());
		holder.country.setText(worldpopulationlist.get(position).getProduct_name());
		holder.population.setText(worldpopulationlist.get(position)
				.getPurchase_price());
		holder.designer_name.setText(worldpopulationlist.get(position)
				.getDesigner_name());
		// Set the results into ImageView
		imageLoader.DisplayImage(worldpopulationlist.get(position).getProduct_image(), holder.flag);
//		holder.buy_now.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (ShoppingCart.product_names.contains(worldpopulationlist.get(position).getProduct_name())){
//					new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
//							.setTitleText("Already added to Cart")
//							.show();
//				}else {
//					Intent intent = new Intent(mContext, LoginItemCartActivity.class);
//					ShoppingCart.product_names.add(worldpopulationlist.get(position).getProduct_name());
//					ShoppingCart.sale_prices.add(worldpopulationlist.get(position).getSale_price());
//					ShoppingCart.purchase_prices.add(worldpopulationlist.get(position).getPurchase_price());
//					ShoppingCart.designer_names.add(worldpopulationlist.get(position).getDesigner_name());
//					ShoppingCart.product_images.add(worldpopulationlist.get(position).getProduct_image());
//					ShoppingCart.avaliablilityArray.add(worldpopulationlist.get(position).getAvailability());
//					ShoppingCart.qtyArray.add(worldpopulationlist.get(position).getQty());
//					ShoppingCart.product_ids.add(worldpopulationlist.get(position).getProduct_id());
//					mContext.startActivity(intent);
//				}
//			}
//		});
//		holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				if (ShoppingCart.product_names.contains(worldpopulationlist.get(position).getProduct_name())){
//					new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
//							.setTitleText("Already added to Cart")
//							.show();
//				}else {
//					ShoppingCart.product_names.add(worldpopulationlist.get(position).getProduct_name());
//					ShoppingCart.sale_prices.add(worldpopulationlist.get(position).getSale_price());
//					ShoppingCart.purchase_prices.add(worldpopulationlist.get(position).getPurchase_price());
//					ShoppingCart.designer_names.add(worldpopulationlist.get(position).getDesigner_name());
//					ShoppingCart.product_images.add(worldpopulationlist.get(position).getProduct_image());
//					ShoppingCart.avaliablilityArray.add(worldpopulationlist.get(position).getProduct_name());
//					ShoppingCart.qtyArray.add(worldpopulationlist.get(position).getQty());
//					ShoppingCart.product_ids.add(worldpopulationlist.get(position).getProduct_id());
//
//					new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
//							.setTitleText("Added to Cart!")
//							.show();
//
//				}
//			}
//		});
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(mContext, ProductDescriptionPage.class);
				// Pass all data rank
				intent.putExtra("rank",
						(worldpopulationlist.get(position).getSale_price()));
				// Pass all data country
				intent.putExtra("country",
						(worldpopulationlist.get(position).getProduct_name()));
				// Pass all data population
				intent.putExtra("population",
						(worldpopulationlist.get(position).getPurchase_price()));
				// Pass all data flag
				intent.putExtra("flag",
						(worldpopulationlist.get(position).getProduct_image()));
				// Start SingleItemView Class
				mContext.startActivity(intent);
			}
		});

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		worldpopulationlist.clear();
		if (charText.length() == 0) {
			worldpopulationlist.addAll(arraylist);
		} else {
			for (ProductModelClass wp : arraylist) {
				if (wp.getProduct_name().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					worldpopulationlist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
