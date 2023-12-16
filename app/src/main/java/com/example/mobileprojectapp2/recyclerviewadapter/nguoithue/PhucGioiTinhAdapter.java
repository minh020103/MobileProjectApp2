package com.example.mobileprojectapp2.recyclerviewadapter.nguoithue;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;

import java.util.List;

public class PhucGioiTinhAdapter extends RecyclerView.Adapter<PhucGioiTinhAdapter.MyViewHolder> {

    private Activity activity;
    private int layoutID;
    private List<Integer> list;
    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public PhucGioiTinhAdapter(Activity activity, List<Integer> list, int layoutID) {
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
        holder.tvGioiTinh.setText(list.get(position) == Const.ALL_GENDERS? "Nam/nữ":list.get(position) == Const.MALE_GENDERS?"Nam":"Nữ");
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
        TextView tvGioiTinh;
        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGioiTinh = itemView.findViewById(R.id.tv_gioi_tinh);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
