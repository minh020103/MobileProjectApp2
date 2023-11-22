package com.example.mobileprojectapp2.activity.nguoithue;

import static com.example.mobileprojectapp2.api.Const.MALE_GENDERS;
import static com.example.mobileprojectapp2.api.Const.PHONG_DON;
import static com.example.mobileprojectapp2.api.Const.PHONG_TRONG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.EditProfileActivity;
import com.example.mobileprojectapp2.activity.chutro.ZoomOutPageTransformer;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;

import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodel.PhongNguoiThue;
import com.example.mobileprojectapp2.model.HinhAnh2;
import com.example.mobileprojectapp2.model.PhongTro;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.HinhAnhAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.PhongTroChuTroAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.TienIchAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucNguoiThueAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPhongTroNguoiThueActivity extends AppCompatActivity {


    private TextView tvLoaiPhongNguoiThue, tvGioTinhNguoiThue, tvGiaNguoiThue, tvSoLuongToiDaNguoiThue, tvDienTichNguoiThue,
            tvTienCocNguoiThue, tvTienDienNguoiThue, tvTienNuocNguoiThue, tvQuanNguoiThue, tvDiaChiNguoiThue, tvTienIchRong, tvHinhAnhRong, tvTenChuTro;
    private ReadMoreTextView tvMoTaNguoiThue;
    private TienIchAdapter adapterTienIch;
    private ImageView imageBack, imageViewChuTro;
    private LinearLayoutManager layoutManagerTienIch = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
    private LinearLayoutManager layoutManagerNguoiThue = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
    private RecyclerView rcvListTienIchNguoiThue, rcvListNguoiThue;
    private HinhAnhAdapter adapterHinhAnh;
    private ViewPager2 mViewPager2;
    private List<TienIch> listTienIch;
    private List<HinhAnh> listHinhAnh;
    private List<PhongNguoiThue> listNguoiThue;
    private PhucNguoiThueAdapter adapterNguoiThue;
    private RelativeLayout rlt_tren_dsnt;
    private int idPhong = 2;

    private LinearLayout llXemThem, llThuGon,ll_dsnt;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == listHinhAnh.size() - 1) {
                mViewPager2.setCurrentItem(0);
            } else {
                //            Next sang page tiep theo
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_detail_phong_tro_layout);

        listTienIch = new ArrayList<>();
        listHinhAnh = new ArrayList<>();
        listNguoiThue = new LinkedList<>();

//        Intent intent = getIntent();
//        idPhong = intent.getIntExtra("idPhong", 0);

        anhXa();
        getDataFromApi();
//        getNguoiThue();
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                //Thoi gian chuyen sile
                handler.postDelayed(runnable, 5000);
            }
        });

        mViewPager2.setPageTransformer(new ZoomOutPageTransformer());

        adapterNguoiThue.setMyOnClickListener(new PhongTroChuTroAdapter.MyOnClickListener() {
            @Override
            public void OnClickItem(int position, View v) {
                alertSuccess("Nhắn tin");
            }
        });
    }

    private void getDataFromApi() {
        Call<PhongTro> call = ApiServicePhuc.apiService.getPhongTroByID(2);
        call.enqueue(new Callback<PhongTro>() {
            @Override
            public void onResponse(Call<PhongTro> call, Response<PhongTro> response) {
                tvDienTichNguoiThue.setText(response.body().getDienTich() + "㎡");
                tvQuanNguoiThue.setText(response.body().getIdQuan() + "");
                tvGiaNguoiThue.setText(response.body().getGia() + " / tháng");
                if (response.body().getLoaiPhong() == PHONG_TRONG) {
                    tvLoaiPhongNguoiThue.setText("Phòng trống");
                } else if (response.body().getLoaiPhong() == PHONG_DON) {
                    tvLoaiPhongNguoiThue.setText("Phòng đơn");
                    ll_dsnt.setVisibility(View.GONE);
                    rlt_tren_dsnt.setVisibility(View.GONE);
                } else {
                    tvLoaiPhongNguoiThue.setText("Phòng ghép");
                    ll_dsnt.setVisibility(View.VISIBLE);
                    getNguoiThue();
                    rlt_tren_dsnt.setVisibility(View.VISIBLE);
                }
                tvMoTaNguoiThue.setText(response.body().getMoTa());
                if (response.body().getGioiTinh() == MALE_GENDERS) {
                    tvGioTinhNguoiThue.setText("Nam ♂");
                } else {
                    tvGioTinhNguoiThue.setText("Nữ ♀");
                }
                tvTienDienNguoiThue.setText(response.body().getTienDien() + "k");
                tvTienCocNguoiThue.setText(response.body().getTienCoc() + " ₫");
                tvTienNuocNguoiThue.setText(response.body().getTienNuoc() + "k");
                tvSoLuongToiDaNguoiThue.setText(response.body().getSoLuongToiDa() + " người");
                tvDiaChiNguoiThue.setText(response.body().getDiaChiChiTiet());

//                Log.d("TAG", "onResponse: "+ response.body().getHinhAnhPhongTro().size());

                if (response.body().getHinhAnhPhongTro().size() == 0){
                    tvHinhAnhRong.setVisibility(View.VISIBLE);
                    mViewPager2.setVisibility(View.GONE);
                }
                for (HinhAnh hinhAnh : response.body().getHinhAnhPhongTro()) {
                    listHinhAnh.add(hinhAnh);
                }
                adapterHinhAnh.notifyDataSetChanged();

                if (response.body().getDanhSachTienIch().size() == 0) {
                    tvTienIchRong.setVisibility(View.VISIBLE);
                }
                if (response.body().getDanhSachTienIch().size() < 8) {
                    llXemThem.setVisibility(View.GONE);
                }
                int i = 0;
                for (TienIch tienIch : response.body().getDanhSachTienIch()) {
                    listTienIch.add(tienIch);
                    i++;
                    if (i == 8) {
                        break;
                    }
                }
                adapterTienIch.notifyDataSetChanged();
                llXemThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listTienIch.clear();
                        for (TienIch tienIch : response.body().getDanhSachTienIch()) {
                            listTienIch.add(tienIch);
                        }
                        adapterTienIch.notifyDataSetChanged();
                        llXemThem.setVisibility(View.GONE);
                        llThuGon.setVisibility(View.VISIBLE);
                    }
                });
                llThuGon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listTienIch.clear();
                        int i = 0;
                        for (TienIch tienIch : response.body().getDanhSachTienIch()) {
                            listTienIch.add(tienIch);
                            i++;
                            if (i == 8)
                                break;
                        }
                        adapterTienIch.notifyDataSetChanged();
                        llXemThem.setVisibility(View.VISIBLE);
                        llThuGon.setVisibility(View.GONE);
                    }
                });

                tvTenChuTro.setText(response.body().getPhongTroChuTro().getTen());
                Glide.with(DetailPhongTroNguoiThueActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getPhongTroChuTro().getHinh()).into(imageViewChuTro);


            }
            @Override
            public void onFailure(Call<PhongTro> call, Throwable t) {
                Toast.makeText(DetailPhongTroNguoiThueActivity.this, "Error not call Api", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNguoiThue(){
      Call<List<PhongNguoiThue>> call = ApiServicePhuc2.apiService.getNguoiThueTheoPhong(idPhong);
      call.enqueue(new Callback<List<PhongNguoiThue>>() {
          @Override
          public void onResponse(Call<List<PhongNguoiThue>> call, Response<List<PhongNguoiThue>> response) {
              Log.d("TAG", "onResponse: "+ response.body());
              if (response.code() == 200) {
                  listNguoiThue.clear();
                  listNguoiThue.addAll(response.body());
                  adapterNguoiThue.notifyDataSetChanged();
              }
          }
          @Override
          public void onFailure(Call<List<PhongNguoiThue>> call, Throwable t) {

          }
      });

    }

    private void anhXa() {
        tvDienTichNguoiThue = findViewById(R.id.tv_detail_dien_tich_nguoi_thue);
        tvQuanNguoiThue = findViewById(R.id.tv_detail_quan_nguoi_thue);
        tvMoTaNguoiThue = findViewById(R.id.tv_detail_mo_ta_nguoi_thue);
        tvGiaNguoiThue = findViewById(R.id.tv_detail_gia_nguoi_thue);
        tvLoaiPhongNguoiThue = findViewById(R.id.tv_detail_loai_phong_nguoi_thue);
        tvSoLuongToiDaNguoiThue = findViewById(R.id.tv_detail_so_luong_toi_da_nguoi_thue);
        tvTienCocNguoiThue = findViewById(R.id.tv_detail_tien_coc_nguoi_thue);
        tvTienDienNguoiThue = findViewById(R.id.tv_detail_tien_dien_nguoi_thue);
        tvTienNuocNguoiThue = findViewById(R.id.tv_detail_tien_nuoc_nguoi_thue);
        tvGioTinhNguoiThue = findViewById(R.id.tv_detail_gio_tinh_nguoi_thue);
        tvDiaChiNguoiThue = findViewById(R.id.tv_detail_dia_chi_nguoi_thue);
        rcvListTienIchNguoiThue = findViewById(R.id.rcv_list_tien_ich_nguoi_thue);
        rcvListNguoiThue = findViewById(R.id.rcv_list_nguoi_thue);
        rlt_tren_dsnt = findViewById(R.id.rlt_tren_dsnt);
        imageViewChuTro = findViewById(R.id.imgView_chu_tro);
        tvTenChuTro = findViewById(R.id.tv_ten_chu_tro);

        llThuGon = findViewById(R.id.ll_thu_gon);
        llXemThem = findViewById(R.id.ll_xem_them);
        ll_dsnt = findViewById(R.id.ll_dsnt);
        tvTienIchRong = findViewById(R.id.tv_tien_ich_rong);
        tvHinhAnhRong = findViewById(R.id.tv_hinh_anh_rong);

        mViewPager2 = findViewById(R.id.view_pager_2_nguoi_thue);
        adapterHinhAnh = new HinhAnhAdapter(DetailPhongTroNguoiThueActivity.this, listHinhAnh, R.layout.chutro_item_image_layout);
        mViewPager2.setAdapter(adapterHinhAnh);

        adapterTienIch = new TienIchAdapter(DetailPhongTroNguoiThueActivity.this, listTienIch, R.layout.cardview_item_tien_ich_layout);
        layoutManagerTienIch = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
        layoutManagerTienIch.setOrientation(RecyclerView.HORIZONTAL);
        layoutManagerTienIch = new GridLayoutManager(this, 4);
        rcvListTienIchNguoiThue.setLayoutManager(layoutManagerTienIch);
        rcvListTienIchNguoiThue.setAdapter(adapterTienIch);
        imageBack = findViewById(R.id.img_back_detail);

//        listNguoiThue2 = getListNguoiThue2();
        adapterNguoiThue = new PhucNguoiThueAdapter(DetailPhongTroNguoiThueActivity.this, listNguoiThue, R.layout.nguoithue_cardview_item_nguoi_thue_layout);
        layoutManagerNguoiThue = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
        layoutManagerNguoiThue.setOrientation(RecyclerView.VERTICAL);
        rcvListNguoiThue.setLayoutManager(layoutManagerNguoiThue);
        rcvListNguoiThue.setAdapter(adapterNguoiThue);
    }

    private List<HinhAnh2> getListPhoto() {
        List<HinhAnh2> list = new ArrayList<>();
        list.add(new HinhAnh2(R.drawable.anhdaidien));
        list.add(new HinhAnh2(R.drawable.cccd));
        list.add(new HinhAnh2(R.drawable.anhdaidien));

        return list;
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