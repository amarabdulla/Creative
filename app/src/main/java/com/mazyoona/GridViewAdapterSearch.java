package com.mazyoona;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
	public interface OnDataChangeListener{
		public void onDataChanged(int size);
	}
	OnDataChangeListener mOnDataChangeListener;
	public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
		mOnDataChangeListener = onDataChangeListener;
	}
	private void doButtonOneClickActions() {

		if(mOnDataChangeListener != null){
			mOnDataChangeListener.onDataChanged(ShoppingCart.product_names.size());
		}
	}
	public class ViewHolder {
		TextView rank;
		TextView country;
		TextView population;
		ImageView flag;
		TextView designer_name;
		TextView discount;
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
			view = inflater.inflate(R.layout.grid_search_new, null);
			holder.rank = (TextView) view.findViewById(R.id.sale_price);
			holder.country = (TextView) view.findViewById(R.id.product_name);
//			holder.buy_now =(Button) view.findViewById(R.id.buy_now);
//			holder.add_to_cart =(Button) view.findViewById(R.id.add_to_cart);
			holder.population = (TextView) view.findViewById(R.id.purchase_price);
			holder.designer_name = (TextView) view.findViewById(R.id.designer_name);
			holder.flag = (ImageView) view.findViewById(R.id.imageView1);
			holder.discount =(TextView) view.findViewById(R.id.discount) ;
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

//		holder.rank.setText(worldpopulationlist.get(position).getSale_price()+" AED");


		if (worldpopulationlist.get(position).getSale_price().equals(worldpopulationlist.get(position).getPurchase_price())){
//			holder.sale_price.setText("");
			holder.rank.setText("");
		}else {
			holder.rank.setPaintFlags(holder.rank.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//			holder.sale_price.setText(sale_prices.get(position) + " AED");
			holder.rank.setText(worldpopulationlist.get(position).getSale_price()+" AED");
		}
		holder.country.setText(worldpopulationlist.get(position).getProduct_name());
		holder.population.setText(worldpopulationlist.get(position)
				.getPurchase_price()+" AED");
		holder.designer_name.setText(worldpopulationlist.get(position)
				.getDesigner_name());
		// Set the results into ImageView
//		imageLoader.DisplayImage(worldpopulationlist.get(position).getProduct_image(), holder.flag);
		Glide.with(mContext)
				.load(worldpopulationlist.get(position).getProduct_image())
				.placeholder(R.drawable.mazyoona_stub_logo)
				.error(R.drawable.stub_error_gridview)
				.crossFade()
				.into(holder.flag);
	if (!worldpopulationlist.get(position).getDiscount().equals("0")){
		holder.discount.setVisibility(View.VISIBLE);
		holder.discount.setText(worldpopulationlist.get(position).getDiscount()+"% OFF");

		holder.discount.setBackgroundResource(R.color.green);
		holder.discount.setTextColor(ContextCompat.getColor(mContext, R.color.white));
		holder.discount.setGravity(Gravity.CENTER);
	}else {
		holder.discount.setVisibility(View.INVISIBLE);
	}
	if (worldpopulationlist.get(position).getAvailability().equals("0")){
		holder.discount.setVisibility(View.VISIBLE);
		holder.discount.setText("Out of Stock");
		holder.discount.setBackgroundResource(R.color.red);
		holder.discount.setTextColor(ContextCompat.getColor(mContext, R.color.white));
		holder.discount.setGravity(Gravity.CENTER);
	}else {
//            holder.discount.setVisibility(View.INVISIBLE);
	}
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent= new Intent(mContext,ProductsDetailActivity.class);
				intent.putExtra("productId",worldpopulationlist.get(position).getProduct_id());
				intent.putExtra("productName", worldpopulationlist.get(position).getProduct_name());
				intent.putExtra("productImage", worldpopulationlist.get(position).getImageOrg());
				intent.putExtra("sale_price", worldpopulationlist.get(position).getSale_price());
				intent.putExtra("purchase_price", worldpopulationlist.get(position).getPurchase_price());
				intent.putExtra("designer_name", worldpopulationlist.get(position).getDesigner_name());
				intent.putExtra("available", worldpopulationlist.get(position).getAvailability());
				intent.putExtra("discount", worldpopulationlist.get(position).getDiscount());
				intent.putExtra("qty", worldpopulationlist.get(position).getQty());
				intent.putExtra("desc", worldpopulationlist.get(position).getDesc());
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
				else{
//					System.out.println("No items found");
					doButtonOneClickActions();
				}
			}
		}
		notifyDataSetChanged();
	}

}
