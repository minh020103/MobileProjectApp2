package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.util.Log;
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
import com.example.mobileprojectapp2.datamodel.Comment;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private Activity activity;
    private List<PhongBinhLuan> list;
    private int layoutID;


    public CommentAdapter(Activity activity, List<PhongBinhLuan> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView view = (CardView) inflater.inflate(layoutID, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PhongBinhLuan comment = list.get(position);
        if (comment != null) {
            if (comment.getNguoiGui() != null) {
                holder.tvTen.setText(comment.getNguoiGui().getTen());
                Log.d("TAG1", "onBindViewHolder: "+Const.DOMAIN + comment.getNguoiGui().getHinh());
                Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN + comment.getNguoiGui().getHinh()).into(holder.imgMain);
            }else {
                holder.tvTen.setText("Không rõ tên");
            }
            holder.tvContent.setText(comment.getNoiDungBinhLuan());
            holder.tvLoaiTaiKhoan.setText(comment.getLoaiTaiKhoan() == null ? "Không rõ loại tài khoản": comment.getLoaiTaiKhoan());
            if (comment.getCreated_at() != null) {
                String sTime = new SimpleDateFormat("dd/MM/yyyy").format(comment.getCreated_at());
                holder.tvTime.setText(sTime);
            }
            else {
                holder.tvTime.setText("Không rõ thời gian");
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMain;
        TextView tvTen;
        TextView tvLoaiTaiKhoan;
        TextView tvContent;
        TextView tvTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMain = itemView.findViewById(R.id.imgMain);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvLoaiTaiKhoan = itemView.findViewById(R.id.tvLoaiTaiKhoan);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
