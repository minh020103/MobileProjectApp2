package com.example.mobileprojectapp2.activity.nguoithue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.model.Quan;
import com.example.mobileprojectapp2.model.Selected;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.SelectedAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.TienIchAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucGioiTinhAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucLoaiPhongAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBoLocActivity extends AppCompatActivity {
    private Button btnTienIch, btnLoaiPhong, btnGia, btnSoNguoi;

    //List call api
    private List<TienIch> listTienIch;
    private List<Integer> listLoaiPhong;
    private List<Integer> listGioiTinh;

    //List luu data
    private List<TienIch> listTienIchSeleted;
    private List<Integer> listLoaiPhongSelected;
    private List<Integer> listGioiTinhSelected;

    //List nguoi dung
    private List<Selected> listSelected;
    private ImageView imgDownTienIch, imgUpTienIch, imgClear, img_loai_phong_down, img_loai_phong_up,
            img_gia_down, img_gia_up, img_so_nguoi_down, img_so_nguoi_up;
    private RecyclerView rcvListTienIch, rcvListSelected, rcvListLoaiPhong, rcvListGioiTinh;

    private TienIchAdapter adapterTienIch;
    private PhucLoaiPhongAdapter adapterLoaiPhong;
    private PhucGioiTinhAdapter adapterGioiTinh;
    private SelectedAdapter adapterSelected;
    private LinearLayoutManager layoutManagerTienIch, layoutManagerSelected, layoutManagerLoaiPhong, layoutManagerGioiTinh;
    private LinearLayout ll_list_Selected, llSearchBoLoc, ll_so_nguoi_gioi_tinh;
    private TextView tv_quan, tv_huy;
    private LinearLayout llGiaSeekBar;
    private int flagTienIch = 0;
    private int flagLoaiPhong = 0;
    private int flagGia = 0;
    private int flagSoNguoi = 0;
    private int id;
    private RelativeLayout rlt_bg;


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
        rcvListLoaiPhong.setVisibility(View.GONE);
        llGiaSeekBar.setVisibility(View.GONE);
        ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);


    }
    private void _Initialization(){
        listTienIch = new ArrayList<>();
        listTienIchSeleted = new ArrayList<>();
        listSelected = new ArrayList<>();
        listLoaiPhong = new ArrayList<>();
        listLoaiPhongSelected = new ArrayList<>();
        listGioiTinh = new ArrayList<>();
        listGioiTinhSelected = new ArrayList<>();
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
                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                    Log.d("TAG", "onClickListData: " + listTienIchSeleted.size());
                    Log.d("TAG", "onClickTenTienIchNguoiDungThay: " + listSelected.get(position).getName());

                } else if (listSelected.get(position).getKey() == Const.LOAI_PHONG) {
                    listLoaiPhongSelected.remove(listLoaiPhong.get(listSelected.get(position).getId()));
                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                    Log.d("TAG", "onClickListData: " + listLoaiPhongSelected.size());
                } else if(listSelected.get(position).getKey() == Const.GIOI_TINH){
                    listGioiTinhSelected.remove(listGioiTinh.get(listSelected.get(position).getId()));
                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                    Log.d("TAG", "onClickListData: " + listGioiTinhSelected.size());
                }
                listSelected.remove(listSelected.get(position));
                adapterSelected.notifyDataSetChanged();
                notShowListSelected();
            }
        });
        adapterTienIch.setMyOnCLickListener(new TienIchAdapter.MyOnCLickListener() {
            @Override
            public void OnClickItem(int position, View v) {
                if (!listTienIchSeleted.contains(listTienIch.get(position))) {
                    //add vao list nguoi dung
                    listSelected.add(new Selected(Const.TIEN_ICH, position, listTienIch.get(position).getTen()));
                    //add vao list luu du lieu
                    listTienIchSeleted.add(listTienIch.get(position));
                    adapterSelected.notifyDataSetChanged();
                    ll_list_Selected.setVisibility(View.VISIBLE);
                    Log.d("TAG", "listNguoiDung: "+ listSelected.size());
                    Log.d("TAG", "listTienIch: "+listTienIchSeleted.size());
//                    rlt_bg.setBackground(getDrawable(R.drawable.btn_p4));

                }
            }
        });
        adapterLoaiPhong.setOnClick(new PhucLoaiPhongAdapter.OnClick() {
            @Override
            public void onClickItemListener(int position, View view) {
                if (!listLoaiPhongSelected.contains(listLoaiPhong.get(position))) {
                    if (listLoaiPhongSelected.size() == 0 || listSelected.size() == 0) {
                        //add moi vao list nguoi dung
                        listSelected.add(new Selected(Const.LOAI_PHONG, position, listLoaiPhong.get(position) == Const.PHONG_TRONG ? "Phòng trống" : listLoaiPhong.get(position) == Const.PHONG_DON ? "Phòng đơn" : "Phòng ghép"));
                        //add moi vao list luu du lieu
                        listLoaiPhongSelected.add(listLoaiPhong.get(position));

                        adapterSelected.notifyDataSetChanged();
                        ll_list_Selected.setVisibility(View.VISIBLE);
                        Log.d("TAG", "listNguoiDung: "+ listSelected.size());
                        Log.d("TAG", "listLoaiPhong: "+listLoaiPhongSelected.size());
                    } else {
                        //Xoa loai phong hien tai
//                        listLoaiPhongSelected.remove(listLoaiPhong.get(listSelected.get(position).getId()));
//                        listSelected.remove(listLoaiPhong.get(listSelected.get(position).getId()));


                        Toast.makeText(SearchBoLocActivity.this, "Xóa cái hiện tại", Toast.LENGTH_SHORT).show();

                        //add moi vao list nguoi dung
                        listSelected.add(new Selected(Const.LOAI_PHONG, position, listLoaiPhong.get(position) == Const.PHONG_TRONG ? "Phòng trống" : listLoaiPhong.get(position) == Const.PHONG_DON ? "Phòng đơn" : "Phòng ghép"));
                        //add moi vao list luu du lieu
                        listLoaiPhongSelected.add(listLoaiPhong.get(position));

                        adapterSelected.notifyDataSetChanged();
                        ll_list_Selected.setVisibility(View.VISIBLE);

                        Log.d("TAG", "listNguoiDung: "+ listSelected.size());
                        Log.d("TAG", "listLoaiPhong: "+listLoaiPhongSelected.size());
                    }
                } else {
                    alertSuccess("da co");
                }
            }
        });
        adapterGioiTinh.setOnClick(new PhucGioiTinhAdapter.OnClick() {
            @Override
            public void onClickItemListener(int position, View view) {
                if (!listGioiTinhSelected.contains(listGioiTinh.get(position))) {
                    if (listGioiTinhSelected.size() == 0 || listGioiTinh.size() == 0) {
                        //add moi vao list nguoi dung
                        listSelected.add(new Selected(Const.GIOI_TINH, position, listGioiTinh.get(position) == Const.ALL_GENDERS ? "Tất cả" : listGioiTinh.get(position) == Const.MALE_GENDERS ? "Nam" : "Nữ"));
                        //add moi vao list luu du lieu
                        listGioiTinhSelected.add(listGioiTinh.get(position));

                        adapterSelected.notifyDataSetChanged();
                        ll_list_Selected.setVisibility(View.VISIBLE);
                        Toast.makeText(SearchBoLocActivity.this, "Thêm mới", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "listNguoiDung: "+ listSelected.size());
                        Log.d("TAG", "listGioiTinh: "+listGioiTinhSelected.size());

                    } else {
                        //Xoa loai phong hien tai
//                        listLoaiPhongSelected.remove(listLoaiPhong.get(listSelected.get(position).getId()));
//                        listSelected.remove(listLoaiPhong.get(listSelected.get(position).getId()));

                        //add moi vao list nguoi dung
                        listSelected.add(new Selected(Const.GIOI_TINH, position, listGioiTinh.get(position) == Const.ALL_GENDERS ? "Tất cả" : listGioiTinh.get(position) == Const.MALE_GENDERS ? "Nam" : "Nữ"));
                        //add moi vao list luu du lieu
                        listGioiTinhSelected.add(listGioiTinh.get(position));

                        adapterSelected.notifyDataSetChanged();
                        ll_list_Selected.setVisibility(View.VISIBLE);
                        Toast.makeText(SearchBoLocActivity.this, "Xóa -> Thêm", Toast.LENGTH_SHORT).show();


                        Log.d("TAG", "listNguoiDung: " + listSelected.size());
                        Log.d("TAG", "listGioiTinh: " + listGioiTinhSelected.size());
                    }
                } else {
                    alertSuccess("da co");
                }
            }
        });
    }

    private void onClickButton() {
        btnTienIch.setOnClickListener(onClickListener);
        btnLoaiPhong.setOnClickListener(onClickListener);
        btnGia.setOnClickListener(onClickListener);
        btnSoNguoi.setOnClickListener(onClickListener);

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
                            imgDownTienIch.setVisibility(View.GONE);
                            imgUpTienIch.setVisibility(View.VISIBLE);
                            flagTienIch = 1;
                            getListTienIch();
                            rcvListTienIch.setVisibility(View.VISIBLE);

                            flagLoaiPhong = 0;
                            flagGia = 0;
                            flagSoNguoi = 0;

                            img_loai_phong_up.setVisibility(View.GONE);
                            img_loai_phong_down.setVisibility(View.VISIBLE);
                            img_gia_up.setVisibility(View.GONE);
                            img_gia_down.setVisibility(View.VISIBLE);
                            img_so_nguoi_up.setVisibility(View.GONE);
                            img_so_nguoi_down.setVisibility(View.VISIBLE);

                            rcvListLoaiPhong.setVisibility(View.GONE);
                            llGiaSeekBar.setVisibility(View.GONE);
                            ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);
                            Log.d("TAG", "listNguoiDung: "+ listSelected.size());
                            Log.d("TAG", "listTienIch: "+listTienIchSeleted.size());
                            break;
                        case 1:
                            //Tat
                            imgDownTienIch.setVisibility(View.VISIBLE);
                            imgUpTienIch.setVisibility(View.GONE);
                            flagTienIch = 0;
                            rcvListTienIch.setVisibility(View.GONE);

                            break;
                    }
                    break;

                case R.id.btn_loai_phong:
                    switch (flagLoaiPhong) {
                        case 0:
                            //Bat
                            img_loai_phong_down.setVisibility(View.GONE);
                            img_loai_phong_up.setVisibility(View.VISIBLE);
                            flagLoaiPhong = 1;
                            getListLoaiPhong();
                            rcvListLoaiPhong.setVisibility(View.VISIBLE);


                            flagTienIch = 0;
                            flagGia = 0;
                            flagSoNguoi = 0;

                            imgUpTienIch.setVisibility(View.GONE);
                            imgDownTienIch.setVisibility(View.VISIBLE);
                            img_gia_up.setVisibility(View.GONE);
                            img_gia_down.setVisibility(View.VISIBLE);
                            img_so_nguoi_up.setVisibility(View.GONE);
                            img_so_nguoi_down.setVisibility(View.VISIBLE);

                            rcvListTienIch.setVisibility(View.GONE);
                            llGiaSeekBar.setVisibility(View.GONE);
                            ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);
                            Log.d("TAG", "listNguoiDung: "+ listSelected.size());
                            Log.d("TAG", "listLoaiPhong: "+listLoaiPhongSelected.size());

                            break;
                        case 1:
                            //Tat
                            img_loai_phong_down.setVisibility(View.VISIBLE);
                            img_loai_phong_up.setVisibility(View.GONE);
                            flagLoaiPhong = 0;
                            rcvListLoaiPhong.setVisibility(View.GONE);
                            break;
                    }
                    break;

                case R.id.btn_gia:
                    switch (flagGia) {
                        //Bat
                        case 0:
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
                            img_so_nguoi_up.setVisibility(View.GONE);
                            img_so_nguoi_down.setVisibility(View.VISIBLE);

                            rcvListLoaiPhong.setVisibility(View.GONE);
                            rcvListTienIch.setVisibility(View.GONE);
                            ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);


                            break;
                        case 1:
                            img_gia_down.setVisibility(View.VISIBLE);
                            img_gia_up.setVisibility(View.GONE);
                            llGiaSeekBar.setVisibility(View.GONE);

                            flagGia = 0;

                            break;
                    }
                    break;

                case R.id.btn_so_nguoi:
                    switch (flagSoNguoi) {
                        case 0:

                            if (listSelected.size() != 0){
                                ll_list_Selected.setVisibility(View.VISIBLE);
                            }
                            Toast.makeText(SearchBoLocActivity.this, "ok", Toast.LENGTH_SHORT).show();

                            img_so_nguoi_up.setVisibility(View.VISIBLE);
                            img_so_nguoi_down.setVisibility(View.GONE);
                            flagSoNguoi = 1;
                            getListGioiTinh();
                            ll_so_nguoi_gioi_tinh.setVisibility(View.VISIBLE);


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
                            Log.d("TAG", "listNguoiDung: "+ listSelected.size());
                            Log.d("TAG", "listSoNguoi: "+listGioiTinhSelected.size());
                            break;
                        case 1:
                            img_so_nguoi_down.setVisibility(View.VISIBLE);
                            img_so_nguoi_up.setVisibility(View.GONE);
                            flagSoNguoi = 0;
                            ll_so_nguoi_gioi_tinh.setVisibility(View.GONE);

                            break;
                        default:
                    }

                case R.id.img_clear:
                    listSelected.clear();
                    listTienIchSeleted.clear();
                    listLoaiPhongSelected.clear();
                    notShowListSelected();
                    break;

                case R.id.tv_huy:
                case R.id.ll_search_bo_loc:
                    startActivity(new Intent(SearchBoLocActivity.this, SearchQuanActivity.class));
                    break;
                default:
            }
        }
    };

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
            }

            @Override
            public void onFailure(Call<Quan> call, Throwable t) {
                alertFail(getString(R.string.error_unable_to_call_api));

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
        img_so_nguoi_down = findViewById(R.id.img_so_nguoi_down);
        img_so_nguoi_up = findViewById(R.id.img_so_nguoi_up);
        rcvListTienIch = findViewById(R.id.rcv_list_tien_ich);
        rcvListSelected = findViewById(R.id.rcv_list_selected);
        rcvListLoaiPhong = findViewById(R.id.rcv_list_loai_phong);
        rcvListGioiTinh = findViewById(R.id.rcv_list_gioi_tinh);

        ll_list_Selected = findViewById(R.id.ll_list_selected);
        llSearchBoLoc = findViewById(R.id.ll_search_bo_loc);
        ll_so_nguoi_gioi_tinh = findViewById(R.id.ll_so_nguoi_gioi_tinh);

        rlt_bg = findViewById(R.id.rlt_bg);

        tv_quan = findViewById(R.id.tv_ten_quan);
        tv_huy = findViewById(R.id.tv_huy);

        llGiaSeekBar = findViewById(R.id.ll_seek_bar);


        adapterTienIch = new TienIchAdapter(SearchBoLocActivity.this, listTienIch, R.layout.cardview_item_tien_ich_layout);
        layoutManagerTienIch = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerTienIch = new GridLayoutManager(this, 2);
        layoutManagerTienIch.setOrientation(RecyclerView.HORIZONTAL);
        rcvListTienIch.setLayoutManager(layoutManagerTienIch);
        rcvListTienIch.setAdapter(adapterTienIch);

        adapterSelected = new SelectedAdapter(SearchBoLocActivity.this, listSelected, R.layout.nguoithue_cardview_item_selected_layout);
        layoutManagerSelected = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerSelected.setOrientation(RecyclerView.HORIZONTAL);
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



    }

    private List<Integer> getListLoaiPhong() {
        List<Integer> list = new ArrayList<>();
        list.add(new Integer(Const.PHONG_TRONG));
        list.add(new Integer(Const.PHONG_DON));
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
}