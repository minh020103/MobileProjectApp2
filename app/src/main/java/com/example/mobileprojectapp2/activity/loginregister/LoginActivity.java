package com.example.mobileprojectapp2.activity.loginregister;

import static com.google.android.material.tabs.TabLayout.GRAVITY_FILL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.viewpager2adapter.LoginRegisterViewPage2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    LoginRegisterViewPage2Adapter adapter;
    Float v = 0.0f;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        constraintLayout = findViewById(R.id.content_login);
        constraintLayout.setTranslationY(800);
        constraintLayout.setAlpha(v);
        constraintLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        adapter = new LoginRegisterViewPage2Adapter(this);
        viewPager2.setAdapter(adapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case LoginRegisterViewPage2Adapter.LOGIN:
                    tab.setText("Đăng Nhập");
                    break;
                case LoginRegisterViewPage2Adapter.REGISTER:
                    tab.setText("Đăng Ký");
                    break;

            }
        }).attach();


        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
}