package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ORDER_ID;
import static com.example.baostore.Constant.Constants.ORDER_OBJECT;
import static com.example.baostore.Api.RetrofitCallBack.getOrderDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.Order;
import com.example.baostore.responses.OrderDetailResponse;
import com.google.gson.JsonObject;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import retrofit2.Call;

public class DetailOrderItemActivity extends AppCompatActivity {
    public TextView tvDate, tvTotalPrice, tvPaymentType, tvReceiverName, tvReceiverPhoneNumber,tvReceiverAddress;
    public RecyclerView recyOrderDetail;
    Button btnToPayment;
    ApiService service;
    double totalPrice;
    private static final String clientKey = "AQwusKD4K-4uW7Vu16IMPfvggch0k-g4bgBmwEcHuLjF3l5xmpzIc6HvOcxj1dE18AbMX5arDq-6KCyQ";
    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready,
            // switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            // on below line we are passing a client id.
            .clientId(clientKey);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_item);
        tvDate = findViewById(R.id.tvOrderDate_detailorderitem);
        tvTotalPrice = findViewById(R.id.tvTotalPrice_detailorderitem);
        tvPaymentType = findViewById(R.id.tvPaymentType_orderhistoryitem);
        tvReceiverName = findViewById(R.id.tvReceiverName_detailorder);
        tvReceiverPhoneNumber = findViewById(R.id.tvReceiverPhoneNumber_detailorder);
        tvReceiverAddress = findViewById(R.id.tvReceiverAddress_orderhistoryitem);
        recyOrderDetail = findViewById(R.id.recyDetailOrder_detailorder);
        btnToPayment = findViewById(R.id.btnToPayment);

        recyOrderDetail.setLayoutManager(new LinearLayoutManager(this));

        service = GetRetrofit.getInstance().createRetrofit();

        Bundle bundle = getIntent().getExtras();
        Order order = (Order) bundle.getSerializable(ORDER_OBJECT);

        JsonObject object = new JsonObject();
        object.addProperty(ORDER_ID, order.getOrderid());

        Call<OrderDetailResponse> orderDetailCall = service.getOrderDetailById(object);
        orderDetailCall.enqueue(getOrderDetail(this, recyOrderDetail, tvTotalPrice));

        tvDate.setText(order.getCreatedate());
        //tvTotalPrice = order.getOrderTotal()
        tvPaymentType.setText(order.getPayment() + "");
        tvReceiverName.setText(order.getFullname());
        tvReceiverPhoneNumber.setText(order.getPhonenumber());
        tvReceiverAddress.setText(order.getAddress());

        checkTotalPrice();


        btnToPayment.setOnClickListener(view -> {
            getPayment();
        });
    }

    private void getPayment(){
        double amount = totalPrice* 0.000043;

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Course Fees",
                PayPalPayment.PAYMENT_INTENT_SALE);

        // Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        // Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // Starting the intent activity for result
        // the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }

    public void checkTotalPrice(){
        if(!tvTotalPrice.getText().toString().isEmpty()){
            totalPrice = Double.parseDouble(tvTotalPrice.getText().toString());
            tvTotalPrice.setText(new Utils().priceToString(totalPrice));
        } else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkTotalPrice();
                }
            },1000);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                // if confirmation is not null
                if (confirm != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");
                        Toast.makeText(this, "Payment " + state + "\n with payment id is " + payID, Toast.LENGTH_SHORT).show();
                        Log.d("--Payment", "Payment " + state + "\n with payment id is " + payID);
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // on below line we are checking the payment status.
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // on below line when the invalid paypal config is submitted.
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
}