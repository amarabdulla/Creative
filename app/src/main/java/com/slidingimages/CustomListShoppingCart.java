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
    private ArrayList<String> names;
    private ArrayList<String> desc;
    private ArrayList<String> imageid;
    private Activity context;
    private ImageLoader imageLoader;

    public CustomListShoppingCart(Activity context, ArrayList<String> names, ArrayList<String> imageid, ArrayList<String> desc) {
        super(context, R.layout.list_layout_cart, names);
        this.context = context;
        this.names = names;
        this.imageid = imageid;
        this.desc = desc;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.list_item_cart, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        Button close=(Button)listViewItem.findViewById(R.id.close) ;
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(names.get(position));
        textViewDesc.setText(desc.get(position));
        imageLoader.DisplayImage(imageid.get(position), image);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(listViewItem,names.get(position)+" removed from cart",Snackbar.LENGTH_SHORT).show();
                names.remove(names.get(position));
                desc.remove(desc.get(position));
                imageid.remove(imageid.get(position));
                notifyDataSetChanged();


            }
        });
        return  listViewItem;
    }
}
