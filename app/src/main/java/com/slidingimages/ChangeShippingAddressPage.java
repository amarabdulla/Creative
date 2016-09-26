package com.slidingimages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Creative on 04-Sep-16.
 */
public class ChangeShippingAddressPage extends Activity {
    private Button useThisAddress,changeAddress;
    private TextView address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipping_address_change);
        useThisAddress=(Button)findViewById(R.id.use_this_address);
        changeAddress=(Button)findViewById( R.id.change_address);
        address=(TextView)findViewById( R.id.address);
        address.setText(ShippingPage.address_one_str+"\n"+ShippingPage.address_two_str+"\n");
        useThisAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChangeShippingAddressPage.this,PaymentPage.class);
                startActivity(intent);
            }
        });
        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChangeShippingAddressPage.this,ShippingPage.class);
                startActivity(intent);
            }
        });
    }
}
