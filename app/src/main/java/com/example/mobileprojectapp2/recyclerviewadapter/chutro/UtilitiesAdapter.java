package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

import org.w3c.dom.Text;

import java.util.List;

public class UtilitiesAdapter extends RecyclerView.Adapter<UtilitiesAdapter.MyViewHolder> {

    private Activity activity;
    private List<TienIch> list;
    private List<TienIch> listSeleted;
    private int layoutID;
    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public UtilitiesAdapter(Activity activity, List<TienIch> list, List<TienIch> listSeleted, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.listSeleted = listSeleted;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView view = (CardView) inflater.inflate(layoutID,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TienIch tienIch = list.get(position);
        if (tienIch != null) {
            holder.tvTenTienIch.setText(tienIch.getTen());
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN+tienIch.getHinh()).into(holder.imgAnhTienIch);
            holder.onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.cbTienIch.isChecked()) {
                        listSeleted.add(tienIch);
                    } else {
                        listSeleted.remove(tienIch);
                    }
                    onClick.checkBox(position, view);
                }
            };
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClick{
        void checkBox(int position, View v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox cbTienIch;
        TextView tvTenTienIch;
        ImageView imgAnhTienIch;
        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbTienIch = itemView.findViewById(R.id.cbTienIch);
            tvTenTienIch = itemView.findViewById(R.id.tvTenTienIch);
            imgAnhTienIch = itemView.findViewById(R.id.imgHinhTienIch);

            cbTienIch.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
