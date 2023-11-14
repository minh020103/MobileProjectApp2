package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.HinhAnh;

import java.util.List;

public class ImagesOldAdapter extends RecyclerView.Adapter<ImagesOldAdapter.MyViewHolder> {
    private Activity activity;
    private List<HinhAnh> list;
    private int layoutID;
    private ImagesAdapter.OnCLick onCLick;


    public ImagesOldAdapter(Activity activity, int layoutID, List<HinhAnh> list) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    public interface OnCLick{
        void delete(int position, View v);
    }

    public void setOnCLick(ImagesAdapter.OnCLick onCLick) {
        this.onCLick = onCLick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) activity.getLayoutInflater().inflate(layoutID, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HinhAnh hinhAnh = list.get(position);

        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+hinhAnh.getHinh()).into(holder.imgItem);
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLick.delete(position, view);
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgItem, imgXoaHinh;
        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            imgXoaHinh = itemView.findViewById(R.id.imgXoaHinh);

            imgXoaHinh.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
