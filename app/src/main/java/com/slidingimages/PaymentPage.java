package com.slidingimages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.slidingimages.app.Config;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Creative on 04-Sep-16.
 */
public class PaymentPage extends Activity {
    private RadioGroup radioGroup1;
    private String payment_mode="paypal";
    String paymentAmount;
    private Button place_order;
    private String result="null";
    private String sale_id;
    ArrayList<String> product_idArray= new ArrayList<>();
    ArrayList<String> product_nameArray= new ArrayList<>();
    ArrayList<String> product_priceArray= new ArrayList<>();
    ArrayList<String> product_subtotalArray= new ArrayList<>();
    ArrayList<String> firstnameArray= new ArrayList<>();
    ArrayList<String> lastnameArray= new ArrayList<>();
    ArrayList<String> zipArray= new ArrayList<>();
    ArrayList<String> cityArray= new ArrayList<>();
    ArrayList<String> address1Array= new ArrayList<>();
    ArrayList<String> address2Array= new ArrayList<>();

    private TextView total_qty,total_sale_price,total_purchase_price,shipping,tax,grand_total;
    JSONObject jsonObjFinal;
    JSONArray jsonArr;

    private CustomProgressDialog mCustomProgressDialog;
    private static final String REGISTER_URL = HomePage.FIRSTPART+"cartFinish";
//    private String REGISTER_URL="http://192.168.0.109/creative/mazyoona/index.php/rest/api/cartFinish1";

    private String ORDER_URL = "";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PRODUCTS = "products";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_lASTNAME = "lastname";
    public static final String KEY_ADDRESS_ONE = "address1";
    public static final String KEY_ADDRESS_TWO = "address2";
    public static final String KEY_CITY = "city";
    public static final String KEY_ZIP = "zip";
    public static final String KEY_PAYMENT_TYPE = "payment_type";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_PRODUCT_ID = "id";
    public static final String KEY_QTY   = "qty";
    public static final String KEY_OPTION = "option";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PRICE = "price";
    public static final String KEY_PRODUCTNAME = "name";
    public static final String KEY_TAX = "tax";
    public static final String KEY_SUBTOTAL = "subtotal";
    public static final String KEY_SALEID = "sale_id";
    String userid_pref;
    String paymentDetails;
    // PayPal configuration
    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(Config.PAYPAL_ENVIRONMENT).clientId(
                    Config.PAYPAL_CLIENT_ID);

    public final int PAYPAL_RESPONSE = 123;
    Double aDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        total_qty=(TextView)findViewById(R.id.total_qty);
        total_sale_price=(TextView)findViewById(R.id.total_sale_price);
        total_purchase_price=(TextView)findViewById(R.id.total_purchase_price);
        shipping=(TextView)findViewById(R.id.shipping);
        grand_total=(TextView)findViewById(R.id.grandtotal);
        tax=(TextView)findViewById(R.id.tax);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        place_order=(Button)findViewById(R.id.place_order) ;
        mCustomProgressDialog = new CustomProgressDialog(PaymentPage.this);
        total_qty.setText(ShoppingCart.qtyArray.size()+"");
        total_sale_price.setText(String.valueOf(total_sale_price()));
        shipping.setText("20 AED");
        tax.setText("0");
        jsonObjFinal = new JSONObject();
        if (!ShoppingCart.purchase_prices.isEmpty()){
            paymentAmount= String.valueOf(total_sale_price()+20);
            grand_total.setText(paymentAmount+" AED");
        }else {
            paymentAmount = "0";
        }
        jsonArr = new JSONArray();



//        for (int i=0;i< ShoppingCart.product_names.size();i++){
//            JSONObject jsonObject= new JSONObject();
//            try {
//                jsonObject.put(KEY_PRODUCT_ID, ShoppingCart.product_ids.get(i));
//                jsonObject.put(KEY_QTY,ShoppingCart.qtyArray.get(i));
//                jsonObject.put(KEY_PRICE,ShoppingCart.purchase_prices.get(i));
//                jsonObject.put(KEY_PRODUCTNAME,ShoppingCart.product_names.get(i));
//                Integer subtotal=ShoppingCart.qtyArray.get(i)*Integer.parseInt(ShoppingCart.purchase_prices.get(i));
//                jsonObject.put(KEY_SUBTOTAL,subtotal);
//                jsonArr.put(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
        SharedPreferences prefs = getSharedPreferences(Activity_Login.MY_PREFS_NAME, MODE_PRIVATE);
        userid_pref= prefs.getString("userid", "null");
//        try {
//            jsonObjFinal.put("products",jsonArr);
//            System.out.println(jsonArr+"jsonn");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (payment_mode.equals("paypal")){

                    new SweetAlertDialog(PaymentPage.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Selected payment mode is paypal.Click OK to continue")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    if (!ShoppingCart.purchase_prices.isEmpty()){
                                        aDouble=total_sale_price()+20;
                                        paymentAmount= String.valueOf(aDouble);
                                    }else {
                                        paymentAmount = "0";
                                    }
                                    jsonArr = new JSONArray();

                                    for (int i=0;i< ShoppingCart.product_names.size();i++){
                                        JSONObject jsonObject= new JSONObject();
                                        try {
                                            jsonObject.put(KEY_PRODUCT_ID, ShoppingCart.product_ids.get(i));
                                            jsonObject.put(KEY_QTY,ShoppingCart.qtyArray.get(i));
                                            jsonObject.put(KEY_PRICE,ShoppingCart.purchase_prices.get(i));
                                            jsonObject.put(KEY_PRODUCTNAME,ShoppingCart.product_names.get(i));
                                            Integer subtotal=ShoppingCart.qtyArray.get(i)*Integer.parseInt(ShoppingCart.purchase_prices.get(i));
                                            jsonObject.put(KEY_SUBTOTAL,subtotal);
                                            jsonArr.put(jsonObject);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    getPayment();
                                }
                            })
                            .setCancelText("Cancel")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
//                }else if (payment_mode.equals("bank")){
//                    new SweetAlertDialog(PaymentPage.this, SweetAlertDialog.NORMAL_TYPE)
//                            .setTitleText("")
//                            .setContentText("Not available please select any other payment mode")
//                            .setConfirmText("OK")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    sDialog.dismissWithAnimation();
//                                }
//                            })
//                            .show();
                }else if (payment_mode.equals("cod")){
                    new SweetAlertDialog(PaymentPage.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Selected payment mode is Cash On Delivery.Click OK to continue")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    if (!ShoppingCart.purchase_prices.isEmpty()){
                                        paymentAmount= String.valueOf(total_sale_price()+20);
                                    }else {
                                        paymentAmount = "0";
                                    }
                                    jsonArr = new JSONArray();

                                    for (int i=0;i< ShoppingCart.product_names.size();i++){
                                        JSONObject jsonObject= new JSONObject();
                                        try {
                                            jsonObject.put(KEY_PRODUCT_ID, ShoppingCart.product_ids.get(i));
                                            jsonObject.put(KEY_QTY,ShoppingCart.qtyArray.get(i));
                                            jsonObject.put(KEY_PRICE,ShoppingCart.purchase_prices.get(i));
                                            jsonObject.put(KEY_PRODUCTNAME,ShoppingCart.product_names.get(i));
                                            Integer subtotal=ShoppingCart.qtyArray.get(i)*Integer.parseInt(ShoppingCart.purchase_prices.get(i));
                                            jsonObject.put(KEY_SUBTOTAL,subtotal);
                                            jsonArr.put(jsonObject);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    ProgressTask1 progressTask= new ProgressTask1();
                                    progressTask.execute();
                                    Intent intent = new Intent(PaymentPage.this, ConfirmationActivity.class);
                                    intent.putExtra("PaymentDetails", "Cash On Delivery");
                                    intent.putExtra("PaymentAmount", paymentAmount);
                                    startActivity(intent);
                                    PaymentPage.this.finish();
                                }
                            })
                            .setCancelText("Cancel")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }else {
                    new SweetAlertDialog(PaymentPage.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("")
                            .setContentText("Please select suitable payment mode")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }
        });
        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

        startService(intent);
        // Checked change Listener for RadioGroup 1
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.paypal:
                        payment_mode="paypal";
                        break;
//                    case R.id.bank:
//                        payment_mode="bank";
//                        break;
                    case R.id.cod:
                        payment_mode="cod";
                        break;
                    default:
                        break;
                }
            }
        });
    }
    public double total_sale_price()
    {
        double sum = 0;
        for(int i = 0; i < ShoppingCart.purchase_prices.size(); i++)
        {
            sum += (int)Double.parseDouble(ShoppingCart.purchase_prices.get(i));
        }
        return sum;
    }
    private void getPayment() {

//        double integer=Integer.valueOf(paymentAmount.trim())/3.7;
        String paypalAmount=String.valueOf(aDouble/3.7);
//        paymentAmount= String.valueOf(aDouble/3.7);
        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paypalAmount)), "USD", "Mazyoona Billing",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_RESPONSE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_RESPONSE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        ProgressTask1 progressTask= new ProgressTask1();
                        progressTask.execute();



                        //Getting the payment details
                        paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        Intent intent = new Intent(PaymentPage.this, ConfirmationActivity.class);
                        intent.putExtra("PaymentDetails", paymentDetails);
                        intent.putExtra("PaymentAmount", paymentAmount);
                        startActivity(intent);

//                        ProgressTask2 progressTask2= new ProgressTask2();
//                        progressTask2.execute();



                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private class ProgressTask1 extends AsyncTask<String, Void, Boolean> {

        protected void onPreExecute() {
            mCustomProgressDialog.show("");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (mCustomProgressDialog.isShowing()|| mCustomProgressDialog!=null) {
                mCustomProgressDialog.dismiss("");
            }
        }

        protected Boolean doInBackground(final String... args) {

//


            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(PaymentPage.this,response,Toast.LENGTH_LONG).show();
                            try {
                                JSONObject mainObject = new JSONObject(response.toString());
                                sale_id=mainObject.getString("sale_id");
                                ORDER_URL=HomePage.FIRSTPART+"orderInvoice?sale_id="+sale_id;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                Toast.makeText(PaymentPage.this,error.toString(),Toast.LENGTH_LONG).show();
//                              result="failed";
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(KEY_PRODUCTS,String.valueOf(jsonArr));
                    params.put(KEY_FIRSTNAME,ShippingPage.f_name_str);
                    params.put(KEY_lASTNAME,ShippingPage.l_name_str);
                    params.put(KEY_PHONE,ShippingPage.phone_str);
                    params.put(KEY_ADDRESS_ONE,ShippingPage.address_one_str);
                    params.put(KEY_ADDRESS_TWO,ShippingPage.address_two_str);
                    params.put(KEY_CITY,ShippingPage.city_str);
                    params.put(KEY_ZIP,ShippingPage.zip_str);
                    params.put(KEY_TOTAL,paymentAmount);

                    if (userid_pref.equals("")||  userid_pref.equals("null")){
                        params.put(KEY_USER_ID,Activity_Login.userId);
                    }else {
                        params.put(KEY_USER_ID,userid_pref);
                    }

                    params.put(KEY_PAYMENT_TYPE,payment_mode);
                    System.out.println(params+"params");
                    return params;

                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(PaymentPage.this);
            requestQueue.add(stringRequest);


            return null;
        }
    }
    private class ProgressTask2 extends AsyncTask<String, Void, Boolean> {

        protected void onPreExecute() {
            mCustomProgressDialog.show("");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (mCustomProgressDialog.isShowing()|| mCustomProgressDialog!=null) {
                mCustomProgressDialog.dismiss("");
            }
        }

        protected Boolean doInBackground(final String... args) {



            StringRequest stringRequest = new StringRequest(Request.Method.GET, ORDER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(PaymentPage.this,response,Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                JSONObject jsonObject1=jsonObject.getJSONObject("sale_details");
                                JSONArray jsonArrayProducts = jsonObject1.getJSONArray("products");
//                                JSONArray jsonArrayShipping = jsonObject1.getJSONArray("shipping_info");


                                for (int i = 0; i < jsonArrayProducts.length(); i++) {
                                    JSONObject jsonObjectUser = jsonArrayProducts.getJSONObject(i);
                                    String id = jsonObjectUser.getString("id");
                                    String name = jsonObjectUser.getString("name");
                                    String price = jsonObjectUser.getString("price");
                                    String subtotal = jsonObjectUser.getString("subtotal");


                                    product_idArray.add(id);
                                    product_nameArray.add(name);
                                    product_priceArray.add(price);
                                    product_subtotalArray.add(subtotal);

                                }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = new Intent(PaymentPage.this, ConfirmationActivity.class);
                                    intent.putExtra("PaymentDetails", paymentDetails);
                                    intent.putExtra("PaymentAmount", paymentAmount);
                                    intent.putExtra("productsIdArray", product_idArray);
                                    intent.putExtra("productsNameArray", product_nameArray);
                                    intent.putExtra("productsPriceArray", product_priceArray);
                                    intent.putExtra("productsSubtotalArray", product_subtotalArray);
                                    intent.putExtra("firstnameArray", firstnameArray);
                                    intent.putExtra("lastnameArray", lastnameArray);
                                    intent.putExtra("address1Array", address1Array);
                                    intent.putExtra("address2Array", address2Array);
                                    intent.putExtra("cityArray", cityArray);
                                    intent.putExtra("zipArray", zipArray);
                                    startActivity(intent);
                                    PaymentPage.this.finish();
                                }
                            });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PaymentPage.this,error.toString(),Toast.LENGTH_LONG).show();
//                              result="failed";
                        }
                    }){
//                @Override
//                protected Map<String,String> getParams(){
//                    Map<String,String> params = new HashMap<String, String>();
//                    params.put(KEY_SALEID, sale_id);
//                    return params;
//                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(PaymentPage.this);
            requestQueue.add(stringRequest);


            return null;
        }
    }
}

