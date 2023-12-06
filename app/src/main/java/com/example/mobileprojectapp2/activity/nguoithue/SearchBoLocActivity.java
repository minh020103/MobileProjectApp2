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
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.LoaiPhongAdapter2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBoLocActivity extends AppCompatActivity {
    private Button btnTienIch, btnLoaiPhong;

    //List call api
    private List<TienIch> listTienIch;
    private List<Integer> listLoaiPhong;

    //List luu data
    private List<TienIch> listTienIchSeleted;
    private List<Integer> listLoaiPhongSelected;

    //List nguoi dung
    private List<Selected> listSelected;
    private ImageView imgDownTienIch, imgUpTienIch, img_clear, img_loai_phong_down, img_loai_phong_up;
    private RecyclerView rcvListTienIch, rcvListSelected, rcvListLoaiPhong;

    private TienIchAdapter adapterTienIch;
    private LoaiPhongAdapter2 adapterLoaiPhong;

    private SelectedAdapter adapterSelected;
    private LinearLayoutManager layoutManagerTienIch, layoutManagerSelected, layoutManagerLoaiPhong;
    private LinearLayout ll_list_Selected;
    private TextView tv_quan, tv_huy;
    private int flagTienIch = 0;
    private int flagLoaiPhong = 0;

    private int id;

    private RelativeLayout rlt_bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_search_bo_loc_activity);
        listTienIch = new ArrayList<>();
        listTienIchSeleted = new ArrayList<>();
        listSelected = new ArrayList<>();
        listLoaiPhong = new ArrayList<>();
        listLoaiPhongSelected = new ArrayList<>();

        Intent intent = getIntent();
        id = intent.getIntExtra("idPhong", -1);

        anhXa();
        onClick();
        notShowListSelected();
        getQuanById();
        rcvListLoaiPhong.setVisibility(View.GONE);


    }

    private void notShowListSelected() {
        if (listSelected.size() == 0) {
            ll_list_Selected.setVisibility(View.GONE);
        }
    }

    private void onClick() {
        adapterSelected.setMyOnCLickListener(new SelectedAdapter.MyOnCLickListener() {
            @Override
            public void OnClickImg(int position, View v) {
                OnCLickCloseItem(position, v);
            }

            @Override
            public void OnCLickCloseItem(int position, View v) {
                if (listSelected.get(position).getKey() == Const.TIEN_ICH ) {
                    listTienIchSeleted.remove(listTienIch.get(listSelected.get(position).getId()));
                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                    Log.d("TAG", "onClickListData: " + listTienIchSeleted.size());
                    Log.d("TAG", "onClickTenTienIchNguoiDungThay: " + listSelected.get(position).getName());

                }else if (listSelected.get(position).getKey() == Const.LOAI_PHONG){
                    listLoaiPhongSelected.remove(listLoaiPhong.get(listSelected.get(position).getId()));
                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                    Log.d("TAG", "onClickListData: " + listLoaiPhongSelected.size());
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


                    Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                    Log.d("TAG", "onClickListData: " + listTienIchSeleted.size());
//                    rlt_bg.setBackground(getDrawable(R.drawable.btn_p4));

                }
            }
        });

        btnTienIch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flagTienIch) {
                    case 0:
                        //Bat
                        imgDownTienIch.setVisibility(View.GONE);
                        imgUpTienIch.setVisibility(View.VISIBLE);

                        img_loai_phong_up.setVisibility(View.GONE);
                        img_loai_phong_down.setVisibility(View.VISIBLE);

                        flagTienIch = 1;
                        flagLoaiPhong = 0;
                        getListTienIch();
                        rcvListTienIch.setVisibility(View.VISIBLE);
                        rcvListLoaiPhong.setVisibility(View.GONE);

                        break;
                    case 1:
                        //Tat
                        imgDownTienIch.setVisibility(View.VISIBLE);
                        imgUpTienIch.setVisibility(View.GONE);
                        flagTienIch = 0;
                        rcvListTienIch.setVisibility(View.GONE);

                        break;
                }
            }
        });

        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSelected.clear();
                listTienIchSeleted.clear();
                listLoaiPhongSelected.clear();

                notShowListSelected();

            }
        });

        tv_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchBoLocActivity.this, SearchQuanActivity.class));
            }
        });

      btnLoaiPhong.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              switch (flagLoaiPhong) {
                  case 0:
                      //Bat
                      img_loai_phong_down.setVisibility(View.GONE);
                      img_loai_phong_up.setVisibility(View.VISIBLE);
                      flagLoaiPhong = 1;
                      flagTienIch = 0;
                      getListLoaiPhong();
                      rcvListTienIch.setVisibility(View.GONE);
                      rcvListLoaiPhong.setVisibility(View.VISIBLE);

                      imgUpTienIch.setVisibility(View.GONE);
                      imgDownTienIch.setVisibility(View.VISIBLE);
                      break;
                  case 1:
                      //Tat
                      img_loai_phong_down.setVisibility(View.VISIBLE);
                      img_loai_phong_up.setVisibility(View.GONE);
                      flagLoaiPhong = 0;
                      rcvListLoaiPhong.setVisibility(View.GONE);
                      break;
              }
          }
      });

      adapterLoaiPhong.setOnClick(new LoaiPhongAdapter2.OnClick() {
          @Override
          public void onClickItemListener(int position, View view) {
                 if (!listLoaiPhongSelected.contains(listLoaiPhong.get(position))) {
                  //add vao list nguoi dung
                  listSelected.add(new Selected(Const.LOAI_PHONG, position,listLoaiPhong.get(position) == Const.PHONG_TRONG? "Phòng trống":listLoaiPhong.get(position) == Const.PHONG_DON?"Phòng đơn":"Phòng ghép"));
                  //add vao list luu du lieu
                  listLoaiPhongSelected.add(listLoaiPhong.get(position));
                  adapterSelected.notifyDataSetChanged();
                  ll_list_Selected.setVisibility(View.VISIBLE);

                     Log.d("TAG", "onClickListNguoiDung: " + listSelected.size());
                     Log.d("TAG", "onClickListData: " + listLoaiPhongSelected.size());
              }
          }
      });


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

        img_clear = findViewById(R.id.img_clear);
        imgDownTienIch = findViewById(R.id.img_tien_ich_down);
        imgUpTienIch = findViewById(R.id.img_tien_ich_up);
        img_loai_phong_down = findViewById(R.id.img_loai_phong_down);
        img_loai_phong_up = findViewById(R.id.img_loai_phong_up);

        rcvListTienIch = findViewById(R.id.rcv_list_tien_ich);
        rcvListSelected = findViewById(R.id.rcv_list_selected);
        rcvListLoaiPhong = findViewById(R.id.rcv_list_loai_phong);

        ll_list_Selected = findViewById(R.id.ll_list_selected);


        rlt_bg = findViewById(R.id.rlt_bg);

        tv_quan = findViewById(R.id.tv_ten_quan);
        tv_huy = findViewById(R.id.tv_huy);


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
        adapterLoaiPhong = new LoaiPhongAdapter2(SearchBoLocActivity.this, listLoaiPhong, R.layout.nguoithue_cardview_item_loai_phong_layout);
        layoutManagerLoaiPhong = new LinearLayoutManager(SearchBoLocActivity.this);
        layoutManagerLoaiPhong.setOrientation(RecyclerView.VERTICAL);
        rcvListLoaiPhong.setLayoutManager(layoutManagerLoaiPhong);
        rcvListLoaiPhong.setAdapter(adapterLoaiPhong);


    }

    private List<Integer> getListLoaiPhong() {
        List<Integer> list = new ArrayList<>();
        list.add(new Integer(Const.PHONG_TRONG));
        list.add(new Integer(Const.PHONG_DON));
        list.add(new Integer(Const.PHONG_GHEP));
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