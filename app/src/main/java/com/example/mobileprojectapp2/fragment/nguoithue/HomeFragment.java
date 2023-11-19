package com.example.mobileprojectapp2.fragment.nguoithue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.Quan;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhongAdaprer;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.QuanAdaprer;

import java.util.LinkedList;

public class HomeFragment extends AbstractFragment{
    private ViewPager2 vp2Banner;
    private TextView tvTimPhong;
    private LinearLayout llGhep, llRandom;
    private RecyclerView rcvQuan, rcvPhong;
    // Adapter
    private QuanAdaprer quanAdaprer;
    private PhongAdaprer phongAdaprer;
    // List
    private LinkedList<Quan> listQuan;
    private LinkedList<PhongTro>  listPhong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.nguoithue_fragment_home_layout, container, false);
        anhXa(fragmentLayout);

        listQuan = new LinkedList<>();
        listPhong = new LinkedList<>();

        batSuKien();
        // set adapter for RecyclerView
        // 1. adapter of quận
        quanAdaprer = new QuanAdaprer(getActivity(), listQuan, R.layout.nguoithue_item_quan_layout);
        LinearLayoutManager layoutManagerQuan = new LinearLayoutManager(getActivity());
        layoutManagerQuan.setOrientation(RecyclerView.VERTICAL);
        layoutManagerQuan = new GridLayoutManager(getActivity(), 3);
        rcvQuan.setLayoutManager(layoutManagerQuan);
        rcvQuan.setAdapter(quanAdaprer);
        // 2. adapter of phòng
        phongAdaprer = new PhongAdaprer(getActivity(), listPhong, R.layout.nguoithue_item_room_layout);
        LinearLayoutManager layoutManagerPhong = new LinearLayoutManager(getActivity());
        layoutManagerPhong.setOrientation(RecyclerView.VERTICAL);
        layoutManagerPhong = new GridLayoutManager(getActivity(), 2);
        rcvPhong.setLayoutManager(layoutManagerPhong);
        rcvPhong.setAdapter(phongAdaprer);


        getDataForQuan();
        getDataForPhong();
        return fragmentLayout;
    }

    private void getDataForPhong() {
        listPhong.add(new PhongTro());
        listPhong.add(new PhongTro());
        listPhong.add(new PhongTro());
        listPhong.add(new PhongTro());
        listPhong.add(new PhongTro());
        listPhong.add(new PhongTro());
        phongAdaprer.notifyDataSetChanged();

    }

    private void getDataForQuan() {
        listQuan.add(new Quan());
        listQuan.add(new Quan());
        listQuan.add(new Quan());
        listQuan.add(new Quan());
        listQuan.add(new Quan());
        listQuan.add(new Quan());
        quanAdaprer.notifyDataSetChanged();

    }

    private void batSuKien() {
        tvTimPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llGhep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void anhXa(View fragmentLayout) {
        vp2Banner = fragmentLayout.findViewById(R.id.vp2Banner);
        tvTimPhong = fragmentLayout.findViewById(R.id.tvTimPhong);
        llGhep = fragmentLayout.findViewById(R.id.llGhep);
        llRandom = fragmentLayout.findViewById(R.id.llRandom);
        rcvQuan = fragmentLayout.findViewById(R.id.rcvQuan);
        rcvPhong = fragmentLayout.findViewById(R.id.rcvPhong);
    }
}
