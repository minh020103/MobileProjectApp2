package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.viewpager2adapter.ChuTroImageSlideViewPager2Adapter;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MotelRoomAdapter extends RecyclerView.Adapter<MotelRoomAdapter.MyViewHolder> {

    private Activity activity;
    private List<PhongTro> list;
    private int layoutID;
    private OnClickItemRoomListener onClickItemRoomListener;
    private int position=0;


    public MotelRoomAdapter(Activity activity, List<PhongTro> list, int layoutID) {
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
                    case R.id.imgXoa:
                        onClickItemRoomListener.setOnClickEdit(position, view);
                        break;
                    case R.id.llComment:
                        onClickItemRoomListener.setOnClickComment(position, view);
                        break;
                    case R.id.llRating:
                        onClickItemRoomListener.setOnClickRating(position, view);
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
        void setOnClickDelete(int position, View view);
        void setOnClickComment(int position, View view);
        void setOnClickRating(int position, View view);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewPager2 vp2SlideImage;
        CircleIndicator3 ci3SlideImage;

        ImageView  imgDanhSachNguoiThue, imgChinhSua, imgXoa;

        LinearLayout llComment;
        LinearLayout llRating;

        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDanhSachNguoiThue = itemView.findViewById(R.id.imgDanhSachNguoiThue);
            imgChinhSua = itemView.findViewById(R.id.imgChinhSua);
            imgXoa = itemView.findViewById(R.id.imgXoa);


            llComment = itemView.findViewById(R.id.llComment);
            llRating = itemView.findViewById(R.id.llRating);
            vp2SlideImage = itemView.findViewById(R.id.vp2SlideImage);
            ci3SlideImage = itemView.findViewById(R.id.ci3SlideImage);


            ChuTroImageSlideViewPager2Adapter adapter = new ChuTroImageSlideViewPager2Adapter(activity,list.get(position++).getList(),R.layout.chutro_item_image_layout);
            vp2SlideImage.setAdapter(adapter);
            ci3SlideImage.setViewPager(vp2SlideImage);

            // Click
            imgDanhSachNguoiThue.setOnClickListener(this);
            imgChinhSua.setOnClickListener(this);
            imgXoa.setOnClickListener(this);
            llComment.setOnClickListener(this);
            llRating.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
