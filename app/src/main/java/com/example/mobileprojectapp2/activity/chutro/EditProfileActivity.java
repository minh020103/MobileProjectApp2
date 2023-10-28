package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.Api.ApiServicePhuc;
import com.example.mobileprojectapp2.Api.Const;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.model.ChuTro;
import com.makeramen.roundedimageview.RoundedImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imgBack;
    private AppCompatButton btnCancel;

    private RoundedImageView imgViewProfileEdit;
    private EditText edtName, edtPhone, edtStkBank, edtNameBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        anhXa();
        getDataFormApi();


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
    }

    private void getDataFormApi() {
        Call<ChuTro> call = ApiServicePhuc.apiService.getChuTroById(5);
        call.enqueue(new Callback<ChuTro>() {
            @Override
            public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                Glide.with(EditProfileActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getHinh()).into(imgViewProfileEdit);

                edtName.setText(response.body().getTen());
                edtPhone.setText(response.body().getSoDienThoai());
                edtNameBank.setText(response.body().getTenChuTaiKhoanNganHang());
                edtStkBank.setText(response.body().getSoTaiKhoanNganHang());
            }

            @Override
            public void onFailure(Call<ChuTro> call, Throwable t) {
                alertFail("Error not call api");
            }
        });
    }

    private void anhXa() {
        imgBack = findViewById(R.id.img_back);
        btnCancel = findViewById(R.id.btn_cancel);
        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtNameBank = findViewById(R.id.edt_name_user_bank);
        edtStkBank = findViewById(R.id.edt_number_bank);
        imgViewProfileEdit = findViewById(R.id.imgView_profile_edit);
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Failed")
                .setIcon(R.drawable.icon_profile)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

}