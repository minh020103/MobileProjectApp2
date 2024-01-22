package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.datamodels.PhongTro;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucDanhSachPhongGoiYAdapter;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachPhongYeuThichActivity extends AppCompatActivity {

    private ImageView imgViewBack;
    private RecyclerView rcvListPhongYeuThich;
    private TextView tv_thongbao;
    private PhucDanhSachPhongGoiYAdapter adapter;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(DanhSachPhongYeuThichActivity.this);
    private List<PhongTro> listPhongYeuThich;

    private int idTaiKhoan;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_danh_sach_phong_yeu_thich);
        listPhongYeuThich = new LinkedList<>();

        anhXa();

        sharedPreferences = DanhSachPhongYeuThichActivity.this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);

        adapter.setMyOnCLickListener(new PhucDanhSachPhongGoiYAdapter.MyOnCLickListener() {
            @Override
            public void OnClickItem(int position, View v) {
                Animation anim = AnimationUtils.loadAnimation(DanhSachPhongYeuThichActivity.this, R.anim.item_click);
                v.startAnimation(anim);
                Intent intent = new Intent(DanhSachPhongYeuThichActivity.this, DetailPhongTroNguoiThueActivity.class);
                intent.putExtra("idPhong", listPhongYeuThich.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void OnCLickLike(int position, View v) {
                alertComingSoon("Coming soon");
            }
        });

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataPhongYeuThichApi();
    }

    private void getDataPhongYeuThichApi() {
        Call<List<PhongTro>> call = ApiServicePhuc2.apiService.getAllPhongYeuThich(idTaiKhoan);
       call.enqueue(new Callback<List<PhongTro>>() {
           @Override
           public void onResponse(Call<List<PhongTro>> call, Response<List<PhongTro>> response) {
               if (response.code() == 200) {
                   if (response.body()!=null) {
                       if (response.body().size() == 0) {
                           tv_thongbao.setVisibility(View.VISIBLE);
                       } else {
                           tv_thongbao.setVisibility(View.GONE);
                           listPhongYeuThich.clear();
                           listPhongYeuThich.addAll(response.body());
                           adapter.notifyDataSetChanged();
                       }
                   }
               }
           }

           @Override
           public void onFailure(Call<List<PhongTro>> call, Throwable t) {

           }
       });
    }

    private void anhXa() {
        tv_thongbao = findViewById(R.id.tv_thongbao);
        imgViewBack = findViewById(R.id.img_back_detail);
        rcvListPhongYeuThich = findViewById(R.id.rcv_list_ds_phong_yeu_thich);

        adapter = new PhucDanhSachPhongGoiYAdapter(DanhSachPhongYeuThichActivity.this, listPhongYeuThich, R.layout.nguoi_thue_cardview_item_phong_goi_y);
        layoutManager = new LinearLayoutManager(DanhSachPhongYeuThichActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListPhongYeuThich.setLayoutManager(layoutManager);
        rcvListPhongYeuThich.setAdapter(adapter);
    }

    private void alertComingSoon(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(s)
                .setIcon(R.drawable.iconp_coming_soon)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}