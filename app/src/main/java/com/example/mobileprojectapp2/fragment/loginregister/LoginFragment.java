package com.example.mobileprojectapp2.fragment.loginregister;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.activity.chutro.RoomMassageActivity;
import com.example.mobileprojectapp2.api.ApiServiceNghiem;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.fragment.chutro.ListRoomFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends AbstractFragment{
    EditText tenTaiKhoan;
    EditText matKhau;
    TextView quenMatKhau;
    Button dangNhap;
    Float v = 0.0f;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = getLayoutInflater().inflate(R.layout.login_tab_fragment,container, false);
        anhXa(view);
        setHieuUng();
        batSuKienDangNhap();
        return view;
    }
    private void batSuKienDangNhap(){
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(kiemTraRong()){
                    String ten = tenTaiKhoan.getText().toString();
                    String mk = matKhau.getText().toString();
                    kiemTraDangNhap(ten, mk);
                }else{
                    thongBao("Không Được Để Trống!");
                }
            }
        });


    }
    private void kiemTraDangNhap(String tenTaiKhoan, String matKhau){
        Call<TaiKhoan> call = ApiServiceNghiem.apiService.kiemTraDangNhap(tenTaiKhoan,matKhau);
        call.enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt("idTaiKhoan", response.body().getId()).commit();
                if(response.body().getLoaiTaiKhoan()==1){
                    Intent intent = new Intent(getContext(), MotelRoomOwnerActivity.class);
                    startActivity(intent);
                }else{
                    thongBao("Đây là Tài khoản người thuê");
                }

            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
            thongBao("Tài Khoản Hoặc Mật Khẩu Không Đúng!");
            }
        });
    }
    private boolean kiemTraRong(){
        if(!tenTaiKhoan.getText().toString().isEmpty()&&!matKhau.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }
    private void thongBao(String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }
    private void setHieuUng(){
        tenTaiKhoan.setTranslationX(800);
        matKhau.setTranslationX(800);
        quenMatKhau.setTranslationX(800);
        dangNhap.setTranslationX(800);
        tenTaiKhoan.setAlpha(v);
        matKhau.setAlpha(v);
        quenMatKhau.setAlpha(v);
        dangNhap.setAlpha(v);
        tenTaiKhoan.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        matKhau.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        quenMatKhau.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        dangNhap.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
    }
    private void anhXa(View fragment){
        tenTaiKhoan = fragment.findViewById(R.id.tenTaiKhoan);
        matKhau = fragment.findViewById(R.id.matKhau);
        quenMatKhau = fragment.findViewById(R.id.quenMatKhau);
        dangNhap = fragment.findViewById(R.id.dangNhap);
    }
}
