package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.NotificationDetailActivity;
import com.example.mobileprojectapp2.adapter.chutro.ThongBaoAdapter;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.datamodel.ThongBao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends AbstractFragment{
    RecyclerView recyclerView;
    List<ThongBao> list;
    ThongBaoAdapter thongBaoAdapter;
    LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_notification_layout, container, false);

        recyclerView = fragmentLayout.findViewById(R.id.rvThongBao);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(thongBaoAdapter);
        list = new ArrayList<>();

        listThongBaoTheoIdTaiKhoan(2);

        return fragmentLayout;
    }

    private void listThongBaoTheoIdTaiKhoan(int id){
        ApiServiceKiet.apiServiceKiet.getListThongBaoTheoIdTaiKhoan(id).enqueue(new Callback<List<ThongBao>>() {
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
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        listThongBaoTheoIdTaiKhoan(2);
    }
}
