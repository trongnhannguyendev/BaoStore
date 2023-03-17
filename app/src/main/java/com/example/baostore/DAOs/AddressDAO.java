package com.example.baostore.DAOs;

import static com.example.baostore.Constant.Constants.ADDRESS_ID;
import static com.example.baostore.Constant.Constants.ADDRESS_LOCATION;

import com.example.baostore.models.Address;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    public AddressDAO() {
    }

    public List<Address> getData(JsonArray array){
        List<Address> list = new ArrayList<>();

        for(JsonElement element: array){
            JsonObject object = element.getAsJsonObject();
            Address address = new Address();
            address.setAddressid(object.get(ADDRESS_ID).getAsInt());
            address.setLocation(object.get(ADDRESS_LOCATION).getAsString());
            list.add(address);
        }
        return  list;


    }
}
