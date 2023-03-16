package com.example.baostore.testapi;

import static com.example.baostore.Constant.Constants.RESPONSE_NOT_OKAY;
import static com.example.baostore.Constant.Constants.RESPONSE_OKAY;
import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_FULL_NAME;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.Constant.Constants.USER_PHONE_NUMBER;
import static com.example.baostore.Constant.Constants.USER_STATE;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.Utils.Utils;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.activities.SplashActivity;
import com.example.baostore.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallBack {
    public static void checkLogin(Context context, JsonObject jsonObject){
        ApiService service = new GetRetrofit().getRetrofit();

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

        ApiService service = new GetRetrofit().getRetrofit();

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
}
