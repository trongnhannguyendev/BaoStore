package com.example.baostore.activities;

import static com.example.baostore.Constant.Constants.USER_EMAIL;
import static com.example.baostore.Constant.Constants.USER_PASSWORD;
import static com.example.baostore.testapi.RetrofitCallBack.userUpdateInfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.models.User;
import com.example.baostore.responses.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class ChangePassActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView tvTitleHeader;
    EditText edOldPass, edNewPass, edReNewPass;
    Button btnChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        // header
        tvTitleHeader = findViewById(R.id.title);
        tvTitleHeader.setText("Thay đổi mật khẩu");

        edOldPass = findViewById(R.id.edOldPass_cp);
        edNewPass = findViewById(R.id.edNewPass_cp);
        edReNewPass = findViewById(R.id.edReNewPass_cp);
        btnChangePass = findViewById(R.id.btnChangePass_cp);

        btnChangePass.setOnClickListener(view -> {

            changePass();


        });

    }


    private void changePass() {
        boolean hasError = false;

        String oldPass = edOldPass.getText().toString().trim();
        String newPass = edNewPass.getText().toString().trim();
        String reNewPass = edReNewPass.getText().toString().trim();

        if (!newPass.equals(reNewPass)) {
            Toast.makeText(this, getResources().getString(R.string.err_not_identical), Toast.LENGTH_SHORT).show();
            return;
        }
        if (oldPass.isEmpty() || newPass.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.err_pass_empty) + "", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = new GetRetrofit().getRetrofit();

        User user = SharedPrefManager.getInstance(this).getUser();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(USER_EMAIL, user.getEmail());
        jsonObject.addProperty(USER_PASSWORD, oldPass);

        Call<UserResponse> call = service.updatePassword(jsonObject);

        call.enqueue(userUpdateInfo(this));

    }


}
