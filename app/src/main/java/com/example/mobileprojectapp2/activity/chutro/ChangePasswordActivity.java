package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mobileprojectapp2.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText edtPassNew,edtPassNow,edtPassConfirm;

    private AppCompatButton btnAccept, btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);

        anhXa();

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

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void anhXa() {

        imgBack = findViewById(R.id.img_back);
        edtPassNew = findViewById(R.id.edt_pass_new);
        edtPassNow = findViewById(R.id.edt_pass_now);
        edtPassConfirm = findViewById(R.id.edt_pass_confrim);
        btnAccept = findViewById(R.id.btn_accept);
        btnCancel = findViewById(R.id.btn_cancel);
    }


}