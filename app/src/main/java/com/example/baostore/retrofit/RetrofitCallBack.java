package com.example.baostore.retrofit;

public class RetrofitCallBack {
//    public static Callback<UserResponse> getFullname(Context context,TextView textView){
//        Callback<UserResponse> check_exists_emailCallback = new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.isSuccessful()){
//                    UserResponse userResponse = response.body();
//                    Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
//                    if (userResponse.getRespone_code() == 0){
//                        textView.setText("Email is available");
//                    }else if (userResponse.getRespone_code() == 1){
//                        textView.setText("Email is existed");
//                    }else {
//                        textView.setText(userResponse.getMessage());
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                        Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
//                        textView.setText(t.getMessage());
//            }
//        };
//        return check_exists_emailCallback;
//    }
//
//    public static Callback<UserResponse> getCheckLogin(Context context){
//        Callback<UserResponse> check_loginCallback = new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.isSuccessful()){
//                    UserResponse userResponse = response.body();
//                    if (userResponse.getRespone_code() == 0){
//                        Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
//                    }else if (userResponse.getRespone_code() == 1){
//                        AppVariable.getInstance(context).getLocalStorageManager().setUserLoginState(true);
//                        AppVariable.getInstance(context).getLocalStorageManager().setUserLoginEmail(userResponse.getData().get(0).getEmail());
//                        Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(context, MainActivity.class);
//                        ((SignInActivity)context).finishAffinity();
//                        context.startActivity(intent);
//                    }else {
//                        Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        };
//        return check_loginCallback;
//    }
//    public static Callback<UserResponse> signUpAccount(Context context){
//        Callback<UserResponse> signUpAccountCallback = new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.isSuccessful()){
//                    UserResponse userResponse = response.body();
//                    if (userResponse.getRespone_code() == 1){
//                        Intent intent = new Intent(context, SignInActivity.class);
//                        ((SignUpActivity)context).finishAffinity();
//                        context.startActivity(intent);
//                    }
//                    Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        };
//        return signUpAccountCallback;
//    }
//    public static Callback<UserResponse> updateAccountPassword(Context context){
//        Callback<UserResponse> updateAccountPasswordCallBack = new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.isSuccessful()){
//                    UserResponse userResponse = response.body();
//                    if (userResponse.getRespone_code() == 1){
//                        AppVariable.getInstance(context).getLocalStorageManager().setUserLoginState(false);
//                        AppVariable.getInstance(context).getLocalStorageManager().setUserLoginEmail(null);
//                        AppHelper.pushNotification(context,"Thông báo","Thay đổi mật khẩu thành công");
//                        Intent intent = new Intent(context, SignInActivity.class);
//                        ((MainActivity)context).finishAffinity();
//                        context.startActivity(intent);
//                    }
//                    Toast.makeText(context,userResponse.getMessage()+"\nVui lòng đăng nhập lại!", Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        };
//        return updateAccountPasswordCallBack;
//    }
//
//    public static Callback<UserResponse> changeAccountInformation(Context context, DialogFragment dialogFragment){
//        Callback<UserResponse> changeAccountInformationCallBack = new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.isSuccessful()){
//                    UserResponse userResponse = response.body();
//                    if (userResponse.getRespone_code() == 1){
//                        AppHelper.pushNotification(context,"Thông báo","Thay đổi thông tin tài khoản thành công");
//                        dialogFragment.dismiss();
//                    }
//                    Toast.makeText(context,userResponse.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        };
//        return changeAccountInformationCallBack;
//    }
//
//    public static Callback<ProductResponse> getAllProduct(Context context, RecyclerView recyclerView){
//        Callback<ProductResponse> getAllProductCallBack = new Callback<ProductResponse>() {
//            @Override
//            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
//                if (response.isSuccessful()){
//                    ProductResponse productResponse = response.body();
//                    if (productResponse.getRespone_code() == 1){
//                        ProductAdapter adapter = new ProductAdapter(productResponse.getData(),context);
//                        recyclerView.setAdapter(adapter);
//                        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
//                    }
//                    Toast.makeText(context,productResponse.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ProductResponse> call, Throwable t) {
//                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        };
//        return getAllProductCallBack;
//    }
}
