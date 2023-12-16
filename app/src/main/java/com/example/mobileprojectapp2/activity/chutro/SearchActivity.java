package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.model.PhongTroChuTro2;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.PhongTroChuTroAdapter;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView rcvListPhongTro;

    private PhongTroChuTroAdapter adapter;
    private LinearLayoutManager layoutManager;
    private SearchView searchView;
    private TextView tv_thongbao, tv_sai_noi_dung;

    private List<PhongTroChuTro2> mList;
    private ImageView imgViewBack;
    SharedPreferences sharedPreferences;
    private int idChuTro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chutro_search_phong_tro_layout);

        sharedPreferences = SearchActivity.this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idChuTro = sharedPreferences.getInt("idChuTro", -1);


        mList = new LinkedList<>();
        imgViewBack = findViewById(R.id.img_back);
        rcvListPhongTro = findViewById(R.id.rcv_list_phong_tro);
        tv_thongbao = findViewById(R.id.tv_thongbao);
        tv_sai_noi_dung = findViewById(R.id.tv_sai_noi_dung);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvListPhongTro.setLayoutManager(linearLayoutManager);


        adapter = new PhongTroChuTroAdapter(mList, this, R.layout.cardview_item_search_layout);
        layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListPhongTro.setLayoutManager(layoutManager);
        rcvListPhongTro.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvListPhongTro.addItemDecoration(itemDecoration);
        getAllDataApi();

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        adapter.setMyOnClickListener(new PhongTroChuTroAdapter.MyOnClickListener() {
            @Override
            public void OnClickItem(int position, View v) {
                Log.d("TAG", "OnClickItem: "+mList.get(position).getId());
                Intent intent = new Intent(SearchActivity.this, DetailPhongTroActivity.class);
                intent.putExtra("idPhong",mList.get(position).getIdPhongTro());
                startActivity(intent);

            }
        });

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getAllDataApi() {
        Call<List<PhongTroChuTro2>> call = ApiServicePhuc.apiService.getALlListPhongTro(idChuTro);
        call.enqueue(new Callback<List<PhongTroChuTro2>>() {
            @Override
            public void onResponse(Call<List<PhongTroChuTro2>> call, Response<List<PhongTroChuTro2>> response) {
                if (response.code() == 200) {
                    if (response.body().size() == 0){
                        tv_thongbao.setVisibility(View.VISIBLE);
                        tv_sai_noi_dung.setVisibility(View.GONE);

                    }else {
                        mList.clear();
                        mList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<PhongTroChuTro2>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Error not call api", Toast.LENGTH_SHORT).show();
            }
        });


    }
}