package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.datamodel.ThongBao;
import com.example.mobileprojectapp2.datamodel.YeuCauDatPhong;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YeuCauDatPhongChiTietActivity extends AppCompatActivity {

    ImageView imgBack;
    Button btnXoaThongBaoChiTiet;
    TextView tvTieuDeThongBaoChiTiet;
    TextView tvNoiDungThongBaoChiTiet1;
    TextView tvNoiDungThongBaoChiTiet2;
    TextView tvNoiDungThongBaoChiTiet3;
    TextView tvNoiDungThongBaoChiTiet4;
    Button btnChiTietPhong;
    Button btnChapNhanYC;
    Button btnTuChoiYC;

    int idPhong;


    private int idTaiKhoan;
    private SharedPreferences sharedPreferences;
    int id;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_yeu_cau_dat_phong_chi_tiet);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        imgBack = findViewById(R.id.imgBack);
        tvTieuDeThongBaoChiTiet = findViewById(R.id.tvTieuDeYCThongBaoChiTiet);
        tvNoiDungThongBaoChiTiet1 = findViewById(R.id.tvNoiDungYCThongBaoChiTiet1);
        tvNoiDungThongBaoChiTiet2 = findViewById(R.id.tvNoiDungYCThongBaoChiTiet2);
        tvNoiDungThongBaoChiTiet3 = findViewById(R.id.tvNoiDungYCThongBaoChiTiet3);
        tvNoiDungThongBaoChiTiet4 = findViewById(R.id.tvNoiDungYCThongBaoChiTiet4);
        btnChiTietPhong = findViewById(R.id.btnChiTietPhong);
        btnTuChoiYC = findViewById(R.id.btnTuChoiYC);
        btnChapNhanYC = findViewById(R.id.btnChapNhanYC);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnChiTietPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToChiTietPhong(idPhong);
            }
        });

        getThongBaoByID(id);
    }

    private void getThongBaoByID(int id)
    {
        ApiServiceKiet.apiServiceKiet.getYeuCauDangKiPhongById(id).enqueue(new Callback<YeuCauDatPhong>() {
            @Override
            public void onResponse(Call<YeuCauDatPhong> call, Response<YeuCauDatPhong> response) {
                YeuCauDatPhong data = response.body();
                tvTieuDeThongBaoChiTiet.setText("Yêu cầu đăng ký phòng số " + data.getPhong().getSoPhong());
                tvNoiDungThongBaoChiTiet1.setText("Mã phòng " + data.getPhong().getId());
                tvNoiDungThongBaoChiTiet2.setText("Tên: " + data.getNguoiThue().getTen());
                tvNoiDungThongBaoChiTiet3.setText("SĐT: " + data.getNguoiThue().getSoDienThoai());
                idPhong = data.getIdPhong();
                if (data.getNguoiThue().getGioiTinh() == 0)
                {
                    tvNoiDungThongBaoChiTiet4.setText("Giới tính: Nam");
                }
                else
                {
                    tvNoiDungThongBaoChiTiet4.setText("Giới tính: Nữ");
                }

            }

            @Override
            public void onFailure(Call<YeuCauDatPhong> call, Throwable t) {

            }
        });
    }

    private void goToChiTietPhong(int idPhong)
    {
        Intent intent = new Intent(this, DetailPhongTroActivity.class);
        intent.putExtra("idPhong", idPhong);
        //intent.putExtra("thongBao",  thongBao());
        // trangthaithongbao = 1
        startActivity(intent);
    }
}