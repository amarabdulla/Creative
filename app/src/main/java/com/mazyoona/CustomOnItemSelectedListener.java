package com.mazyoona;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Creative on 15-Sep-16.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {
//        ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
//
//        Toast.makeText(parent.getContext(),
//                "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
//                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
