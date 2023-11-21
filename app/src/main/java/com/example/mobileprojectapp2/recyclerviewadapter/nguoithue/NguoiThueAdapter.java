package com.example.mobileprojectapp2.viewpager2adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.model.NguoiThue2;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.PhongTroChuTroAdapter;

import java.util.List;

public class NguoiThueAdapter extends RecyclerView.Adapter<NguoiThueAdapter.MyViewHolder> {

    private Activity activity;
    private List<NguoiThue2> list;
    private int layoutID;

    private PhongTroChuTroAdapter.MyOnClickListener myOnClickListener;


    public void setMyOnClickListener(PhongTroChuTroAdapter.MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public NguoiThueAdapter(Activity activity, List<NguoiThue2> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(layoutID, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NguoiThue2 nguoiThue = list.get(position);
        if (nguoiThue == null) {
            return;
        }
        holder.imageViewNguoiOGhep.setImageResource(nguoiThue.getHinh());
        holder.tvTenNguoiOGhep.setText(nguoiThue.getTen());
        holder.tvGioiTinhNguoiOGhep.setText(nguoiThue.getGioiTinh());
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClickListener.OnClickItem(position, v);
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface MyOnCLickListener {
        void OnClickItem(int position, View v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View.OnClickListener onClickListener;
        private ImageView imageViewNguoiOGhep;
        private TextView tvTenNguoiOGhep, tvGioiTinhNguoiOGhep;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewNguoiOGhep = itemView.findViewById(R.id.imgView_nguoi_o_ghep);
            tvTenNguoiOGhep = itemView.findViewById(R.id.tv_ten_nguoi_o_ghep);
            tvGioiTinhNguoiOGhep = itemView.findViewById(R.id.tv_gio_tinh_nguoi_o_ghep);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
