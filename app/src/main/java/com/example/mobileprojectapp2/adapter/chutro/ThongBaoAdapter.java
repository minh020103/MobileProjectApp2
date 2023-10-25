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
import com.example.mobileprojectapp2.datamodel.ThongBao;

import java.util.List;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.MyViewHolder> {
    private Activity activity;
    private List<ThongBao> list;
    private int layoutID;

    OnClickItemListener onClickItemListener;

    public ThongBaoAdapter(Activity activity, List<ThongBao> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView view = (CardView) inflater.inflate(viewType, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ThongBao data =list.get(position);
        holder.tvTenNguoiGuiThongBao.setText("Nguoi gui: " + data.getTaiKhoanNguoiGui().getTenTaiKhoan());
        holder.tvNoiDungThongBao.setText(data.getNoiDung());

        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickItem(position, v);
            }
        };

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    public interface OnClickItemListener{
        void onClickItem(int position, View v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenNguoiGuiThongBao;
        TextView tvNoiDungThongBao;

        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNguoiGuiThongBao = itemView.findViewById(R.id.tvNguoiGuiThongBao);
            tvNoiDungThongBao = itemView.findViewById(R.id.tvNoiDungThongBao);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
