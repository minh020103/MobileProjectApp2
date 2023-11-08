package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText edtPassNew, edtPassNow, edtPassConfirm;

    private AppCompatButton btnCancel;
    private Button btnAcceptChangePass;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        anhXa();


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAcceptChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPassEmpty(edtPassNow, edtPassNew, edtPassConfirm)) {
                    doiMatKhau();
                }
            }
        });
    }

    private void doiMatKhau() {
        if (checklength(edtPassNow, edtPassNew, edtPassConfirm)) {

            if (edtPassNew.getText().toString().equals(edtPassConfirm.getText().toString())){
                Call call = ApiServicePhuc.apiService.changePassWord(2, edtPassNow.getText().toString(), edtPassNew.getText().toString());
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        int intResponse = Integer.parseInt(response.body().toString());
                        if (intResponse == 0 ){
                        alertFail("Mật khẩu hiện tại sai");
                        edtPassNow.setText("");
                        edtPassNew.setText("");
                        edtPassConfirm.setText("");
                        }else {
                            alertSuccess("Cập nhật Thành Công");
                        edtPassNow.setText("");
                        edtPassNew.setText("");
                        edtPassConfirm.setText("");
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.postDelayed(this, 2000);
                                finish();
                            }
                        }, 2000);
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
            }
            else {
                alertFail("Nhập lại mật khẩu không đúng");
                edtPassNow.setText("");
                edtPassNew.setText("");
                edtPassConfirm.setText("");
            }
        } else {
            alertFail("Mật khẩu tối thiểu 6 chữ số");
            edtPassNow.setText("");
            edtPassNew.setText("");
            edtPassConfirm.setText("");
        }
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
        imgBack = findViewById(R.id.img_back);
        edtPassNew = findViewById(R.id.edt_pass_new);
        edtPassNow = findViewById(R.id.edt_pass_now);
        edtPassConfirm = findViewById(R.id.edt_pass_confrim);
        btnAcceptChangePass = findViewById(R.id.btn_accept_change_pass);
        btnCancel = findViewById(R.id.btn_cancel);
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