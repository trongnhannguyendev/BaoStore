package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.ADDRESS_LOCATION;
import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.CART_LIST;
import static com.example.baostore.Constant.Constants.CART_TOTAL_PRICE;
import static com.example.baostore.Constant.Constants.ODER_NOTE;
import static com.example.baostore.Constant.Constants.ORDER_ADDRESS;
import static com.example.baostore.Constant.Constants.ORDER_PAYMENT;
import static com.example.baostore.Constant.Constants.ORDER_USER_NAME;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.Api.RetrofitCallBack.insertOrder;
import static com.example.baostore.activities.DetailOrderItemActivity.PAYPAL_REQUEST_CODE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.models.Book;
import com.example.baostore.models.Cart;
import com.example.baostore.models.User;
import com.example.baostore.responses.OrderResponse;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class CartPaymentActivity extends AppCompatActivity {
    private LinearLayout btn_hide_show, layout_hide_show;
    ImageView img_funcpayment_arrow;
    Spinner spnPaymentType;
    TextView tvTitleHeader, tvFullname, tvPhoneNumber, tvAddress,
            tvOrderDate, tvNote, tvShipPrice, tvTotalPrice;
    ImageView imgBack;
    MotionButton btnConfirm;
    private CardView cvIconProgress;
    ApiService service;
    List<Cart> cartList;
    List<Book> bookList;
    double totalPrice;
    String fullName, phoneNumber,address;
    User user;

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
        setContentView(R.layout.activity_cart_payment);

        cvIconProgress = findViewById(R.id.cvProgress_3);
        tvTitleHeader = findViewById(R.id.title);
        imgBack = findViewById(R.id.back_button);
        btn_hide_show = findViewById(R.id.btn_hide_show);
        layout_hide_show = findViewById(R.id.layout_hide_show);
        img_funcpayment_arrow = findViewById(R.id.img_funcpayment_arrow);
        spnPaymentType = findViewById(R.id.spnPaymentType_cp);

        tvFullname = findViewById(R.id.tvFullname_cpayment);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber_cpayment);
        tvAddress = findViewById(R.id.tvAddress_cpayment);
        tvOrderDate = findViewById(R.id.tvOrderDate_cpayment);
        tvNote = findViewById(R.id.tvNote_cpayment);
        tvShipPrice = findViewById(R.id.tvShipPrice_cpayment);
        tvTotalPrice = findViewById(R.id.totalPrice_cpayment);
        btnConfirm = findViewById(R.id.btnConfirm_CartPayment);

        service = GetRetrofit.getInstance().createRetrofit();
        Bundle bundle = getIntent().getExtras();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.payment_type));
        spnPaymentType.setAdapter(adapter);

        // Load dữ liệu lên Payment
        String orderDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        fullName =bundle.get(USER_FULL_NAME).toString();
        phoneNumber = bundle.get(USER_PHONE_NUMBER).toString();
        address = bundle.get(ADDRESS_LOCATION).toString();
        totalPrice =Double.parseDouble(bundle.get(CART_TOTAL_PRICE).toString()) + 20000;

        tvFullname.setText(fullName);
        tvPhoneNumber.setText(phoneNumber);
        tvAddress.setText(address);
        tvOrderDate.setText(orderDate);
        //tvBookPrice.setText(null);
        //tvShipPrice.setText(null);
        tvTotalPrice.setText(new Utils().priceToString(totalPrice));

        user= SharedPrefManager.getInstance(this).getUser();
        cartList = (List<Cart>) bundle.getSerializable(CART_LIST);
        bookList = (List<Book>) bundle.getSerializable(BOOK_LIST);

        btnConfirm.setOnClickListener(view ->{
            int paymentType = spnPaymentType.getSelectedItemPosition();
            if (paymentType == 0){
                insertItem(fullName,phoneNumber,address, user);

            }
            if (paymentType == 1){
                getPayment();
            }




        });


    }

    private void insertItem(String fullName, String phoneNumber, String address, User user){
        JsonObject object = new JsonObject();
        object.addProperty(ORDER_USER_NAME, fullName);
        object.addProperty(USER_PHONE_NUMBER, phoneNumber);
        object.addProperty(ORDER_ADDRESS, address);
        object.addProperty(ORDER_PAYMENT, 0);
        object.addProperty(ODER_NOTE, "None");
        object.addProperty(USER_ID, user.getUserid());
        Call<OrderResponse> call = service.addOrder(object);
        call.enqueue(insertOrder(this, cartList,bookList, user.getUserid()));
    }

    private void getPayment(){
        double amount = totalPrice*0.000043 ;

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Book Fees",
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
