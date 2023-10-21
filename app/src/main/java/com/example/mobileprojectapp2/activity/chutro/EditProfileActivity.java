package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.chutro.GioiTinhAdapter;
import com.example.mobileprojectapp2.model.GioiTinh;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {
    private Spinner spinGender;
    private GioiTinhAdapter adapter;

    private ImageView imgBack;
    private AppCompatButton btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        
        anhXa();
        spinGender = findViewById(R.id.spiner_gender);
        adapter = new GioiTinhAdapter(this, R.layout.item_seleted_gioitinh, getListGender());
        spinGender.setAdapter(adapter);
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EditProfileActivity.this,adapter.getItem(position).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        imgBack = findViewById(R.id.img_back);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    private List<GioiTinh> getListGender() {
        List<GioiTinh> list = new ArrayList<>();
        list.add(new GioiTinh("Nam"));
        list.add(new GioiTinh("Nữ"));
        list.add(new GioiTinh("Khác"));

        return list;

    }
}