package com.example.baostore.testapi;

import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;
import static com.example.baostore.Constant.Constants.USER_EMAIL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.activities.RegisterActivity;
import com.example.baostore.activities.SplashActivity;
import com.example.baostore.models.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallBack {
    static ApiService service = new GetRetrofit().getRetrofit();
    public static void checkLogin(Context context, JsonObject jsonObject){

        LoginActivity loginActivity = (LoginActivity) context;

        Call<UserResponse> call = service.userLogin(jsonObject);
        call.enqueue(new Callback<UserResponse>() {
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
        });
    }

    public static void checkSaveUserSplash(Context context){
        SplashActivity activity = (SplashActivity)context;
        User user = SharedPrefManager.getInstance(context).getUser();
        Log.d("---sdrognp", user.getUserid() +" " + user.getFullname()+" " + user.getState());

        if(!SharedPrefManager.getInstance(context).isLoggedIn()){
            activity.finish();
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);

        }


        JsonObject emailObj = new JsonObject();
        emailObj.addProperty(USER_EMAIL, user.getEmail());

        Call<UserResponse> emailCall = service.checkUserEmailExist(emailObj);

        emailCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                Intent i = new Intent(context, LoginActivity.class);

                // Kiểm tra email có tồn tại trong server
                if(response.body().getResponseCode() == 1) {
                    User user1 = response.body().getData().get(0);
                    Log.d("---slkan", user1.getUserid() +" " + user1.getFullname()+" " + user1.getState());

                    i = new Intent(context, MainActivity.class);

                    int state = user1.getState();
                    String fullName = user1.getFullname();
                    String phoneNumber = user1.getPhoneNumber();

                    // Kiểm tra dữ liệu không đồng nhất -> cập nhật SharedPref
                    if(user.getUserid() != 0) {
                        if (user.getFullname() != user1.getFullname() || user.getPhoneNumber() != user1.getPhoneNumber()) {
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
        });

    }

    public static void userRegister(Context context,JsonObject object) {
        RegisterActivity activity = (RegisterActivity) context;

        JsonObject emailObject = new JsonObject();
        emailObject.addProperty(USER_EMAIL, object.get(USER_EMAIL).getAsString());

        Call<UserResponse> checkEmailCall = service.checkUserEmailExist(emailObject);
        checkEmailCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                // Hủy nếu email tồn tại
                if (response.body().getResponseCode() == RESPONSE_OKAY) {
                    Toast.makeText(context, "Email đã sử dụng!", Toast.LENGTH_SHORT).show();
                    activity.turnEditingOn();
                    return;
                }
                // Gọi đăng ký
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
                activity.turnEditingOn();
                Log.d(String.valueOf(R.string.debug_RegisterActivity), String.valueOf(t.getMessage()));

            }
        });
    }
}
