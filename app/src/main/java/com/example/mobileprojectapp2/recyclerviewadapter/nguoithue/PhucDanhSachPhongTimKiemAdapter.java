package com.example.mobileprojectapp2.recyclerviewadapter.nguoithue;

import static com.example.mobileprojectapp2.api.Const.MALE_GENDERS;
import static com.example.mobileprojectapp2.api.Const.PHONG_GHEP;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;

import java.text.DecimalFormat;
import java.util.List;

public class PhucDanhSachPhongTimKiem extends RecyclerView.Adapter<PhucDanhSachPhongTimKiem.PhongGoiYViewHolder> {
    private Activity activity;
    private List<PhongTroChuTro> list;
    private int layoutID;
    private PhucDanhSachPhongTimKiem.MyOnCLickListener myOnCLickListener;

    public void setMyOnCLickListener(PhucDanhSachPhongTimKiem.MyOnCLickListener myOnCLickListener) {
        this.myOnCLickListener = myOnCLickListener;
    }

    public PhucDanhSachPhongTimKiem(Activity activity, List<PhongTroChuTro> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public PhongGoiYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new PhongGoiYViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PhongGoiYViewHolder holder, int position) {
        PhongTroChuTro phongTro = list.get(position);
        if (phongTro == null) {
            return;
        }
        if (phongTro.getHinhAnh().size() > 0) {
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN + phongTro.getHinhAnh().get(0).getHinh()).placeholder(R.drawable.anhdaidien).into(holder.imgViewPhongTimKiem);
        } else {
            holder.imgViewPhongTimKiem.setImageResource(R.drawable.khongcoanh);
        }

        float trieu = 1000000;
        float ngan = 1000;
        float tram = 100000;
        float gia;
        String gia2;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        if (phongTro.getPhongTro().getGia() < tram) {
            holder.tvGia.setText("Đang cập nhật");
        } else if (phongTro.getPhongTro().getGia() < trieu) {
            gia = phongTro.getPhongTro().getGia() / ngan;
            gia2 = decimalFormat.format(gia);
            holder.tvGia.setText(gia2 + "k /tháng");
        } else if (phongTro.getPhongTro().getGia() >= trieu) {
            gia = phongTro.getPhongTro().getGia() / trieu;
            gia2 = decimalFormat.format(gia);
            holder.tvGia.setText(gia2 + " triệu/tháng");
        }

        holder.tvLoaiPhong.setText(phongTro.getPhongTro().getLoaiPhong() == PHONG_GHEP ? "Phòng ghép" : "Tìm người thuê");
        holder.tvGioiTinh.setText(phongTro.getPhongTro().getGioiTinh() == MALE_GENDERS ? "Nam" : "Nữ");
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnCLickListener.OnClickItem(position, v);
            }
        };
    }

    public interface MyOnCLickListener {
        void OnClickItem(int position, View v);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhongGoiYViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgViewPhongTimKiem;
        private TextView tvLoaiPhong, tvGioiTinh, tvGia;
        View.OnClickListener onClickListener;

        public PhongGoiYViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewPhongTimKiem = itemView.findViewById(R.id.imgView_phong_tim_kiem);
            tvLoaiPhong = itemView.findViewById(R.id.tv_loai_phong_ds_tim_kiem);
            tvGioiTinh = itemView.findViewById(R.id.tv_gioi_tinh_ds_tim_kiem);
            tvGia = itemView.findViewById(R.id.tv_gia_ds_tim_kiem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
