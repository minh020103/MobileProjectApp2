package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.model.Quan;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucQuanAdaprer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchQuanActivity extends AppCompatActivity {
    private TextView tvHuy;
    private SearchView searchView;
    private RecyclerView rcvListQuan;
    private PhucQuanAdaprer adapterQuan;
    private List<Quan> listQuan;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_search_quan_activity);
        listQuan = new ArrayList<>();
        anhXa();

        // Tự động click vào SearchView
        searchView.requestFocus();
        // Hiển thị bàn phím
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);

        onClick();
        getListQuanApi();
        searchQuan();

    }

    private void onClick() {
        tvHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                alertSuccess("OK");
            }
        });

        adapterQuan.setOnClick(new PhucQuanAdaprer.OnClick() {
            @Override
            public void onClickItemListenner(int position, View view) {
                Intent intent = new Intent(SearchQuanActivity.this, SearchBoLocActivity.class);
                intent.putExtra("idPhong",listQuan.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void getListQuanApi() {
        Call<List<Quan>> call = ApiServicePhuc2.apiService.getAllListQuan();
        call.enqueue(new Callback<List<Quan>>() {
            @Override
            public void onResponse(Call<List<Quan>> call, Response<List<Quan>> response) {
                if (response.code() == 200) {
                    listQuan.clear();
                    listQuan.addAll(response.body());
                    adapterQuan.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Quan>> call, Throwable t) {
                    alertFail(getString(R.string.error_unable_to_call_api));
            }
        });
    }
    private void searchQuan(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterQuan.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterQuan.getFilter().filter(newText);
                return false;
            }
        });

    }


    private void anhXa() {
        tvHuy = findViewById(R.id.tv_huy);
        searchView = findViewById(R.id.search_view);
        rcvListQuan = findViewById(R.id.rcv_list_quan);

        adapterQuan = new PhucQuanAdaprer(SearchQuanActivity.this, listQuan, R.layout.nguoithue_cardview_item_quan_layout);
        linearLayoutManager = new LinearLayoutManager(SearchQuanActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListQuan.setLayoutManager(linearLayoutManager);
        rcvListQuan.setAdapter(adapterQuan);

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