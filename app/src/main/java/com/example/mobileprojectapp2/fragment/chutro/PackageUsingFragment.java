package com.example.mobileprojectapp2.fragment.chutro;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.api.chutro.ApiServiceDung;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.datamodel.Goi;
import com.example.mobileprojectapp2.model.ChuTro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageUsingFragment extends AbstractFragment{

    TextView ten_nguoi_dung;
    TextView Ngay_het_han;
    TextView goi_dang_dung;
    TextView text;
    Button dang_ki_goi;
    LinearLayout da_dang_ki;
    Button nang_cap_goi;
    Button gia_han_lai;
    Button huy_dich_vu;
    int temp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_package_using_layout, container, false);

        //anh xa
        ten_nguoi_dung = fragmentLayout.findViewById(R.id.ten_nguoi_dung);
        Ngay_het_han = fragmentLayout.findViewById(R.id.Ngay_het_han);
        text = fragmentLayout.findViewById(R.id.textView11);
        goi_dang_dung = fragmentLayout.findViewById(R.id.goi_dang_dung);
        dang_ki_goi = fragmentLayout.findViewById(R.id.dang_ki_goi);
        da_dang_ki = fragmentLayout.findViewById(R.id.da_dang_ki);
        nang_cap_goi = fragmentLayout.findViewById(R.id.nang_cap_goi);
        gia_han_lai = fragmentLayout.findViewById(R.id.gia_han_lai);
        huy_dich_vu = fragmentLayout.findViewById(R.id.huy_dich_vu);

        getGoiByIdAPI(2);
        getGoiByIdAPI(temp);



        return fragmentLayout;
    }

    private void HostByIdApi(int key)
    {
        ApiServicePhuc.apiService.getChuTroById(key).enqueue(new Callback<ChuTro>() {
            @Override
            public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                ChuTro chuTro = response.body();
                ten_nguoi_dung.setText(chuTro.getTen());
                temp = chuTro.getIdGoi();


                if (chuTro.getIdGoi() == 0){
                    text.setText("Dang ki goi di ba");
                    dang_ki_goi.setVisibility(View.VISIBLE);
                    da_dang_ki.setVisibility(View.GONE);
                }else {
                    text.setText("Nang cap goi di");
                    dang_ki_goi.setVisibility(View.GONE);
                    da_dang_ki.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ChuTro> call, Throwable t) {

            }
        });
    }

    private void getGoiByIdAPI(int key){
        ApiServiceDung.apiServiceDung.getPakageByIdAPI(key).enqueue(new Callback<Goi>() {
            @Override
            public void onResponse(Call<Goi> call, Response<Goi> response) {
                Goi goi = response.body();
                String thoihan = String.valueOf(goi.getThoiHan());
                String soPhong = String.valueOf(goi.getSoLuongPhongToiDa());
                goi_dang_dung.setText(thoihan + " / " + soPhong);
            }

            @Override
            public void onFailure(Call<Goi> call, Throwable t) {

            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        MotelRoomOwnerActivity.vp2Chutro.setUserInputEnabled(true);
    }
}
