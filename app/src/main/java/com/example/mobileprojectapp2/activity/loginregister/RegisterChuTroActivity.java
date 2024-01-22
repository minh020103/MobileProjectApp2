package com.example.mobileprojectapp2.activity.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodel.ChinhSach;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterChuTroActivity extends AppCompatActivity {


    EditText edtTen;
    EditText edtMatKhau;
    EditText edtEmail;
    CheckBox checkBox;
    TextView xemChinhSach;
    Button btnDangKy;
    AppCompatImageView ic_back;
    ProgressBar progressBar;
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
        if(!edtTen.getText().toString().isEmpty()&&!edtMatKhau.getText().toString().isEmpty()&&!edtEmail.getText().toString().isEmpty()){
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
                        if (response.body()!=null) {
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
                batTatProgessBar(0);
                if(kiemTraRong()){
                    if(edtMatKhau.getText().toString().length()>5){
                        if(kiemTraCheckBox()){
                            Call<ArrayList<TaiKhoan>> call = ApiServiceNghiem.apiService.layTatCaTaiKhoan();
                            call.enqueue(new Callback<ArrayList<TaiKhoan>>() {
                                @Override
                                public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                                    if (response.body()!=null) {
                                        boolean kt = true;

                                        for (TaiKhoan taiKhoan :
                                                response.body()) {
                                            if (edtEmail.getText().toString().equals(taiKhoan.getEmail())) {
                                                kt = false;
                                            }
                                        }
                                        if (kt == true) {

                                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                            firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtMatKhau.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                @Override
                                                public void onSuccess(AuthResult authResult) {
                                                    RequestBody ten = RequestBody.create(MediaType.parse("multipart/form-data"), edtTen.getText().toString());
                                                    RequestBody matKhau = RequestBody.create(MediaType.parse("multipart/form-data"), edtMatKhau.getText().toString());
                                                    RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), edtEmail.getText().toString());
                                                    Call<ChuTro> call1 = ApiServiceNghiem.apiService.taoTaiKhoanChuTro(ten, email, matKhau, email);
                                                    call1.enqueue(new Callback<ChuTro>() {
                                                        @Override
                                                        public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                                                            batTatProgessBar(1);
                                                            thongBao("Tạo Tài Khoản Thành Công");
                                                            edtTen.setText("");
                                                            edtMatKhau.setText("");
                                                            edtEmail.setText("");
                                                            checkBox.setChecked(false);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ChuTro> call, Throwable t) {
                                                            batTatProgessBar(1);
                                                            thongBao("Tạo Tài Khoản Thất Bại");
                                                        }
                                                    });


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    batTatProgessBar(1);
                                                    thongBao("Lỗi Hệ Thống!");
                                                }
                                            });
                                        } else {
                                            batTatProgessBar(1);
                                            thongBao("Tên Tài Khoản Đã Có!");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                                    batTatProgessBar(1);
                                    thongBao("Lấy Tất Cả Tài Khoản Sai");
                                }
                            });
                        }else{
                            batTatProgessBar(1);
                            thongBao("Bạn Chưa Đồng Ý Với Điều Kiên!");
                        }
                    }else{
                        batTatProgessBar(1);
                        thongBao("Mật Khẩu Tối Thiểu 6 Kí Tự");
                    }

                }else{
                    batTatProgessBar(1);
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
    private void addUserFireBase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                batTatProgessBar(1);
                thongBao("Tạo Tài Khoản Thành Công");
                edtTen.setText("");
                edtMatKhau.setText("");
                edtEmail.setText("");
                checkBox.setChecked(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                batTatProgessBar(1);
                thongBao("Lỗi Hệ Thống!");
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
    private void batTatProgessBar(int kt) {
        if (kt == 0) {
            progressBar.setVisibility(View.VISIBLE);
        } else if (kt == 1) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void anhXa(){
        edtTen = findViewById(R.id.hoTenNguoiDung);
        edtMatKhau = findViewById(R.id.matKhau);
        edtEmail = findViewById(R.id.email);
        checkBox = findViewById(R.id.checkBox);
        xemChinhSach = findViewById(R.id.chinhSach);
        btnDangKy = findViewById(R.id.btnChapNhan);
        ic_back = findViewById(R.id.ic_back);
        progressBar = findViewById(R.id.progessbar);
    }
}