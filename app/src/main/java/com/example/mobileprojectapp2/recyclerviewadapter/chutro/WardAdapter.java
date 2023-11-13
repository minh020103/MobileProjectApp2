package com.example.mobileprojectapp2.recyclerviewadapter.chutro;


import android.app.Activity;
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
import com.example.mobileprojectapp2.datamodel.Phuong;

import java.util.List;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.MyViewHolder> {
    private Activity activity;
    private List<Phuong> list;
    private int layoutID;
    private OnClick onClick;

    public WardAdapter(Activity activity, List<Phuong> list, int layoutID) {
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
        Phuong phuong = list.get(position);
        holder.tvPhuong.setText(phuong.getTenPhuong());

        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemListener(position, view);
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClick{
        void onClickItemListener(int position, View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPhuong;
        LinearLayout llPhuong;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhuong = itemView.findViewById(R.id.tvPhuong);
            llPhuong = itemView.findViewById(R.id.llPhuong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
