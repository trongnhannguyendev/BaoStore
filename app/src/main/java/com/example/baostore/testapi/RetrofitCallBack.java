package com.example.baostore.testapi;

import static com.example.baostore.Constant.Constants.ADDRESS_LIST;
import static com.example.baostore.Constant.Constants.BOOK_IMAGE_LIST;
import static com.example.baostore.Constant.Constants.BOOK_LIST;
import static com.example.baostore.Constant.Constants.CART_LIST;
import static com.example.baostore.Constant.Constants.CATEGORY_LIST;
import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.activities.DetailItemActivity;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.activities.RegisterActivity;
import com.example.baostore.activities.SplashActivity;
import com.example.baostore.activities.UserInforActivity;
import com.example.baostore.fragments.CartFragment;
import com.example.baostore.models.Address;
import com.example.baostore.models.Book;
import com.example.baostore.models.BookImage;
import com.example.baostore.models.Cart;
import com.example.baostore.models.Category;
import com.example.baostore.models.User;
import com.example.baostore.responses.AddressResponse;
import com.example.baostore.responses.BookImageResponse;
import com.example.baostore.responses.BookResponse;
import com.example.baostore.responses.CartResponse;
import com.example.baostore.responses.CategoryResponse;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallBack {
    static ApiService service = new GetRetrofit().getRetrofit();
    // User

    public static Callback<UserResponse> getCheckLogin(Context context){
        LoginActivity loginActivity = (LoginActivity) context;
        Callback<UserResponse> checkLoginCallBack = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                Log.d("---Login", userResponse.toString());

                if (userResponse.getResponseCode() == RESPONSE_OKAY) {

                    List<User> list = userResponse.getData();
                    // Lưu dữ liệu vào sharedPreference
                    SharedPrefManager.getInstance(context).userLogin(list.get(0));

                    // chuyển qua MainActivity
                    loginActivity.finish();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    // thông báo lỗi
                    Toast.makeText(context, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
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
        return checkLoginCallBack;
    }

    public static Callback<UserResponse> getCheckSaveUserSplash(Context context){
        SplashActivity activity = (SplashActivity)context;
        Callback<UserResponse> callback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                User user = SharedPrefManager.getInstance(context).getUser();
                Intent i = new Intent(context, LoginActivity.class);

                // Kiểm tra email có tồn tại trong server
                if(response.body().getResponseCode() == 1) {
                    User user1 = response.body().getData().get(0);
                    Log.d("---slkan", user1.getUserid() +" " + user1.getFullname()+" " + user1.getState());

                    i = new Intent(context, MainActivity.class);

                    int state = user1.getState();

                    // Kiểm tra dữ liệu không đồng nhất -> cập nhật SharedPref
                    if(user.getUserid() != 0) {
                        if (user.getFullname()  !=user1.getFullname() || user.getPhonenumber()!=user1.getPhonenumber()) {
                            SharedPrefManager.getInstance(context).userLogin(user1);
                        }
                    }

                    // Kiểm tra tình trạng tài khoản

                    if (state != 1) {
                        SharedPrefManager.getInstance(context).logout();
                        Toast.makeText(context, "Tài khoản đã bị vô hiệu hóa", Toast.LENGTH_SHORT).show();
                        i = new Intent(context, LoginActivity.class);
                    }


                }

                // Email không tồn tại
                else{
                    SharedPrefManager.getInstance(context).logout();
                    Toast.makeText(context, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                    i = new Intent(context, LoginActivity.class);
                }
                activity.finish();
                context.startActivity(i);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, R.string.enqueue_failure, Toast.LENGTH_SHORT).show();
                Log.d(String.valueOf(R.string.debug_SplashActivity), t.toString());
            }
        };
        return callback;
    }

    public static Callback<UserResponse> getUserRegister(Context context, JsonObject object){
        RegisterActivity activity = (RegisterActivity) context;
        Callback<UserResponse> callback = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Call<UserResponse> regCall = service.registerUser(object);

                regCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.body().getResponseCode() == RESPONSE_OKAY) {
                            Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            activity.finish();
                            context.startActivity(new Intent(context, LoginActivity.class));
                        } else {
                            activity.turnEditingOn();
                            Toast.makeText(context, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        activity.turnEditingOn();
                        Log.d(String.valueOf(R.string.debug_RegisterActivity), String.valueOf(t.getMessage()));
                        Toast.makeText(context, "Something wrong happen", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        };


    return callback;
    }

    public static Callback<UserResponse> userUpdateInfo(Context context){
        UserInforActivity activity = (UserInforActivity) context;
        Callback<UserResponse> update = new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                int responseCode = response.body().getResponseCode();
                if(responseCode == RESPONSE_OKAY){
                    Toast.makeText(context, "Update email completed!", Toast.LENGTH_SHORT).show();
                    activity.finish();
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
                Log.d("--", "Cart response: "+responseCode);
                Log.d((String.valueOf(R.string.debug_CartFragment)), responseCode+"");
                if(responseCode == RESPONSE_OKAY) {
                    List<Cart> listCart = response.body().getData();

                    if(!listCart.isEmpty()){
                        bundle.putSerializable(CART_LIST, (Serializable) listCart);


                    }
                    fragment.setArguments(bundle);
                    activity.loadFragment(fragment);

                }else{
                    Log.d(String.valueOf(R.string.debug_CartFragment), response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d(String.valueOf(R.string.debug_CartFragment), t.toString());
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
                } else{
                    Toast.makeText(context, "Item already in cart!", Toast.LENGTH_SHORT).show();
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
        Callback<BookImageResponse> callback = new Callback<BookImageResponse>() {
            @Override
            public void onResponse(Call<BookImageResponse> call, Response<BookImageResponse> response) {
                List<BookImage> imageList = response.body().getData();
                bundle.putSerializable(BOOK_IMAGE_LIST, (Serializable) imageList);
                intent.putExtras(bundle);
                context.startActivity(intent);
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


    public static Callback<CartResponse> cartDeleteItem(Context context){
        Callback<CartResponse> callback = new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                int responseCode = response.body().getResponseCode();
                if (responseCode == RESPONSE_OKAY) {
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    MainActivity activity = (MainActivity) context;
                    Fragment fragment1 = new CartFragment();
                    activity.loadFragment(fragment1);
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
}
