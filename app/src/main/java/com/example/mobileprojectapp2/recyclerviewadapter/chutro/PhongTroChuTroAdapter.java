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
import com.example.mobileprojectapp2.model.Define;
import com.example.mobileprojectapp2.model.PhongTroChuTro;

import java.util.ArrayList;
import java.util.List;

public class PhongTroChuTroAdapter extends RecyclerView.Adapter<PhongTroChuTroAdapter.PhongTroViewHolder> {
    private List<PhongTroChuTro> mListPhongTro;
    private List<PhongTroChuTro> mListPhongTroOld;
    private Activity activity;
    private int layoutID;



    public PhongTroChuTroAdapter(List<PhongTroChuTro> mListPhongTro, Activity activity, int layoutID) {
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
        PhongTroChuTro phongTroChuTro = mListPhongTro.get(position);
        if (phongTroChuTro == null) {
            return;
        }
        holder.soPhong.setText(phongTroChuTro.getPhongTro().getSoPhong() + "");
        holder.gia.setText(phongTroChuTro.getPhongTro().getGia() + " " + "₫");

        holder.loaiPhong.setText(phongTroChuTro.getPhongTro().getLoaiPhong() == Define.PHONG_TRONG ? "Phòng trống" : phongTroChuTro.getPhongTro().getLoaiPhong() == Define.PHONG_GHEP ? "Phòng ghép" : "Phòng đơn");

        holder.dienTich.setText(phongTroChuTro.getPhongTro().getDienTich() + "" + "㎡");

    }

    @Override
    public int getItemCount() {
        if (mListPhongTro != null) {
            return mListPhongTro.size();
        }

        return 0;
    }

    public class PhongTroViewHolder extends RecyclerView.ViewHolder {

        private TextView soPhong, gia, loaiPhong, dienTich, tvThongBao;

        public PhongTroViewHolder(@NonNull View itemView) {
            super(itemView);

            soPhong = itemView.findViewById(R.id.tv_soPhong);
            gia = itemView.findViewById(R.id.tv_gia);
            loaiPhong = itemView.findViewById(R.id.tv_loaiPhong);
            dienTich = itemView.findViewById(R.id.tv_dienTich);
            tvThongBao = itemView.findViewById(R.id.tv_thongbao);
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
                    List<PhongTroChuTro> list = new ArrayList<>();
                    for (PhongTroChuTro phongTroChuTro : mListPhongTroOld) {
                        if ((phongTroChuTro.getPhongTro().getSoPhong()+"").contains(strSearch)) {
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
                mListPhongTro = (List<PhongTroChuTro>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
