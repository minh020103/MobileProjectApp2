package com.example.mobileprojectapp2.recyclerviewadapter.chutro;


import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.Quan;

import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.MyViewHolder> {
    private Activity activity;
    private List<Quan> list;
    private int layoutID;
    private OnClick onClick;

    public DistrictAdapter(Activity activity, List<Quan> list, int layoutID) {
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
        Quan quan = list.get(position);
        holder.tvQuan.setText(quan.getTenQuan());
        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+quan.getHinh()).into(holder.imgQuan);

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
        TextView tvQuan;
        ImageView imgQuan;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuan = itemView.findViewById(R.id.tvQuan);
            imgQuan = itemView.findViewById(R.id.imgQuan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
