package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.DetailPhongTroActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.model.Selected;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.SelectedAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.TienIchAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private Button btnTienIch;

    //List call api
    private List<TienIch> listTienIch;

    //List luu data
    private List<TienIch> listTienIchSeleted;

    //List nguoi dung
    private List<Selected> listSelected;
    private ImageView imgDownTienIch, imgUpTienIch, img_clear;
    private RecyclerView rcvListTienIch, rcvListSelected;
    private TienIchAdapter adapterTienIch;
    private SelectedAdapter adapterSelected;
    private LinearLayoutManager layoutManagerTienIch, layoutManagerSelected;
    private LinearLayout ll_list_Selected;
    private int flag = 0;

    private RelativeLayout rlt_bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_search_activity);
        listTienIch = new ArrayList<>();
        listTienIchSeleted = new ArrayList<>();
        listSelected = new ArrayList<>();
        anhXa();
        onClick();

        notShowListSelected();

    }

    private void notShowListSelected() {
        if (listSelected.size() == 0) {
            ll_list_Selected.setVisibility(View.GONE);
        }
    }

    private void onClick() {
        adapterSelected.setMyOnCLickListener(new SelectedAdapter.MyOnCLickListener() {
            @Override
            public void OnClickItemTienIch(int position, View v) {
                OnCLickCloseItemTienIch(position, v);
            }

            @Override
            public void OnCLickCloseItemTienIch(int position, View v) {
                if (listSelected.get(position).getKey() == Const.TIEN_ICH) {
                    listTienIchSeleted.remove(listTienIch.get(listSelected.get(position).getId()));
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
                    listSelected.add(new Selected(Const.TIEN_ICH, position, listTienIch.get(position).getTen()));
                    listTienIchSeleted.add(listTienIch.get(position));
                    adapterSelected.notifyDataSetChanged();
                    ll_list_Selected.setVisibility(View.VISIBLE);

                    Log.d("TAG", "onClick: "+listSelected.size());
                    Log.d("TAG", "onClick: "+listTienIchSeleted.size());

//                    rlt_bg.setBackground(getDrawable(R.drawable.btn_p4));

                }
            }
        });

        btnTienIch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (flag) {
                    case 0:
                        imgDownTienIch.setVisibility(View.GONE);
                        imgUpTienIch.setVisibility(View.VISIBLE);
                        flag = 1;
                        getListTienIch();
                        rcvListTienIch.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        imgDownTienIch.setVisibility(View.VISIBLE);
                        imgUpTienIch.setVisibility(View.GONE);
                        flag = 0;
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
                notShowListSelected();
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
                alertFail("Error not call api");
            }
        });
    }

    private void anhXa() {
        btnTienIch = findViewById(R.id.btn_tien_ich);

        imgDownTienIch = findViewById(R.id.img_tien_ich_down);
        imgUpTienIch = findViewById(R.id.img_tien_ich_up);
        img_clear = findViewById(R.id.img_clear);

        rcvListTienIch = findViewById(R.id.rcv_list_tien_ich);
        rcvListSelected = findViewById(R.id.rcv_list_selected);

        ll_list_Selected = findViewById(R.id.ll_list_selected);

        rlt_bg = findViewById(R.id.rlt_bg);

        adapterTienIch = new TienIchAdapter(SearchActivity.this, listTienIch, R.layout.cardview_item_tien_ich_layout);
        layoutManagerTienIch = new LinearLayoutManager(SearchActivity.this);
        layoutManagerTienIch = new GridLayoutManager(this, 2);
        layoutManagerTienIch.setOrientation(RecyclerView.HORIZONTAL);
        rcvListTienIch.setLayoutManager(layoutManagerTienIch);
        rcvListTienIch.setAdapter(adapterTienIch);

        adapterSelected = new SelectedAdapter(SearchActivity.this, listSelected, R.layout.nguoithue_cardview_item_selected_layout);
        layoutManagerSelected = new LinearLayoutManager(SearchActivity.this);
        layoutManagerSelected.setOrientation(RecyclerView.HORIZONTAL);
        rcvListSelected.setLayoutManager(layoutManagerSelected);
        rcvListSelected.setAdapter(adapterSelected);


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