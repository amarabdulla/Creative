package com.slidingimages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.slidingimages.cart.LoginItemCartActivity;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;


public class GridViewAdapter extends BaseAdapter {

    //Imageloader to load images
    private ImageLoader imageLoader;

    //Context
    private Context context;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> images;
    private ArrayList<String> product_names;
    private ArrayList<String> sale_prices;
    private ArrayList<String> designer_names;
    private ArrayList<String> purchase_prices;
    private static LayoutInflater inflater=null;
    private ArrayList<String> avaliablilityArray = new ArrayList<String>();
    private ArrayList<String> discountArray = new ArrayList<String>();
    private ArrayList<String> qtyArray = new ArrayList<String>();
    private ArrayList<String> productIdArray=new ArrayList<>();
    private ArrayList<String> descArray=new ArrayList<>();
    private ArrayList<String> images_l=new ArrayList<>();

    public GridViewAdapter (Context context, ArrayList<String> images,ArrayList<String> images_l, ArrayList<String> product_names,ArrayList<String> sale_prices
            ,ArrayList<String> purchase_prices, ArrayList<String> designer_names, ArrayList<String> avaliablilityArray,
                            ArrayList<String> qtyArray,ArrayList<String> discountArray,ArrayList<String> productIdArray,ArrayList<String> descArray){
        //Getting all the values
        this.context = context;
        this.images = images;
        this.images_l  = images_l;
        this.sale_prices = sale_prices;
        this.designer_names = designer_names;
        this.purchase_prices = purchase_prices;
        this.product_names = product_names;
        this.avaliablilityArray = avaliablilityArray;
        this.qtyArray = qtyArray;
        this.discountArray = discountArray;
        this.productIdArray = productIdArray;
        this.descArray = descArray;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    public class Holder
    {
        TextView product_name;
        TextView sale_price;
        TextView purchase_price;
        TextView designer_name;
        ImageView img;
        TextView discount;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
       final View vi=inflater.inflate(R.layout.layout_grid_test, null);

//        vi = inflater.inflate(R.layout.grid, null);
        holder.product_name =(TextView) vi.findViewById(R.id.product_name);
        holder.sale_price =(TextView) vi.findViewById(R.id.sale_price);
        holder.purchase_price =(TextView) vi.findViewById(R.id.purchase_price);
//        holder.buy_now =(Button) vi.findViewById(R.id.buy_now);
//        holder.add_to_cart =(Button) vi.findViewById(R.id.add_to_cart);
        holder.designer_name =(TextView) vi.findViewById(R.id.designer_name);
        holder.img=(ImageView) vi.findViewById(R.id.imageView1);
        holder.discount=(TextView) vi.findViewById(R.id.discount) ;


            holder.product_name.setText(product_names.get(position));
            holder.designer_name.setText(designer_names.get(position));
            holder.purchase_price.setText(purchase_prices.get(position)+" AED");
            if (sale_prices.get(position).equals(purchase_prices.get(position))){
                holder.sale_price.setText("");
            }else {
                holder.sale_price.setPaintFlags(holder.sale_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.sale_price.setText(sale_prices.get(position) + " AED");
            }

       /* holder.buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShoppingCart.product_names.contains(product_names.get(position))){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Already added to Cart")
                            .show();
                }else {
                    Intent intent = new Intent(context, LoginItemCartActivity.class);
                    ShoppingCart.product_names.add(product_names.get(position));
                    ShoppingCart.sale_prices.add(sale_prices.get(position));
                    ShoppingCart.purchase_prices.add(purchase_prices.get(position));
                    ShoppingCart.designer_names.add(designer_names.get(position));
                    ShoppingCart.product_images.add(images.get(position));
                    ShoppingCart.avaliablilityArray.add(avaliablilityArray.get(position));
                    Shopp
                    ingCart.qtyArray.add(qtyArray.get(position));
                    ShoppingCart.product_ids.add(productIdArray.get(position));
                    context.startActivity(intent);
                }
            }
        });
        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ShoppingCart.product_names.contains(product_names.get(position))){
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Already added to Cart")
                            .show();
                }else {
                    ShoppingCart.product_names.add(product_names.get(position));
                    ShoppingCart.sale_prices.add(sale_prices.get(position));
                    ShoppingCart.purchase_prices.add(purchase_prices.get(position));
                    ShoppingCart.designer_names.add(designer_names.get(position));
                    ShoppingCart.product_images.add(images.get(position));
                    ShoppingCart.avaliablilityArray.add(avaliablilityArray.get(position));
                    ShoppingCart.qtyArray.add(qtyArray.get(position));
                    ShoppingCart.product_ids.add(productIdArray.get(position));

                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Added to Cart!")
                            .show();

                }
            }
        });*/
        holder.product_name.setText(product_names.get(position));
        //DisplayImage function from ImageLoader Class
//        imageLoader.DisplayImage(images.get(position), holder.img);
        Picasso.with(context).load(images.get(position)).placeholder(R.drawable.stub).noFade().into(holder.img);
        if (!discountArray.get(position).equals("0")){
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText(discountArray.get(position)+"% OFF");

            holder.discount.setBackgroundResource(R.color.green);
            holder.discount.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.discount.setGravity(Gravity.CENTER);
        }else {
            holder.discount.setVisibility(View.INVISIBLE);
        }
        if (avaliablilityArray.get(position).equals("0")){
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText("Out of Stock");
            holder.discount.setBackgroundResource(R.color.red);
            holder.discount.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.discount.setGravity(Gravity.CENTER);
        }else {
//            holder.discount.setVisibility(View.INVISIBLE);
        }

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,ProductsDetailActivity.class);
                intent.putExtra("productId",productIdArray.get(position));
                intent.putExtra("productName", product_names.get(position));
                intent.putExtra("productImage", images_l.get(position));
                intent.putExtra("sale_price", sale_prices.get(position));
                intent.putExtra("purchase_price", purchase_prices.get(position));
                intent.putExtra("designer_name", designer_names.get(position));
                intent.putExtra("available", avaliablilityArray.get(position));
                intent.putExtra("qty", qtyArray.get(position));
                intent.putExtra("desc", descArray.get(position));
                context.startActivity(intent);
            }
        });
        return vi;
    }

}
