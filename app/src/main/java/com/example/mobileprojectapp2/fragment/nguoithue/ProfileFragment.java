package com.example.mobileprojectapp2.fragment.nguoithue;

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
import com.example.mobileprojectapp2.activity.nguoithue.RenterActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.makeramen.roundedimageview.RoundedImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends AbstractFragment{

    private RoundedImageView imgViewProfileNguoiThue;
    private TextView tvNameNguoiThue, tvPhoneNguoiThue;
    private AppCompatButton btnEditProfileNguoiThue, btnChangePassWordNguoiThue, btnLogoutNguoiThue;
    private int idTaiKhoan = 8;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.nguoithue_fragment_profile_layout, container, false);
        anhXa(fragmentLayout);



        return fragmentLayout;
    }

    private void getFromDataApi(){
        Call<NguoiThue> call = ApiServicePhuc2.apiService.getThongTinNguoiThue(idTaiKhoan);
        call.enqueue(new Callback<NguoiThue>() {
            @Override
            public void onResponse(Call<NguoiThue> call, Response<NguoiThue> response) {
                if (response.body().getHinh() != null)
//                    Glide.with(com.example.mobileprojectapp2.fragment.nguoithue.ProfileFragment.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getHinh()).into(imgViewProfile);

                tvPhoneNguoiThue.setText(response.body().getSoDienThoai());
                tvNameNguoiThue.setText(response.body().getTen());
            }

            @Override
            public void onFailure(Call<NguoiThue> call, Throwable t) {

            }
        });
    }

    private void anhXa(View fragment) {
        imgViewProfileNguoiThue = fragment.findViewById(R.id.imgView_profile_nguoi_thue);
        tvNameNguoiThue = fragment.findViewById(R.id.tv_name_nguoi_thue);
        tvPhoneNguoiThue = fragment.findViewById(R.id.tv_phone_nguoi_thue);
        btnEditProfileNguoiThue = fragment.findViewById(R.id.btn_Edit_Profile_nguoi_thue);
        btnChangePassWordNguoiThue = fragment.findViewById(R.id.btn_Change_Password_nguoi_thue);
        btnLogoutNguoiThue = fragment.findViewById(R.id.btn_Logout);
    }

    @Override
    public void onResume() {
        super.onResume();
        RenterActivity.viewPager2NguoiThue.setUserInputEnabled(true);
    }


}
