package com.example.mobileprojectapp2.viewpager2adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.nguoithue.RenterActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.Banner;
import com.example.mobileprojectapp2.datamodel.HinhAnh;

import java.util.List;

public class NguoiThueImageSlideViewPager2Adapter extends RecyclerView.Adapter<NguoiThueImageSlideViewPager2Adapter.MyViewHolder> {

    private Activity activity;
    private List<Banner> list;
    private int layoutID;

    public NguoiThueImageSlideViewPager2Adapter(Activity activity, List<Banner> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(layoutID, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Banner hinhAnh = list.get(position);
//        holder.imgItem.setImageDrawable(activity.getResources().getDrawable(R.drawable.phong_tro, activity.getTheme()));
        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+hinhAnh.getHinhBanner()).into(holder.imgItem);
        holder.imgItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("TAG", "onTouch: OKKKKKKK");
                RenterActivity.viewPager2NguoiThue.setUserInputEnabled(false);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imgItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
        }
    }
}
