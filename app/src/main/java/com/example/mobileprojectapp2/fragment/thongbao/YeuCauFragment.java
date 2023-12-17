package com.example.mobileprojectapp2.fragment.thongbao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.NotificationDetailActivity;
import com.example.mobileprojectapp2.activity.chutro.YeuCauDatPhongChiTietActivity;
import com.example.mobileprojectapp2.adapter.chutro.ThongBaoAdapter;
import com.example.mobileprojectapp2.adapter.chutro.ThongBaoYeuCauDatPhongAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.datamodel.YeuCauDatPhong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YeuCauFragment extends AbstractFragment {
    private SharedPreferences shared;
    private int idTaiKhoan;
    RecyclerView recyclerView;
    List<YeuCauDatPhong> list;
    ThongBaoYeuCauDatPhongAdapter thongBaoYeuCauDatPhongAdapter;
    LinearLayoutManager layoutManager;
    SharedPreferences sharedPreferences;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.activity_yeu_cau_fragment, container, false);

        shared = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = shared.getInt("idTaiKhoan", -1);
        recyclerView = fragmentLayout.findViewById(R.id.rvThongBaoYC);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(thongBaoYeuCauDatPhongAdapter);
        list = new ArrayList<>();

        databaseReference.child("notification").child(idTaiKhoan+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listYeuCauThongBaoTheoIdTaiKhoan();
                Log.d("TAG", "onDataChange: GET OK");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listYeuCauThongBaoTheoIdTaiKhoan();


        return fragmentLayout;
    }

    private void listYeuCauThongBaoTheoIdTaiKhoan(){
        ApiServiceKiet.apiServiceKiet.getListYeuCauDangKiPhong(idTaiKhoan).enqueue(new Callback<List<YeuCauDatPhong>>() {
            @Override
            public void onResponse(Call<List<YeuCauDatPhong>> call, Response<List<YeuCauDatPhong>> response) {
                if (response.body()!=null) {
                    list = response.body();
                    thongBaoYeuCauDatPhongAdapter = new ThongBaoYeuCauDatPhongAdapter(getActivity(), list, R.layout.cardview_yeu_cau_dat_phong);
                    recyclerView.setAdapter(thongBaoYeuCauDatPhongAdapter);

                    thongBaoYeuCauDatPhongAdapter.setOnClickItemListener(new ThongBaoYeuCauDatPhongAdapter.OnClickItemListener() {
                        @Override
                        public void onClickItem(int position, View v) {
                            nextActivity(list.get(position).getId());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<YeuCauDatPhong>> call, Throwable t) {

            }
        });
    }

    private void nextActivity(int id)
    {
        Intent intent = new Intent(getActivity(), YeuCauDatPhongChiTietActivity.class);
        intent.putExtra("id", id);
        //intent.putExtra("thongBao",  thongBao());
        // trangthaithongbao = 1
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        listYeuCauThongBaoTheoIdTaiKhoan();
    }
}