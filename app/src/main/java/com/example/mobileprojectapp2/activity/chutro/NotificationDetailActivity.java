package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.ThongBao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetailActivity extends AppCompatActivity {

    ImageView imgBack;
    Button btnXoaThongBaoChiTiet;
    TextView tvTieuDeThongBaoChiTiet;
    TextView tvNoiDungThongBaoChiTiet;
    private int idTaiKhoan;
    private SharedPreferences sharedPreferences;
    int id;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_thong_bao_layout);
        sharedPreferences = this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        databaseReference.child("notification").child(idTaiKhoan+"").child(id+"").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
        imgBack = findViewById(R.id.imgBack);
        btnXoaThongBaoChiTiet = findViewById(R.id.btnXoaThongBaoChiTiet);
        tvTieuDeThongBaoChiTiet = findViewById(R.id.tvTieuDeThongBaoChiTiet);
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
                openDialogDeleteNotification();
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
                tvTieuDeThongBaoChiTiet.setText(notify.getTieuDe());
                tvNoiDungThongBaoChiTiet.setText(notify.getNoiDung());
            }

            @Override
            public void onFailure(Call<ThongBao> call, Throwable t) {

            }
        });
    }

    private void openDialogDeleteNotification()
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