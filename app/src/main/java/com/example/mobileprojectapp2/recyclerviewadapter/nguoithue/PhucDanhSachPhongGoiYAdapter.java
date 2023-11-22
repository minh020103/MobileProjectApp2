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
import com.example.mobileprojectapp2.model.PhongTro;

import java.util.List;

public class PhucDanhSachPhongGoiYAdapter extends RecyclerView.Adapter<PhucDanhSachPhongGoiYAdapter.PhongGoiYViewHolder>{
    private Activity activity;
    private List<PhongTro> list;
    private int layoutID;

    public PhucDanhSachPhongGoiYAdapter(Activity activity, List<PhongTro> list, int layoutID) {
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
        PhongTro phongTro = list.get(position);
        if (phongTro == null){
            return;
        }
//        Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN +phongTro.getHinhAnhPhongTro().get(0).getHinh()).placeholder(R.drawable.anhdaidien).into(holder.imgViewPhongGoiY);
        holder.tvDiaChi.setText(phongTro.getDiaChiChiTiet());
        holder.tvQuan.setText(phongTro.getQuan().getTenQuan()+"");
        holder.tvGia.setText(phongTro.getGia()+"triệu VNĐ/tháng");
        holder.tvLoaiPhong.setText(phongTro.getLoaiPhong() == PHONG_GHEP ? "Phòng ghép" : "Tìm người thuê");
        holder.tvGioiTinh.setText(phongTro.getGioiTinh() == MALE_GENDERS ? "Nam" : "Nữ");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhongGoiYViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgViewPhongGoiY;
        private TextView tvLoaiPhong, tvGioiTinh, tvGia, tvDiaChi, tvQuan;

        public PhongGoiYViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewPhongGoiY = itemView.findViewById(R.id.imgView_phong_goi_y);
            tvLoaiPhong = itemView.findViewById(R.id.tv_loai_phong_ds_goi_y);
            tvGioiTinh = itemView.findViewById(R.id.tv_gioi_tinh_ds_goi_y);
            tvGia = itemView.findViewById(R.id.tv_gia_ds_goi_y);
            tvQuan = itemView.findViewById(R.id.tv_quan_ds_goi_y);
            tvDiaChi = itemView.findViewById(R.id.tv_dia_chi_ds_goi_y);
        }
    }
}
