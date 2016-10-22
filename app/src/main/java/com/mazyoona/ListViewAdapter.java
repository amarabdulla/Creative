package com.mazyoona;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private ImageLoader imageLoader;
	private List<ProductModelClass> productmodel_list = null;
	private ArrayList<ProductModelClass> arraylist;

	public ListViewAdapter(Context context,
						   List<ProductModelClass> productmodel_list) {
		mContext = context;
		this.productmodel_list = productmodel_list;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<ProductModelClass>();
		this.arraylist.addAll(productmodel_list);
		imageLoader = new ImageLoader(context.getApplicationContext());
	}

	public class ViewHolder {
		TextView sale_price;
		TextView product_name;
		TextView purchase_price;
		ImageView product_image;
		TextView designer_name;
	}

	@Override
	public int getCount() {
		return productmodel_list.size();
	}

	@Override
	public ProductModelClass getItem(int position) {
		return productmodel_list.get(position);
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
			holder.sale_price = (TextView) view.findViewById(R.id.sale_price);
			holder.product_name = (TextView) view.findViewById(R.id.product_name);
			holder.purchase_price = (TextView) view.findViewById(R.id.purchase_price);
			holder.designer_name = (TextView) view.findViewById(R.id.designer_name);
			holder.product_image = (ImageView) view.findViewById(R.id.imageView1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.sale_price.setText(productmodel_list.get(position).getSale_price());
		holder.product_name.setText(productmodel_list.get(position).getProduct_name());
		holder.purchase_price.setText(productmodel_list.get(position)
				.getPurchase_price());
		holder.designer_name.setText(productmodel_list.get(position)
				.getDesigner_name());
		imageLoader.DisplayImage(productmodel_list.get(position).getProduct_image(), holder.product_image);
		// Set the results into ImageView


		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		productmodel_list.clear();
		if (charText.length() == 0) {
			productmodel_list.addAll(arraylist);
		} else {
			for (ProductModelClass wp : arraylist) {
				if (wp.getProduct_name().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					productmodel_list.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
