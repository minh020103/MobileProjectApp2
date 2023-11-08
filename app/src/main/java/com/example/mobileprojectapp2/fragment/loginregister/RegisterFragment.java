package com.example.mobileprojectapp2.fragment.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.loginregister.RegisterChuTroActivity;
import com.example.mobileprojectapp2.activity.loginregister.RegisterNguoiThueActivity;

import java.util.ArrayList;

public class RegisterFragment extends AbstractFragment{
    Button btnNguoiThue;
    Button btnChuTro;
    Float v = 0.0f;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = getLayoutInflater().inflate(R.layout.register_tab_fragment,container, false);
        anhXa(view);
        batSuKien();
        btnChuTro.setTranslationX(800);
        btnChuTro.setAlpha(v);
        btnChuTro.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        btnNguoiThue.setTranslationX(800);
        btnNguoiThue.setAlpha(v);
        btnNguoiThue.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        return view;
    }
    private void batSuKien(){
        btnChuTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterChuTroActivity.class);
                startActivity(intent);
            }
        });
        btnNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterNguoiThueActivity.class);
                startActivity(intent);
            }
        });
    }
    private void anhXa(View fragment){
        btnChuTro = fragment.findViewById(R.id.taiKhoanChuTro);
        btnNguoiThue = fragment.findViewById(R.id.taiKhoanNguoiThue);
    }
}
