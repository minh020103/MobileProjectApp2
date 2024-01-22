package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.PhongNhanTinActivity;
import com.example.mobileprojectapp2.activity.chutro.RoomMassageActivity;
import com.example.mobileprojectapp2.adapter.ListTinNhanAdapter;
import com.example.mobileprojectapp2.adapter.TinNhanAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.TinNhan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TinNhanFragment extends AbstractFragment {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<PhongTinNhan> arrayList;
    ListTinNhanAdapter tinNhanAdapter;
    private int senderId;
    SharedPreferences sharedPreferences;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragmant_tinnhan_layout, container, false);
        anhXa(fragmentLayout);
        khoiTaoFB();
        layShared();
        arrayList = new ArrayList<>();
        tinNhanAdapter = new ListTinNhanAdapter(getActivity(), R.layout.cardview_item_message, arrayList, senderId);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        layDuLieu();
        return fragmentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        layDuLieu();
    }

    private void thongBao(String mes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }

    private void setSuKien() {
        tinNhanAdapter.setOnClickItemListener(new ListTinNhanAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int position, View v) {
                Intent intent = new Intent(getActivity(), PhongNhanTinActivity.class);
                if (arrayList.get(position).getIdTaiKhoan1() != senderId) {
                    callThongTin(arrayList.get(position).getIdTaiKhoan1(), arrayList.get(position).getId(), arrayList.get(position).getTrangThai2(), intent);

                } else if (arrayList.get(position).getIdTaiKhoan2() != senderId) {
                    callThongTin(arrayList.get(position).getIdTaiKhoan2(), arrayList.get(position).getId(), arrayList.get(position).getTrangThai1(), intent);

                }
//                startActivity(intent);
            }
        });
    }

    private void setIntent(int idPhong, int idDoiPhuong, int trangThaiSender, String ten, String hinh, Intent intent) {
        intent.putExtra("idDoiPhuong", idDoiPhuong);
        intent.putExtra("idPhong", idPhong);
        intent.putExtra("trangThaiSender", trangThaiSender);
        intent.putExtra("ten", ten);
        intent.putExtra("hinh", hinh);
        startActivity(intent);
    }

    private void callThongTin(int idDoiPhuong, int idPhong, int trangThaiSender, Intent intent) {
        Call<TaiKhoan> call = ApiServiceNghiem.apiService.callThongTinDoiPhuong(senderId, idPhong, idDoiPhuong);
        call.enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                if (response.body() != null) {
                    setIntent(idPhong, idDoiPhuong, trangThaiSender, response.body().getThongTin().getTen(), response.body().getThongTin().getHinh(), intent);
                }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {

            }
        });
    }

    private void layDuLieu() {

        databaseReference.child("thongBaoReset").child(senderId + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               

                Call<ArrayList<PhongTinNhan>> call = ApiServiceNghiem.apiService.danhSachTinNhanTheoIdTaiKhoan(senderId);
                call.enqueue(new Callback<ArrayList<PhongTinNhan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PhongTinNhan>> call, Response<ArrayList<PhongTinNhan>> response) {
                        if (response.body() != null) {
                            arrayList.clear();
                            arrayList.addAll(response.body());
                            tinNhanAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(tinNhanAdapter);
                            setSuKien();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PhongTinNhan>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void layShared() {
        sharedPreferences = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        senderId = sharedPreferences.getInt("idTaiKhoan", -1);
    }

    private void khoiTaoFB() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void anhXa(View fragment) {
        recyclerView = fragment.findViewById(R.id.recyclerViewTinNhan);
    }
}
