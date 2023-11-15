package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.view.LayoutInflater;
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

public class HinhAnhAdapter extends RecyclerView.Adapter<HinhAnhAdapter.HinhAnhViewHolder>{
    private Activity activity;
    private List<HinhAnh> list;

    private int layoutID;

    public HinhAnhAdapter(Activity activity, List<HinhAnh> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public HinhAnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(layoutID,parent,false);
        return new HinhAnhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HinhAnhViewHolder holder, int position) {
        HinhAnh hinhAnh = list.get(position);
        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+hinhAnh.getHinh()).into(holder.imgItem);
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class HinhAnhViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgItem;
        public HinhAnhViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
        }
    }
}
