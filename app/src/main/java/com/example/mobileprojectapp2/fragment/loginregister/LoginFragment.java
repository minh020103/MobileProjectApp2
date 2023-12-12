package com.example.mobileprojectapp2.fragment.loginregister;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.activity.loginregister.QuenMatKhauActivity;
import com.example.mobileprojectapp2.activity.nguoithue.DanhSachPhongGoiYActivity;
import com.example.mobileprojectapp2.activity.nguoithue.RenterActivity;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh;
import com.example.mobileprojectapp2.component.MComponent;
import com.example.mobileprojectapp2.datamodel.FirebaseCloudMessaging;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends AbstractFragment {
    EditText tenTaiKhoan;
    EditText matKhau;
    TextView quenMatKhau;
    Button dangNhap;
    Float v = 0.0f;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = getLayoutInflater().inflate(R.layout.login_tab_fragment, container, false);
        requestPermisstion();
        sharedPreferences = getContext().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        anhXa(view);
        setHieuUng();
        batSuKienDangNhap();
        return view;
    }

    private void batSuKienDangNhap() {
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                batTatProgessBar(0);
                if (kiemTraRong()) {
                    String ten = tenTaiKhoan.getText().toString();
                    String mk = matKhau.getText().toString();
                    kiemTraDangNhap(ten, mk);
                } else {
                    batTatProgessBar(1);
                    thongBao("Không Được Để Trống!");
                }
            }
        });

        quenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuenMatKhauActivity.class);
                startActivity(intent);
            }
        });


    }

    private void kiemTraDangNhap(String tenTaiKhoan, String matKhau) {
//        Call<TaiKhoan> call = ApiServiceNghiem.apiService.kiemTraDangNhap(tenTaiKhoan,matKhau);
//        call.enqueue(new Callback<TaiKhoan>() {
//            @Override
//            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
//                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
//                sharedPreferences.edit().putInt("idTaiKhoan", response.body().getId()).commit();
//                if(response.body().getLoaiTaiKhoan()==1){
//                    Intent intent = new Intent(getContext(), MotelRoomOwnerActivity.class);
//                    startActivity(intent);
//                }else{
//                    thongBao("Đây là Tài khoản người thuê");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TaiKhoan> call, Throwable t) {
//            thongBao("Tài Khoản Hoặc Mật Khẩu Không Đúng!");
//            }
//        });
//
//        Call<TaiKhoan> call = ApiServiceNghiem.apiService.dangNhap(tenTaiKhoan,matKhau);
//        call.enqueue(new Callback<TaiKhoan>() {
//            @Override
//            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
//                sharedPreferences.edit().putInt("idTaiKhoan", response.body().getId()).commit();
//                if(response.body().getLoaiTaiKhoan()==1){
//                    sharedPreferences.edit().putInt("idChuTro", response.body().getNguoiDangNhap().getId()).commit();
//                    sharedPreferences.edit().putInt("trangThaiXacThuc", response.body().getNguoiDangNhap().getXacThuc()).commit();
//                    batTatProgessBar(1);
//                    Intent intent = new Intent(getContext(), MotelRoomOwnerActivity.class);
//                    startActivity(intent);
//                }else{
//                    batTatProgessBar(1);
//                    Intent intent = new Intent(getContext(), RenterActivity.class);
//                    startActivity(intent);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<TaiKhoan> call, Throwable t) {
//                batTatProgessBar(1);
//                thongBao("Tài Khoản Hoặc Mật Khẩu Không Đúng!");
//            }
//        });


        Call<TaiKhoan> call = ApiServiceNghiem.apiService.dangNhapFB(tenTaiKhoan);
        call.enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                FirebaseAuth firebaseAuth;
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(tenTaiKhoan, matKhau).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        MComponent.saveTokenAppDevice(response.body().getId());
                        sharedPreferences.edit().putInt("idTaiKhoan", response.body().getId()).commit();
                        sharedPreferences.edit().putInt("loaiTaiKhoan", response.body().getLoaiTaiKhoan()).commit();
                        if (response.body().getLoaiTaiKhoan() == 1) {
                            sharedPreferences.edit().putInt("idChuTro", response.body().getNguoiDangNhap().getId()).commit();
                            sharedPreferences.edit().putInt("trangThaiXacThuc", response.body().getNguoiDangNhap().getXacThuc()).commit();

                            batTatProgessBar(1);
                            Intent intent = new Intent(getContext(), MotelRoomOwnerActivity.class);
                            startActivity(intent);
                        } else {
                            batTatProgessBar(1);
                            Intent intent = new Intent(getContext(), RenterActivity.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        batTatProgessBar(1);
                        thongBao("Tài Khoản Hoặc Mật Khẩu Không Đúng 1");
                    }
                });
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                batTatProgessBar(1);
                thongBao("Tài Khoản Hoặc Mật Khẩu Không Đúng");
            }
        });

    }

    private boolean kiemTraRong() {
        if (!tenTaiKhoan.getText().toString().isEmpty() && !matKhau.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    private void thongBao(String message) {
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

    private void setHieuUng() {
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

    private void batTatProgessBar(int kt) {
        if (kt == 0) {
            progressBar.setVisibility(View.VISIBLE);
        } else if (kt == 1) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void anhXa(View fragment) {
        tenTaiKhoan = fragment.findViewById(R.id.tenTaiKhoan);
        matKhau = fragment.findViewById(R.id.matKhau);
        quenMatKhau = fragment.findViewById(R.id.quenMatKhau);
        dangNhap = fragment.findViewById(R.id.dangNhap);
        progressBar = fragment.findViewById(R.id.progessbar);
    }

    private void requestPermisstion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    getActivity(), Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
            } else {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 111);
            }
        }
    }
}
