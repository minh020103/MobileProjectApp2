package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private ImageView imgBackNguoiThue;
    private EditText edtPassNewNguoiThue, edtPassNowNguoiThue, edtPassConfirmNguoiThue;

    private AppCompatButton btnCancelNguoiThue;
    private Button btnAcceptChangePassNguoiThue;
    private SharedPreferences sharedPreferences;
    private int idTaiKhoan;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_change_password_layout);
        sharedPreferences = ChangePasswordActivity.this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        anhXa();
        imgBackNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancelNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAcceptChangePassNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPassEmpty(edtPassNowNguoiThue, edtPassNewNguoiThue, edtPassConfirmNguoiThue)) {
                    doiMatKhau();
                }
            }
        });
    }

    private void doiMatKhau() {
        if (checklength(edtPassNowNguoiThue, edtPassNewNguoiThue, edtPassConfirmNguoiThue)) {

            if (edtPassNewNguoiThue.getText().toString().equals(edtPassConfirmNguoiThue.getText().toString())){
//              FireBase Kiểm Tra Mật Khẩu Hiện tại
                reAuthentication(edtPassNewNguoiThue.getText().toString());
            }
            else {
                alertFail("Nhập lại mật khẩu không đúng");
                edtPassNowNguoiThue.setText("");
                edtPassNewNguoiThue.setText("");
                edtPassConfirmNguoiThue.setText("");
            }
        } else {
            alertFail("Mật khẩu tối thiểu 6 chữ số");
            edtPassNowNguoiThue.setText("");
            edtPassNewNguoiThue.setText("");
            edtPassConfirmNguoiThue.setText("");
        }
    }
    private void reAuthentication(String matKhauMoi){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),edtPassNowNguoiThue.getText().toString());
        firebaseUser.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                firebaseUser.updatePassword(matKhauMoi).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        alertSuccess("Cập Nhật Mật Khẩu Thành Công!");
                        edtPassNowNguoiThue.setText("");
                        edtPassNewNguoiThue.setText("");
                        edtPassConfirmNguoiThue.setText("");
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.postDelayed(this, 2000);
                                finish();
                            }
                        }, 2000);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        alertFail("Cập Nhật Mật Khẩu Thất Bại Thành Công!");
                        edtPassNowNguoiThue.setText("");
                        edtPassNewNguoiThue.setText("");
                        edtPassConfirmNguoiThue.setText("");
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alertFail("Mật Khẩu Hiện Tại Không Đúng!");
                edtPassNowNguoiThue.setText("");
                edtPassNewNguoiThue.setText("");
                edtPassConfirmNguoiThue.setText("");
            }
        });


    }
    private boolean checkPassEmpty(EditText edtPassNow, EditText edtPassNew, EditText edtPassConfirm) {
        if (!edtPassNow.getText().toString().isEmpty() && !edtPassNew.getText().toString().isEmpty() && !edtPassConfirm.getText().toString().isEmpty()) {
            return true;
        } else {
            alertFail("Vui lòng nhập đủ các thông tin");
        }
        return false;
    }

    private boolean checklength(EditText edtPassNew, EditText edtPassNow, EditText edtPassConfirm) {
        if (edtPassNow.getText().toString().length() < 6 || edtPassNew.getText().toString().length() < 6 || edtPassConfirm.getText().toString().length() < 6) {
            return false;
        }
        return true;
    }
    private void anhXa() {
        imgBackNguoiThue = findViewById(R.id.img_back_nguoi_thue);
        edtPassNewNguoiThue = findViewById(R.id.edt_pass_new_nguoi_thue);
        edtPassNowNguoiThue = findViewById(R.id.edt_pass_now_nguoi_thue);
        edtPassConfirmNguoiThue = findViewById(R.id.edt_pass_confrim_nguoi_thue);
        btnAcceptChangePassNguoiThue = findViewById(R.id.btn_accept_change_pass_nguoi_thue);
        btnCancelNguoiThue = findViewById(R.id.btn_cancel_nguoi_thue);
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(s)
                .setIcon(R.drawable.iconp_fail)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void alertSuccess(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(s)
                .setIcon(R.drawable.iconp_check)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}