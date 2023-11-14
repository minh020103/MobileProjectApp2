package com.example.mobileprojectapp2.activity.chutro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.viewpager2adapter.MotelRoomOwnerViewPager2Adapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotelRoomOwnerActivity extends AppCompatActivity {

    public static ViewPager2 vp2Chutro;
    private BottomNavigationView bnvChuTro;
    private MotelRoomOwnerViewPager2Adapter viewPager2Adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    SharedPreferences sharedPreferences;
    private int idTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motel_room_owner_layout);
        sharedPreferences = this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        vp2Chutro = findViewById(R.id.vp2ChuTro);
        bnvChuTro = findViewById(R.id.bnvChuTro);
        viewPager2Adapter = new MotelRoomOwnerViewPager2Adapter(this);

        vp2Chutro.setAdapter(viewPager2Adapter);

        // Đếm số lượng thông báo
        BadgeDrawable badgeDrawableNotifi = bnvChuTro.getOrCreateBadge(R.id.notification);
        badgeDrawableNotifi.setVisible(false);
        databaseReference.child("notification").child(idTaiKhoan + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG", "onDataChange: GET REALTIME OK");
                ApiServiceMinh.apiService.demThongBaoCuaTaiKhoan(idTaiKhoan).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.code() == 200) {
                            badgeDrawableNotifi.setVisible(true);
                            badgeDrawableNotifi.setNumber(response.body());
                            if (response.body() == 0)
                                badgeDrawableNotifi.setVisible(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        vp2Chutro.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case MotelRoomOwnerViewPager2Adapter.LIST_ROOM:
                        bnvChuTro.getMenu().findItem(R.id.room_management).setChecked(true);
                        break;
                    case MotelRoomOwnerViewPager2Adapter.PACKAGE_USING:
                        bnvChuTro.getMenu().findItem(R.id.package_using).setChecked(true);
                        break;
                    case MotelRoomOwnerViewPager2Adapter.NOTIFICATION:
                        bnvChuTro.getMenu().findItem(R.id.notification).setChecked(true);
                        break;
                    case MotelRoomOwnerViewPager2Adapter.MESSAGE:
                        bnvChuTro.getMenu().findItem(R.id.message).setChecked(true);
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
                switch (item.getItemId()) {
                    case R.id.room_management:
                        vp2Chutro.setCurrentItem(MotelRoomOwnerViewPager2Adapter.LIST_ROOM);
                        break;
                    case R.id.package_using:
                        vp2Chutro.setCurrentItem(MotelRoomOwnerViewPager2Adapter.PACKAGE_USING);
                        break;
                    case R.id.notification:
                        vp2Chutro.setCurrentItem(MotelRoomOwnerViewPager2Adapter.NOTIFICATION);
                        break;
                    case R.id.message:
                        vp2Chutro.setCurrentItem(MotelRoomOwnerViewPager2Adapter.MESSAGE);
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