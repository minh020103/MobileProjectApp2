package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.model.HinhAnh2;
import com.example.mobileprojectapp2.model.PhongTroChuTro2;
import com.example.mobileprojectapp2.viewpager2adapter.ChuTroImageSlideViewPager2Adapter;
import com.example.mobileprojectapp2.viewpager2adapter.ChuTroImageSlideViewPager2Adapter2;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class DetailPhongTro extends AppCompatActivity {

    private ChuTroImageSlideViewPager2Adapter2 adapter;

    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<HinhAnh2> listHinhAnh;
    PhongTroChuTro2 phongTroChuTro = new PhongTroChuTro2();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_phong_tro);
        anhXa();

    }

    private void anhXa(){
        listHinhAnh = new ArrayList<>();
        listHinhAnh =  getListPhoto();
        mViewPager2 = findViewById(R.id.view_pager_2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator_3);
        adapter = new ChuTroImageSlideViewPager2Adapter2(this,listHinhAnh,R.layout.chutro_item_image_layout);
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);
    }


    private List<HinhAnh2> getListPhoto() {
        List<HinhAnh2> list = new ArrayList<>();
        list.add(new HinhAnh2(R.drawable.anhdaidien));
        list.add(new HinhAnh2(R.drawable.cccd));
        list.add(new HinhAnh2(R.drawable.anhdaidien));


        return list;
    }
}