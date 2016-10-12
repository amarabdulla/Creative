package com.slidingimages;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomListShoppingCart extends ArrayAdapter<String> {
    private ArrayList<String> product_names;
    private ArrayList<String> purchase_prices;
    private ArrayList<String> product_images;
    private Activity context;
    private ImageLoader imageLoader;
    Integer number=1;
    TextView quantity;
    Integer integer;
    public CustomListShoppingCart(Activity context, ArrayList<String> product_names, ArrayList<String> product_images, ArrayList<String> purchase_prices) {
        super(context, R.layout.list_layout_cart, product_names);
        this.context = context;
        this.product_names = product_names;
        this.product_images = product_images;
        this.purchase_prices = purchase_prices;
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
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.list_item_cart, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        Button close=(Button)listViewItem.findViewById(R.id.close) ;
        Button plus=(Button)listViewItem.findViewById(R.id.plus);
        Button minus=(Button)listViewItem.findViewById(R.id.minus);
        quantity=(TextView)listViewItem.findViewById(R.id.textViewQty);
        final Button count=(Button)listViewItem.findViewById(R.id.count) ;
//        integer=Integer.parseInt(ShoppingCart.qtyArray.get(position));
        count.setText(ShoppingCart.qtyArray.get(position)+"");
        quantity.setText("Quantity : "+ShoppingCart.qtyArray.get(position)+"");
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                count.setText(number+++"");
                ShoppingCart.qtyArray.set(position,ShoppingCart.qtyArray.get(position)+1);
                count.setText(ShoppingCart.qtyArray.get(position)+"");
                quantity.setText("Quantity : "+ShoppingCart.qtyArray.get(position)+"");
                notifyDataSetChanged();
                doButtonOneClickActions();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShoppingCart.qtyArray.get(position)!=1) {
                    ShoppingCart.qtyArray.set(position, ShoppingCart.qtyArray.get(position) - 1);
                    count.setText(ShoppingCart.qtyArray.get(position) + "");
                    quantity.setText("Quantity : "+ShoppingCart.qtyArray.get(position)+"");
                    notifyDataSetChanged();
                    doButtonOneClickActions();
                }
            }
        });
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(product_names.get(position));
        textViewDesc.setText("Price  : "+purchase_prices.get(position)+" AED");
        imageLoader.DisplayImage(product_images.get(position), image);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(listViewItem,product_names.get(position)+" removed from cart",Snackbar.LENGTH_SHORT).show();
                ShoppingCart.product_names.remove(ShoppingCart.product_names.get(position));
                ShoppingCart.purchase_prices.remove(ShoppingCart.purchase_prices.get(position));
                ShoppingCart.product_images.remove(ShoppingCart.product_images.get(position));
                ShoppingCart.qtyArray.remove(ShoppingCart.qtyArray.get(position));
//                product_names.remove(product_names.get(position));
//                purchase_prices.remove(purchase_prices.get(position));
//                product_images.remove(product_images.get(position));
                doButtonOneClickActions();
                notifyDataSetChanged();


            }
        });
        return  listViewItem;
    }
}
