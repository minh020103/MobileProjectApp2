package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.viewpager2adapter.ChuTroImageSlideViewPager2Adapter;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MotelRoomAdapter extends RecyclerView.Adapter<MotelRoomAdapter.MyViewHolder> {

    private Activity activity;
    private List<PhongTroChuTro> list;
    private int layoutID;
    private OnClickItemRoomListener onClickItemRoomListener;
    private int position=0;


    public MotelRoomAdapter(Activity activity, List<PhongTroChuTro> list, int layoutID) {
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
        PhongTroChuTro phongTroChuTro = list.get(position);

        if (phongTroChuTro.getPhongTro() != null) {
            String content =
                            "Giá: " + phongTroChuTro.getPhongTro().getGia() + "\n" +
                            "Địa chỉ: " + phongTroChuTro.getPhongTro().getDiaChiChiTiet() + "\n" +
                            "Giới tính: " + (phongTroChuTro.getPhongTro().getGioiTinh() == 0 ? "Tất cả" : phongTroChuTro.getPhongTro().getGioiTinh() == 1 ? "Nam" : phongTroChuTro.getPhongTro().getGioiTinh() == 2 ? "Nữ" : "Null");
            holder.tvNoiDungPhong.setText(content);
        }
        holder.tvTenChuTro.setText(phongTroChuTro.getChuTro().getTen());
        if (phongTroChuTro.getBinhLuan() > 0) {
            holder.tvCountComment.setText(phongTroChuTro.getBinhLuan() + "");
        } else {
            holder.tvCountComment.setText("0");
        }
        if (phongTroChuTro.getDanhGia() > 0) {
            holder.tvTBRating.setText(phongTroChuTro.getDanhGia()+"");
        } else {
            holder.tvTBRating.setText("0");
        }
        if (phongTroChuTro.getChuTro() != null && phongTroChuTro.getChuTro().getHinh() != null){
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+phongTroChuTro.getChuTro().getHinh()).into(holder.imgAnhChuTro);
        }

        ChuTroImageSlideViewPager2Adapter adapter = null;
        if (list.get(position).getHinhAnh() != null && list.get(position).getHinhAnh().size() > 0){
            adapter = new ChuTroImageSlideViewPager2Adapter(activity, list.get(position).getHinhAnh(), R.layout.chutro_item_image_layout);
            holder.rlImages.setVisibility(View.VISIBLE);
            holder.vp2SlideImage.setAdapter(adapter);
            holder.ci3SlideImage.setViewPager(holder.vp2SlideImage);
        }else {
            holder.rlImages.setVisibility(View.GONE);
        }
        if (phongTroChuTro.getPhongTro().getHoatDong() == Const.KHONG_HOAT_DONG){
            holder.imgControls.setImageDrawable(activity.getResources().getDrawable(R.drawable.icon_off, activity.getTheme()));
            holder.tvControls.setText("Phòng đang ẩn phía người thuê");
        }
        else {
            holder.imgControls.setImageDrawable(activity.getResources().getDrawable(R.drawable.icon_open, activity.getTheme()));
            holder.tvControls.setText("Phòng đang hiển thị phía người thuê");
        }

//        Log.d("TAGH", "onBindViewHolder: "+phongTroChuTro.getChuTro().getHinh());

//        ChuTroImageSlideViewPager2Adapter adapter = new ChuTroImageSlideViewPager2Adapter(activity,list.get(position).getList(),R.layout.chutro_item_image_layout);
//        holder.vp2SlideImage.setAdapter(adapter);
//        holder.ci3SlideImage.setViewPager(holder.vp2SlideImage);



        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.imgDanhSachNguoiThue:
                        onClickItemRoomListener.setOnClickListPerson(position, view);
                        break;
                    case R.id.imgChinhSua:
                        onClickItemRoomListener.setOnClickEdit(position, view);
                        break;
                    case R.id.imgVideoReview:
                        onClickItemRoomListener.setOnClickVideoReview(position,view);
                        break;
                    case R.id.imgXoa:
                        onClickItemRoomListener.setOnClickDelete(position, view);
                        break;
                    case R.id.llComment:
                        onClickItemRoomListener.setOnClickComment(position, view);
                        break;
                    case R.id.llRating:
                        onClickItemRoomListener.setOnClickRating(position, view);
                        break;
                    case R.id.llControls:
                        onClickItemRoomListener.setOnClickControls(position, view, phongTroChuTro.getPhongTro().getHoatDong());
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
        void setOnClickListPerson(int position, View view);
        void setOnClickEdit(int position, View view);
        void setOnClickVideoReview(int position, View view);
        void setOnClickDelete(int position, View view);
        void setOnClickComment(int position, View view);
        void setOnClickRating(int position, View view);
        void setOnClickControls(int position, View view, int trangThaiHoatDong);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewPager2 vp2SlideImage;
        CircleIndicator3 ci3SlideImage;

        ImageView  imgDanhSachNguoiThue, imgChinhSua, imgXoa,imgControls,imgReview;

        LinearLayout llComment;
        LinearLayout llRating,  llControls;
        RelativeLayout rlImages;

        ImageView imgAnhChuTro;
        TextView tvTenChuTro, tvNoiDungPhong, tvCountComment, tvTBRating, tvControls;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rlImages = itemView.findViewById(R.id.rlImages);
            imgDanhSachNguoiThue = itemView.findViewById(R.id.imgDanhSachNguoiThue);
            imgChinhSua = itemView.findViewById(R.id.imgChinhSua);
            imgXoa = itemView.findViewById(R.id.imgXoa);
            imgControls = itemView.findViewById(R.id.imgControls);
            imgReview = itemView.findViewById(R.id.imgVideoReview);

            llComment = itemView.findViewById(R.id.llComment);
            llRating = itemView.findViewById(R.id.llRating);
            llControls = itemView.findViewById(R.id.llControls);
            vp2SlideImage = itemView.findViewById(R.id.vp2SlideImage);
            ci3SlideImage = itemView.findViewById(R.id.ci3SlideImage);

            imgAnhChuTro = itemView.findViewById(R.id.imgAnhChuTro);
            tvTenChuTro = itemView.findViewById(R.id.tvTenChuTro);
            tvNoiDungPhong = itemView.findViewById(R.id.tvNoiDungPhong);
            tvCountComment = itemView.findViewById(R.id.tvCountComment);
            tvTBRating = itemView.findViewById(R.id.tvTBRating);
            tvControls = itemView.findViewById(R.id.tvControls);
//            ChuTroImageSlideViewPager2Adapter adapter = null;
//            if (list.get(position++).getHinhAnh() != null){
//                adapter = new ChuTroImageSlideViewPager2Adapter(activity, list.get(position++).getHinhAnh(), R.layout.chutro_item_image_layout);
//            }
//            if (adapter != null) {
//                vp2SlideImage.setVisibility(View.VISIBLE);
//                ci3SlideImage.setVisibility(View.VISIBLE);
//                vp2SlideImage.setAdapter(adapter);
//                ci3SlideImage.setViewPager(vp2SlideImage);
//            }else {
//                vp2SlideImage.setVisibility(View.GONE);
//                ci3SlideImage.setVisibility(View.GONE);
//            }

            // Click
            imgDanhSachNguoiThue.setOnClickListener(this);
            imgChinhSua.setOnClickListener(this);
            imgXoa.setOnClickListener(this);
            imgReview.setOnClickListener(this);
            llComment.setOnClickListener(this);
            llRating.setOnClickListener(this);
            llControls.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
