package com.example.mobileprojectapp2.adapter.nguoithue;

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
import com.example.mobileprojectapp2.datamodels.PhongTro;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class DanhSachGoiYAdapter extends RecyclerView.Adapter<DanhSachGoiYAdapter.MyViewHolder> {

    Activity activity;
    int layoutId;
    ArrayList<PhongTro> arrayList;

    public DanhSachGoiYAdapter(Activity activity, int layoutId, ArrayList<PhongTro> arrayList) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView cardView = (CardView) inflater.inflate(viewType,parent,false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PhongTro phongTro = arrayList.get(position);
        if(phongTro.getChuTro()!=null){
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+phongTro.getChuTro().getHinh()).into(holder.imgChuTro);
            holder.tenChuTro.setText(phongTro.getChuTro().getTen());

        }
        holder.tenQuan.setText(phongTro.getQuan().getTenQuan());
        if(phongTro.getGioiTinh()==1){
            holder.gioiTinh.setText("Nam");
        }else if(phongTro.getGioiTinh()==0){
            holder.gioiTinh.setText("Nữ");
        }else{
            holder.gioiTinh.setText("Khác");
        }
        if(phongTro.getHinhAnhPhongTro().size()!=0){
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+phongTro.getHinhAnhPhongTro().get(0).getHinh()).into(holder.imgAnhGoiY);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgChuTro;
        TextView tenChuTro;
        TextView gioiTinh;
        TextView tenQuan;
        ShapeableImageView imgAnhGoiY;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChuTro = itemView.findViewById(R.id.imgChuTro);
            tenChuTro = itemView.findViewById(R.id.tenChuTro);
            gioiTinh = itemView.findViewById(R.id.gioiTinh);
            tenQuan = itemView.findViewById(R.id.tenQuan);
            imgAnhGoiY = itemView.findViewById(R.id.imgAnhGoiY);
        }
    }
}
