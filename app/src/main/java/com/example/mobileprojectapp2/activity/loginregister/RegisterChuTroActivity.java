package com.example.mobileprojectapp2.activity.loginregister;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodel.ChinhSach;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterChuTroActivity extends AppCompatActivity {


    EditText edtTen;
    EditText edtTenTaiKhoan;
    EditText edtMatKhau;
    EditText edtEmail;
    CheckBox checkBox;
    TextView xemChinhSach;
    Button btnDangKy;
    AppCompatImageView ic_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_chu_tro);
        anhXa();
        setSuKien();

    }
    private boolean kiemTraCheckBox(){
        if(checkBox.isChecked()){
            return true;
        }
        return false;
    }
    private boolean kiemTraRong(){
        if(!edtTen.getText().toString().isEmpty()&&!edtTenTaiKhoan.getText().toString().isEmpty()&&!edtMatKhau.getText().toString().isEmpty()&&!edtEmail.getText().toString().isEmpty()){
           return true;
        }return false;
    }

    private void setSuKien(){
        xemChinhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ChinhSach> call = ApiServiceNghiem.apiService.xemChinhSach(1);
                call.enqueue(new Callback<ChinhSach>() {
                    @Override
                    public void onResponse(Call<ChinhSach> call, Response<ChinhSach> response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterChuTroActivity.this);
                        builder.setMessage(response.body().getNoiDungChinhSach()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.setTitle("Thông Tin Chính Sách");
                        builder.create();
                        builder.show();
                    }
                    @Override
                    public void onFailure(Call<ChinhSach> call, Throwable t) {
                        thongBao("Không Xem Được!");
                    }
                });
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kiemTraRong()){
                    if(kiemTraCheckBox()){
                        Call<ArrayList<TaiKhoan>> call = ApiServiceNghiem.apiService.layTatCaTaiKhoan();
                        call.enqueue(new Callback<ArrayList<TaiKhoan>>() {
                            @Override
                            public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                                boolean kt = true;

                                for (TaiKhoan taiKhoan:
                                     response.body()) {
                                    if(edtTenTaiKhoan.getText().toString().equals(taiKhoan.getTenTaiKhoan())){
                                        kt= false;
                                    }
                                }
                                if(kt==true){
                                    RequestBody ten = RequestBody.create(MediaType.parse("multipart/form-data"),edtTen.getText().toString());
                                    RequestBody tenTaiKhoan = RequestBody.create(MediaType.parse("multipart/form-data"),edtTenTaiKhoan.getText().toString());
                                    RequestBody matKhau = RequestBody.create(MediaType.parse("multipart/form-data"),edtMatKhau.getText().toString());
                                    RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"),edtEmail.getText().toString());
                                    Call<ChuTro> call1 = ApiServiceNghiem.apiService.taoTaiKhoanChuTro(ten, tenTaiKhoan,matKhau,email);
                                    call1.enqueue(new Callback<ChuTro>() {
                                        @Override
                                        public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                                            thongBao("Tạo Tài Khoản Thành Công");
                                            edtTen.setText("");
                                            edtTenTaiKhoan.setText("");
                                            edtMatKhau.setText("");
                                            edtEmail.setText("");
                                            checkBox.setChecked(false);
                                        }

                                        @Override
                                        public void onFailure(Call<ChuTro> call, Throwable t) {
                                            thongBao("Tạo Tài Khoản Thất Bại");
                                        }
                                    });


                                }else{
                                    thongBao("Tên Tài Khoản Đã Có!");
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                                thongBao("Lấy Tất Cả Tài Khoản Sai");
                            }
                        });
                    }else{
                        thongBao("Bạn Chưa Đồng Ý Với Điều Kiên!");
                    }
                }else{
                    thongBao("Không Được Để Trống Thông Tin!");
                }
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
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
    private void anhXa(){
        edtTen = findViewById(R.id.hoTenNguoiDung);
        edtTenTaiKhoan = findViewById(R.id.tenTaiKhoan);
        edtMatKhau = findViewById(R.id.matKhau);
        edtEmail = findViewById(R.id.email);
        checkBox = findViewById(R.id.checkBox);
        xemChinhSach = findViewById(R.id.chinhSach);
        btnDangKy = findViewById(R.id.btnChapNhan);
        ic_back = findViewById(R.id.ic_back);
    }
}