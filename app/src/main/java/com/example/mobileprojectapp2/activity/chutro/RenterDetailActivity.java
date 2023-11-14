package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterDetailActivity extends AppCompatActivity {
    ImageView imgNguoiThueChiTiet;
    TextView tvTenNguoiThueChiTiet;
    TextView tvSDTNguoiThueChiTiet;
    TextView tvPhongNguoiThueChiTiet;
    TextView tvGioiTinhNguoiThueChiTiet;
    Button btnGoiDienNguoiThue;
    Button btnNhanTinNguoiThue;
    ImageView imgBackNguoiThueChiTiet;

    NguoiThue nguoiThue;
    SharedPreferences sharedPreferences;
    private int senderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_nguoi_thue_layout);
        Intent intent = getIntent();
        nguoiThue = new NguoiThue();
        nguoiThue = (NguoiThue) intent.getSerializableExtra("nguoiThue");
        sharedPreferences = getApplicationContext().getSharedPreferences(Const.PRE_LOGIN,MODE_PRIVATE);
        senderId = sharedPreferences.getInt("idTaiKhoan", -1);
        imgNguoiThueChiTiet = findViewById(R.id.imgNguoiThueChiTiet);
        tvTenNguoiThueChiTiet = findViewById(R.id.tvTenNguoiThueChiTiet);
        tvSDTNguoiThueChiTiet = findViewById(R.id.tvSDTNguoiThueChiTiet);
        tvGioiTinhNguoiThueChiTiet = findViewById(R.id.tvGioiTinhNguoiThueChiTiet);
        btnGoiDienNguoiThue = findViewById(R.id.btnGoiDienNguoiThue);
        imgBackNguoiThueChiTiet = findViewById(R.id.imgBackNguoiThueChiTiet);
        btnNhanTinNguoiThue = findViewById(R.id.btnNhanTinNguoiThue);
        getNguoiThueById(nguoiThue.getId());
        btnNhanTinNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<Integer> call = ApiServiceNghiem.apiService.layIdPhongTinNhan(senderId,nguoiThue.getIdTaiKhoan());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.body()==-1){
                            RequestBody senderID = RequestBody.create(MediaType.parse("multipart/form-data"),senderId+"");
                            RequestBody nguoiThueID = RequestBody.create(MediaType.parse("multipart/form-data"),nguoiThue.getIdTaiKhoan()+"");
                            Call<PhongTinNhan> phongTinNhanCall = ApiServiceNghiem.apiService.taoPhongTinNhan(senderID,nguoiThueID);
                            phongTinNhanCall.enqueue(new Callback<PhongTinNhan>() {
                                @Override
                                public void onResponse(Call<PhongTinNhan> call, Response<PhongTinNhan> response) {
                                    Call<Integer> phong = ApiServiceNghiem.apiService.layIdPhongTinNhan(senderId,nguoiThue.getIdTaiKhoan());
                                    phong.enqueue(new Callback<Integer>() {
                                        @Override
                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                            thietLapIntent(nguoiThue.getIdTaiKhoan(),response.body());
                                        }

                                        @Override
                                        public void onFailure(Call<Integer> call, Throwable t) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<PhongTinNhan> call, Throwable t) {

                                }
                            });
                        }else{
                            thietLapIntent(nguoiThue.getIdTaiKhoan(),response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        });

        btnGoiDienNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "tel:"+tvSDTNguoiThueChiTiet.getText();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(phone));
                startActivity(callIntent);
            }
        });

        imgBackNguoiThueChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void layIntent(int idDoiPhuong, int idPhong, String ten, String hinh){
        Intent intent = new Intent(RenterDetailActivity.this,PhongNhanTinActivity.class);
        intent.putExtra("idDoiPhuong",idDoiPhuong);
        intent.putExtra("idPhong",idPhong);
        intent.putExtra("ten",ten);
        intent.putExtra("hinh",hinh);
        startActivity(intent);
    }
    private void thietLapIntent(int idDoiPhuong, int phong){
        ApiServiceKiet.apiServiceKiet.getChiTietNguoiThueTheoIdPhong(idDoiPhuong).enqueue(new Callback<NguoiThue>() {
            @Override
            public void onResponse(Call<NguoiThue> call, Response<NguoiThue> response) {
                layIntent(response.body().getIdTaiKhoan(),phong,response.body().getTen(),response.body().getHinh());
            }
            @Override
            public void onFailure(Call<NguoiThue> call, Throwable t) {
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

    private void getNguoiThueById(int id)
    {
        ApiServiceKiet.apiServiceKiet.getChiTietNguoiThueTheoIdPhong(id).enqueue(new Callback<NguoiThue>() {
            @Override
            public void onResponse(Call<NguoiThue> call, Response<NguoiThue> response) {
                NguoiThue renter = response.body();
                Glide.with(getApplicationContext()).load(Const.DOMAIN + renter.getHinh()).into(imgNguoiThueChiTiet);
                tvTenNguoiThueChiTiet.setText(renter.getTen());
                tvSDTNguoiThueChiTiet.setText(renter.getSoDienThoai());
                if (renter.getGioiTinh() == 1)
                {
                    tvGioiTinhNguoiThueChiTiet.setText("Nu");
                }
                else
                {
                    tvGioiTinhNguoiThueChiTiet.setText("Nam");
                }


            }
            @Override
            public void onFailure(Call<NguoiThue> call, Throwable t) {

            }
        });
    }
}