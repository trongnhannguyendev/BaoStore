package com.example.baostore.retrofit;

public class RetrofitController {
    //defit builder
//    private static class RetrofiBuilder{
//        private static final String URL = AppConstant.API_SERVER_HOST_ADDRESS + ":" +AppConstant.API_SERVER_PORT_NUMBER;
//        private final Retrofit retrofit = buildRetrofit();
//        private static RetrofiBuilder instance;
//        private Context context;
//
//        private RetrofiBuilder(Context context){
//            this.context = context;
//        }
//
//        public static RetrofiBuilder getInstance(Context context){
//            if (instance == null){
//                instance = new RetrofiBuilder(context);
//            }
//            return instance;
//        }
//
//        private static Retrofit buildRetrofit() {
//            Interceptor interceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    String token = AppVariable.getInstance(instance.context).getLocalStorageManager().getUserLoginToken();
//                    Request request = chain.request().newBuilder().addHeader("Authorization","Bearer "+token).build();
//                    return chain.proceed(request);
//                }
//            };
//            OkHttpClient client = (new OkHttpClient.Builder()).addInterceptor(interceptor).build();
//            Gson gson = new GsonBuilder().setLenient().create();
//            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
//            return retrofit;
//        }
//
//        public  <T> T createService(Class<T> service){
//            return this.retrofit.create(service);
//        }
//    }
//    //defi service
//    public static class RetrofitService{
//
//        private static IRetrofitService service;
//        public static IRetrofitService getService(Context context){
//            return service = RetrofiBuilder.getInstance(context).createService(IRetrofitService.class);
//        }
//
//    }
//    //defi interface
//    public interface IRetrofitService{
//        //user
//        @POST("/views/check-exists-email.php")
//        Call<UserResponse> check_exists_email(@Body Account account);
//        @POST("/views/check-login.php")
//        Call<UserResponse> check_login(@Body Account account);
//        @POST("/views/insert-account.php")
//        Call<UserResponse> insert_account(@Body Account account);
//        @POST("/views/update-account-password.php")
//        Call<UserResponse> update_account_password(@Body Account account);
//        @POST("/views/get-account-detail.php")
//        Call<UserResponse> get_account_detail(@Body Account account);
//        @POST("/views/update-account.php")
//        Call<UserResponse> update_account(@Body Account account);
//        @POST("/views/get-all-product.php")
//        Call<ProductResponse> get_all_product(@Body Product product);
//    }
}
