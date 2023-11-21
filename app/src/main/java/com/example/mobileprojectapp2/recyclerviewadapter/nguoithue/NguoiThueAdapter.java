package com.example.mobileprojectapp2.recyclerviewadapter.nguoithue;

import static com.example.mobileprojectapp2.api.Const.MALE_GENDERS;

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
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongNguoiThue;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.PhongTroChuTroAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NguoiThueAdapter extends RecyclerView.Adapter<NguoiThueAdapter.MyViewHolder> {

    private Activity activity;
    private List<PhongNguoiThue> list;
    private int layoutID;

    private PhongTroChuTroAdapter.MyOnClickListener myOnClickListener;


    public void setMyOnClickListener(PhongTroChuTroAdapter.MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public NguoiThueAdapter(Activity activity, List<PhongNguoiThue> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PhongNguoiThue phongNguoiThue = list.get(position);
        if (phongNguoiThue == null) {
            return;
        }

        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN + phongNguoiThue.getNguoiThue().getHinh()).into(holder.imageViewNguoiThue);
        holder.tvTenNguoiThue.setText(phongNguoiThue.getNguoiThue().getTen()+"");
        holder.tvGioiTinhNguoiThue.setText(phongNguoiThue.getNguoiThue().getGioiTinh() == MALE_GENDERS ? "Nam" : "Nữ");
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
        private CircleImageView imageViewNguoiThue;
        private TextView tvTenNguoiThue, tvGioiTinhNguoiThue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewNguoiThue = itemView.findViewById(R.id.imgView_nguoi_thue);
            tvTenNguoiThue = itemView.findViewById(R.id.tv_ten_nguoi_thue);
            tvGioiTinhNguoiThue = itemView.findViewById(R.id.tv_gio_tinh_nguoi_thue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
