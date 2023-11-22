package com.example.mobileprojectapp2.activity.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodel.ChinhSach;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
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

public class RegisterNguoiThueActivity extends AppCompatActivity {

    Spinner spinner;
    EditText edtTen;
    EditText edtMatKhau;
    EditText edtEmail;
    CheckBox checkBox;
    TextView xemChinhSach;
    Button btnDangKy;
    Integer gioiTinh;
    AppCompatImageView ic_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nguoi_thue);
        anhXa();
        setDuLieuSpinner();
        setSuKien();
    }
    private void setSuKien(){
        xemChinhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ChinhSach> call = ApiServiceNghiem.apiService.xemChinhSach(1);
                call.enqueue(new Callback<ChinhSach>() {
                    @Override
                    public void onResponse(Call<ChinhSach> call, Response<ChinhSach> response) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterNguoiThueActivity.this);
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
                    if(edtMatKhau.getText().toString().length()>5){
                        if(kiemTraCheckBox()){
                            Call<ArrayList<TaiKhoan>> call = ApiServiceNghiem.apiService.layTatCaTaiKhoan();
                            call.enqueue(new Callback<ArrayList<TaiKhoan>>() {
                                @Override
                                public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                                    boolean kt = true;

                                    for (TaiKhoan taiKhoan:
                                            response.body()) {
                                        if(edtEmail.getText().toString().equals(taiKhoan.getEmail())){
                                            kt= false;
                                        }
                                    }
                                    if(kt==true){
                                        RequestBody ten = RequestBody.create(MediaType.parse("multipart/form-data"),edtTen.getText().toString());
                                        RequestBody matKhau = RequestBody.create(MediaType.parse("multipart/form-data"),edtMatKhau.getText().toString());
                                        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"),edtEmail.getText().toString());
                                        RequestBody gioiTinhApi = RequestBody.create(MediaType.parse("multipart/form-data"),gioiTinh+"");
                                        Call<NguoiThue> call1 = ApiServiceNghiem.apiService.taoTaiKhoanNguoiThue(ten,email,matKhau,email,gioiTinhApi);
                                        call1.enqueue(new Callback<NguoiThue>() {
                                            @Override
                                            public void onResponse(Call<NguoiThue> call, Response<NguoiThue> response) {
                                                addUserFireBase(edtEmail.getText().toString(),edtMatKhau.getText().toString());
                                            }

                                            @Override
                                            public void onFailure(Call<NguoiThue> call, Throwable t) {
                                                thongBao("Tạo tài Khoản Thất Bại");
                                            }
                                        });


                                    }else{
                                        thongBao("Email Đã Có!");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                                    thongBao("Lỗi!");
                                }
                            });
                        }else{
                            thongBao("Bạn Chưa Đồng Ý Với Điều Kiên!");
                        }
                    }else{
                            thongBao("Mật Khẩu Tối Thiểu 6 Kí Tự");
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

    private void addUserFireBase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                thongBao("Tạo Tài Khoản Thành Công");
                edtTen.setText("");
                edtMatKhau.setText("");
                edtEmail.setText("");
                checkBox.setChecked(false);
                spinner.setSelection(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                thongBao("Lỗi Hệ Thống!");
            }
        });
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
    private void setDuLieuSpinner(){
        ArrayList arrayList = new ArrayList();
        arrayList.add("Nam");
        arrayList.add("Nữ");
        arrayList.add("Khác");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gioiTinh = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        edtMatKhau = findViewById(R.id.matKhau);
        edtEmail = findViewById(R.id.email);
        checkBox = findViewById(R.id.checkBox);
        xemChinhSach = findViewById(R.id.chinhSach);
        btnDangKy = findViewById(R.id.btnChapNhan);
        spinner = findViewById(R.id.spinner);
        ic_back = findViewById(R.id.ic_back);
    }
}