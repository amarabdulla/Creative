package com.mazyoona;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> images;
    private ArrayList<String> product_names;
    private ArrayList<String> sale_prices;
    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
//            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public AlbumsAdapter(Context mContext, ArrayList<String> images, ArrayList<String> product_names, ArrayList<String> sale_prices) {
        this.mContext = mContext;
        this.images=images;
        this.product_names=product_names;
        this.sale_prices=sale_prices;
        imageLoader = new ImageLoader(mContext.getApplicationContext());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        Album album = albumList.get(position);
        holder.title.setText(product_names.get(position));
        holder.count.setText(sale_prices.get(position) + " AED");
        imageLoader.DisplayImage(images.get(position), holder.thumbnail);
        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }





    @Override
    public int getItemCount() {
        return product_names.size();
    }
}
