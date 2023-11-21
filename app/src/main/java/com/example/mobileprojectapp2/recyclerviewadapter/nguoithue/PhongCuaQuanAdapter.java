package com.example.mobileprojectapp2.recyclerviewadapter.nguoithue;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.viewpager2adapter.ChuTroImageSlideViewPager2Adapter;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class PhongCuaQuanAdapter extends RecyclerView.Adapter<PhongCuaQuanAdapter.MyViewHolder> {

    private Activity activity;
    private List<PhongTro> list;
    private int layoutID;
    private OnClickItemRoomListener onClickItemRoomListener;
    private int position=0;


    public PhongCuaQuanAdapter(Activity activity, List<PhongTro> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    public OnClickItemRoomListener getOnClickItemRoomListener() {
        return onClickItemRoomListener;
    }

    public void setOnClickItemRoomListener(OnClickItemRoomListener onClickItemRoomListener) {
        this.onClickItemRoomListener = onClickItemRoomListener;
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
        PhongTro phongTro = list.get(position);
        if (phongTro != null) {
            String content =
                            "Giá: " + phongTro.getGia() + "\n" +
                            "Địa chỉ: " + phongTro.getDiaChiChiTiet() + "\n" +
                            "Giới tính: " + (phongTro.getGioiTinh() == 0 ? "Tất cả" : phongTro.getGioiTinh() == 1 ? "Nam" : phongTro.getGioiTinh() == 2 ? "Nữ" : "Null");
            holder.tvNoiDungPhong.setText(content);
        }
        if (phongTro.getPhongTroChuTro() != null) {
            holder.tvTenChuTro.setText(phongTro.getPhongTroChuTro().getTen());
        }
        if (phongTro.getBinhLuan() > 0) {
            holder.tvCountComment.setText(phongTro.getBinhLuan() + "");
        } else {
            holder.tvCountComment.setText("0");
        }
        if (phongTro.getDanhGia() > 0) {
            holder.tvTBRating.setText(phongTro.getDanhGia()+"");
        } else {
            holder.tvTBRating.setText("0");
        }
        if (phongTro.getPhongTroChuTro() != null && phongTro.getPhongTroChuTro().getHinh() != null){
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+phongTro.getPhongTroChuTro().getHinh()).placeholder(R.drawable.not_found).into(holder.imgAnhChuTro);
        }

        ChuTroImageSlideViewPager2Adapter adapter = null;
        if (list.get(position).getHinhAnhPhongTro() != null && list.get(position).getHinhAnhPhongTro().size() > 0){
            adapter = new ChuTroImageSlideViewPager2Adapter(activity, list.get(position).getHinhAnhPhongTro(), R.layout.chutro_item_image_layout);
            holder.rlImages.setVisibility(View.VISIBLE);
            holder.vp2SlideImage.setAdapter(adapter);
            holder.ci3SlideImage.setViewPager(holder.vp2SlideImage);
        }else {
            holder.rlImages.setVisibility(View.GONE);
        }

//        Log.d("TAGH", "onBindViewHolder: "+phongTroChuTro.getChuTro().getHinh());

//        ChuTroImageSlideViewPager2Adapter adapter = new ChuTroImageSlideViewPager2Adapter(activity,list.get(position).getList(),R.layout.chutro_item_image_layout);
//        holder.vp2SlideImage.setAdapter(adapter);
//        holder.ci3SlideImage.setViewPager(holder.vp2SlideImage);


        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.llComment:
                        onClickItemRoomListener.setOnClickComment(position, view);
                        break;
                    default:
                        onClickItemRoomListener.setOnClickCItem(position, view);
                        break;
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClickItemRoomListener{
        void setOnClickComment(int position, View view);
        void setOnClickCItem(int position, View view);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewPager2 vp2SlideImage;
        CircleIndicator3 ci3SlideImage;

        LinearLayout llComment;
        RelativeLayout rlImages;

        ImageView imgAnhChuTro;
        TextView tvTenChuTro, tvNoiDungPhong, tvCountComment, tvTBRating;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rlImages = itemView.findViewById(R.id.rlImages);

            llComment = itemView.findViewById(R.id.llComment);
            vp2SlideImage = itemView.findViewById(R.id.vp2SlideImage);
            ci3SlideImage = itemView.findViewById(R.id.ci3SlideImage);

            imgAnhChuTro = itemView.findViewById(R.id.imgAnhChuTro);
            tvTenChuTro = itemView.findViewById(R.id.tvTenChuTro);
            tvNoiDungPhong = itemView.findViewById(R.id.tvNoiDungPhong);
            tvCountComment = itemView.findViewById(R.id.tvCountComment);
            tvTBRating = itemView.findViewById(R.id.tvTBRating);
            // Click
            llComment.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
