package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.model.HinhAnh2;
import com.example.mobileprojectapp2.model.PhongTro;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.HinhAnhAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.TienIchAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPhongTro extends AppCompatActivity {

    private static final int PHONG_TRONG = 0;
    private static final int PHONG_DON = 1;
    private static final int PHONG_GHEP = 2;
    private static final int NAM = 1;
    private static final int NU = 2;

    private TextView tvLoaiPhong, tvGioTinh, tvGia, tvSoLuongToiDa, tvDienTich,
            tvTienCoc, tvTienDien, tvTienNuoc, tvQuan, tvDiaChi;
    private ReadMoreTextView tvMoTa;
    private List<TienIch> listTienIch;
    private TienIchAdapter adapterTienIch;
    private ImageView imageBack;
    private LinearLayoutManager layoutManagerTienIch;
    private RecyclerView rcvListTienIch;
    private HinhAnhAdapter adapterHinhAnh;
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<HinhAnh> listHinhAnh;
    private int idPhong;

    private LinearLayout llXemThem, llThuGon;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == listHinhAnh.size() - 1){
                mViewPager2.setCurrentItem(0);
            }else {
                //            Next sang page tiep theo
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem()+ 1);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_phong_tro);

        listTienIch = new ArrayList<>();
        listHinhAnh = new ArrayList<>();

        Intent intent = getIntent();
        idPhong = intent.getIntExtra("idPhong",0);

        anhXa();
        getDataFromApi();
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
    }
    private void getDataFromApi() {
        Call<PhongTro> call = ApiServicePhuc.apiService.getPhongTroByID(idPhong);
        call.enqueue(new Callback<PhongTro>() {
            @Override
            public void onResponse(Call<PhongTro> call, Response<PhongTro> response) {
                tvDienTich.setText(response.body().getDienTich() + "㎡");
                tvQuan.setText(response.body().getIdQuan() + "");
                tvGia.setText(response.body().getGia() + " / tháng");
                if (response.body().getLoaiPhong() == PHONG_TRONG) {
                    tvLoaiPhong.setText("Phòng trống");
                } else if (response.body().getLoaiPhong() == PHONG_DON) {
                    tvLoaiPhong.setText("Phòng đơn");
                } else {
                    tvLoaiPhong.setText("Phòng ghép");
                }
                tvMoTa.setText(response.body().getMoTa());
                if (response.body().getGioiTinh() == NAM) {
                    tvGioTinh.setText("Nam ♂");
                } else {
                    tvGioTinh.setText("Nữ ♀");
                }
                tvTienDien.setText(response.body().getTienDien() + "k");
                tvTienCoc.setText(response.body().getTienCoc() + " ₫");
                tvTienNuoc.setText(response.body().getTienNuoc() + "k");
                tvSoLuongToiDa.setText(response.body().getSoLuongToiDa() + " người");
                tvDiaChi.setText(response.body().getDiaChiChiTiet());

                for (HinhAnh hinhAnh : response.body().getHinhAnhPhongTro()){
                    listHinhAnh.add((hinhAnh));
                }
                adapterHinhAnh.notifyDataSetChanged();

                int i = 0;
                for (TienIch tienIch : response.body().getDanhSachTienIch() ){
                    listTienIch.add(tienIch);
                    i++;
                    if (i == 8)
                        break;
                }
                adapterTienIch.notifyDataSetChanged();

                llXemThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listTienIch.clear();
                        for (TienIch tienIch : response.body().getDanhSachTienIch() ) {
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
                        for (TienIch tienIch : response.body().getDanhSachTienIch() ){
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
            }

            @Override
            public void onFailure(Call<PhongTro> call, Throwable t) {
                Toast.makeText(DetailPhongTro.this, "Error not call Api", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa() {

        mViewPager2 = findViewById(R.id.view_pager_2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator_3);
        adapterHinhAnh = new HinhAnhAdapter(DetailPhongTro.this, listHinhAnh, R.layout.chutro_item_image_layout);
        mViewPager2.setAdapter(adapterHinhAnh);
        mCircleIndicator3.setViewPager(mViewPager2);


        tvDienTich = findViewById(R.id.tv_detail_dien_tich);
        tvQuan = findViewById(R.id.tv_detail_quan);
        tvMoTa = findViewById(R.id.tv_detail_mo_ta);
        tvGia = findViewById(R.id.tv_detail_gia);
        tvLoaiPhong = findViewById(R.id.tv_detail_loai_phong);
        tvSoLuongToiDa = findViewById(R.id.tv_detail_so_luong_toi_da);
        tvTienCoc = findViewById(R.id.tv_detail_tien_coc);
        tvTienDien = findViewById(R.id.tv_detail_tien_dien);
        tvTienNuoc = findViewById(R.id.tv_detail_tien_nuoc);
        tvGioTinh = findViewById(R.id.tv_detail_gio_tinh);
        tvDiaChi = findViewById(R.id.tv_detail_dia_chi);
        rcvListTienIch = findViewById(R.id.rcv_list_tien_ich);
        llThuGon = findViewById(R.id.ll_thu_gon);
        llXemThem = findViewById(R.id.ll_xem_them);



        adapterTienIch = new TienIchAdapter(DetailPhongTro.this, listTienIch, R.layout.cardview_item_tien_ich_layout);
        layoutManagerTienIch = new LinearLayoutManager(DetailPhongTro.this);
        layoutManagerTienIch.setOrientation(RecyclerView.HORIZONTAL);
        layoutManagerTienIch = new GridLayoutManager(this, 4);
//        layoutManagerTienIch.setOrientation(RecyclerView.VERTICAL);
        rcvListTienIch.setLayoutManager(layoutManagerTienIch);
        rcvListTienIch.setAdapter(adapterTienIch);
        imageBack = findViewById(R.id.img_back_detail);

    }


    private List<HinhAnh2> getListPhoto() {
        List<HinhAnh2> list = new ArrayList<>();
        list.add(new HinhAnh2(R.drawable.anhdaidien));
        list.add(new HinhAnh2(R.drawable.cccd));
        list.add(new HinhAnh2(R.drawable.anhdaidien));

        return list;
    }

}