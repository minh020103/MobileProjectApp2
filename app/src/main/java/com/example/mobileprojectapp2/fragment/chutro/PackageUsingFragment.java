package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.activity.chutro.ThanhToanGoiActivity;
import com.example.mobileprojectapp2.activity.nguoithue.DanhSachPhongGoiYActivity;
import com.example.mobileprojectapp2.adapter.chutro.GoiDichVuAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceDung;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.Banner;
import com.example.mobileprojectapp2.datamodel.Goi;
import com.example.mobileprojectapp2.model.ChuTro;
import com.example.mobileprojectapp2.viewpager2adapter.NguoiThueImageSlideViewPager2Adapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageUsingFragment extends AbstractFragment{

    TextView ten_nguoi_dung;
    TextView Ngay_het_han;
    TextView goi_dang_dung;
    TextView text;
    TextView text_buttomsheet;
    ImageView dang_ki_goi;
    LinearLayout da_dang_ki;
    ImageView nang_cap_goi;
    ImageView gia_han_lai;
    ImageView huy_dich_vu;
    TextView thoi_gian;
    ViewGroup container;
    GoiDichVuAdapter goiDichVuAdapter;
    RecyclerView recyclerView;

    List<Goi> goiList;
    LinearLayoutManager layoutManager;
    private SharedPreferences shared;
    private int idTaiKhoan;

    ViewPager2 vp2Banner;
    LinkedList<Banner> listHinh;
    NguoiThueImageSlideViewPager2Adapter imagesAdapter;


    int temp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_package_using_layout, container, false);

        this.container = container;

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
        thoi_gian = fragmentLayout.findViewById(R.id.thoi_gian);

        shared = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = shared.getInt("idTaiKhoan", -1);

        listHinh = new LinkedList<>();
        imagesAdapter = new NguoiThueImageSlideViewPager2Adapter(getActivity(), listHinh, R.layout.chutro_item_image_layout);
        vp2Banner.setAdapter(imagesAdapter);
        getDataForImages();

        HostByIdApi(idTaiKhoan);
        getGoiByIdAPI(temp);

        dang_ki_goi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickOpenButtomSheet("Đăng Kí Gói Dịch Vụ");
            }
        });

        nang_cap_goi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickOpenButtomSheet("Nâng Cấp Gói Dịch Vụ");
            }
        });

        gia_han_lai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThanhToanGoiActivity.class);
                intent.putExtra("idGoi",temp);
                startActivity(intent);
            }
        });

        huy_dich_vu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongBao("Bạn Có Chắc Muốn Huỷ Gói ?");
            }

        });

        return fragmentLayout;
    }

    private void HuyDichVu(int key) {
        thoi_gian.setVisibility(View.GONE);
        Ngay_het_han.setVisibility(View.GONE);
        ApiServiceDung.apiServiceDung.xoagoidangdung(key).enqueue(new Callback<com.example.mobileprojectapp2.datamodel.ChuTro>() {
            @Override
            public void onResponse(Call<com.example.mobileprojectapp2.datamodel.ChuTro> call, Response<com.example.mobileprojectapp2.datamodel.ChuTro> response) {

            }
            @Override
            public void onFailure(Call<com.example.mobileprojectapp2.datamodel.ChuTro> call, Throwable t) {

            }
        });
    }
    private void getDataForImages() {
        ApiServiceMinh.apiService.layTatCaBanner().enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                if (response.code() == 200) {
                    vp2Banner.setBackground(null);
                    listHinh.addAll(response.body());
                    imagesAdapter.notifyDataSetChanged();
                } else {
                    vp2Banner.setBackground(PackageUsingFragment.this.getResources().getDrawable(R.drawable.thuduc, PackageUsingFragment.this.getActivity().getTheme()));
                }
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                vp2Banner.setBackground(PackageUsingFragment.this.getResources().getDrawable(R.drawable.thuduc, PackageUsingFragment.this.getActivity().getTheme()));
            }
        });
        imagesAdapter.notifyDataSetChanged();
    }

    private void ClickOpenButtomSheet(String text) {
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_dich_vu, container, false);
        BottomSheetDialog bottomSheetDialog = new  BottomSheetDialog(getActivity(),R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(view);
        text_buttomsheet = view.findViewById(R.id.text_buttomsheet);
        text_buttomsheet.setText(text);
        recyclerView = view.findViewById(R.id.rcvDichvu);
        goiDichVuAdapter = new GoiDichVuAdapter(getActivity(), goiList, R.layout.cardview_goi_item);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(goiDichVuAdapter);
        listGoiByApi();
        bottomSheetDialog.show();
    }

    private void listGoiByApi()
    {
        ApiServiceDung.apiServiceDung.getListPakageAPI().enqueue(new Callback<List<Goi>>() {
            @Override
            public void onResponse(Call<List<Goi>> call, Response<List<Goi>> response) {
                goiList = response.body();
                goiDichVuAdapter = new GoiDichVuAdapter(getActivity(), goiList, R.layout.cardview_goi_item);
                recyclerView.setAdapter(goiDichVuAdapter);
                goiDichVuAdapter.setOnClickItemListener(new GoiDichVuAdapter.OnClickItemListener() {
                    @Override
                    public void onClickItem(int position, View v) {
                        Intent intent = new Intent(getActivity(), ThanhToanGoiActivity.class);
                        intent.putExtra("idGoi",goiList.get(position).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Goi>> call, Throwable t) {

            }
        });
    }

    private void HostByIdApi(int key)
    {
        ApiServicePhuc.apiService.getChuTroById(key).enqueue(new Callback<ChuTro>() {
            @Override
            public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                ChuTro chuTro = response.body();
                ten_nguoi_dung.setText(chuTro.getTen());
                temp = chuTro.getIdGoi();
                getGoiByIdAPI(temp);
                if (chuTro.getIdGoi() == 0){
                    thoi_gian.setVisibility(View.GONE);
                    Ngay_het_han.setVisibility(View.GONE);
                    text.setText("Vui Lòng Đăng Kí Gói Để Có Thể Đăng Cho Thuê Phòng");
                    dang_ki_goi.setVisibility(View.VISIBLE);
                    da_dang_ki.setVisibility(View.GONE);
                }else {
                    thoi_gian.setVisibility(View.VISIBLE);
                    Ngay_het_han.setVisibility(View.VISIBLE);
                    text.setText("Vui Lòng Gia Hạn Trước Khi Hết Hạn Để Không Bị Gián Đoạn Sử Dụng Dịch Vụ");
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
        if (key == 0){
            goi_dang_dung.setText("Chưa Đăng Kí");
        }else {
            ApiServiceDung.apiServiceDung.getPakageByIdAPI(key).enqueue(new Callback<Goi>() {
                @Override
                public void onResponse(Call<Goi> call, Response<Goi> response) {
                    Goi goi = response.body();
                    String thoihan = String.valueOf(goi.getThoiHan());
                    String soPhong = String.valueOf(goi.getSoLuongPhongToiDa());
                    goi_dang_dung.setText(thoihan + " Ngày / " + soPhong + " Phòng");
                }
                @Override
                public void onFailure(Call<Goi> call, Throwable t) {

                }
            });
        }
    }

    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HuyDichVu(idTaiKhoan);
                onResume();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        MotelRoomOwnerActivity.vp2Chutro.setUserInputEnabled(true);
        HostByIdApi(idTaiKhoan);
    }
}
