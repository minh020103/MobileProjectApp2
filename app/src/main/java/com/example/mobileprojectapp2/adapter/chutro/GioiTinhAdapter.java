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
import com.example.mobileprojectapp2.model.Gender;

import java.util.List;

public class GenderAdapter extends ArrayAdapter<Gender> {
    public GenderAdapter(@NonNull Context context, int resource, @NonNull List<Gender> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seleted,parent,false);
        TextView tvSelected = convertView.findViewById(R.id.tv_seleted);

        Gender gender = this.getItem(position);
        if (gender != null){
            tvSelected.setText(gender.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gender,parent,false);
        TextView tvGender = convertView.findViewById(R.id.tv_gender);

        Gender gender = this.getItem(position);
        if (gender != null){
            tvGender.setText(gender.getName());
        }
        return convertView;
    }
}