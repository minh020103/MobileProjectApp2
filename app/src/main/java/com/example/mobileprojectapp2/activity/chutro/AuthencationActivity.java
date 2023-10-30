package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;

public class AuthencationActivity extends AppCompatActivity {

    private ImageView imgViewBack, imageViewMatTruocCCCD, imageViewMatSauCCCD;
    private TextView tvNotAuthencation, tvOkAuthencation;
    private AppCompatButton btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authencation_layout);
        anhXa();

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        imgViewBack = findViewById(R.id.img_back);
        imageViewMatTruocCCCD = findViewById(R.id.imgView_mat_truoc_cccd);
        imageViewMatSauCCCD = findViewById(R.id.imgView_mat_sau_cccd);
        tvNotAuthencation = findViewById(R.id.tv_not_authencation);
        tvOkAuthencation = findViewById(R.id.tv_ok_authencation);
        btnAccept = findViewById(R.id.btn_accept);
    }
}