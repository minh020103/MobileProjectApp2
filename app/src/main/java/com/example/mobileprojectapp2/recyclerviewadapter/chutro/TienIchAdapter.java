package com.example.mobileprojectapp2.recyclerviewadapter.chutro;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucGioiTinhAdapter;

import java.util.List;

public class TienIchAdapter extends RecyclerView.Adapter<TienIchAdapter.TienIchViewHolder> {
    private Activity activity;
    private List<TienIch> list;
    private int layoutID;
    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public TienIchAdapter(Activity activity, List<TienIch> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public TienIchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = activity.getLayoutInflater();
//        LinearLayout view = (LinearLayout) inflater.inflate(layoutID, parent, false);
//    return new TienIchViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new TienIchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TienIchViewHolder holder, int position) {
        TienIch tienIch = list.get(position);
        if (tienIch == null) {
            return;
        }
//        holder.tvTenTienIch.setText("abc");
//        Log.d("TAG", "onBindViewHolder: " + tienIch);


        holder.tvTenTienIch.setText(tienIch.getTen());
        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN + tienIch.getHinh()).into(holder.imageTienIch);
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemListener(position, view);
            }
        };
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }
    public interface OnClick{
        void onClickItemListener(int position, View view);
    }

    public class TienIchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private ImageView imageTienIch;
        private TextView tvTenTienIch;
        LinearLayout llBg;
        View.OnClickListener onClickListener;


        public TienIchViewHolder(@NonNull View itemView) {
            super(itemView);
            llBg = itemView.findViewById(R.id.ll_bg_item_loai_phong);
            imageTienIch = itemView.findViewById(R.id.img_tien_ich);
            tvTenTienIch = itemView.findViewById(R.id.tv_ten_tien_ich);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
