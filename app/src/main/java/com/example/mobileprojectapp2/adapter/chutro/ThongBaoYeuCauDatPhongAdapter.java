package com.example.mobileprojectapp2.adapter.chutro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.ThongBao;
import com.example.mobileprojectapp2.datamodel.YeuCauDatPhong;

import java.util.List;

public class ThongBaoYeuCauDatPhongAdapter extends RecyclerView.Adapter<ThongBaoYeuCauDatPhongAdapter.MyViewHolder> {

    private Activity activity;
    private List<YeuCauDatPhong> list;
    private int layoutID;

    OnClickItemListener onClickItemListener;

    public ThongBaoYeuCauDatPhongAdapter(Activity activity, List<YeuCauDatPhong> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView view = (CardView) inflater.inflate(layoutID, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        YeuCauDatPhong data =list.get(position);
        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN + list.get(position).getNguoiThue().getHinh()).into(holder.imgNguoiGuiThongBao);
        holder.tvTenNguoiGuiThongBao.setText(data.getNguoiThue().getTen());
        holder.tvTieuDeThongBao.setText("Yêu cầu đặt phòng số " + data.getPhong().getSoPhong());
        if (data.getTrangThaiThongBao() == 0)
        {
            holder.bgItemthongBao.setBackgroundColor(0xFFBDFDA7);
            holder.tvTieuDeThongBao.setTextColor(0xFF000000);
            holder.tvTenNguoiGuiThongBao.setTextColor(0xFF000000);
        }
        else
        {
            holder.bgItemthongBao.setBackgroundColor(0xFFFFFFFF);
            holder.tvTieuDeThongBao.setTextColor(0xFF858383);
            holder.tvTenNguoiGuiThongBao.setTextColor(0xFF858383);
        }

        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickItem(position, v);
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    public interface OnClickItemListener{
        void onClickItem(int position, View v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout bgItemthongBao;
        ImageView imgNguoiGuiThongBao;
        TextView tvTenNguoiGuiThongBao;
        TextView tvTieuDeThongBao;

        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bgItemthongBao = itemView.findViewById(R.id.bg_itemThongBaoYC);
            imgNguoiGuiThongBao = itemView.findViewById(R.id.imgNguoiGuiThongBaoYC);
            tvTenNguoiGuiThongBao = itemView.findViewById(R.id.tvNguoiGuiThongBaoYC);
            tvTieuDeThongBao = itemView.findViewById(R.id.tvTieuDeThongBaoYC);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
