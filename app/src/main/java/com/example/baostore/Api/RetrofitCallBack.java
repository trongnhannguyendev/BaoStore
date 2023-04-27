package com.example.baostore.Api;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.AUTHOR_LIST;
import static com.example.baostore.Constant.Constants.BOOK_ID;
import static com.example.baostore.Constant.Constants.BOOK_IMAGE_LIST;
import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.BOOK_QUANTITY;
import static com.example.baostore.Constant.Constants.CART_LIST;
import static com.example.baostore.Constant.Constants.CATEGORY_LIST;
import static com.example.baostore.Constant.Constants.PUBLISHER_LIST;
import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;
import static com.example.baostore.Constant.Constants.USER_ID;
import static com.example.baostore.Constant.Constants.VERIFICATION_CODE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baostore.R;
import com.example.baostore.activities.BuyHistoryActivity;
import com.example.baostore.activities.DetailItemActivity;
import com.example.baostore.activities.CodeVerifyActivity;
import com.example.baostore.activities.ForgotPasswordActivity;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.activities.RegisterActivity;
import com.example.baostore.activities.SplashActivity;
import com.example.baostore.adapters.OrderHistoryAdapter;
import com.example.baostore.adapters.OrderItemAdapter;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.models.Address;
import com.example.baostore.models.Author;
import com.example.baostore.models.Book;
import com.example.baostore.models.BookImage;
import com.example.baostore.models.Cart;
import com.example.baostore.models.Category;
import com.example.baostore.models.Order;
import com.example.baostore.models.OrderDetail;
import com.example.baostore.models.Publisher;
import com.example.baostore.models.User;
import com.example.baostore.responses.AddressResponse;
import com.example.baostore.responses.AuthorResponse;
import com.example.baostore.responses.BookImageResponse;
import com.example.baostore.responses.BookResponse;
import com.example.baostore.responses.CartResponse;
import com.example.baostore.responses.CategoryResponse;
import com.example.baostore.responses.OrderDetailResponse;
import com.example.baostore.responses.OrderResponse;
import com.example.baostore.responses.PublisherResponse;
import com.example.baostore.responses.UserResponse;
import com.example.baostore.responses.VerificationCodeResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallBack {
    static ApiService service = GetRetrofit.getInstance().createRetrofit();

    // User
    public static Callback<UserResponse> getCheckLogin(Context context){
        // Lấy activity hiện tại
        LoginActivity loginActivity = (LoginActivity) context;
        Callback<UserResponse> checkLoginCallBack = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();

                if (userResponse.getResponseCode() == RESPONSE_OKAY) {
                    List<User> list = userResponse.getData();
                    // Lưu dữ liệu vào sharedPreference
                    SharedPrefManager.getInstance(context).userLogin(list.get(0));

                    // chuyển qua MainActivity
                    loginActivity.finish();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    // thông báo lỗi
                    Toast.makeText(context, R.string.err_fail_login, Toast.LENGTH_SHORT).show();
                }
                loginActivity.turnEditingOn();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                loginActivity.turnEditingOn();
                Log.d("-------------", t.getMessage() + "");
                Toast.makeText(context, "Hãy thử lại sau", Toast.LENGTH_SHORT).show();
            }
        };
        loginActivity.turnEditingOn();
        return checkLoginCallBack;
    }

    public static Callback<UserResponse> getCheckSaveUserSplash(Context context){
        SplashActivity activity = (SplashActivity)context;
        Callback<UserResponse> callback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                User user = SharedPrefManager.getInstance(context).getUser();
                Intent i;
                // Nếu email tồn tại trong server
                if(userResponse.getResponseCode() == RESPONSE_OKAY) {
                    User getUser = userResponse.getData().get(0);

                    Log.d(context.getString(R.string.debug_callback),
                            "User id: " + getUser.getUserid() +
                                    "\nFull name: " + getUser.getFullname()+
                                    "\nState: " + getUser.getState());


                    i = new Intent(context, MainActivity.class);

                    int state = getUser.getState();
                    // Kiểm tra tình trạng tài khoản
                    if (state != 1) {
                        SharedPrefManager.getInstance(context).logout();
                        Toast.makeText(context, context.getString(R.string.err_account_deactivate), Toast.LENGTH_SHORT).show();
                        i = new Intent(context, LoginActivity.class);
                    }

                    // Kiểm tra dữ liệu không đồng nhất -> cập nhật SharedPref
                    if (user.getFullname()  !=getUser.getFullname() || user.getPhonenumber()!=getUser.getPhonenumber()) {
                        SharedPrefManager.getInstance(context).userLogin(getUser);
                    }
                }
                // Email không tồn tại
                else{
                    SharedPrefManager.getInstance(context).logout();
                    Toast.makeText(context, context.getString(R.string.err_email_not_found), Toast.LENGTH_SHORT).show();
                    i = new Intent(context, LoginActivity.class);
                }
                activity.finish();
                context.startActivity(i);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, R.string.text_something_wrong, Toast.LENGTH_SHORT).show();
                Log.d(context.getString(R.string.debug_SplashActivity), t.toString());
            }
        };
        return callback;
    }

    public static Callback<UserResponse> getUserRegister(Context context, JsonObject object){
        RegisterActivity activity = (RegisterActivity) context;
        return new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode() == RESPONSE_OKAY) {
                        Toast.makeText(context, context.getString(R.string.text_register_success), Toast.LENGTH_SHORT).show();
                        activity.finish();
                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        Toast.makeText(context, context.getString(R.string.text_register_fail), Toast.LENGTH_SHORT).show();
                        Log.d(context.getString(R.string.debug_callback), "onResponse: " + response.body().getMessage());
                    }
                } else{
                    Toast.makeText(context, context.getString(R.string.text_register_fail), Toast.LENGTH_SHORT).show();
                    Log.d(context.getString(R.string.debug_callback), "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d(String.valueOf(R.string.debug_callback), String.valueOf(t.getMessage()));
                Toast.makeText(context, context.getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            }
        };

    }

    public static Callback<UserResponse> userUpdateInfo(Context context, int goTo){

        Callback<UserResponse> update = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                int responseCode = response.body().getResponseCode();
                Log.d("--", "onResponse: "+response.body().getMessage());
                if(responseCode == RESPONSE_OKAY){
                    if (goTo == 1){
                        Toast.makeText(context, "Update completed!", Toast.LENGTH_SHORT).show();
                    }
                    if (goTo ==2){
                        ForgotPasswordActivity activity = (ForgotPasswordActivity) context;
                        Toast.makeText(context, "Update completed!", Toast.LENGTH_SHORT).show();
                        activity.finish();
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d(String.valueOf(R.string.debug_UserInforActivity),t.toString());
            }
        };
        return update;

    }

    // Book
    public static Callback<BookResponse> bookGetAll(Context context, Bundle bundle, Fragment fragment){
        MainActivity activity = (MainActivity) context;
        Callback<BookResponse> callback = new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                List<Book> list = response.body().getData();

                bundle.putSerializable(BOOK_LIST, (Serializable) list);

                fragment.setArguments(bundle);
                activity.loadFragment(fragment);
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("----------------------", t.toString());
            }
        };
        return callback;
    }

    public static Callback<BookResponse> updateBook(Context context, int actionCode){
        Callback<BookResponse> callback = new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if(response.body() !=null) {
                    Log.d("--updateBook", "onResponse: " + response.body().getMessage());
                }
                if (actionCode == 1){
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }


            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("----------------------", t.toString());
            }
        };
        return callback;
    }


    public static Callback<CategoryResponse> categoryGetAll(Context context, Bundle bundle, Fragment fragment){
        MainActivity activity = (MainActivity) context;
        Callback<CategoryResponse> callback = new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.body().getResponseCode() == 1) {
                    List<Category> categoryList = response.body().getData();

                    Log.d("-----------------Main", categoryList.get(0).getCategoryname());
                    bundle.putSerializable(CATEGORY_LIST, (Serializable) categoryList);

                    fragment.setArguments(bundle);
                    activity.loadFragment(fragment);

                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("----------------------", t.toString());
            }
        };
        return callback;
    }

    public static Callback<AddressResponse> userAddressGetAll(Context context, Bundle bundle, Fragment fragment){
        MainActivity activity = (MainActivity) context;
        Callback<AddressResponse> callback = new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if(response.body().getResponseCode() == RESPONSE_OKAY){

                    List<Address> addressList = response.body().getData();

                    if(!addressList.isEmpty()){
                        bundle.putSerializable(ADDRESS_LIST, (Serializable) addressList);
                        fragment.setArguments(bundle);

                    }
                    activity.loadFragment(fragment);

                } else{
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(String.valueOf(R.string.debug_MainActivity), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Toast.makeText(context, "Something wrong happen", Toast.LENGTH_SHORT).show();
                Log.d(String.valueOf(R.string.debug_MainActivity), t.toString());
            }
        };
        return callback;
    }

    public static Callback<CartResponse> cartGetAllByUserID(Context context, Bundle bundle, Fragment fragment){
        MainActivity activity = (MainActivity) context;
        Callback<CartResponse> callback = new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                int responseCode = response.body().getResponseCode();
                Log.d(context.getResources().getString(R.string.debug_callback), "response code: "+responseCode);
                if(responseCode == RESPONSE_OKAY) {
                    List<Cart> listCart = response.body().getData();

                    if(!listCart.isEmpty()){
                        bundle.putSerializable(CART_LIST, (Serializable) listCart);


                    }
                    fragment.setArguments(bundle);
                    activity.loadFragment(fragment);

                }else{
                    Log.d(context.getResources().getString(R.string.debug_frag_cart), "Message: "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d(context.getString(R.string.debug_callback), t.toString());
            }
        };
        return callback;
    }


    public static Callback<CartResponse> cartAddItem(Context context){
        DetailItemActivity activity = (DetailItemActivity) context;
        Callback<CartResponse> callback = new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                int responseCode = response.body().getResponseCode();
                if(responseCode == RESPONSE_OKAY){
                    Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                else if(responseCode == 5){
                    Toast.makeText(context, "Item already in cart!: "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    activity.finish();
                } else{
                    Toast.makeText(activity, "Something wrong happen", Toast.LENGTH_SHORT).show();
                    Log.d("---", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(context, "Some wrong happen", Toast.LENGTH_SHORT).show();
                Log.d("--DetailItem", "onFailure: " + t.toString());
            }
        };
        return callback;
    }

    public static Callback<BookImageResponse> BookImageGetAll(Context context, Bundle bundle, Intent intent){
        MainActivity activity = (MainActivity)context;
        Callback<BookImageResponse> callback = new Callback<BookImageResponse>() {
            @Override
            public void onResponse(Call<BookImageResponse> call, Response<BookImageResponse> response) {
                List<BookImage> imageList = response.body().getData();
                bundle.putSerializable(BOOK_IMAGE_LIST, (Serializable) imageList);
                intent.putExtras(bundle);
                activity.openActivityForResult(intent);
            }

            @Override
            public void onFailure(Call<BookImageResponse> call, Throwable t) {
                Log.d("----DetailItemActivity", t.toString());
            }
        };
        return callback;
    }

    public static Callback<CartResponse> cartNoData(Context context){
        Callback<CartResponse> callback = new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                int responseCode = response.body().getResponseCode();
                if (responseCode != RESPONSE_OKAY) {
                    Toast.makeText(context, "Fail to update quantity", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(context, "Something wrong happen", Toast.LENGTH_SHORT).show();
                Log.d("----Cartadapter", t.toString());
            }
        };
        return callback;
    }

    public static Callback<AddressResponse> addressNoData(Context context, int code){
        Callback<AddressResponse> callback = new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                int responseCode = response.body().getResponseCode();
                if (responseCode == RESPONSE_OKAY) {
                    if (code == 1) {
                        context.startActivity(new Intent(context, MainActivity.class));
                    }
                    if (context != null) {

                        Toast.makeText(context, "Complete update quantity", Toast.LENGTH_SHORT).show();
                    } else{
                        Log.d("Callback", "Fail to update quantity");
                    }

                }
                if (responseCode == RESPONSE_OKAY){
                    if (context!= null){
                        Toast.makeText(context, "Insert success", Toast.LENGTH_SHORT).show();
                    } else{
                        Log.d("Callback", "Insert success");
                    }

                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Toast.makeText(context, "Something wrong happen", Toast.LENGTH_SHORT).show();
                Log.d("----Cartadapter", t.toString());
            }
        };
        return callback;
    }

    public static Callback<CartResponse> cartDeleteItem(Context context, int actionCode){
        Callback<CartResponse> callback = new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                int responseCode = response.body().getResponseCode();
                if (responseCode == RESPONSE_OKAY) {
                    if(actionCode ==1) {
                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                        MainActivity activity = (MainActivity) context;
                        Fragment fragment1 = new CartFragment();
                        activity.loadFragment(fragment1);
                    }
                    if(actionCode == 2){
                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(context, "Something wrong happen", Toast.LENGTH_SHORT).show();
                Log.d(context.getResources().getString(R.string.debug_CartAdapter), t.toString());
            }
        };
        return callback;
    }

    public static Callback<PublisherResponse> getPublisher(Context context, Bundle bundle, Fragment fragment) {
        MainActivity activity = (MainActivity) context;
        Callback<PublisherResponse> callback = new Callback<PublisherResponse>() {
            @Override
            public void onResponse(Call<PublisherResponse> call, Response<PublisherResponse> response) {
                if (response.body().getResponseCode() == RESPONSE_OKAY) {
                    List<Publisher> list = response.body().getData();
                    bundle.putSerializable(PUBLISHER_LIST, (Serializable) list);
                    fragment.setArguments(bundle);
                    activity.loadFragment(fragment);
                }
            }

            @Override
            public void onFailure(Call<PublisherResponse> call, Throwable t) {
                Log.d("---", t.toString()+"");
            }

        };
        return callback;
    }

    public static Callback<AuthorResponse> getAuthor(Context context, Bundle bundle, Fragment fragment){
        MainActivity activity = (MainActivity) context;
        Callback<AuthorResponse> callback = new Callback<AuthorResponse>() {
            @Override
            public void onResponse(Call<AuthorResponse> call, Response<AuthorResponse> response) {
                if(response.body().getResponseCode() == RESPONSE_OKAY){
                    List<Author> list = response.body().getData();
                    bundle.putSerializable(AUTHOR_LIST, (Serializable) list);

                    fragment.setArguments(bundle);
                    activity.loadFragment(fragment);
                }
            }

            @Override
            public void onFailure(Call<AuthorResponse> call, Throwable t) {
                Log.d("---", t.toString()+"");
            }
        };
        return callback;
    }

    @NonNull
    public static Callback<OrderResponse> getOrderByUserID(Context context, RecyclerView recy){
        BuyHistoryActivity activity = (BuyHistoryActivity) context;
        Callback<OrderResponse> callback = new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.body().getResponseCode() == 1) {
                    List<Order> orderList = response.body().getData();
                    if (orderList.size()>0) {
                        OrderHistoryAdapter adapter = new OrderHistoryAdapter(orderList, context);
                        Toast.makeText(activity, orderList.get(0).getPhonenumber() + "", Toast.LENGTH_SHORT).show();
                        recy.setAdapter(adapter);
                    } else{
                        Toast.makeText(activity, "Chưa có đơn hàng nào", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d("--", "onFailure: "+t.toString());
            }
        };
        return callback;
    }

    //TODO: test
    public static Callback<OrderResponse> insertOrder(Context context, List<Cart> cartList, List<Book> bookList, int userid){
        Callback<OrderResponse> callback = new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                Log.d("--Callback", "message: "+response.body().getMessage());
                if(response.body().getResponseCode() == RESPONSE_OKAY){
                    Toast.makeText(context, "Order has been placed", Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < cartList.size(); i++) {
                        JsonObject object = new JsonObject();
                        object.addProperty(USER_ID, userid);
                        object.addProperty(BOOK_QUANTITY, cartList.get(i).getAmount());
                        object.addProperty(BOOK_ID, cartList.get(i).getBookid());

                        Call<OrderDetailResponse> call1 = service.addOrderDetail(object);
                        call1.enqueue(insertOrderDetail(context, object));


                        Book book = bookList.get(cartList.get(i).getBookid() -1);
                        book.setQuantity(book.getQuantity() - cartList.get(i).getAmount());


                        JsonObject object1 = new JsonObject();
                        object1.addProperty(BOOK_QUANTITY, book.getQuantity());
                        object1.addProperty(BOOK_ID, book.getbookid());

                        Log.d("--Object", "Object: "+ object1);

                        int actionCode = 0;
                        if (i == cartList.size() -1){
                            actionCode =1;
                        }
                        Call<BookResponse> call2 = service.updateBook(object1);
                        call2.enqueue(updateBook(context, actionCode));


                    }

                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d(context.getString(R.string.debug_callback), t.toString());
            }
        };
        return  callback;
    }

    @NonNull
    public static Callback<OrderDetailResponse> getOrderDetail(Context context, RecyclerView recy, TextView tvTotalPrice){
        return new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                if (response.body().getResponseCode() == 1) {
                    List<OrderDetail> orderDetailList = response.body().getData();
                        Log.d("--callback", "onResponse: "+ orderDetailList.get(0).getBookid());
                        OrderItemAdapter adapter = new OrderItemAdapter(orderDetailList, context);
                        Toast.makeText(context, orderDetailList.get(0).getQuantity()+"", Toast.LENGTH_SHORT).show();
                        recy.setAdapter(adapter);

                    int totalAmount =0;
                    for (int i = 0; i < orderDetailList.size(); i++) {
                        totalAmount += orderDetailList.get(i).getTotal();
                    }
                    tvTotalPrice.setText(totalAmount+"");

                }
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                Log.d("--", "onFailure: "+t.toString());
            }
        };
    }

    public static Callback<OrderDetailResponse> insertOrderDetail(Context context, JsonObject object){
        Callback<OrderDetailResponse> callback = new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                OrderDetailResponse myRes = response.body();
                if (myRes.getResponseCode() == RESPONSE_OKAY){
                    Toast.makeText(context, "Insert order detail success", Toast.LENGTH_SHORT).show();
                    Call<CartResponse> call1 = service.deleteCart(object);
                    call1.enqueue(cartDeleteItem(context, 2));

                }
                Log.d("--Callback", "Response code: "+ myRes.getResponseCode()+"\n\nMessage: "+myRes.getMessage());
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                Log.d("--Callback",t.toString());

            }
        };
        return callback;
    }

    public static Callback<VerificationCodeResponse> getVerificationCode(Context context, Intent intent){
        Callback<VerificationCodeResponse> callback = new Callback<VerificationCodeResponse>() {
            @Override
            public void onResponse(Call<VerificationCodeResponse> call, Response<VerificationCodeResponse> response) {
                if (response.body().getResponseCode() == RESPONSE_OKAY){
                    intent.putExtra(VERIFICATION_CODE, response.body().getData().getCode());
                    ((CodeVerifyActivity)context).turnEditingOn();
                    ((CodeVerifyActivity)context).turnVerifyFieldsOn();
                }
            }

            @Override
            public void onFailure(Call<VerificationCodeResponse> call, Throwable t) {
                Log.d("--", "onFailure: "+t.toString());

            }
        };
        return callback;
    }

}
