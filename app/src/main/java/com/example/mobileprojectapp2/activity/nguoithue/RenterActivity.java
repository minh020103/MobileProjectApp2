package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.fragment.nguoithue.AbstractFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.HomeFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.MessageFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.MyRoomFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.NotificationFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.ProfileFragment;
import com.example.mobileprojectapp2.fragment.nguoithue.TinNhanFragment;
import com.example.mobileprojectapp2.viewpager2adapter.RenterViewPage2Adapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.LinkedList;
import java.util.List;

public class RenterActivity extends AppCompatActivity {

    public static ViewPager2 viewPager2NguoiThue;
    private BottomNavigationView bottomNavigationViewNguoiThue;
    private RenterViewPage2Adapter adapter;
    private LinkedList<AbstractFragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renter_layout);
        list = new LinkedList<>();
        viewPager2NguoiThue = findViewById(R.id.vp2NguoiThue);
        bottomNavigationViewNguoiThue = findViewById(R.id.bnvNguoiThue);
        BadgeDrawable badgeNotification = bottomNavigationViewNguoiThue.getOrCreateBadge(R.id.navNotification);
        badgeNotification.setVisible(true);
        badgeNotification.setNumber(100);
        BadgeDrawable badgeMessage = bottomNavigationViewNguoiThue.getOrCreateBadge(R.id.navMessage);
        badgeMessage.setVisible(true);
        badgeMessage.setNumber(10);
        adapter = new RenterViewPage2Adapter(this);

        list.add(new HomeFragment());
        list.add(new MyRoomFragment());
        list.add(new NotificationFragment());
//        list.add(new MessageFragment());
        list.add(new TinNhanFragment());
        list.add(new ProfileFragment());
        adapter.setFragments(list);
        viewPager2NguoiThue.setAdapter(adapter);
        viewPager2NguoiThue.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case RenterViewPage2Adapter.HOME:
                        bottomNavigationViewNguoiThue.getMenu().findItem(R.id.navHome).setChecked(true);
                        break;
                    case RenterViewPage2Adapter.MY_ROOM:
                        bottomNavigationViewNguoiThue.getMenu().findItem(R.id.navMyRoom).setChecked(true);
                        break;
                    case RenterViewPage2Adapter.NOTIFICATION:
                        bottomNavigationViewNguoiThue.getMenu().findItem(R.id.navNotification).setChecked(true);
                        break;
                    case RenterViewPage2Adapter.MESSAGE:
                        bottomNavigationViewNguoiThue.getMenu().findItem(R.id.navMessage).setChecked(true);
                        break;
                    case RenterViewPage2Adapter.PROFILE:
                        bottomNavigationViewNguoiThue.getMenu().findItem(R.id.navProfile).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationViewNguoiThue.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navHome:
                        viewPager2NguoiThue.setCurrentItem(RenterViewPage2Adapter.HOME);
                        break;
                    case R.id.navMyRoom:
                        viewPager2NguoiThue.setCurrentItem(RenterViewPage2Adapter.MY_ROOM);
                        break;
                    case R.id.navNotification:
                        viewPager2NguoiThue.setCurrentItem(RenterViewPage2Adapter.NOTIFICATION);
                        break;
                    case R.id.navMessage:
                        viewPager2NguoiThue.setCurrentItem(RenterViewPage2Adapter.MESSAGE);
                        break;
                    case R.id.navProfile:
                        viewPager2NguoiThue.setCurrentItem(RenterViewPage2Adapter.PROFILE);
                        break;
                }
                return true;
            }
        });

    }
}