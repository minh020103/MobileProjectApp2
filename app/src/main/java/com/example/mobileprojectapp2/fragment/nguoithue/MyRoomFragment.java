package com.example.mobileprojectapp2.fragment.nguoithue;

import static com.example.mobileprojectapp2.api.Const.MALE_GENDERS;
import static com.example.mobileprojectapp2.api.Const.PHONG_DON;
import static com.example.mobileprojectapp2.api.Const.PHONG_TRONG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.RenterDetailActivity;
import com.example.mobileprojectapp2.activity.chutro.ZoomOutPageTransformer;
import com.example.mobileprojectapp2.activity.nguoithue.DanhSachPhongTheoChuTroActivity;
import com.example.mobileprojectapp2.activity.nguoithue.DetailPhongTroNguoiThueActivity;
import com.example.mobileprojectapp2.adapter.chutro.PhongNguoiThueAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceDung;
import com.example.mobileprojectapp2.api.chutro.ApiServiceKiet;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.component.MComponent;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
import com.example.mobileprojectapp2.datamodel.PhongNguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.example.mobileprojectapp2.datamodel.Phuong;
import com.example.mobileprojectapp2.model.PhongTro;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.HinhAnhAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRoomFragment extends AbstractFragment{

    TextView tv_detail_soPhong,tv_detail_gia_my_room,tv_detail_so_luong_toi_da,tv_detail_loai_phong,tv_detail_tien_coc,
    tv_detail_dien_tich,tv_detail_gioi_tinh,tv_detail_tien_dien,tv_detail_tien_nuoc,tv_detail_mo_ta,
            tv_detail_dia_chi,chuacotro;
    LinearLayout thongtin,anh;
    LinearLayout edtComment, ic_danhgia;
    Button btnRoiTro;
    TextView title;
    LinearLayoutManager layoutManager;

    private List<HinhAnh> listHinhAnh;
    private HinhAnhAdapter adapterHinhAnh;

    LinkedList<PhongBinhLuan> list;
    private SharedPreferences shared;
    private int idTaiKhoan,idTaiKhoanNguoiThue,idphong;
    private ViewPager2 mViewPager2;

    List<PhongNguoiThue> nguoithueList;
    RecyclerView recyclerView;
    PhongNguoiThueAdapter phongNguoiThueAdapter;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == listHinhAnh.size() - 1) {
                mViewPager2.setCurrentItem(0);
            } else {
                // Next sang page tiep theo
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.nguoithue_fragment_my_room_layout, container, false);

        anhxa(fragmentLayout);
        LayDuLieu();

        list = new LinkedList<>();
        edtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MComponent.comment(getActivity(), container, idphong,list ,idTaiKhoanNguoiThue);
            }
        });

        ic_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MComponent.rating(getActivity(),idphong,idTaiKhoanNguoiThue);
            }
        });

        btnRoiTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongBao("Bạn Có Chắc Muốn Rời Trọ \nThao Tác Này Sẽ Không Hoàn Tác Được");
            }
        });


        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                //Thoi gian chuyen sile
                handler.postDelayed(runnable, 3000);
            }
        });

        mViewPager2.setPageTransformer(new ZoomOutPageTransformer());


        return fragmentLayout;
    }

    private void LayDuLieu(){
        shared = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoanNguoiThue = shared.getInt("idNguoiThue", -1);
        idTaiKhoan = shared.getInt("idTaiKhoan", -1);
        Call<PhongNguoiThue> call = ApiServiceDung.apiServiceDung.layphongnguoithue(idTaiKhoanNguoiThue);
        call.enqueue(new Callback<PhongNguoiThue>() {
            @Override
            public void onResponse(Call<PhongNguoiThue> call, Response<PhongNguoiThue> response) {
                if (response.body() != null){
                    idphong = response.body().getIdPhong();

                    getDetailPhongApi(idphong);
                    getDataFromApi(idphong);
                    listNguoiThueTheoIdPhong(idphong);

                    listHinhAnh = new ArrayList<>();
                    adapterHinhAnh = new HinhAnhAdapter(getActivity(), listHinhAnh, R.layout.chutro_item_image_layout);
                    mViewPager2.setAdapter(adapterHinhAnh);

                    layoutManager = new LinearLayoutManager(getActivity());
                    nguoithueList = new ArrayList<>();
                    phongNguoiThueAdapter = new PhongNguoiThueAdapter(getActivity(), nguoithueList, R.layout.cardview_danh_sach_nguoi_thue_my_room);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(phongNguoiThueAdapter);

                    chuacotro.setVisibility(View.GONE);
                    thongtin.setVisibility(View.VISIBLE);
                    anh.setVisibility(View.VISIBLE);
                    btnRoiTro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PhongNguoiThue> call, Throwable t) {
                chuacotro.setVisibility(View.VISIBLE);
                thongtin.setVisibility(View.GONE);
                anh.setVisibility(View.GONE);
                btnRoiTro.setVisibility(View.GONE);
            }
        });
    }

    private void getDataFromApi(int idphong) {
        Call<PhongTro> call = ApiServicePhuc.apiService.getPhongTroByID(idphong);
        call.enqueue(new Callback<PhongTro>() {
            @Override
            public void onResponse(Call<PhongTro> call, Response<PhongTro> response) {

                tv_detail_soPhong.setText(response.body().getSoPhong()+"");
//                gia
                float trieu = 1000000;
                float ngan = 1000;
                float tram = 100000;
                float gia;
                String gia2;
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                if (response.body().getGia() < tram) {
                    tv_detail_gia_my_room.setText("Đang cập nhật");
                } else if (response.body().getGia() < trieu) {
                    gia = response.body().getGia() / ngan;
                    gia2 = decimalFormat.format(gia);
                    tv_detail_gia_my_room.setText(gia2 + "k /tháng");
                } else if (response.body().getGia() >= trieu) {
                    gia = response.body().getGia() / trieu;
                    gia2 = decimalFormat.format(gia);
                    tv_detail_gia_my_room.setText(gia2 + " triệu/tháng");
                }

                tv_detail_so_luong_toi_da.setText(response.body().getSoLuongToiDa() + " người");

                if (response.body().getLoaiPhong() == PHONG_TRONG) {
                    tv_detail_loai_phong.setText("Phòng trống");
                } else if (response.body().getLoaiPhong() == PHONG_DON) {
                    tv_detail_loai_phong.setText("Phòng đơn");
                } else {
                    tv_detail_loai_phong.setText("Phòng ghép");
                }

                if (response.body().getTienCoc() < tram) {
                    tv_detail_tien_coc.setText("Đang cập nhật");
                } else if (response.body().getTienCoc() < trieu) {
                    gia = response.body().getTienCoc() / ngan;
                    gia2 = decimalFormat.format(gia);
                    tv_detail_tien_coc.setText(gia2 + "k");
                } else if (response.body().getTienCoc() >= trieu) {
                    gia = response.body().getTienCoc() / trieu;
                    gia2 = decimalFormat.format(gia);
                    tv_detail_tien_coc.setText(gia2 + " triệu");
                }

                tv_detail_dien_tich.setText(response.body().getDienTich() + "㎡");

                if (response.body().getGioiTinh() == MALE_GENDERS) {
                    tv_detail_gioi_tinh.setText("Nam ♂");
                } else {
                    tv_detail_gioi_tinh.setText("Nữ ♀");
                }

                //tv_detail_tien_dien.setText(response.body().getTienDien()+"");
                if (response.body().getTienDien() < tram) {
                    tv_detail_tien_dien.setText("Đang cập nhật");
                } else if (response.body().getTienDien() < trieu) {
                    gia = response.body().getTienDien() / ngan;
                    gia2 = decimalFormat.format(gia);
                    tv_detail_tien_dien.setText(gia2 + "k");
                } else if (response.body().getTienDien() >= trieu) {
                    gia = response.body().getTienDien() / trieu;
                    gia2 = decimalFormat.format(gia);
                    tv_detail_tien_dien.setText(gia2 + " triệu");
                }

                //tv_detail_tien_nuoc.setText(response.body().getTienNuoc()+"");
                if (response.body().getTienNuoc() < tram) {
                    tv_detail_tien_nuoc.setText("Đang cập nhật");
                } else if (response.body().getTienNuoc() < trieu) {
                    gia = response.body().getTienNuoc() / ngan;
                    gia2 = decimalFormat.format(gia);
                    tv_detail_tien_nuoc.setText(gia2 + "k");
                } else if (response.body().getTienNuoc() >= trieu) {
                    gia = response.body().getTienNuoc() / trieu;
                    gia2 = decimalFormat.format(gia);
                    tv_detail_tien_nuoc.setText(gia2 + " triệu");
                }

                tv_detail_mo_ta.setText(response.body().getMoTa()+"");

                tv_detail_dia_chi.setText(response.body().getDiaChiChiTiet()+"");

            }
            @Override
            public void onFailure(Call<PhongTro> call, Throwable t) {
                Toast.makeText(getActivity(), "Error not call Api", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getDetailPhongApi(int idphong) {
        Call<PhongTro> call = ApiServicePhuc.apiService.getPhongTroByID(idphong);
        call.enqueue(new Callback<PhongTro>() {
            @Override
            public void onResponse(Call<PhongTro> call, Response<PhongTro> response) {
                for (HinhAnh hinhAnh : response.body().getHinhAnhPhongTro()) {
                    listHinhAnh.add(hinhAnh);

                }
                adapterHinhAnh.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PhongTro> call, Throwable t) {

            }
        });
    }

    private void listNguoiThueTheoIdPhong(int id)
    {
        ApiServiceKiet.apiServiceKiet.getListNguoiThueTheoIdPhong(id).enqueue(new Callback<List<PhongNguoiThue>>() {
            @Override
            public void onResponse(Call<List<PhongNguoiThue>> call, Response<List<PhongNguoiThue>> response) {
                if (response.body()!=null)
                {
                    if(response.body().size()!=0){
                        nguoithueList.addAll(response.body());
                        title.setVisibility(View.GONE);
                        phongNguoiThueAdapter.notifyDataSetChanged();

                        phongNguoiThueAdapter.setOnClickItemListener(new PhongNguoiThueAdapter.OnClickItemListener() {
                            @Override
                            public void onClickItem(int position, View v) {
                                nextActivity(nguoithueList.get(position).getNguoiThue());
                            }
                        });
                    } else
                    {
                        title.setVisibility(View.VISIBLE);
                        title.setText("Chưa có người thuê");
                        title.setTextSize(30);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PhongNguoiThue>> call, Throwable t) {
            }
        });
    }


    private void nextActivity(NguoiThue nguoiThue)
    {
        Intent intent = new Intent(getActivity(), RenterDetailActivity.class);
        intent.putExtra("nguoiThue", nguoiThue);
        startActivity(intent);
    }

    private void anhxa(View fragmentLayout){
        edtComment = fragmentLayout.findViewById(R.id.edtComment);
        ic_danhgia = fragmentLayout.findViewById(R.id.ic_danhgia);
        title = fragmentLayout.findViewById(R.id.title_solo);

        //anh xa
        tv_detail_soPhong = fragmentLayout.findViewById(R.id.tv_detail_soPhong);
        tv_detail_gia_my_room = fragmentLayout.findViewById(R.id.tv_detail_gia_my_room);
        tv_detail_so_luong_toi_da = fragmentLayout.findViewById(R.id.tv_detail_so_luong_toi_da);
        tv_detail_loai_phong = fragmentLayout.findViewById(R.id.tv_detail_loai_phong);
        tv_detail_tien_coc = fragmentLayout.findViewById(R.id.tv_detail_tien_coc);
        tv_detail_dien_tich = fragmentLayout.findViewById(R.id.tv_detail_dien_tich);
        tv_detail_gioi_tinh = fragmentLayout.findViewById(R.id.tv_detail_gioi_tinh);
        tv_detail_tien_dien = fragmentLayout.findViewById(R.id.tv_detail_tien_dien);
        tv_detail_tien_nuoc = fragmentLayout.findViewById(R.id.tv_detail_tien_nuoc);
        tv_detail_mo_ta = fragmentLayout.findViewById(R.id.tv_detail_mo_ta);
        tv_detail_dia_chi = fragmentLayout.findViewById(R.id.tv_detail_dia_chi);
        recyclerView = fragmentLayout.findViewById(R.id.rcv_list);
        mViewPager2 = fragmentLayout.findViewById(R.id.view_pager_2);
        btnRoiTro = fragmentLayout.findViewById(R.id.btnRoiTro);

        chuacotro = fragmentLayout.findViewById(R.id.chuacotro);
        thongtin = fragmentLayout.findViewById(R.id.thongtin);
        anh = fragmentLayout.findViewById(R.id.anh);

    }

    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(mes).setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Call<Integer> call = ApiServiceDung.apiServiceDung.xoaphongcuanguoithue(idTaiKhoan,idTaiKhoanNguoiThue);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        alertSuccess("Huỷ Đăng Ký Phòng Thành Công");
                        onResume();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        alertFail("Huỷ Đăng Ký Thất Bại Vui Lòng Liên Hệ Admin");
                    }
                });
            }
        });
        builder.create();
        builder.show();
    }

    private void alertSuccess(String s) {
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Thông báo")
                .setIcon(R.drawable.iconp_check)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void alertFail(String s) {
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Thông báo")
                .setIcon(R.drawable.iconp_fail)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }


    @Override
    public void onResume() {
        LayDuLieu();
        super.onResume();
    }


}
