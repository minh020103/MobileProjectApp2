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

public class PhucDanhSachPhongGoiYAdapter2 extends RecyclerView.Adapter<PhucDanhSachPhongGoiYAdapter2.PhongGoiYViewHolder> {
    private Activity activity;
    private List<PhongTroChuTro> list;
    private int layoutID;
    private PhucDanhSachPhongGoiYAdapter2.MyOnCLickListener myOnCLickListener;

    public void setMyOnCLickListener(PhucDanhSachPhongGoiYAdapter2.MyOnCLickListener myOnCLickListener) {
        this.myOnCLickListener = myOnCLickListener;
    }

    public PhucDanhSachPhongGoiYAdapter2(Activity activity, List<PhongTroChuTro> list, int layoutID) {
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
//        Log.d("TAG", "onBindViewHolder: "+ phongTro.getHinhAnhPhongTro().size());
        if (phongTro.getHinhAnh().size() > 0) {
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN + phongTro.getHinhAnh().get(0).getHinh()).placeholder(R.drawable.anhdaidien).into(holder.imgViewPhongGoiY);
        } else {
            holder.imgViewPhongGoiY.setImageResource(R.drawable.khongcoanh);
        }
        holder.tvDiaChi.setText(phongTro.getPhongTro().getDiaChiChiTiet());
//        holder.tvQuan.setText(phongTro.getPhongTro().getQuan().getTenQuan()+ "");
//        holder.tvGia.setText(phongTro.getPhongTro().getGia() + "triệu VNĐ/tháng");

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

        private ImageView imgViewPhongGoiY;
        private TextView tvLoaiPhong, tvGioiTinh, tvGia, tvDiaChi, tvQuan;
        View.OnClickListener onClickListener;

        public PhongGoiYViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewPhongGoiY = itemView.findViewById(R.id.imgView_phong_goi_y);
            tvLoaiPhong = itemView.findViewById(R.id.tv_loai_phong_ds_goi_y);
            tvGioiTinh = itemView.findViewById(R.id.tv_gioi_tinh_ds_goi_y);
            tvGia = itemView.findViewById(R.id.tv_gia_ds_goi_y);
            tvQuan = itemView.findViewById(R.id.tv_quan_ds_goi_y);
            tvDiaChi = itemView.findViewById(R.id.tv_dia_chi_ds_goi_y);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
