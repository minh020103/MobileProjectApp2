package com.example.mobileprojectapp2.activity.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.loginregister.LoginActivity;

public class LoadingActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Float v = 0.0f;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
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
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                finish();
            }
        },3000);
    }
}