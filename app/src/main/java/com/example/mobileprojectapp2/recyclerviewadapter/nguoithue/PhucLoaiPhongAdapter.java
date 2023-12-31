package com.example.mobileprojectapp2.recyclerviewadapter.nguoithue;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;

import java.util.List;

public class PhucLoaiPhongAdapter extends RecyclerView.Adapter<PhucLoaiPhongAdapter.MyViewHolder> {

    private Activity activity;
    private int layoutID;
    private List<Integer> list;
    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public PhucLoaiPhongAdapter(Activity activity, List<Integer> list, int layoutID) {
        this.activity = activity;
        this.layoutID = layoutID;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) activity.getLayoutInflater().inflate(layoutID, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvLoaiPhong.setText(list.get(position) == Const.PHONG_TRONG? "Phòng trống":list.get(position) == Const.PHONG_DON?"Phòng đơn":"Phòng ghép");
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemListener(position, view, holder.llBg);
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClick{
        void onClickItemListener(int position, View view, LinearLayout llBg);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvLoaiPhong;
        LinearLayout llBg;
        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLoaiPhong = itemView.findViewById(R.id.tv_loai_phong);
            llBg = itemView.findViewById(R.id.ll_bg_item_loai_phong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
