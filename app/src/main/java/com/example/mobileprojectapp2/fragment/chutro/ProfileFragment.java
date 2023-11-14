package com.example.mobileprojectapp2.fragment.chutro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.activity.loginregister.LoginActivity;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.AuthencationActivity;
import com.example.mobileprojectapp2.activity.chutro.ChangePasswordActivity;
import com.example.mobileprojectapp2.activity.chutro.EditProfileActivity;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.model.ChuTro;
import com.makeramen.roundedimageview.RoundedImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends AbstractFragment {

    private RoundedImageView imgViewProfile;
    private TextView tvName, tvPhone;
    private AppCompatButton btnEditProfile, btnChangePassWord, btnAuthencation, btnLogout;
    private int idTaiKhoan;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_profile_layout, container, false);
        anhXa(fragmentLayout);


        sharedPreferences = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);

        getDataFromApi();
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("idTaiKhoan", -1);

                startActivity(intent);
            }
        });
        btnChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        btnAuthencation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AuthencationActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getContext().getSharedPreferences("SharedPreferencesLogin", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("idTaiKhoan").commit();
                sharedPreferences.edit().remove("idChuTro").commit();
                sharedPreferences.edit().remove("trangThaiXacThuc").commit();

                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return fragmentLayout;
    }

    private void getDataFromApi() {
        Call<ChuTro> call = ApiServicePhuc.apiService.getChuTroById(idTaiKhoan);

        call.enqueue(new Callback<ChuTro>() {
            @Override
            public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                if (response.body().getHinh() != null)
                    Glide.with(ProfileFragment.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getHinh()).into(imgViewProfile);

                tvPhone.setText(response.body().getSoDienThoai());
                tvName.setText(response.body().getTen());
            }

            @Override
            public void onFailure(Call<ChuTro> call, Throwable t) {
                Toast.makeText(getActivity(), "Url Api error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void anhXa(View fragment) {
        imgViewProfile = fragment.findViewById(R.id.imgView_profile);
        tvName = fragment.findViewById(R.id.tv_nameChuTro);
        tvPhone = fragment.findViewById(R.id.tv_phone);
        btnEditProfile = fragment.findViewById(R.id.btn_Edit_Profile);
        btnChangePassWord = fragment.findViewById(R.id.btn_Change_Password);
        btnAuthencation = fragment.findViewById(R.id.btn_Authencation);
        btnLogout = fragment.findViewById(R.id.btn_Logout);
    }

    @Override
    public void onResume() {
        super.onResume();
        MotelRoomOwnerActivity.vp2Chutro.setUserInputEnabled(true);
        getDataFromApi();
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(getActivity())
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
