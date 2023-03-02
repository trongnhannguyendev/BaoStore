package com.example.baostore.fragments;

import static com.example.baostore.Constant.Constants.*;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.fragment.app.Fragment;

import com.example.baostore.R;
import com.example.baostore.Utils.Utils;

public class DetailItemFragment extends Fragment {
    ImageView ivBook;
    TextView tvTitle, tvPrice, tvDescription;
    MotionButton btnAddToCart, btnToPayment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailitem, container,false);

        ivBook = v.findViewById(R.id.ivBook_detail);
        tvTitle = v.findViewById(R.id.tvTitle_detail);
        tvPrice = v.findViewById(R.id.tvPrice_detail);
        tvDescription = v.findViewById(R.id.tvDescription_detail);


        Bundle bundle = getArguments();
        if(bundle!= null){
            String title = bundle.get(BOOK_TITLE).toString();
            double price = (double) bundle.get(BOOK_PRICE);
            // Book has no description
            String description;
            String url = bundle.get(BOOK_URL).toString();

            tvTitle.setText(title);
            tvPrice.setText(new Utils().priceToString(price));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = new Utils().loadImageFromURL(url);
                    ivBook.post(new Runnable() {
                        @Override
                        public void run() {
                            ivBook.setImageBitmap(bitmap);
                        }
                    });
                }
            });
            thread.start();

        }



        return v;
    }
}
