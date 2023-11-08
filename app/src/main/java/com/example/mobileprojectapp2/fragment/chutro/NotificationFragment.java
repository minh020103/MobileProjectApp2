package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.NotificationDetailActivity;
import com.example.mobileprojectapp2.adapter.chutro.ThongBaoAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.datamodel.ThongBao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends AbstractFragment{
    private SharedPreferences shared;
    private int idTaiKhoan;
    RecyclerView recyclerView;
    List<ThongBao> list;
    ThongBaoAdapter thongBaoAdapter;
    LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_notification_layout, container, false);
        shared = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = shared.getInt("idTaiKhoan", -1);
        recyclerView = fragmentLayout.findViewById(R.id.rvThongBao);
        ImageView imgRefresh = fragmentLayout.findViewById(R.id.imgRefresh);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(thongBaoAdapter);
        list = new ArrayList<>();

        listThongBaoTheoIdTaiKhoan();

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listThongBaoTheoIdTaiKhoan();
            }
        });

        return fragmentLayout;
    }

    private void listThongBaoTheoIdTaiKhoan(){
        ApiServiceKiet.apiServiceKiet.getListThongBaoTheoIdTaiKhoan(idTaiKhoan).enqueue(new Callback<List<ThongBao>>() {
            @Override
            public void onResponse(Call<List<ThongBao>> call, Response<List<ThongBao>> response) {
                list = response.body();
                thongBaoAdapter = new ThongBaoAdapter(getActivity(), list, R.layout.cardview_danh_sach_thong_bao);
                recyclerView.setAdapter(thongBaoAdapter);
                thongBaoAdapter.setOnClickItemListener(new ThongBaoAdapter.OnClickItemListener() {
                    @Override
                    public void onClickItem(int position, View v) {
                        nextActivity(list.get(position).getId());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ThongBao>> call, Throwable t) {

            }
        });
    }

    private void nextActivity(int id)
    {
        Intent intent = new Intent(getActivity(), NotificationDetailActivity.class);
        intent.putExtra("id", id);
        //intent.putExtra("thongBao",  thongBao());
        // trangthaithongbao = 1
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        listThongBaoTheoIdTaiKhoan();
    }
}
