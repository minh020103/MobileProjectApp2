package com.example.mobileprojectapp2.fragment.nguoithue;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.nguoithue.ChangePasswordActivity;
import com.example.mobileprojectapp2.activity.nguoithue.EditProfileActivity;
import com.example.mobileprojectapp2.activity.loginregister.LoginActivity;
import com.example.mobileprojectapp2.activity.nguoithue.RenterActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.component.MComponent;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.makeramen.roundedimageview.RoundedImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends AbstractFragment {

    private RoundedImageView imgViewProfileNguoiThue;
    private TextView tvNameNguoiThue, tvPhoneNguoiThue, tv_phone_nguoi_thue_chua_dl;
    private AppCompatButton btnEditProfileNguoiThue, btnChangePassWordNguoiThue, btnLogoutNguoiThue;
    private int idTaiKhoan;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.nguoithue_fragment_profile_layout, container, false);
        anhXa(fragmentLayout);
        sharedPreferences = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        getFromDataApi();

        btnEditProfileNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("idTaiKhoan", -1);

                startActivity(intent);
            }
        });

        btnChangePassWordNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        btnLogoutNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getContext().getSharedPreferences("SharedPreferencesLogin", Context.MODE_PRIVATE);
                MComponent.deleteTokenDivice();
                sharedPreferences.edit().remove("idTaiKhoan").commit();
                sharedPreferences.edit().remove("idChuTro").commit();
                sharedPreferences.edit().remove("trangThaiXacThuc").commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return fragmentLayout;
    }

    private void getFromDataApi() {
        Call<NguoiThue> call = ApiServicePhuc2.apiService.getThongTinNguoiThue(idTaiKhoan);
        call.enqueue(new Callback<NguoiThue>() {
            @Override
            public void onResponse(Call<NguoiThue> call, Response<NguoiThue> response) {
                if (response.body().getHinh() != null)
                    Glide.with(com.example.mobileprojectapp2.fragment.nguoithue.ProfileFragment.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getHinh()).into(imgViewProfileNguoiThue);
                else {
                imgViewProfileNguoiThue.setImageResource(R.drawable.khongcoanh);
                }
                if (response.body().getSoDienThoai() != null ) {
                    tvPhoneNguoiThue.setText(response.body().getSoDienThoai());
                    tv_phone_nguoi_thue_chua_dl.setVisibility(View.GONE);
                    tvPhoneNguoiThue.setVisibility(View.VISIBLE);
                }
                else {
                    tv_phone_nguoi_thue_chua_dl.setVisibility(View.VISIBLE);
                    tvPhoneNguoiThue.setVisibility(View.GONE);
                }
                tvNameNguoiThue.setText(response.body().getTen());
            }

            @Override
            public void onFailure(Call<NguoiThue> call, Throwable t) {
                alertFail("Url Api error");
            }
        });
    }

    private void anhXa(View fragment) {
        imgViewProfileNguoiThue = fragment.findViewById(R.id.imgView_profile_nguoi_thue);
        tvNameNguoiThue = fragment.findViewById(R.id.tv_name_nguoi_thue);
        tvPhoneNguoiThue = fragment.findViewById(R.id.tv_phone_nguoi_thue);
        tv_phone_nguoi_thue_chua_dl = fragment.findViewById(R.id.tv_phone_nguoi_thue_chua_dl);
        btnEditProfileNguoiThue = fragment.findViewById(R.id.btn_Edit_Profile_nguoi_thue);
        btnChangePassWordNguoiThue = fragment.findViewById(R.id.btn_Change_Password_nguoi_thue);
        btnLogoutNguoiThue = fragment.findViewById(R.id.btn_Logout_nguoi_thue);
    }

    @Override
    public void onResume() {
        super.onResume();
        RenterActivity.viewPager2NguoiThue.setUserInputEnabled(true);
        getFromDataApi();
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Failed")
                .setIcon(R.drawable.iconp_fail)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

}
