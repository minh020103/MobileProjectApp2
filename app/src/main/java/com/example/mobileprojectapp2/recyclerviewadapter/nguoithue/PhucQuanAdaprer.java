package com.example.mobileprojectapp2.recyclerviewadapter.nguoithue;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.model.Quan;

import java.util.ArrayList;
import java.util.List;

public class PhucQuanAdaprer extends RecyclerView.Adapter<PhucQuanAdaprer.MyViewHolder> {
    private Activity activity;
    private List<Quan> listQuan;
    private List<Quan> listQuanOld;

    private int layoutID;
    private OnClick onClick;

    public PhucQuanAdaprer(Activity activity, List<Quan> list, int layoutID) {
        this.activity = activity;
        this.listQuan = list;
        this.listQuanOld = listQuan;
        this.layoutID = layoutID;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Quan quan = listQuan.get(position);
        if (quan != null){
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+quan.getHinh()).placeholder(R.drawable.not_found).into(holder.imgQuan);
        }else {
            holder.imgQuan.setImageResource(R.drawable.khongcoanh);
        }

        holder.tvQuan.setText(quan.getTenQuan());


        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemListenner(position, view);
            }
        };


    }

    @Override
    public int getItemCount() {
        return listQuan.size();
    }

    public interface OnClick{
        void onClickItemListenner(int position, View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvQuan;
        ImageView imgQuan;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuan = itemView.findViewById(R.id.tv_ten_quan);
            imgQuan = itemView.findViewById(R.id.img_quan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    listQuan = listQuanOld;
                } else {
                    List<Quan> list = new ArrayList<>();
                    for (Quan quan : listQuanOld) {
                        if ((quan.getTenQuan() + "").contains(strSearch)) {
                            list.add(quan);
                        }
                    }
                    listQuan = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listQuan;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listQuan = (List<Quan>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
