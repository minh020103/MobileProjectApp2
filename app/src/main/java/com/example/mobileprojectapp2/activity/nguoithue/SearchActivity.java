package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.DetailPhongTroActivity;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.TienIchAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private Button btnTienIch;
    private List<TienIch> listTienIch;
    private ImageView imgDownTienIch, imgUpTienIch;
    private RecyclerView rcvListTienIch;
    private TienIchAdapter adapterTienIch;
    private LinearLayoutManager layoutManagerTienIch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_search_activity);
        listTienIch = new ArrayList<>();
        anhXa();
        onClick();
    }

    private void onClick() {
        btnTienIch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDownTienIch.setVisibility(View.GONE);
                imgUpTienIch.setVisibility(View.VISIBLE);

                Call<List<TienIch>> call = ApiServicePhuc2.apiService.getAllListTienIch();
                call.enqueue(new Callback<List<TienIch>>() {
                    @Override
                    public void onResponse(Call<List<TienIch>> call, Response<List<TienIch>> response) {
                        alertSuccess("ok");
                        if (response.code() == 200) {
                            listTienIch.clear();
                            listTienIch.addAll(response.body());
                            adapterTienIch.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TienIch>> call, Throwable t) {
                        alertSuccess("Gay");
                    }
                });

            }
        });
    }

    private void anhXa() {
        btnTienIch = findViewById(R.id.btn_tien_ich);
        imgDownTienIch = findViewById(R.id.img_tien_ich_down);
        imgUpTienIch = findViewById(R.id.img_tien_ich_up);
        rcvListTienIch = findViewById(R.id.rcv_list_tien_ich);

        adapterTienIch = new TienIchAdapter(SearchActivity.this, listTienIch, R.layout.cardview_item_tien_ich_layout);
        layoutManagerTienIch = new LinearLayoutManager(SearchActivity.this);
        layoutManagerTienIch.setOrientation(RecyclerView.HORIZONTAL);
        rcvListTienIch.setLayoutManager(layoutManagerTienIch);
        rcvListTienIch.setAdapter(adapterTienIch);
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