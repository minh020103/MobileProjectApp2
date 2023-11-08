package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.ThongBao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetailActivity extends AppCompatActivity {

    ImageView imgBack;
    Button btnXoaThongBaoChiTiet;
    TextView tvNoiDungThongBaoChiTiet;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_thong_bao_layout);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        imgBack = findViewById(R.id.imgBack);
        btnXoaThongBaoChiTiet = findViewById(R.id.btnXoaThongBaoChiTiet);
        tvNoiDungThongBaoChiTiet = findViewById(R.id.tvNoiDungThongBaoChiTiet);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnXoaThongBaoChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDeleteNotify();
            }
        });

        getThongBaoByID(id);
    }

    private void getThongBaoByID(int id)
    {
        ApiServiceKiet.apiServiceKiet.getChiTietThongBaoTheoId(id).enqueue(new Callback<ThongBao>() {
            @Override
            public void onResponse(Call<ThongBao> call, Response<ThongBao> response) {
                ThongBao notify = response.body();
                tvNoiDungThongBaoChiTiet.setText(notify.getNoiDung());
            }

            @Override
            public void onFailure(Call<ThongBao> call, Throwable t) {

            }
        });
    }

    private void openDialogDeleteNotify()
    {
        new AlertDialog.Builder(this).setMessage("Xác nhận xóa thông báo ?").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                xoaThongBao(id);
                finish();
            }
        }).setNegativeButton("Cancle",null).show();
    }

    private void xoaThongBao(int id)
    {
        ApiServiceKiet.apiServiceKiet.xoaThongBaoTheoId(id).enqueue(new Callback<ThongBao>() {
            @Override
            public void onResponse(Call<ThongBao> call, Response<ThongBao> response) {
                Toast.makeText(NotificationDetailActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ThongBao> call, Throwable t) {

            }
        });
    }

}