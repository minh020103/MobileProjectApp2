package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.chutro.NguoiThueAdapter;
import com.example.mobileprojectapp2.adapter.chutro.ThongBaoAdapter;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.ThongBao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NguoiThue> list;
    NguoiThueAdapter nguoiThueAdapter;
    LinearLayoutManager layoutManager;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_nguoi_thue_layout);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        recyclerView = findViewById(R.id.rvNguoiThue);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(nguoiThueAdapter);
        list = new ArrayList<>();

        listNguoiThueTheoIdPhong(id);

    }

    private void listNguoiThueTheoIdPhong(int id)
    {
        ApiServiceKiet.apiServiceKiet.getListNguoiThueTheoIdPhong(id).enqueue(new Callback<List<NguoiThue>>() {
            @Override
            public void onResponse(Call<List<NguoiThue>> call, Response<List<NguoiThue>> response) {
                list = response.body();
                nguoiThueAdapter = new NguoiThueAdapter(RenterListActivity.this, list, R.layout.cardview_danh_sach_nguoi_thue);
                recyclerView.setAdapter(nguoiThueAdapter);
                nguoiThueAdapter.setOnClickItemListener(new NguoiThueAdapter.OnClickItemListener() {
                    @Override
                    public void onClickItem(int position, View v) {
                        nextActivity(list.get(position).getId());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<NguoiThue>> call, Throwable t) {

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