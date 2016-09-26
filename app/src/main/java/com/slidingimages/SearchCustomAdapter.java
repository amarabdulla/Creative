package com.slidingimages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Creative on 06-Sep-16.
 */
public class SearchCustomAdapter extends BaseAdapter {
    Context context;

    LayoutInflater inflater;

    ArrayList<String> aaaa1, bbbb1, cccc1;

    ArrayList<String> c_pname;
    ArrayList<String> c_pprice;
    ArrayList<String> c_pdescription;
    ArrayList<String> c_pimage;

    public void setC_pname(ArrayList<String> c_pname) {
        this.c_pname = c_pname;
    }

    public void setC_pimage(ArrayList<String> c_pimage) {
        this.c_pimage = c_pimage;
    }

    public void setC_pprice(ArrayList<String> c_pprice) {
        this.c_pprice = c_pprice;
    }

    public void setC_pdescription(ArrayList<String> c_pdescription) {
        this.c_pdescription = c_pdescription;
    }

    String[] data_text;
    String[] data_image;

    ImageLoader iloader;

    public SearchCustomAdapter(Context c, int productDescribe, ArrayList<String> pname, ArrayList<String> pimage, ArrayList<String> productdescription, ArrayList<String> pprice) {
        // TODO Auto-generated constructor stub

        this.context = c;

        this.c_pname = pname;

        this.c_pprice = pprice;

        this.c_pdescription = productdescription;

        this.c_pimage = pimage;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        iloader = new ImageLoader(context.getApplicationContext());

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return c_pname.size();
        // return data_text.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;

    }

    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        // return c_pname.indexOf(getItem(pos));//arg0;
        return pos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row = convertView;

        ViewHolder vh;

        if (row == null) {
            row = inflater.inflate(R.layout.grid, parent, false);

            vh = new ViewHolder();

            vh.pname = (TextView) row.findViewById(R.id.product_name);

            vh.pprice = (TextView) row.findViewById(R.id.purchase_price);

            vh.image1 = (ImageView) row.findViewById(R.id.imageView1);

//            vh.btn = (Button) row.findViewById(R.id.p_custom_tv);


            row.setTag(vh);
        } else {
            vh = (ViewHolder) row.getTag();
            row = convertView;
        }

        vh.pname.setText(c_pname.get(position));
        vh.pprice.setText("KD " + c_pprice.get(position));
        iloader.DisplayImage(c_pimage.get(position), vh.image1);

        return row;
    }

    public static class ViewHolder {
        TextView pname, pprice;

        ImageView image1;

        Button btn;
    }
}
