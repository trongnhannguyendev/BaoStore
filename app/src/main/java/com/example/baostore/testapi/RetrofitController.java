package com.example.baostore.testapi;

import static com.example.baostore.Constant.Constants.API_SERVER_HOST_ADDRESS;
import static com.example.baostore.Constant.Constants.API_SERVER_PORT_NUMBER;

import android.content.Context;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RetrofitController {
    private static class RetrofiBuilder{
        private static final String URL = API_SERVER_HOST_ADDRESS + ":" +API_SERVER_PORT_NUMBER;
        private final Retrofit retrofit = buildRetrofit();
        private static RetrofiBuilder instance;
        private Context context;

        private RetrofiBuilder(Context context){
            this.context = context;
        }

        public static RetrofiBuilder getInstance(Context context){
            if (instance == null){
                instance = new RetrofiBuilder(context);
            }
            return instance;
        }

        private static Retrofit buildRetrofit() {
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    String token = SharedPrefManager.getInstance(instance.context).getUserLoginToken();
                    Request request = chain.request().newBuilder().addHeader("Authorization","Bearer "+token).build();
                    return chain.proceed(request);
                }
            };
            OkHttpClient client = (new OkHttpClient.Builder()).addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
            return retrofit;
        }

        public  <T> T createService(Class<T> service){
            return this.retrofit.create(service);
        }
    }
    //defi service
    public static class RetrofitService{

        private static IRetrofitService service;
        public static IRetrofitService getService(Context context){
            return service = RetrofiBuilder.getInstance(context).createService(IRetrofitService.class);
        }

    }
    //defi interface
    public interface IRetrofitService{
        //user
        @POST("/views/check-exists-email.php")
        Call<UserResponse> check_exists_email(@Body User user);
        @POST("/views/check-login.php")
        Call<UserResponse> check_login(@Body User user);
        @POST("/views/insert-account.php")
        Call<UserResponse> insert_account(@Body User user);
        @POST("/views/update-account-password.php")
        Call<UserResponse> update_account_password(@Body User user);
        @POST("/views/get-account-detail.php")
        Call<UserResponse> get_account_detail(@Body User user);
        @POST("/views/update-account.php")
        Call<UserResponse> update_account(@Body User user);
    }
}
