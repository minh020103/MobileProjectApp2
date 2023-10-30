package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.chutro.PhongNguoiThueAdapter;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongNguoiThue;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<PhongNguoiThue> list;
    PhongNguoiThueAdapter phongNguoiThueAdapter;
    LinearLayoutManager layoutManager;
    TextView title;
    ImageView imgBackNguoiThueDanhSach;

    int idPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_nguoi_thue_layout);

        Intent intent = getIntent();
        idPhong = intent.getIntExtra("idPhong", 0);

        recyclerView = findViewById(R.id.rvNguoiThue);
        imgBackNguoiThueDanhSach = findViewById(R.id.imgBackNguoiThueDanhSach);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();

        title = findViewById(R.id.tvTitle);


        listNguoiThueTheoIdPhong(idPhong);


        imgBackNguoiThueDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void listNguoiThueTheoIdPhong(int id)
    {
        ApiServiceKiet.apiServiceKiet.getListNguoiThueTheoIdPhong(id).enqueue(new Callback<List<PhongNguoiThue>>() {
            @Override
            public void onResponse(Call<List<PhongNguoiThue>> call, Response<List<PhongNguoiThue>> response) {
                list = response.body();
                Log.d("TAG", "onResponse: "+list);
                if (list != null)
                {
                    phongNguoiThueAdapter = new PhongNguoiThueAdapter(RenterListActivity.this, list, R.layout.cardview_danh_sach_nguoi_thue);
                    recyclerView.setAdapter(phongNguoiThueAdapter);
                    phongNguoiThueAdapter.setOnClickItemListener(new PhongNguoiThueAdapter.OnClickItemListener() {
                        @Override
                        public void onClickItem(int position, View v) {
                            nextActivity(list.get(position).getId());
                        }
                    });
                    //list.addAll(response.body());
                    //phongNguoiThueAdapter.notifyDataSetChanged();
                }
                else
                {
                    title.setText("Chưa có người thuê");
                    title.setTextSize(30);
                }

            }

            @Override
            public void onFailure(Call<List<PhongNguoiThue>> call, Throwable t) {

            }
        });
    }

    private void nextActivity(int id)
    {
        Intent intent = new Intent(this, RenterDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}