package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.mobileprojectapp2.datamodel.TienIch;

import java.util.List;

public class UtilitiesSeletedAdapter extends RecyclerView.Adapter<UtilitiesSeletedAdapter.MyViewHolder> {

    private Activity activity;
    private List<TienIch> listSeleted;
    private int layoutID;
    private OnClick onClick;

    public UtilitiesSeletedAdapter(Activity activity, List<TienIch> listSeleted, int layoutID) {
        this.activity = activity;
        this.listSeleted = listSeleted;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(layoutID,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TienIch tienIch = listSeleted.get(position);
        if (tienIch != null) {
            Log.d("TAG", "onBindViewHolder: "+Const.DOMAIN + tienIch.getHinh());
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN + tienIch.getHinh()).into(holder.imgAnhTienIch);
            holder.tvTenTienIch.setText(tienIch.getTen());
        }
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.deleteImage(position, view);
            }
        };
    }

    @Override
    public int getItemCount() {
        return listSeleted.size();
    }

    public interface OnClick {
        void deleteImage(int position, View v);
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTenTienIch;
        ImageView imgAnhTienIch;
        ImageView imgXoa;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenTienIch = itemView.findViewById(R.id.tvTenTienIch);
            imgAnhTienIch = itemView.findViewById(R.id.imgHinhTienIch);
            imgXoa = itemView.findViewById(R.id.imgXoa);
            imgXoa.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
