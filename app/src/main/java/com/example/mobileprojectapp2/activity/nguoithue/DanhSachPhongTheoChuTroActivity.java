package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.model.ChuTro;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucDanhSachPhongTheoChuTroAdapter;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachPhongTheoChuTroActivity extends AppCompatActivity {

    private ImageView imgViewChuTro, imgViewBack;
    private TextView tvName, tvSdt;
    private RecyclerView rcvListPhong;
    private PhucDanhSachPhongTheoChuTroAdapter adapter;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(DanhSachPhongTheoChuTroActivity.this);
    private List<PhongTroChuTro> listDanhSachPhong;

    private int idTaiKhoan;
    private int idChuTro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_danh_sach_phong_theo_chu_tro);

        listDanhSachPhong = new LinkedList<>();
        anhXa();

        Intent intent = getIntent();
        idTaiKhoan = intent.getIntExtra("idTaiKhoan", -1);
        Log.d("TAG", "onClick: " + idTaiKhoan);
        idChuTro = intent.getIntExtra("idChuTro", -1);
        Log.d("TAG", "onClick: " + idChuTro);
        getChuTroByID();
        getListPhongDataApi(idChuTro);



        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getChuTroByID() {
        Call<ChuTro> call = ApiServicePhuc.apiService.getChuTroById(idTaiKhoan);
        call.enqueue(new Callback<ChuTro>() {
            @Override
            public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                if (response.body()!=null) {
                    if (response.body().getHinh() != null)
                        Glide.with(DanhSachPhongTheoChuTroActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getHinh()).into(imgViewChuTro);
                    else
                        imgViewChuTro.setImageResource(R.drawable.khongcoanh);
                    tvName.setText(response.body().getTen());
                    tvSdt.setText(response.body().getSoDienThoai());
                }
            }
            @Override
            public void onFailure(Call<ChuTro> call, Throwable t) {
                Toast.makeText(DanhSachPhongTheoChuTroActivity.this, "Loi chu tro", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getListPhongDataApi(int idChuTro) {
     Call<List<PhongTroChuTro>> call = ApiServicePhuc2.apiService.getALlListPhongTroTheoChuTro(idChuTro);
     call.enqueue(new Callback<List<PhongTroChuTro>>() {
         @Override
         public void onResponse(Call<List<PhongTroChuTro>> call, Response<List<PhongTroChuTro>> response) {
             if (response.code() == 200) {
                 if (response.body()!=null) {
                     listDanhSachPhong.clear();
                     listDanhSachPhong.addAll(response.body());
                     adapter.notifyDataSetChanged();
                 }
             }
             adapter.setMyOnCLickListener(new PhucDanhSachPhongTheoChuTroAdapter.MyOnCLickListener() {
                 @Override
                 public void OnClickItem(int position, View v) {
                     Animation anim = AnimationUtils.loadAnimation(DanhSachPhongTheoChuTroActivity.this, R.anim.item_click);
                     v.startAnimation(anim);
                     Intent intent = new Intent(DanhSachPhongTheoChuTroActivity.this, DetailPhongTroNguoiThueActivity.class);
                     intent.putExtra("idPhong", response.body().get(position).getIdPhongTro());
                     startActivity(intent);
                 }
             });

         }

         @Override
         public void onFailure(Call<List<PhongTroChuTro>> call, Throwable t) {

         }
     });
    }

    private void anhXa() {
        imgViewBack = findViewById(R.id.img_back_detail);
        imgViewChuTro = findViewById(R.id.imgView_chu_tro);
        tvName = findViewById(R.id.tv_ten_chu_tro);
        tvSdt = findViewById(R.id.tv_sdt_chu_tro);
        rcvListPhong = findViewById(R.id.rcv_list_ds_phong_theo_chu_tro);

        adapter = new PhucDanhSachPhongTheoChuTroAdapter(DanhSachPhongTheoChuTroActivity.this, listDanhSachPhong, R.layout.nguoi_thue_cardview_item_phong_theo_chu_tro);
        layoutManager = new LinearLayoutManager(DanhSachPhongTheoChuTroActivity.this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager = new GridLayoutManager(this, 2);
        rcvListPhong.setLayoutManager(layoutManager);
        rcvListPhong.setAdapter(adapter);



    }

}