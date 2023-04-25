package com.example.baostore.fragments;

import static android.app.Activity.RESULT_OK;
import static com.example.baostore.Constant.Constants.ADDRESS_LIST;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.fragment.app.Fragment;

import com.example.baostore.Api.SharedPrefManager;
import com.example.baostore.R;
import com.example.baostore.SettingsActivity;
import com.example.baostore.activities.BuyHistoryActivity;
import com.example.baostore.activities.ChangePassActivity;
import com.example.baostore.activities.LoginActivity;
import com.example.baostore.activities.MainActivity;
import com.example.baostore.activities.UserInforActivity;
import com.example.baostore.models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class ProfileFragment extends Fragment {
    LinearLayout btnUserInfor, btnChangePass, btnToCartHistory, btnToSettings;
    MotionButton btnLogOut;
    TextView tvFullname;
    ImageView ivUserProfile;
    Bundle bundle;
    Intent i;

    String currentPhotoPath;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        try {
            // ánh xạ
            tvFullname = view.findViewById(R.id.tvFullname_profile);
            btnUserInfor = view.findViewById(R.id.btnUserInfor);
            btnChangePass = view.findViewById(R.id.btnChangePass_profile);
            btnToCartHistory = view.findViewById(R.id.btnToCartHistory_profile);
            btnToSettings = view.findViewById(R.id.btnToSettings_profile);
            btnLogOut = view.findViewById(R.id.btnLogOut);
            ivUserProfile = view.findViewById(R.id.ivUser_profile);


            User user = SharedPrefManager.getInstance(getContext()).getUser();
            tvFullname.setText(user.getFullname());

            bundle = getArguments();
            try {
                String path = SharedPrefManager.getInstance(getContext()).getUserImage();
                File f = new File(path, "profile.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                ivUserProfile.setImageBitmap(b);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // xử lý thông tin cá nhân
            btnUserInfor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), UserInforActivity.class);

                    if (bundle != null && bundle.containsKey(ADDRESS_LIST)) {
                        i.putExtras(bundle);
                    }
                    startActivity(i);
                }
            });

            btnChangePass.setOnClickListener(v -> {
                i = new Intent(getActivity(), ChangePassActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            });

            btnToCartHistory.setOnClickListener(v -> {
                i = new Intent(getActivity(), BuyHistoryActivity.class);
                startActivity(i);
            });

            ivUserProfile.setOnClickListener(v -> {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                getImageResult(photoPickerIntent);
                //startActivityForResult(photoPickerIntent, 1);
            });

            btnToSettings.setOnClickListener(v -> {
                i = new Intent(getActivity(), SettingsActivity.class);
                startActivity(i);
            });

            // Đăng xuất

            btnLogOut.setOnClickListener(v -> {
                getActivity().finish();
                SharedPrefManager.getInstance(getContext()).logout();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);

            });

            return view;
        } catch (Exception e){
            Toast.makeText(getContext(), getString(R.string.text_something_wrong), Toast.LENGTH_SHORT).show();
            Log.d(getString(R.string.debug_LoginActivity), "Error: "+e);
        }
        return view;

    }

    private void getImageResult(Intent photoPickerIntent) {
        getImageResultLauncher.launch(photoPickerIntent);
    }

    protected ActivityResultLauncher<Intent> getImageResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        try {
                            Intent data = result.getData();
                            Uri imageUri = data.getData();

                            final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ivUserProfile.setImageBitmap(selectedImage);

                            ContextWrapper cw = new ContextWrapper(getContext());
                            // path to /data/data/yourapp/app_data/imageDir
                            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                            // Create imageDir
                            File mypath=new File(directory,"profile.jpg");

                            FileOutputStream fos = null;
                                fos = new FileOutputStream(mypath);
                                // Use the compress method on the BitMap object to write image to the OutputStream
                                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

                                fos.close();
                            SharedPrefManager.getInstance(getContext()).saveUserImage(directory.getAbsolutePath());

                        } catch (FileNotFoundException e){
                            e.printStackTrace();
                            ((MainActivity)getContext()).createToast(getString(R.string.err_file_not_found));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else{
                        ((MainActivity)getContext()).createToast(getString(R.string.text_something_wrong));
                    }
                }
            });

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                /*final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivUserProfile.setImageBitmap(selectedImage);*/

                String imgUri = SharedPrefManager.getInstance(getContext()).getUserImage();
                Uri myImg = Uri.parse(imgUri);
                final InputStream imageStream = getContext().getContentResolver().openInputStream(myImg);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivUserProfile.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ((MainActivity)getContext()).createToast(getString(R.string.text_something_wrong));

            }

        }else {
            ((MainActivity)getContext()).createToast(getString(R.string.image_not_picked));
        }
    }


}