package com.example.mobileprojectapp2.fragment.nguoithue;

import static com.example.mobileprojectapp2.api.Const.MALE_GENDERS;
import static com.example.mobileprojectapp2.api.Const.PHONG_DON;
import static com.example.mobileprojectapp2.api.Const.PHONG_TRONG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    tv_quan,tv_phuong,tv_detail_dien_tich,tv_detail_gioi_tinh,tv_detail_tien_dien,tv_detail_tien_nuoc,tv_detail_mo_ta,
            tv_detail_dia_chi ;
    LinearLayout edtComment, ic_danhgia;
    TextView title;
    LinearLayoutManager layoutManager;

    private List<HinhAnh> listHinhAnh;
    private HinhAnhAdapter adapterHinhAnh;

    LinkedList<PhongBinhLuan> list;
    private SharedPreferences shared;
    private int idTaiKhoan;
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
        edtComment = fragmentLayout.findViewById(R.id.edtComment);
        ic_danhgia = fragmentLayout.findViewById(R.id.ic_danhgia);
        title = fragmentLayout.findViewById(R.id.title_solo);

        //anh xa
        tv_detail_soPhong = fragmentLayout.findViewById(R.id.tv_detail_soPhong);
        tv_detail_gia_my_room = fragmentLayout.findViewById(R.id.tv_detail_gia_my_room);
        tv_detail_so_luong_toi_da = fragmentLayout.findViewById(R.id.tv_detail_so_luong_toi_da);
        tv_detail_loai_phong = fragmentLayout.findViewById(R.id.tv_detail_loai_phong);
        tv_detail_tien_coc = fragmentLayout.findViewById(R.id.tv_detail_tien_coc);
        tv_quan = fragmentLayout.findViewById(R.id.tv_quan);
        tv_phuong = fragmentLayout.findViewById(R.id.tv_phuong);
        tv_detail_dien_tich = fragmentLayout.findViewById(R.id.tv_detail_dien_tich);
        tv_detail_gioi_tinh = fragmentLayout.findViewById(R.id.tv_detail_gioi_tinh);
        tv_detail_tien_dien = fragmentLayout.findViewById(R.id.tv_detail_tien_dien);
        tv_detail_tien_nuoc = fragmentLayout.findViewById(R.id.tv_detail_tien_nuoc);
        tv_detail_mo_ta = fragmentLayout.findViewById(R.id.tv_detail_mo_ta);
        tv_detail_dia_chi = fragmentLayout.findViewById(R.id.tv_detail_dia_chi);


        recyclerView = fragmentLayout.findViewById(R.id.rcv_list_tien_ich);
        listHinhAnh = new ArrayList<>();
        mViewPager2 = fragmentLayout.findViewById(R.id.view_pager_2);
        adapterHinhAnh = new HinhAnhAdapter(getActivity(), listHinhAnh, R.layout.chutro_item_image_layout);
        mViewPager2.setAdapter(adapterHinhAnh);

        layoutManager = new LinearLayoutManager(getActivity());
        nguoithueList = new ArrayList<>();
        phongNguoiThueAdapter = new PhongNguoiThueAdapter(getActivity(), nguoithueList, R.layout.cardview_danh_sach_nguoi_thue_my_room);
        listNguoiThueTheoIdPhong(2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(phongNguoiThueAdapter);

        getDetailPhongApi();
        getDataFromApi();

//        shared = getActivity().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
//        idTaiKhoan = shared.getInt("idTaiKhoan", -1);

        list = new LinkedList<>();
        edtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MComponent.comment(getActivity(), container, 1,list ,1 );
            }
        });

        ic_danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MComponent.rating(getActivity(),1,1);
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

    private void getDataFromApi() {
        Call<PhongTro> call = ApiServicePhuc.apiService.getPhongTroByID(2);
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

                tv_quan.setText(response.body().getIdQuan()+"");

                tv_phuong.setText(response.body().getIdPhuong()+"");

                tv_detail_dien_tich.setText(response.body().getDienTich() + "㎡");

                if (response.body().getGioiTinh() == MALE_GENDERS) {
                    tv_detail_gioi_tinh.setText("Nam ♂");
                } else {
                    tv_detail_gioi_tinh.setText("Nữ ♀");
                }

                tv_detail_tien_dien.setText(response.body().getTienDien()+"");

                tv_detail_tien_nuoc.setText(response.body().getTienNuoc()+"");

                tv_detail_mo_ta.setText(response.body().getMoTa()+"");

                tv_detail_dia_chi.setText(response.body().getDiaChiChiTiet()+"");

//                Call<Phuong> call1 = ApiServiceDung.apiServiceDung.layPhuongTheoIDAPI(response.body().getIdPhuong());
//                call1.enqueue(new Callback<Phuong>() {
//                    @Override
//                    public void onResponse(Call<Phuong> call, Response<Phuong> response) {
//                        Log.d("TAG", "onResponse: "+ response.body().getTenPhuong());
//                        tv_phuong.setText(response.body().getTenPhuong());
//                    }
//                    @Override
//                    public void onFailure(Call<Phuong> call, Throwable t) {
//                        Toast.makeText(getActivity(), "Error not call Api", Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
            @Override
            public void onFailure(Call<PhongTro> call, Throwable t) {
                Toast.makeText(getActivity(), "Error not call Api", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getDetailPhongApi() {
        Call<PhongTro> call = ApiServicePhuc.apiService.getPhongTroByID(2);
        call.enqueue(new Callback<PhongTro>() {
            @Override
            public void onResponse(Call<PhongTro> call, Response<PhongTro> response) {
                for (HinhAnh hinhAnh : response.body().getHinhAnhPhongTro()) {
                    listHinhAnh.add(hinhAnh);

                }
                Log.d("TAG", "onResponse: "+ response.body().getHinhAnhPhongTro().size());

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

    @Override
    public void onResume() {
        super.onResume();
    }


}
