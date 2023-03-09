package com.example.baostore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baostore.R;
import com.example.baostore.models.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressSpinnerAdapter extends ArrayAdapter<Address> {
    public AddressSpinnerAdapter(@NonNull Context context, List<Address> addressArrayList) {
        super(context, 0, addressArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_address, parent,false);

        }
        TextView tvAddressLocation =convertView.findViewById(R.id.tvAddress_spnItem);
        Address address = getItem(position);
        tvAddressLocation.setText(address.getAddressLocation());
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

        tvAddressLocation.setText(address.getAddressLocation());

        return convertView;
    }


}
