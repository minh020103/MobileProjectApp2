package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView toolbar;
    ImageView imgBackNguoiThueDanhSach;

    int idPhong;
    int soPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_nguoi_thue_layout);

        Intent intent = getIntent();
        idPhong = intent.getIntExtra("idPhong", 0);
        soPhong = intent.getIntExtra("soPhong", 0);


        recyclerView = findViewById(R.id.rvNguoiThue);
        imgBackNguoiThueDanhSach = findViewById(R.id.imgBackNguoiThueDanhSach);
        layoutManager = new LinearLayoutManager(this);
        list = new ArrayList<>();
        phongNguoiThueAdapter = new PhongNguoiThueAdapter(this, list, R.layout.cardview_danh_sach_nguoi_thue);
        listNguoiThueTheoIdPhong(idPhong);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(phongNguoiThueAdapter);


        title = findViewById(R.id.tvTitle);
        toolbar = findViewById(R.id.tvToolbar);
        toolbar.setText("Người thuê phòng số " + soPhong);





        imgBackNguoiThueDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    private void listNguoiThueTheoIdPhong(int id)
    {
        ApiServiceKiet.apiServiceKiet.getListNguoiThueTheoIdPhong(id).enqueue(new Callback<List<PhongNguoiThue>>() {
            @Override
            public void onResponse(Call<List<PhongNguoiThue>> call, Response<List<PhongNguoiThue>> response) {
//                list = response.body();
                if (response.body()!=null)
                {
//                    thongBao("Ok");
                    if(response.body().size()!=0){
                        list.addAll(response.body());

                        phongNguoiThueAdapter.notifyDataSetChanged();

//                    recyclerView.setAdapter(phongNguoiThueAdapter);
                        phongNguoiThueAdapter.setOnClickItemListener(new PhongNguoiThueAdapter.OnClickItemListener() {
                            @Override
                            public void onClickItem(int position, View v) {
                                nextActivity(list.get(position).getNguoiThue());
                            }
                        });
                    } else
                    {
                        title.setText("Chưa có người thuê");
                        title.setTextSize(30);
                    }

                }


            }

            @Override
            public void onFailure(Call<List<PhongNguoiThue>> call, Throwable t) {

            }
        });
    }

    private void nextActivity(NguoiThue nguoiThue)
    {
        Intent intent = new Intent(this, RenterDetailActivity.class);
        intent.putExtra("nguoiThue", nguoiThue);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        listNguoiThueTheoIdPhong(idPhong);
    }
}