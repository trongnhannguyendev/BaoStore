//package com.example.baostore.fragments;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.example.baostore.R;
//
//
//public class CartPaymentFragment extends Fragment {
//    private LinearLayout btn_hide_show, layout_hide_show;
//    ImageView img_funcpayment_arrow;
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v =inflater.inflate(R.layout.fragment_cart_payment,container,false);
//        btn_hide_show = v.findViewById(R.id.btn_hide_show);
//        layout_hide_show =  v.findViewById(R.id.layout_hide_show);
//        img_funcpayment_arrow= v.findViewById(R.id.img_funcpayment_arrow);
//
//        btn_hide_show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int isvisible = layout_hide_show.getVisibility();
//                if (isvisible==View.VISIBLE){
//                    layout_hide_show.setVisibility(View.GONE);
//                    img_funcpayment_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
//
//
//                }
//                else {
//                    layout_hide_show.setVisibility(View.VISIBLE);
//                    img_funcpayment_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
//                }
//            }
//        });
//        return v;
//    }
//}