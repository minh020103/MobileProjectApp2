package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.mobileprojectapp2.datamodel.NguoiThue;

import java.util.List;

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
    ImageView imgBackNguoiThueChiTiet;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_nguoi_thue_layout);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        imgNguoiThueChiTiet = findViewById(R.id.imgNguoiThueChiTiet);
        tvTenNguoiThueChiTiet = findViewById(R.id.tvTenNguoiThueChiTiet);
        tvSDTNguoiThueChiTiet = findViewById(R.id.tvSDTNguoiThueChiTiet);
        tvGioiTinhNguoiThueChiTiet = findViewById(R.id.tvGioiTinhNguoiThueChiTiet);
        btnGoiDienNguoiThue = findViewById(R.id.btnGoiDienNguoiThue);
        imgBackNguoiThueChiTiet = findViewById(R.id.imgBackNguoiThueChiTiet);

        getNguoiThueById(id);

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