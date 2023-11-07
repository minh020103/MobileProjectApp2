package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.GioiTinh;

import java.util.List;

public class GenderAdapter extends RecyclerView.Adapter<GenderAdapter.MyViewHolder> {

    private Activity activity;
    private List<GioiTinh> list;
    private int layoutID;
    private OnClick onClick;

    public GenderAdapter(Activity activity, List<GioiTinh> list, int layoutID) {
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
        GioiTinh gioiTinh = list.get(position);
        holder.tvGioiTinh.setText(gioiTinh.getGioiTinh());
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickGender(position,view);
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClick{
        void onClickGender(int position, View view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvGioiTinh;
        LinearLayout llGioiTinh;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGioiTinh = itemView.findViewById(R.id.tvGioiTinh);
            llGioiTinh = itemView.findViewById(R.id.llGioiTinh);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
