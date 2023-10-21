package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.AuthencationActivity;
import com.example.mobileprojectapp2.activity.chutro.ChangePasswordActivity;
import com.example.mobileprojectapp2.activity.chutro.EditProfileActivity;
import com.makeramen.roundedimageview.RoundedImageView;

public class ProfileFragment extends AbstractFragment {

    private RoundedImageView imgViewProfile;
    private TextView tvName, tvPhone;

    private AppCompatButton btnEditProfile, btnChangePassWord, btnMessenger, btnAuthencation, btnLogout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_profile_layout, container, false);

        anhXa(fragmentLayout);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });
        btnChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        btnMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAuthencation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AuthencationActivity.class));
            }
        });

        return fragmentLayout;
    }

    private void anhXa(View fragment) {
        imgViewProfile = fragment.findViewById(R.id.imgView_profile);
        tvName = fragment.findViewById(R.id.tv_nameChuTro);
        tvPhone = fragment.findViewById(R.id.tv_phone);
        btnEditProfile = fragment.findViewById(R.id.btn_Edit_Profile);
        btnChangePassWord = fragment.findViewById(R.id.btn_Change_Password);
        btnMessenger = fragment.findViewById(R.id.btn_Messenger);
        btnAuthencation = fragment.findViewById(R.id.btn_Authencation);
        btnLogout = fragment.findViewById(R.id.btn_Logout);
    }


}
