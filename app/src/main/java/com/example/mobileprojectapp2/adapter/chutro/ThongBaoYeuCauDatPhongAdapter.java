package com.example.mobileprojectapp2.adapter.chutro;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.ThongBao;

import java.util.List;

public class ThongBaoYeuCauDatPhongAdapter extends RecyclerView.Adapter<ThongBaoYeuCauDatPhongAdapter.MyViewHolder> {

    private Activity activity;
    private List<ThongBao> list;
    private int layoutID;

    public ThongBaoYeuCauDatPhongAdapter(Activity activity, List<ThongBao> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout bgItemthongBao;
        ImageView imgNguoiGuiThongBao;
        TextView tvTenNguoiGuiThongBao;
        TextView tvTieuDeThongBao;

        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bgItemthongBao = itemView.findViewById(R.id.bg_itemThongBao);
            imgNguoiGuiThongBao = itemView.findViewById(R.id.imgNguoiGuiThongBao);
            tvTenNguoiGuiThongBao = itemView.findViewById(R.id.tvNguoiGuiThongBao);
            tvTieuDeThongBao = itemView.findViewById(R.id.tvTieuDeThongBao);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
