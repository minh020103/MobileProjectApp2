package com.example.mobileprojectapp2.adapter.chutro;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.model.GioiTinh;

import java.util.List;

public class GioiTinhAdapter extends ArrayAdapter<GioiTinh> {
    public GioiTinhAdapter(@NonNull Context context, int resource, @NonNull List<GioiTinh> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seleted_gioitinh,parent,false);
        TextView tvSelected = convertView.findViewById(R.id.tv_seleted);

        GioiTinh gender = this.getItem(position);
        if (gender != null){
            tvSelected.setText(gender.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gioitinh,parent,false);
        TextView tvGender = convertView.findViewById(R.id.tv_gender);

        GioiTinh gender = this.getItem(position);
        if (gender != null){
            tvGender.setText(gender.getName());
        }
        return convertView;
    }
}