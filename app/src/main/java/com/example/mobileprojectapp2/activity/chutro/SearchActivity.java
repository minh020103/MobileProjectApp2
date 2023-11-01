package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.model.PhongTroChuTro;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.PhongTroChuTroAdapter;

import org.w3c.dom.Text;

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
    private TextView tvThongBao;

    private List<PhongTroChuTro> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        mList = new LinkedList<>();
        tvThongBao = findViewById(R.id.tv_thongbao);
        rcvListPhongTro = findViewById(R.id.rcv_list_phong_tro);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvListPhongTro.setLayoutManager(linearLayoutManager);


        adapter = new PhongTroChuTroAdapter(mList, this, R.layout.cardview_item_search_layout);
        layoutManager = new LinearLayoutManager(this);
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
    }

    private void getAllDataApi() {
        Call<List<PhongTroChuTro>> call = ApiServicePhuc.apiService.getALlListPhongTro(2);
        call.enqueue(new Callback<List<PhongTroChuTro>>() {
            @Override
            public void onResponse(Call<List<PhongTroChuTro>> call, Response<List<PhongTroChuTro>> response) {
                if (response.code() == 200) {
                    mList.clear();
                    mList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhongTroChuTro>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Loi roi ba", Toast.LENGTH_SHORT).show();
            }
        });


    }
}