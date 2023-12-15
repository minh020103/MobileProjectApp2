package com.example.mobileprojectapp2.activity.nguoithue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.model.PhongTro;
import com.example.mobileprojectapp2.model.Quan;
import com.example.mobileprojectapp2.model.Selected;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.SelectedAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.TienIchAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucDanhSachPhongTheoQuanAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucDanhSachPhongTimKiemAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucGioiTinhAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucLoaiPhongAdapter;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBoLocActivity extends AppCompatActivity {
    private Button btnTienIch, btnLoaiPhong, btnGia, btnSoNguoi;
    // Sửa màu cho phòng
    LinearLayout bgLoaiPhongOld = null;
    Drawable backDrawableLoaiPhong = null;
    private List<TienIch> listTienIch;
    private List<Integer> listLoaiPhong;
    private List<Integer> listGioiTinh;
    private List<PhongTro> listPhongTimKiem;
    private List<com.example.mobileprojectapp2.datamodel.PhongTro> listPhongTheoQuan;

    //List luu data api
    private List<TienIch> listTienIchSeleted;
    private int idLoaiPhong = -1;
    private int idGioiTinh = -1;

    //List nguoi dung
    private volatile List<Selected> listSelected;
    private ImageView imgDownTienIch, imgUpTienIch, imgClear, img_loai_phong_down, img_loai_phong_up,
            img_gia_down, img_gia_up, img_gioi_tinh_down, img_gioi_tinh_up;
    private RecyclerView rcvListTienIch, rcvListSelected, rcvListLoaiPhong,
            rcvListGioiTinh, rcvListPhongTimKiem, rcv_list_phong_theo_quan;

    private TienIchAdapter adapterTienIch;
    private PhucLoaiPhongAdapter adapterLoaiPhong;
    private PhucGioiTinhAdapter adapterGioiTinh;
    private SelectedAdapter adapterSelected;
    private PhucDanhSachPhongTheoQuanAdapter adapterPhongTroTheoQuan;
    private PhucDanhSachPhongTimKiemAdapter adapterPhongTimKiem;
    private LinearLayoutManager layoutManagerTienIch, layoutManagerSelected, layoutManagerLoaiPhong,
            layoutManagerGioiTinh, layoutManagerTimKiem, layoutManagerPhongTheoQuan;
    private LinearLayout ll_list_Selected, llSearchBoLoc, ll_so_nguoi_gioi_tinh,
            ll_ap_dung, ll_khong_tim_thay, ll_chua_co_phong_nao, ll_list_phong_theo_quan;
    private TextView tv_quan, tv_huy, tv_gia_start, tv_gia_end;
    private LinearLayout llGiaSeekBar;
    private int flagTienIch = 0;
    private int flagLoaiPhong = 0;
    private int flagGia = 0;
    private int flagSoNguoi = 0;
    private int id;
    private Button btn_ap_dung;
    private RangeSlider range_slider;

    private float minValue = 0.0f, maxValue = 5000.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_search_bo_loc_activity);

        _Initialization();

        Intent intent = getIntent();
        id = intent.getIntExtra("idPhong", -1);

        anhXa();
        onClickButton();
        onClickItemAdapter();
        notShowListSelected();
        getQuanById();
        onChangePrice();

        rcvListLoaiPhong.setVisibility(View.GONE);
        llGiaSeekBar.setVisibility(View.GONE);
        ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);
        ll_ap_dung.setVisibility(View.GONE);
        getQuanById();
        int max = (int) (maxValue * 1000);
        tv_gia_end.setText(String.valueOf(max / 1000000) + " triệu");
    }

    private void _Initialization() {
        listTienIch = new ArrayList<>();
        listTienIchSeleted = new ArrayList<>();
        synchronized (Selected.class) {
            listSelected = new ArrayList<>();
        }
        listLoaiPhong = new ArrayList<>();
        listGioiTinh = new ArrayList<>();
        listPhongTimKiem = new ArrayList<>();
        listPhongTheoQuan = new ArrayList<>();
    }

    private void notShowListSelected() {
        if (listSelected.size() == 0) {
            ll_list_Selected.setVisibility(View.GONE);
        }
    }

    private void onClickItemAdapter() {
        adapterSelected.setMyOnCLickListener(new SelectedAdapter.MyOnCLickListener() {
            @Override
            public void OnClickImg(int position, View v) {
                OnCLickCloseItem(position, v);
            }

            @Override
            public void OnCLickCloseItem(int position, View v) {
                if (listSelected.get(position).getKey() == Const.TIEN_ICH) {
                    listTienIchSeleted.remove(listTienIch.get(listSelected.get(position).getId()));
                    ll_ap_dung.setVisibility(View.VISIBLE);
                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                    Log.d("TAG", "onClickListData: " + listTienIchSeleted.size());
                    Log.d("TAG", "onClickTenTienIchNguoiDungThay: " + listSelected.get(position).getName());

                } else if (listSelected.get(position).getKey() == Const.LOAI_PHONG) {
                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                    Log.d("TAG", "onClickListApi: " + listLoaiPhong.size());
                    ll_ap_dung.setVisibility(View.VISIBLE);
                    idLoaiPhong = -1;

                } else if (listSelected.get(position).getKey() == Const.GIOI_TINH) {
                    ll_ap_dung.setVisibility(View.VISIBLE);
                    idGioiTinh = -1;
                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                } else if (listSelected.get(position).getKey() == Const.GIA) {
                    //Todo
                    minValue = 500000;
                    maxValue = 5000000;
                    range_slider.setValues(0f, 5000f);
                }
                Log.d("TAG", "OnCLickCloseItem: " + minValue);
                Log.d("TAG", "OnCLickCloseItem: " + maxValue);
                listSelected.remove(listSelected.get(position));
                adapterSelected.notifyDataSetChanged();
                notShowListSelected();
            }
        });
        adapterTienIch.setOnClick(new TienIchAdapter.OnClick() {
            @Override
            public void onClickItemListener(int position, View view) {
                onClickListener.onClick(view);
                Animation anim = AnimationUtils.loadAnimation(SearchBoLocActivity.this, R.anim.item_click);
                view.startAnimation(anim);
                if (!listTienIchSeleted.contains(listTienIch.get(position))) {
                    listSelected.add(new Selected(Const.TIEN_ICH, position, listTienIch.get(position).getTen()));
                    //add vao list luu du lieu
                    listTienIchSeleted.add(listTienIch.get(position));

                    int itemCount = adapterSelected.getItemCount();
                    if (itemCount > 0) {
                        rcvListSelected.scrollToPosition(itemCount - 1);
                    }
                    adapterSelected.notifyDataSetChanged();
                    ll_list_Selected.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(SearchBoLocActivity.this, "Đã có", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterLoaiPhong.setOnClick(new PhucLoaiPhongAdapter.OnClick() {
            @Override
            public void onClickItemListener(int position, View view, LinearLayout bg) {
                Animation anim = AnimationUtils.loadAnimation(SearchBoLocActivity.this, R.anim.item_click);
                view.startAnimation(anim);
                if (idLoaiPhong != listLoaiPhong.get(position)) {
                    if (!listSelected.contains(new Selected(Const.LOAI_PHONG, listLoaiPhong.get(position),
                            listLoaiPhong.get(position) == Const.PHONG_GHEP ? "Phòng đơn" : "Phòng ghép"))) {
                        //Xóa cái hiện tại
                        Log.d("TAG", "onClickItemListener: " + listSelected.size());
                        for (int i = 0; i < listSelected.size(); i++) {
                            Selected selected = listSelected.get(i);
                            if (selected.getKey() == Const.LOAI_PHONG) {
                                listSelected.remove(i);
                                adapterSelected.notifyDataSetChanged();
                            }
                        }
//                        bgLoaiPhongOld = bg;
//                        backDrawableLoaiPhong = bg.getBackground();
//                        bg.setBackground(getResources().getDrawable(R.drawable.btn_p4, getTheme()));
                        //add moi vao list nguoi dungint itemCount = adapterSelected.getItemCount();

                        listSelected.add(new Selected(Const.LOAI_PHONG, position, listLoaiPhong.get(position) == Const.PHONG_TRONG ? "Phòng trống" : listLoaiPhong.get(position) == Const.PHONG_DON ? "Phòng đơn" : "Phòng ghép"));
                        //add moi vao list luu du lieu
                        idLoaiPhong = listLoaiPhong.get(position);
                        int itemCount = adapterSelected.getItemCount();
                        if (itemCount > 0) {
                            rcvListSelected.scrollToPosition(itemCount - 1);
                        }
                        adapterSelected.notifyDataSetChanged();
                        ll_list_Selected.setVisibility(View.VISIBLE);

                    }
                } else {
                    Toast.makeText(SearchBoLocActivity.this, "Đã có", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterGioiTinh.setOnClick(new PhucGioiTinhAdapter.OnClick() {
            @Override
            public void onClickItemListener(int position, View view) {
                Animation anim = AnimationUtils.loadAnimation(SearchBoLocActivity.this, R.anim.item_click);
                view.startAnimation(anim);
                if (idGioiTinh != listGioiTinh.get(position)) {
                    if (!listSelected.contains(new Selected(Const.GIOI_TINH, listGioiTinh.get(position), listGioiTinh.get(position) == Const.ALL_GENDERS ? "Nam/Nữ" : listGioiTinh.get(position) == Const.MALE_GENDERS ? "Nam" : "Nữ"))) {
                        //Xóa cái hiện tại
                        for (int i = 0; i < listSelected.size(); i++) {
                            Selected selected = listSelected.get(i);
                            if (selected.getKey() == Const.GIOI_TINH) {
                                listSelected.remove(i);
                                adapterSelected.notifyDataSetChanged();
                            }
                        }
                        //add moi vao list nguoi dung
                        listSelected.add(new Selected(Const.GIOI_TINH, listGioiTinh.get(position), listGioiTinh.get(position) == Const.ALL_GENDERS ? "Nam/Nữ" : listGioiTinh.get(position) == Const.MALE_GENDERS ? "Nam" : "Nữ"));
                        //add moi vao list luu du lieu
                        idGioiTinh = listGioiTinh.get(position);
                        int itemCount = adapterSelected.getItemCount();
                        if (itemCount > 0) {
                            rcvListSelected.scrollToPosition(itemCount - 1);
                        }
                        adapterSelected.notifyDataSetChanged();
                        ll_list_Selected.setVisibility(View.VISIBLE);

                        Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                        Log.d("TAG", "onClickListData: " + idGioiTinh);
                    }
                } else {
                    Toast.makeText(SearchBoLocActivity.this, "Đã có", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterPhongTimKiem.setMyOnCLickListener(new PhucDanhSachPhongTimKiemAdapter.MyOnCLickListener() {
            @Override
            public void OnClickItem(int position, View v) {
                Intent intent = new Intent(SearchBoLocActivity.this, DetailPhongTroNguoiThueActivity.class);
                intent.putExtra("idPhong", listPhongTimKiem.get(position).getId());
                startActivity(intent);
            }
        });
        adapterPhongTroTheoQuan.setMyOnCLickListener(new PhucDanhSachPhongTheoQuanAdapter.MyOnCLickListener() {
            @Override
            public void OnClickItem(int position, View v) {
                Intent intent = new Intent(SearchBoLocActivity.this, DetailPhongTroNguoiThueActivity.class);
                intent.putExtra("idPhong", listPhongTheoQuan.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void onChangePrice() {
        range_slider.setValues(0f, 5000f);

        range_slider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                Animation anim = AnimationUtils.loadAnimation(SearchBoLocActivity.this, R.anim.item_click);
                slider.startAnimation(anim);
                // Lấy giá trị tối thiểu và tối đa hiện tại từ RangeSlider
                minValue = slider.getValues().get(0);
                maxValue = slider.getValues().get(1);

                float min = minValue;
                float max = maxValue;

                if (minValue < 1000) {
                    tv_gia_start.setText(String.valueOf(min) + " k");
                    max = max / 1000;
                    tv_gia_end.setText(String.valueOf(max) + " triệu");
                } else {
                    min = minValue / 1000;
                    max = maxValue / 1000;
                    tv_gia_start.setText(String.valueOf(min) + " triệu");
                    tv_gia_end.setText(String.valueOf(max) + " triệu");
                }

//                if (idGioiTinh != listGioiTinh.get(position)) {
//                    if (!listSelected.contains(new Selected(Const.GIOI_TINH, listGioiTinh.get(position), listGioiTinh.get(position) == Const.ALL_GENDERS ? "Nam/Nữ" : listGioiTinh.get(position) == Const.MALE_GENDERS ? "Nam" : "Nữ"))) {


                for (int i = 0; i < listSelected.size(); i++) {
                    Selected selected = listSelected.get(i);
                    if (selected.getKey() == Const.GIA) {
                        listSelected.remove(i);
                        adapterSelected.notifyDataSetChanged();
                    }
                }
                ll_list_Selected.setVisibility(View.VISIBLE);
                listSelected.add(new Selected(Const.GIA, 0, minValue < 1000 ?
                        min + " k VND" + " - " + max + " triệu" :
                        min + " triệu VND" + " - " + max + " triệu"));

                adapterSelected.notifyDataSetChanged();
            }
        });
    }

    private void flag0() {
        flagGia = 0;
        flagSoNguoi = 0;
        flagLoaiPhong = 0;
        flagTienIch = 0;
    }

    private void onClickButton() {
        btnTienIch.setOnClickListener(onClickListener);
        btnLoaiPhong.setOnClickListener(onClickListener);
        btnGia.setOnClickListener(onClickListener);
        btnSoNguoi.setOnClickListener(onClickListener);
        btn_ap_dung.setOnClickListener(onClickListener);

        imgClear.setOnClickListener(onClickListener);
        tv_huy.setOnClickListener(onClickListener);
        llSearchBoLoc.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_tien_ich:

                    switch (flagTienIch) {
                        case 0:
                            //Bat
                            btnLoaiPhong.setTextColor(getResources().getColor(R.color.black));
                            btnGia.setTextColor(getResources().getColor(R.color.black));
                            btnTienIch.setTextColor(getResources().getColor(R.color.main_color_app_light));
                            btnSoNguoi.setTextColor(getResources().getColor(R.color.black));
                            Log.d("TAG", "listNguoiDung: " + listSelected.size());
                            Log.d("TAG", "listSoNguoi: " + listTienIchSeleted.size());
                            imgDownTienIch.setVisibility(View.GONE);
                            imgUpTienIch.setVisibility(View.VISIBLE);
                            flagTienIch = 1;
                            getListTienIch();
                            rcvListTienIch.setVisibility(View.VISIBLE);
                            ll_ap_dung.setVisibility(View.VISIBLE);

                            flagLoaiPhong = 0;
                            flagGia = 0;
                            flagSoNguoi = 0;

                            img_loai_phong_up.setVisibility(View.GONE);
                            img_loai_phong_down.setVisibility(View.VISIBLE);
                            img_gia_up.setVisibility(View.GONE);
                            img_gia_down.setVisibility(View.VISIBLE);
                            img_gioi_tinh_up.setVisibility(View.GONE);
                            img_gioi_tinh_down.setVisibility(View.VISIBLE);

                            rcvListLoaiPhong.setVisibility(View.GONE);
                            llGiaSeekBar.setVisibility(View.GONE);
                            ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);
                            ll_khong_tim_thay.setVisibility(View.GONE);
                            rcvListPhongTimKiem.setVisibility(View.GONE);
                            ll_list_phong_theo_quan.setVisibility(View.GONE);
                            break;
                        case 1:
                            //Tat
                            btnTienIch.setTextColor(getResources().getColor(R.color.black));
                            imgDownTienIch.setVisibility(View.VISIBLE);
                            imgUpTienIch.setVisibility(View.GONE);
                            flagTienIch = 0;
                            rcvListTienIch.setVisibility(View.GONE);
                            ll_ap_dung.setVisibility(View.GONE);
                            ll_list_phong_theo_quan.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;

                case R.id.btn_loai_phong:

                    switch (flagLoaiPhong) {
                        case 0:
                            btnLoaiPhong.setTextColor(getResources().getColor(R.color.main_color_app_light));
                            btnGia.setTextColor(getResources().getColor(R.color.black));
                            btnTienIch.setTextColor(getResources().getColor(R.color.black));
                            btnSoNguoi.setTextColor(getResources().getColor(R.color.black));
                            //Bat
                            img_loai_phong_down.setVisibility(View.GONE);
                            img_loai_phong_up.setVisibility(View.VISIBLE);
                            flagLoaiPhong = 1;
                            getListLoaiPhong();
                            rcvListLoaiPhong.setVisibility(View.VISIBLE);
                            ll_ap_dung.setVisibility(View.VISIBLE);

                            flagTienIch = 0;
                            flagGia = 0;
                            flagSoNguoi = 0;

                            imgUpTienIch.setVisibility(View.GONE);
                            imgDownTienIch.setVisibility(View.VISIBLE);
                            img_gia_up.setVisibility(View.GONE);
                            img_gia_down.setVisibility(View.VISIBLE);
                            img_gioi_tinh_up.setVisibility(View.GONE);
                            img_gioi_tinh_down.setVisibility(View.VISIBLE);

                            rcvListTienIch.setVisibility(View.GONE);
                            llGiaSeekBar.setVisibility(View.GONE);
                            ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);
                            ll_khong_tim_thay.setVisibility(View.GONE);
                            rcvListPhongTimKiem.setVisibility(View.GONE);
                            ll_list_phong_theo_quan.setVisibility(View.GONE);
                            break;
                        case 1:
                            //Tat
                            btnLoaiPhong.setTextColor(getResources().getColor(R.color.black));
                            img_loai_phong_down.setVisibility(View.VISIBLE);
                            img_loai_phong_up.setVisibility(View.GONE);
                            flagLoaiPhong = 0;
                            rcvListLoaiPhong.setVisibility(View.GONE);
                            ll_ap_dung.setVisibility(View.GONE);
                            ll_list_phong_theo_quan.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;

                case R.id.btn_gia:

                    switch (flagGia) {
                        //Bat
                        case 0:
                            btnLoaiPhong.setTextColor(getResources().getColor(R.color.black));
                            btnGia.setTextColor(getResources().getColor(R.color.main_color_app_light));
                            btnTienIch.setTextColor(getResources().getColor(R.color.black));
                            btnSoNguoi.setTextColor(getResources().getColor(R.color.black));
                            img_gia_down.setVisibility(View.GONE);
                            img_gia_up.setVisibility(View.VISIBLE);
                            llGiaSeekBar.setVisibility(View.VISIBLE);
                            flagGia = 1;

                            flagTienIch = 0;
                            flagLoaiPhong = 0;
                            flagSoNguoi = 0;

                            imgUpTienIch.setVisibility(View.GONE);
                            imgDownTienIch.setVisibility(View.VISIBLE);
                            img_loai_phong_up.setVisibility(View.GONE);
                            img_loai_phong_down.setVisibility(View.VISIBLE);
                            img_gioi_tinh_up.setVisibility(View.GONE);
                            img_gioi_tinh_down.setVisibility(View.VISIBLE);

                            rcvListLoaiPhong.setVisibility(View.GONE);
                            rcvListTienIch.setVisibility(View.GONE);
                            ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);
                            ll_ap_dung.setVisibility(View.VISIBLE);
                            ll_khong_tim_thay.setVisibility(View.GONE);
                            rcvListPhongTimKiem.setVisibility(View.GONE);
                            ll_list_phong_theo_quan.setVisibility(View.GONE);
                            break;
                        case 1:
                            btnGia.setTextColor(getResources().getColor(R.color.black));
                            img_gia_down.setVisibility(View.VISIBLE);
                            img_gia_up.setVisibility(View.GONE);
                            llGiaSeekBar.setVisibility(View.GONE);
                            ll_ap_dung.setVisibility(View.GONE);
                            flagGia = 0;
                            ll_list_phong_theo_quan.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;

                case R.id.btn_so_nguoi:

                    switch (flagSoNguoi) {
                        case 0:
                            btnLoaiPhong.setTextColor(getResources().getColor(R.color.black));
                            btnGia.setTextColor(getResources().getColor(R.color.black));
                            btnTienIch.setTextColor(getResources().getColor(R.color.black));
                            btnSoNguoi.setTextColor(getResources().getColor(R.color.main_color_app_light));
                            img_gioi_tinh_up.setVisibility(View.VISIBLE);
                            img_gioi_tinh_down.setVisibility(View.GONE);
                            flagSoNguoi = 1;
                            getListGioiTinh();
                            ll_so_nguoi_gioi_tinh.setVisibility(View.VISIBLE);
                            ll_ap_dung.setVisibility(View.VISIBLE);
                            flagLoaiPhong = 0;
                            flagTienIch = 0;
                            flagGia = 0;

                            img_loai_phong_up.setVisibility(View.GONE);
                            img_loai_phong_down.setVisibility(View.VISIBLE);
                            imgUpTienIch.setVisibility(View.GONE);
                            imgDownTienIch.setVisibility(View.VISIBLE);
                            img_gia_down.setVisibility(View.VISIBLE);
                            img_gia_up.setVisibility(View.GONE);

                            rcvListLoaiPhong.setVisibility(View.GONE);
                            rcvListTienIch.setVisibility(View.GONE);
                            llGiaSeekBar.setVisibility(View.GONE);
                            ll_khong_tim_thay.setVisibility(View.GONE);
                            rcvListPhongTimKiem.setVisibility(View.GONE);
                            Log.d("TAG", "onClick: " + idGioiTinh);
                            ll_list_phong_theo_quan.setVisibility(View.GONE);
                            break;
                        case 1:
                            btnSoNguoi.setTextColor(getResources().getColor(R.color.black));
                            img_gioi_tinh_down.setVisibility(View.VISIBLE);
                            img_gioi_tinh_up.setVisibility(View.GONE);
                            flagSoNguoi = 0;
                            ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);
                            ll_ap_dung.setVisibility(View.GONE);
                            ll_list_phong_theo_quan.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                    break;
                case R.id.img_clear:
                    listTienIchSeleted.clear();
                    idLoaiPhong = -1;
                    idGioiTinh = -1;
                    minValue = 0;
                    maxValue = 0;
                    ll_khong_tim_thay.setVisibility(View.GONE);
                    range_slider.setValues(0f, 5000f);
                    listSelected.clear();
                    notShowListSelected();
                    offAll();
                    getQuanById();
                    break;

                case R.id.tv_huy:
                    finish();
                    break;
                case R.id.ll_search_bo_loc:
                    Intent intent = new Intent(SearchBoLocActivity.this, SearchQuanActivity.class);
                    startActivity(intent);
                    break;

                default:
                    break;
            }
        }
    };

    private void offAll() {
        img_gia_down.setVisibility(View.VISIBLE);
        img_gia_up.setVisibility(View.GONE);
        imgDownTienIch.setVisibility(View.VISIBLE);
        imgUpTienIch.setVisibility(View.GONE);
        img_loai_phong_down.setVisibility(View.VISIBLE);
        img_loai_phong_up.setVisibility(View.GONE);
        img_gioi_tinh_down.setVisibility(View.VISIBLE);
        img_gioi_tinh_up.setVisibility(View.GONE);
        flagGia = 0;
        flagSoNguoi = 0;
        flagTienIch = 0;
        flagLoaiPhong = 0;
        rcvListTienIch.setVisibility(View.GONE);
        llGiaSeekBar.setVisibility(View.GONE);
        rcvListLoaiPhong.setVisibility(View.GONE);
        ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);
    }

    private void getListTienIch() {
        Call<List<TienIch>> call = ApiServicePhuc2.apiService.getAllListTienIch();
        call.enqueue(new Callback<List<TienIch>>() {
            @Override
            public void onResponse(Call<List<TienIch>> call, Response<List<TienIch>> response) {
                if (response.code() == 200) {
                    listTienIch.clear();
                    listTienIch.addAll(response.body());
                    adapterTienIch.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<TienIch>> call, Throwable t) {
                alertFail(getString(R.string.error_unable_to_call_api));

            }
        });
    }

    private void getQuanById() {
        Call<Quan> call = ApiServicePhuc2.apiService.getQuanById(id);
        call.enqueue(new Callback<Quan>() {
            @Override
            public void onResponse(Call<Quan> call, Response<Quan> response) {
                tv_quan.setText(response.body().getTenQuan());
                useIdQuan(response.body().getId());
                getDanhSachPhongTroTheoQuan(response.body().getId());
            }

            @Override
            public void onFailure(Call<Quan> call, Throwable t) {
                alertFail(getString(R.string.error_unable_to_call_api));

            }
        });
    }

    public void useIdQuan(int id) {
        btn_ap_dung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offAll();
                Log.d("TAG", "onClickL: " + listSelected.size());
                if (listSelected.size() == 0) {
                    getQuanById();
                    ll_list_phong_theo_quan.setVisibility(View.VISIBLE);
                } else {
                    ll_list_phong_theo_quan.setVisibility(View.GONE);
                    ll_list_Selected.setVisibility(View.GONE);
                    rcvListSelected.setVisibility(View.VISIBLE);
                    ll_list_Selected.setVisibility(View.VISIBLE);
                    adapterSelected.notifyDataSetChanged();

                    Call<List<PhongTro>> call = ApiServicePhuc2.apiService.getDanhSachPhongTimKiemBoLoc(id,
                            (int) minValue * 1000,
                            (int) maxValue * 1000,
                            idLoaiPhong == -1 ? idLoaiPhong = 0 : idLoaiPhong,
                            idGioiTinh == -1 ? idGioiTinh = 0 : idGioiTinh,
                            new Gson().toJson(listTienIchSeleted));
                    call.enqueue(new Callback<List<PhongTro>>() {
                        @Override
                        public void onResponse(Call<List<PhongTro>> call, Response<List<PhongTro>> response) {
                            if (response.code() == 200) {
                                if (response.body().size() == 0) {
                                    Log.d("TAG", "onResponse: " + response.body().size());
                                    ll_khong_tim_thay.setVisibility(View.VISIBLE);
                                    rcvListPhongTimKiem.setVisibility(View.GONE);

                                } else {
                                    listPhongTimKiem.clear();
                                    listPhongTimKiem.addAll(response.body());
                                    rcvListPhongTimKiem.setVisibility(View.VISIBLE);
                                    adapterPhongTimKiem.notifyDataSetChanged();
                                }

                            }
                            Log.d("TAG", "onResponse: " + response.body().size());
                            Log.d("TAG", "onClick: " + (int) minValue * 1000);
                            Log.d("TAG", "onClick: " + (int) maxValue * 1000);
                            Log.d("TAG", "onClick: " + idLoaiPhong);
                            Log.d("TAG", "onClick: " + idGioiTinh);
                        }

                        @Override
                        public void onFailure(Call<List<PhongTro>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }



    private void getDanhSachPhongTroTheoQuan(int idQuan) {
        Call<List<com.example.mobileprojectapp2.datamodel.PhongTro>> call = ApiServicePhuc2.apiService.layTatCaPhongTroTheoQuan(idQuan, Const.NHO_DEN_LON);
        call.enqueue(new Callback<List<com.example.mobileprojectapp2.datamodel.PhongTro>>() {
            @Override
            public void onResponse(Call<List<com.example.mobileprojectapp2.datamodel.PhongTro>> call, Response<List<com.example.mobileprojectapp2.datamodel.PhongTro>> response) {
                if (response.code() == 200) {
                    if (response.body().size() == 0) {
                        btnGia.setEnabled(false);
                        btnLoaiPhong.setEnabled(false);
                        btnSoNguoi.setEnabled(false);
                        btnTienIch.setEnabled(false);

                        ll_chua_co_phong_nao.setVisibility(View.VISIBLE);
                    } else {
                        listPhongTheoQuan.addAll(response.body());
                        adapterPhongTroTheoQuan.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<com.example.mobileprojectapp2.datamodel.PhongTro>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        btnTienIch = findViewById(R.id.btn_tien_ich);
        btnLoaiPhong = findViewById(R.id.btn_loai_phong);
        btnGia = findViewById(R.id.btn_gia);
        btnSoNguoi = findViewById(R.id.btn_so_nguoi);

        imgClear = findViewById(R.id.img_clear);
        imgDownTienIch = findViewById(R.id.img_tien_ich_down);
        imgUpTienIch = findViewById(R.id.img_tien_ich_up);

        img_loai_phong_down = findViewById(R.id.img_loai_phong_down);
        img_loai_phong_up = findViewById(R.id.img_loai_phong_up);
        img_gia_down = findViewById(R.id.img_gia_down);
        img_gia_up = findViewById(R.id.img_gia_up);
        img_gioi_tinh_down = findViewById(R.id.img_gioi_tinh_down);
        img_gioi_tinh_up = findViewById(R.id.img_gioi_tinh_up);
        rcvListTienIch = findViewById(R.id.rcv_list_tien_ich);
        rcvListSelected = findViewById(R.id.rcv_list_selected);
        rcvListLoaiPhong = findViewById(R.id.rcv_list_loai_phong);
        rcvListGioiTinh = findViewById(R.id.rcv_list_gioi_tinh);
        rcvListPhongTimKiem = findViewById(R.id.rcv_list_phong_tim_kiem);
        rcv_list_phong_theo_quan = findViewById(R.id.rcv_list_phong_theo_quan);

        ll_list_Selected = findViewById(R.id.ll_list_selected);
        llSearchBoLoc = findViewById(R.id.ll_search_bo_loc);
        ll_so_nguoi_gioi_tinh = findViewById(R.id.ll_so_nguoi_gioi_tinh);
        ll_ap_dung = findViewById(R.id.ll_ap_dung);
        ll_khong_tim_thay = findViewById(R.id.ll_khong_tim_thay);

        tv_quan = findViewById(R.id.tv_ten_quan);
        tv_huy = findViewById(R.id.tv_huy);
        tv_gia_start = findViewById(R.id.tv_gia_start);
        tv_gia_end = findViewById(R.id.tv_gia_end);
        ll_chua_co_phong_nao = findViewById(R.id.ll_chua_co_phong_nao);
        ll_list_phong_theo_quan = findViewById(R.id.ll_list_phong_theo_quan);
        range_slider = findViewById(R.id.range_slider);

        llGiaSeekBar = findViewById(R.id.ll_seek_bar);
        btn_ap_dung = findViewById(R.id.btn_ap_dung);


        adapterTienIch = new TienIchAdapter(SearchBoLocActivity.this, listTienIch, R.layout.cardview_item_tien_ich_layout);
        layoutManagerTienIch = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerTienIch = new GridLayoutManager(this, 2);
        layoutManagerTienIch.setOrientation(RecyclerView.HORIZONTAL);
        rcvListTienIch.setLayoutManager(layoutManagerTienIch);

        rcvListTienIch.setAdapter(adapterTienIch);

        adapterSelected = new SelectedAdapter(SearchBoLocActivity.this, listSelected, R.layout.nguoithue_cardview_item_selected_layout);
        layoutManagerSelected = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerSelected.setOrientation(RecyclerView.HORIZONTAL);
        layoutManagerSelected.scrollToPosition(listSelected.size() > 0 ? listSelected.size() - 1 : 0);
        rcvListSelected.setLayoutManager(layoutManagerSelected);
        rcvListSelected.setAdapter(adapterSelected);


        listLoaiPhong = getListLoaiPhong();
        adapterLoaiPhong = new PhucLoaiPhongAdapter(SearchBoLocActivity.this, listLoaiPhong, R.layout.nguoithue_cardview_item_loai_phong_layout);
        layoutManagerLoaiPhong = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerLoaiPhong.setOrientation(RecyclerView.VERTICAL);
        rcvListLoaiPhong.setLayoutManager(layoutManagerLoaiPhong);
        rcvListLoaiPhong.setAdapter(adapterLoaiPhong);


        listGioiTinh = getListGioiTinh();
        adapterGioiTinh = new PhucGioiTinhAdapter(SearchBoLocActivity.this, listGioiTinh, R.layout.nguoithue_cardview_item_gioi_tinh_layout);
        layoutManagerGioiTinh = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerGioiTinh.setOrientation(RecyclerView.HORIZONTAL);
        rcvListGioiTinh.setLayoutManager(layoutManagerGioiTinh);
        rcvListGioiTinh.setAdapter(adapterGioiTinh);

        adapterPhongTimKiem = new PhucDanhSachPhongTimKiemAdapter(SearchBoLocActivity.this, listPhongTimKiem, R.layout.nguoi_thue_cardview_item_phong_tim_kiem);
        layoutManagerTimKiem = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerTimKiem.setOrientation(RecyclerView.VERTICAL);
        rcvListPhongTimKiem.setLayoutManager(layoutManagerTimKiem);
        rcvListPhongTimKiem.setAdapter(adapterPhongTimKiem);

        adapterPhongTroTheoQuan = new PhucDanhSachPhongTheoQuanAdapter(SearchBoLocActivity.this, listPhongTheoQuan, R.layout.nguoi_thue_cardview_item_phong_tim_kiem);
        layoutManagerPhongTheoQuan = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerPhongTheoQuan.setOrientation(RecyclerView.VERTICAL);
        rcv_list_phong_theo_quan.setLayoutManager(layoutManagerPhongTheoQuan);
        rcv_list_phong_theo_quan.setAdapter(adapterPhongTroTheoQuan);

    }

    private List<Integer> getListLoaiPhong() {
        List<Integer> list = new ArrayList<>();
        list.add(new Integer(Const.PHONG_TRONG));
        list.add(new Integer(Const.PHONG_GHEP));
        return list;
    }

    private List<Integer> getListGioiTinh() {
        List<Integer> list = new ArrayList<>();
        list.add(new Integer(Const.ALL_GENDERS));
        list.add(new Integer(Const.MALE_GENDERS));
        list.add(new Integer(Const.FEMALE_GENDERS));
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

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(s)
                .setIcon(R.drawable.iconp_fail)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void alertComingSoon(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(s)
                .setIcon(R.drawable.iconp_coming_soon)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}