package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.model.Define;
import com.example.mobileprojectapp2.model.PhongTroChuTro2;
import com.example.mobileprojectapp2.viewpager2adapter.ChuTroImageSlideViewPager2Adapter;

import java.util.ArrayList;
import java.util.List;

public class PhongTroChuTroAdapter extends RecyclerView.Adapter<PhongTroChuTroAdapter.PhongTroViewHolder> {
    private List<PhongTroChuTro2> mListPhongTro;
    private List<PhongTroChuTro2> mListPhongTroOld;
    private Activity activity;
    private int layoutID;


    private MyOnClickListener myOnClickListener;

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public PhongTroChuTroAdapter(List<PhongTroChuTro2> mListPhongTro, Activity activity, int layoutID) {
        this.mListPhongTro = mListPhongTro;
        this.mListPhongTroOld = mListPhongTro;
        this.activity = activity;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public PhongTroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_search_layout, parent, false);
        return new PhongTroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongTroViewHolder holder, int position) {
        PhongTroChuTro2 phongTroChuTro = mListPhongTro.get(position);
        if (phongTroChuTro == null) {
            return;
        }
        holder.soPhong.setText(phongTroChuTro.getPhongTro().getSoPhong() + "");
        holder.gia.setText(phongTroChuTro.getPhongTro().getGia() + " " + "₫");
        holder.loaiPhong.setText(phongTroChuTro.getPhongTro().getLoaiPhong() == Define.PHONG_TRONG ? "Phòng trống" : phongTroChuTro.getPhongTro().getLoaiPhong() == Define.PHONG_GHEP ? "Phòng ghép" : "Phòng đơn");
        holder.dienTich.setText(phongTroChuTro.getPhongTro().getDienTich() + "" + "㎡");
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnClickListener.OnClickItem(position, v);
            }
        };
//
//        holder.gioiTinh.setText(phongTroChuTro.getPhongTro().getGioiTinh());
//        holder.tienCoc.setText(phongTroChuTro.getPhongTro().getTienCoc());
//         holder.tienCoc.setText(phongTroChuTro.getPhongTro().getTienCoc());
//        holder.moTaChiTiet.setText(phongTroChuTro.getPhongTro().getMoTa());
//        holder.diaChi.setText(phongTroChuTro.getPhongTro().getDiaChiChiTiet());




    }

    @Override
    public int getItemCount() {
        if (mListPhongTro != null) {
            return mListPhongTro.size();
        }
        return 0;
    }

    public interface MyOnClickListener {
        void OnClickItem(int position, View v);
    }

    public class PhongTroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View.OnClickListener onClickListener;
        private TextView soPhong, gia, loaiPhong, dienTich;



        private List<HinhAnh> mListPhoto;
        private ChuTroImageSlideViewPager2Adapter adapterImage;

        private TextView gioiTinh, soLuongToiDa, tienCoc, tienDien,
                tienNuoc, moTaChiTiet, diaChi;

        public PhongTroViewHolder(@NonNull View itemView) {
            super(itemView);
            soPhong = itemView.findViewById(R.id.tv_soPhong);
            gia = itemView.findViewById(R.id.tv_gia);
            loaiPhong = itemView.findViewById(R.id.tv_loaiPhong);
            dienTich = itemView.findViewById(R.id.tv_dienTich);
            itemView.setOnClickListener(this);


            gioiTinh = itemView.findViewById(R.id.tv_detail_gio_tinh);
            soLuongToiDa = itemView.findViewById(R.id.tv_detail_gio_tinh);
            tienCoc = itemView.findViewById(R.id.tv_detail_gio_tinh);
            tienNuoc = itemView.findViewById(R.id.tv_detail_gio_tinh);
            tienDien = itemView.findViewById(R.id.tv_detail_gio_tinh);
            moTaChiTiet = itemView.findViewById(R.id.tv_detail_gio_tinh);
            diaChi = itemView.findViewById(R.id.tv_detail_gio_tinh);

        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    mListPhongTro = mListPhongTroOld;
                } else {
                    List<PhongTroChuTro2> list = new ArrayList<>();
                    for (PhongTroChuTro2 phongTroChuTro : mListPhongTroOld) {
                        if ((phongTroChuTro.getPhongTro().getSoPhong() + "").contains(strSearch)) {
                            list.add(phongTroChuTro);
                        }
                    }
                    mListPhongTro = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListPhongTro;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListPhongTro = (List<PhongTroChuTro2>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
