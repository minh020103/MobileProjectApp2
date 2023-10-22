package com.example.mobileprojectapp2.activity.chutro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.viewpager2adapter.MotelRoomOwnerViewPager2Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MotelRoomOwnerActivity extends AppCompatActivity {

    private ViewPager2 vp2Chutro;
    private BottomNavigationView bnvChuTro;
    private MotelRoomOwnerViewPager2Adapter viewPager2Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motel_room_owner_layout);

        vp2Chutro = findViewById(R.id.vp2ChuTro);
        bnvChuTro = findViewById(R.id.bnvChuTro);
        viewPager2Adapter = new MotelRoomOwnerViewPager2Adapter(this);

        vp2Chutro.setAdapter(viewPager2Adapter);

        vp2Chutro.setUserInputEnabled(false);

        vp2Chutro.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case MotelRoomOwnerViewPager2Adapter.LIST_ROOM:
                        bnvChuTro.getMenu().findItem(R.id.room_management).setChecked(true);
                        break;
                    case MotelRoomOwnerViewPager2Adapter.PACKAGE_USING:
                        bnvChuTro.getMenu().findItem(R.id.package_using).setChecked(true);
                        break;
                    case MotelRoomOwnerViewPager2Adapter.NOTIFICATION:
                        bnvChuTro.getMenu().findItem(R.id.notification).setChecked(true);
                        break;
                    case MotelRoomOwnerViewPager2Adapter.PROFILE:
                        bnvChuTro.getMenu().findItem(R.id.profile).setChecked(true);
                        break;
                }
            }
        });



        bnvChuTro.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.room_management:
                        vp2Chutro.setCurrentItem(MotelRoomOwnerViewPager2Adapter.LIST_ROOM);
                        break;
                    case R.id.package_using:
                        vp2Chutro.setCurrentItem(MotelRoomOwnerViewPager2Adapter.PACKAGE_USING);
                        break;
                    case R.id.notification:
                        vp2Chutro.setCurrentItem(MotelRoomOwnerViewPager2Adapter.NOTIFICATION);
                        break;
                    case R.id.profile:
                        vp2Chutro.setCurrentItem(MotelRoomOwnerViewPager2Adapter.PROFILE);
                        break;
                }
                return true;
            }
        });
    }
}