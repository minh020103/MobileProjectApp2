package com.example.mobileprojectapp2.adapter.chutro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.Goi;

import java.util.List;

public class GoiDichVuAdapter extends RecyclerView.Adapter<GoiDichVuAdapter.DichVuViewHolder>{

    private Activity activity;
    private List<Goi> list;
    private int layoutID;

    OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public DichVuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView view = (CardView) inflater.inflate(viewType, parent,false);
        return new DichVuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DichVuViewHolder holder, int position) {
        Goi data = list.get(position);
        holder.phong.setText(data.getSoLuongPhongToiDa());
        holder.ngay.setText(data.getThoiHan());
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickItem(position, v);
            }
        };
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public int getItemViewType(int position) {
        return layoutID;
    }

    public interface OnClickItemListener{
        void onClickItem(int position, View v);
    }

    public class DichVuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView phong;
        TextView ngay;

        View.OnClickListener onClickListener;

        public DichVuViewHolder(@NonNull View itemView) {
            super(itemView);
            phong = itemView.findViewById(R.id.phong);
            ngay = itemView.findViewById(R.id.ngay);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
