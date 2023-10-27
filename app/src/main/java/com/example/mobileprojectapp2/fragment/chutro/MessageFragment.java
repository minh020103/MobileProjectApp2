package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.RoomMassageActivity;
import com.example.mobileprojectapp2.adapter.ListTinNhanAdapter;
import com.example.mobileprojectapp2.api.ApiServiceNghiem;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageFragment extends AbstractFragment{
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PhongTinNhan> arrayList;
    ListTinNhanAdapter listTinNhanAdapter;
    SharedPreferences sharedPreferences;
    private int senderId =2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragmant_message_layout, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        senderId =sharedPreferences.getInt("idTaiKhoan", -1);
        anhXa(fragmentLayout);
        setDuLieu();

        return fragmentLayout;
    }
    @Override
    public void onResume() {
        super.onResume();

//
//        Handler handler = new Handler();
//        final Runnable r = new Runnable() {
//            @Override
//            public void run() {
//
//                setDuLieu();
//                handler.postDelayed(this,3000);
//            }
//        };
//        handler.postDelayed(r,3000);
    }

    private void setDuLieu(){
        arrayList = new ArrayList<>();
        listTinNhanAdapter = new ListTinNhanAdapter(getActivity(),R.layout.cardview_item_message,arrayList, senderId);
        Call<ArrayList<PhongTinNhan>> call = ApiServiceNghiem.apiService.danhSachTinNhanTheoIdTaiKhoan(senderId);
        call.enqueue(new Callback<ArrayList<PhongTinNhan>>() {
            @Override
            public void onResponse(Call<ArrayList<PhongTinNhan>> call, Response<ArrayList<PhongTinNhan>> response) {
                arrayList.addAll(response.body());
                listTinNhanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<PhongTinNhan>> call, Throwable t) {

            }
        });
        listTinNhanAdapter.setOnClickItemListener(new ListTinNhanAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int position, View v) {
                Intent intent = new Intent(getActivity(), RoomMassageActivity.class);
                if(arrayList.get(position).getIdTaiKhoan1()!=senderId){
                    intent.putExtra("key", arrayList.get(position).getIdTaiKhoan1());
                    intent.putExtra("phong",arrayList.get(position).getId());
                }else {
                    intent.putExtra("key", arrayList.get(position).getIdTaiKhoan2());
                    intent.putExtra("phong",arrayList.get(position).getId());
                }
                startActivity(intent);
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listTinNhanAdapter);
    }
    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }
    private void anhXa(View fragment){
        recyclerView = fragment.findViewById(R.id.recyclerViewTinNhan);
    }
}
