package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobileprojectapp2.R;
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

    private List<PhongTroChuTro2> mList;
    private ImageView imgViewBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        mList = new LinkedList<>();
        imgViewBack = findViewById(R.id.img_back);
        rcvListPhongTro = findViewById(R.id.rcv_list_phong_tro);
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

        searchView = findViewById(R.id.search);
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
                Intent intent = new Intent(SearchActivity.this, DetailPhongTro.class);
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
        Call<List<PhongTroChuTro2>> call = ApiServicePhuc.apiService.getALlListPhongTro(2);
        call.enqueue(new Callback<List<PhongTroChuTro2>>() {
            @Override
            public void onResponse(Call<List<PhongTroChuTro2>> call, Response<List<PhongTroChuTro2>> response) {
                if (response.code() == 200) {
                    mList.clear();
                    mList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhongTroChuTro2>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Error not call api", Toast.LENGTH_SHORT).show();
            }
        });


    }
}