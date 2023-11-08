package com.example.mobileprojectapp2.viewpager2adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.model.HinhAnh2;

import java.util.List;

public class ChuTroImageSlideViewPager2Adapter2 extends RecyclerView.Adapter<ChuTroImageSlideViewPager2Adapter2.MyViewHolder> {

    private Activity activity;
    private List<HinhAnh2> list;
    private int layoutID;

    public ChuTroImageSlideViewPager2Adapter2(Activity activity, List<HinhAnh2> list, int layoutID) {
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
        HinhAnh2 hinhAnh = list.get(position);
//        holder.imgItem.setImageDrawable(activity.getResources().getDrawable(R.drawable.phong_tro, activity.getTheme()));
//        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+hinhAnh.getHinh()).into(holder.imgItem);

        holder.imgItem.setImageResource(hinhAnh.getIdResource());
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
