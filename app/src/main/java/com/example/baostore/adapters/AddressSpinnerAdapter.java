package com.example.baostore.adapters;

import static com.example.baostore.Api.RetrofitCallBack.addressNoData;
import static com.example.baostore.Constant.Constants.ADDRESS_ID;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baostore.Api.ApiService;
import com.example.baostore.Api.GetRetrofit;
import com.example.baostore.R;
import com.example.baostore.models.Address;
import com.example.baostore.responses.AddressResponse;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;

public class AddressSpinnerAdapter extends ArrayAdapter<Address> {
    ApiService service = GetRetrofit.getInstance().createRetrofit();
    List<Address> addressArrayList;
    public AddressSpinnerAdapter(@NonNull Context context, List<Address> addressArrayList) {
        super(context, 0, addressArrayList);
        this.addressArrayList = addressArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_address, parent,false);

        }
        TextView tvAddressLocation =convertView.findViewById(R.id.tvAddress_spnItem);
        Address address = getItem(position);
        tvAddressLocation.setText(address.getLocation());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_address_edit, parent,false);

        }
        TextView tvAddressLocation =convertView.findViewById(R.id.tvAddress_spnItemEdit);
        ImageView ivDelete = convertView.findViewById(R.id.ivAddress_spnItemEdit);
        Address address = getItem(position);

        tvAddressLocation.setText(address.getLocation());

        if (addressArrayList.size() == position){
            ivDelete.setVisibility(View.GONE);
        }

        ivDelete.setOnClickListener(view->{
            JsonObject object = new JsonObject();
            object.addProperty(ADDRESS_ID, address.getAddressid());
            Call<AddressResponse> call = service.removeUserAddress(object);
            
            call.enqueue(addressNoData(null,0));

        });

        return convertView;
    }


}
