package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.nguoithue.DanhSachGoiYAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodels.PhongTro;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachPhongGoiYActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PhongTro> arrayList;
    LinearLayoutManager linearLayoutManager;
    DanhSachGoiYAdapter danhSachGoiYAdapter;
    ImageView ic_back;
    SharedPreferences sharedPreferences;
    int idTaiKhoan;
    TextView soLuongKetQua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phong_goi_y);
        anhXa();
        sharedPreferences = this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        arrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        danhSachGoiYAdapter = new DanhSachGoiYAdapter(this,R.layout.card_view_item_phong_goi_y,arrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(danhSachGoiYAdapter);
        setDuLieu();
    }
    private void setDuLieu(){
        Call<ArrayList<PhongTro>> call = ApiServiceNghiem.apiService.danhSachPhongGoiY(idTaiKhoan);
        call.enqueue(new Callback<ArrayList<PhongTro>>() {
            @Override
            public void onResponse(Call<ArrayList<PhongTro>> call, Response<ArrayList<PhongTro>> response) {
                if(response!=null){
                    arrayList.clear();
                    arrayList.addAll(response.body());
                    soLuongKetQua.setText(arrayList.size()+"");
                    danhSachGoiYAdapter.notifyDataSetChanged();
                }else{
                    soLuongKetQua.setText(0+"");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PhongTro>> call, Throwable t) {
                thongBao(call.request().toString());
            }
        });
    }
    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }
    private void anhXa(){
        recyclerView = findViewById(R.id.recyclerView);
        ic_back = findViewById(R.id.ic_back);
        soLuongKetQua= findViewById(R.id.soLuongKetQua);
    }
}