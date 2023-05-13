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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.databinding.ActivityCartPaymentBinding;
import com.example.baostore.models.Book;
import com.example.baostore.models.Cart;
import com.example.baostore.models.User;
import com.example.baostore.responses.OrderResponse;
import com.example.baostore.viewmodel.CheckoutViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.gson.JsonObject;


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
    Bundle bundle;

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private CheckoutViewModel model;

    private ActivityCartPaymentBinding layoutBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
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
            bundle = getIntent().getExtras();


            tvTitleHeader.setText(getString(R.string.header_cart_payment));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.payment_type));
            spnPaymentType.setAdapter(adapter);

            // Load dữ liệu lên Payment
            String orderDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            fullName = bundle.get(USER_FULL_NAME).toString();
            phoneNumber = bundle.get(USER_PHONE_NUMBER).toString();
            address = bundle.get(ADDRESS_LOCATION).toString();
            totalPrice = Double.parseDouble(bundle.get(CART_TOTAL_PRICE).toString()) + 20000;

            tvFullname.setText(fullName);
            tvPhoneNumber.setText(phoneNumber);
            tvAddress.setText(address);
            tvOrderDate.setText(orderDate);
            //tvBookPrice.setText(null);
            //tvShipPrice.setText(null);
            tvTotalPrice.setText(new Utils().priceToString(totalPrice));

            user = SharedPrefManager.getInstance(this).getUser();
            cartList = (List<Cart>) bundle.getSerializable(CART_LIST);
            bookList = (List<Book>) bundle.getSerializable(BOOK_LIST);

            btnConfirm.setOnClickListener(view -> {
                if (spnPaymentType.getSelectedItemPosition() == 0){
                    insertItem(fullName,phoneNumber,address,user, 0);
                }
                if (spnPaymentType.getSelectedItemPosition() == 1){
                    initializeUi();
                    requestPayment(this.getCurrentFocus());
                }

            });

            model = new ViewModelProvider(this).get(CheckoutViewModel.class);
            model.canUseGooglePay.observe(this, this::setGooglePayAvailable);
        } catch (Exception e ){
            Toast.makeText(this, getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e);
            finish();
        }

    }



    private void insertItem(String fullName, String phoneNumber, String address, User user, int paymentType){
        JsonObject object = new JsonObject();
        object.addProperty(ORDER_USER_NAME, fullName);
        object.addProperty(USER_PHONE_NUMBER, phoneNumber);
        object.addProperty(ORDER_ADDRESS, address);
        object.addProperty(ORDER_PAYMENT, paymentType);
        object.addProperty(ODER_NOTE, "None");
        object.addProperty(USER_ID, user.getUserid());
        Call<OrderResponse> call = service.addOrder(object);
        call.enqueue(insertOrder(this, cartList,bookList, user.getUserid()));
    }

    private void initializeUi() {

        // Use view binding to access the UI elements
        layoutBinding = ActivityCartPaymentBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        // The Google Pay button is a layout file – take the root view
    }

    private void setGooglePayAvailable(boolean available) {
        if (!available) {
            Toast.makeText(this, "Unavailable", Toast.LENGTH_LONG).show();

        }
    }

    public void requestPayment(View view) {

        // Disables the button to prevent multiple clicks.
        btnConfirm.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        long dummyPriceCents = (long) (totalPrice - 20000);
        long shippingCostCents = 20000;
        long totalPriceCents = dummyPriceCents + shippingCostCents;
        final Task<PaymentData> task = model.getLoadPaymentDataTask(totalPriceCents);

        // Shows the payment sheet and forwards the result to the onActivityResult method.
        AutoResolveHelper.resolveTask(task, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case AppCompatActivity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);

                        break;

                    case AppCompatActivity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status);
                        break;
                }

                // Re-enables the Google Pay payment button.
                btnConfirm.setClickable(true);
        }
    }

    private void handlePaymentSuccess(@Nullable PaymentData paymentData) {
        final String paymentInfo = paymentData.toJson();

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String token = tokenizationData.getString("token");
            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);
            insertItem(fullName,phoneNumber,address,user,1);

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    private void handleError(@Nullable Status status) {
        String errorString = "Unknown error.";
        if (status != null) {
            int statusCode = status.getStatusCode();
            errorString = String.format(Locale.getDefault(), "Error code: %d", statusCode);
        }

        Log.e("loadPaymentData failed", errorString);
    }
}
