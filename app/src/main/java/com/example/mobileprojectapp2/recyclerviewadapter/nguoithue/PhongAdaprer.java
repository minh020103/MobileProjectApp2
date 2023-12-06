package com.example.mobileprojectapp2.recyclerviewadapter.nguoithue;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.PhongTro;

import java.util.List;

public class PhongAdaprer extends RecyclerView.Adapter<PhongAdaprer.MyViewHolder> {
    private Activity activity;
    private List<PhongTro> list;
    private int layoutID;
    private OnClick onClick;

    public PhongAdaprer(Activity activity, List<PhongTro> list, int layoutID) {
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
        CardView view = (CardView) activity.getLayoutInflater().inflate(layoutID, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PhongTro phongTro = list.get(position);
        if (phongTro != null) {
            if (phongTro.getHinhAnhPhongTro().size() > 0) {
                Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+phongTro.getHinhAnhPhongTro().get(0).getHinh()).placeholder(R.drawable.not_found).into(holder.imgPhong);
            }
            else {
                holder.imgPhong.setImageDrawable(activity.getResources().getDrawable(R.drawable.not_image, activity.getTheme()));
            }
            holder.tvMoTa.setText(phongTro.getMoTa());
            holder.tvGioiTinh.setText(phongTro.getGioiTinh() == Const.ALL_GENDERS? "Giới tính: Tất cả":phongTro.getGioiTinh() == Const.MALE_GENDERS?"Giới tính: Nam":"Giới tính: Nữ");
            holder.tvGia.setText(phongTro.getGia()+" đồng");
            holder.tvDiaChi.setText(phongTro.getDiaChiChiTiet());
            holder.tvCountComment.setText(phongTro.getBinhLuan()+"");
            holder.tvTBRating.setText(phongTro.getDanhGia()+"");
        }

        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.llComment:
                        onClick.onClickComment(position,view);
                        break;
                    default:
                        onClick.onClickItemListenner(position, view);
                        break;
                }

            }
        };


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClick {
        void onClickItemListenner(int position, View view);

        void onClickComment(int position, View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout llComment, llRating;
        ImageView imgPhong;
        TextView tvMoTa, tvGioiTinh, tvGia, tvDiaChi, tvCountComment, tvTBRating;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llComment = itemView.findViewById(R.id.llComment);
            llRating = itemView.findViewById(R.id.llRating);
            imgPhong = itemView.findViewById(R.id.imgPhong);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
            tvGioiTinh = itemView.findViewById(R.id.tvGioiTinh);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvCountComment = itemView.findViewById(R.id.tvCountComment);
            tvTBRating = itemView.findViewById(R.id.tvTBRating);

            llComment.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
