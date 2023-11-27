package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.nguoithue.DanhSachGoiYAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodel.Banner;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodels.PhongTro;
import com.example.mobileprojectapp2.viewpager2adapter.NguoiThueImageSlideViewPager2Adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    NguoiThueImageSlideViewPager2Adapter imagesAdapter;
    ViewPager2 vp2Banner;
    LinkedList<Banner> listHinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phong_goi_y);
        anhXa();
        listHinh = new LinkedList<>();
        imagesAdapter = new NguoiThueImageSlideViewPager2Adapter(this, listHinh, R.layout.chutro_item_image_layout);
        vp2Banner.setAdapter(imagesAdapter);
        getDataForImages();
        sharedPreferences = this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        arrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        danhSachGoiYAdapter = new DanhSachGoiYAdapter(this,R.layout.card_view_item_phong_goi_y,arrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(danhSachGoiYAdapter);
        setDuLieu();
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getDataForImages() {
        ApiServiceMinh.apiService.layTatCaBanner().enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                if (response.code() == 200) {
                    vp2Banner.setBackground(null);
                    listHinh.addAll(response.body());
                    imagesAdapter.notifyDataSetChanged();
                } else {
                    vp2Banner.setBackground(DanhSachPhongGoiYActivity.this.getResources().getDrawable(R.drawable.thuduc, DanhSachPhongGoiYActivity.this.getTheme()));
                }
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                vp2Banner.setBackground(DanhSachPhongGoiYActivity.this.getResources().getDrawable(R.drawable.thuduc, DanhSachPhongGoiYActivity.this.getTheme()));
            }
        });
        imagesAdapter.notifyDataSetChanged();
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
                thongBao("Thử Xem Phòng Nào ĐÓ");
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
        vp2Banner = findViewById(R.id.vp2Banner);
    }
}