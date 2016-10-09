package com.slidingimages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Creative on 20-Sep-16.
 */
public class CustomOrderListAdapter  extends BaseAdapter {
    /*********** Declare Used Variables *********/
    private Activity activity;
    private static LayoutInflater inflater=null;
    //    public Resources res;
//    ProductModelClass tempValues=null;
    int i=0;
    private ArrayList<String> images;
    private ArrayList<String> title;
    private ArrayList<String> companyArray;
    private ArrayList<String> designer_names;
    private ArrayList<String> emailArray;
    private ArrayList<String> addressArray;
    private ArrayList<String> designeridArray;
    private ImageLoader imageLoader;
    /*************  CustomAdapter Constructor *****************/
    public CustomOrderListAdapter(Activity a, ArrayList<String> images, ArrayList<String> title, ArrayList<String> companyArray,
                                     ArrayList<String> emailArray,ArrayList<String> addressArray,ArrayList<String> designeridArray) {

        /********** Take passed values **********/
        activity = a;
        this.images = images;
        this.title = title;
        this.companyArray = companyArray;
        this.emailArray = emailArray;
        this.addressArray = addressArray;
        this.designeridArray = designeridArray;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(a.getApplicationContext());

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(title.size()<=0)
            return 1;
        return title.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView product_name;
        public TextView designer_name;
        public TextView textWide;
        public TextView textAddress;
        public ImageView image;
        Button buy_now,add_to_cart;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listview_item_order, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.product_name = (TextView) vi.findViewById(R.id.txt);
            holder.designer_name=(TextView)vi.findViewById(R.id.cur);
            holder.textWide=(TextView)vi.findViewById(R.id.text3);
//            holder.buy_now =(Button) vi.findViewById(R.id.buy_now);
//            holder.add_to_cart =(Button) vi.findViewById(R.id.add_to_cart);
            holder.textAddress=(TextView)vi.findViewById(R.id.text4);
            holder.image=(ImageView)vi.findViewById(R.id.imageView);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(title.size()<=0)
        {
            holder.product_name.setText("No Data");

        }
        else
        {
            holder.product_name.setText(title.get(position) );
            holder.designer_name.setText( "Price- "+companyArray.get(position)+" AED" );
//            holder.textWide.setText( emailArray.get(position) );
            holder.textAddress.setText( "Order ID- "+addressArray.get(position) );
            imageLoader.DisplayImage(images.get(position), holder.image);



            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent=new Intent(activity,DesignerDetailActivity.class);
//                    intent.putExtra("designerName",title.get(position));
//                    intent.putExtra("designerId",designeridArray.get(position));
//                    activity.startActivity(intent);
                }
            });

        }
        return vi;
    }


}
