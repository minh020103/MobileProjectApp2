package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.SearchActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.model.PhongTroYeuThich;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucDanhSachPhongGoiYAdapter2;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucDanhSachPhongYeuThichAdapter;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachPhongYeuThichActivity extends AppCompatActivity {

    private ImageView imgViewBack;
    private RecyclerView rcvListPhongYeuThich;
    private PhucDanhSachPhongYeuThichAdapter adapter;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(DanhSachPhongYeuThichActivity.this);
    private List<PhongTroYeuThich> listPhongYeuThich;

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

      adapter.setMyOnCLickListener(new PhucDanhSachPhongYeuThichAdapter.MyOnCLickListener() {
          @Override
          public void OnClickItem(int position, View v) {
              Animation anim = AnimationUtils.loadAnimation(DanhSachPhongYeuThichActivity.this, R.anim.item_click);
              v.startAnimation(anim);
              Intent intent = new Intent(DanhSachPhongYeuThichActivity.this, DetailPhongTroNguoiThueActivity.class);
              intent.putExtra("idPhong", listPhongYeuThich.get(position).getId());
              startActivity(intent);
          }
      });

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDataPhongYeuThichApi();
    }

    private void getDataPhongYeuThichApi() {
    Call<List<PhongTroYeuThich>> call = ApiServicePhuc2.apiService.getAllPhongYeuThich(idTaiKhoan);
    call.enqueue(new Callback<List<PhongTroYeuThich>>() {
        @Override
        public void onResponse(Call<List<PhongTroYeuThich>> call, Response<List<PhongTroYeuThich>> response) {
            if (response.code() == 200) {
                listPhongYeuThich.clear();
                listPhongYeuThich.addAll(response.body());
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<List<PhongTroYeuThich>> call, Throwable t) {
            Toast.makeText(DanhSachPhongYeuThichActivity.this, "Gay", Toast.LENGTH_SHORT).show();
        }
    });
    }

    private void anhXa() {
        imgViewBack = findViewById(R.id.img_back_detail);
        rcvListPhongYeuThich = findViewById(R.id.rcv_list_ds_phong_yeu_thich);

        adapter = new PhucDanhSachPhongYeuThichAdapter(DanhSachPhongYeuThichActivity.this, listPhongYeuThich, R.layout.nguoi_thue_cardview_item_phong_goi_y);
        layoutManager = new LinearLayoutManager(DanhSachPhongYeuThichActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListPhongYeuThich.setLayoutManager(layoutManager);
        rcvListPhongYeuThich.setAdapter(adapter);
    }
}