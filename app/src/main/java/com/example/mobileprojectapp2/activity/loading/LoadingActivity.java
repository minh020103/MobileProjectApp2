package com.example.mobileprojectapp2.activity.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.activity.loginregister.LoginActivity;
import com.example.mobileprojectapp2.activity.nguoithue.RenterActivity;
import com.example.mobileprojectapp2.api.Const;

public class LoadingActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Float v = 0.0f;
    ProgressBar progressBar;
    int idTaiKhoan,loaiTaiKhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        SharedPreferences sharedPreferences = getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        loaiTaiKhoan = sharedPreferences.getInt("loaiTaiKhoan", -1);
        Log.d("TAG", "onCreate loding: "+idTaiKhoan);
        progressBar = findViewById(R.id.progessbar);
        textView = findViewById(R.id.slogan);
        imageView = findViewById(R.id.logo);

        textView.setTranslationY(800);
        imageView.setTranslationY(800);
        textView.setAlpha(v);
        imageView.setAlpha(v);
        imageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        textView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                if (idTaiKhoan == -1) {
                    startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                }
                else {
                    Log.d("TAG", "run: "+loaiTaiKhoan);
                    if (loaiTaiKhoan != -1) {
                        if (loaiTaiKhoan == Const.CHU_TRO) {
                            startActivity(new Intent(LoadingActivity.this, MotelRoomOwnerActivity.class));
                        } else {
                            startActivity(new Intent(LoadingActivity.this, RenterActivity.class));
                        }
                    }
                }
                finish();
            }
        },1000);
    }
}