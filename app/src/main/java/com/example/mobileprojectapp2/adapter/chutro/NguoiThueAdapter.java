package com.example.mobileprojectapp2.adapter.chutro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.NguoiThue;

import org.w3c.dom.Text;

import java.util.List;

public class NguoiThueAdapter extends RecyclerView.Adapter<NguoiThueAdapter.MyViewHolder> {
    private Activity activity;
    private List<NguoiThue> list;
    private int layoutID;

    OnClickItemListener onClickItemListener;

    public NguoiThueAdapter(Activity activity, List<NguoiThue> list, int layoutID) {
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
        NguoiThue data = list.get(position);
        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN + list.get(position).getHinhNguoiDung()).into(holder.imgNguoiThue);
        holder.tvTenNguoiThue.setText(data.getTenNguoiDung());
        holder.tvSdtNguoiThue.setText(data.getSoDienThoai());

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

    public interface OnClickItemListener{
        void onClickItem(int position, View v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgNguoiThue;
        TextView tvTenNguoiThue;
        TextView tvSdtNguoiThue;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNguoiThue = itemView.findViewById(R.id.imgNguoiThue);
            tvTenNguoiThue = itemView.findViewById(R.id.tvTenNguoiThue);
            tvSdtNguoiThue = itemView.findViewById(R.id.tvSdtNguoiThue);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
