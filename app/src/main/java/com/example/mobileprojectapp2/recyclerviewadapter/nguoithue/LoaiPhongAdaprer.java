package com.example.mobileprojectapp2.recyclerviewadapter.nguoithue;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.Quan;
import com.example.mobileprojectapp2.model.LoaiPhong;

import java.util.List;

public class LoaiPhongAdaprer extends RecyclerView.Adapter<LoaiPhongAdaprer.MyViewHolder> {
    private Activity activity;
    private List<LoaiPhong> list;
    private int layoutID;
    private OnClick onClick;

    public LoaiPhongAdaprer(Activity activity, List<LoaiPhong> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LoaiPhong loaiPhong = list.get(position);
        if (loaiPhong != null){
            holder.tvLoaiPhong.setText(loaiPhong.getLoaiPhong());
        }

        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemListenner(position, view);
            }
        };


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClick{
        void onClickItemListenner(int position, View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvLoaiPhong;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLoaiPhong = itemView.findViewById(R.id.tv_loai_phong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
