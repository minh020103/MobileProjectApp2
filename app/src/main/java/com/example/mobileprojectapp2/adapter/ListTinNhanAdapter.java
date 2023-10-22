package com.example.mobileprojectapp2.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class ListTinNhanAdapter extends RecyclerView.Adapter<ListTinNhanAdapter.MyViewHolder> {

    Activity activity;
    int layoutId;
    ArrayList<PhongTinNhan> arrayList;
    OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public ListTinNhanAdapter(Activity activity, int layoutId, ArrayList<PhongTinNhan> arrayList) {
        this.activity = activity;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView cardView = (CardView)inflater.inflate(viewType,parent,false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PhongTinNhan phongTinNhan = arrayList.get(position);
        ///Truy xuất tài khoản thông qua id ở đây
        holder.ten_item_message.setText("Nguyễn Văn Nan");
        holder.tinnhanmoinhat_item_message.setText(phongTinNhan.getTinNhanMoiNhat());
        holder.thoigiantinnhan_item_message.setText(phongTinNhan.getThoiGianCuaTinNhan());
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemListener.onClickItem(position,view);
            }
        };
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    public interface OnClickItemListener{
        void onClickItem(int position, View v);
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RoundedImageView img_item_message;
        TextView ten_item_message;
        TextView tinnhanmoinhat_item_message;
        TextView thoigiantinnhan_item_message;
        View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item_message = itemView.findViewById(R.id.img_item_message);
            ten_item_message = itemView.findViewById(R.id.ten_item_massage);
            tinnhanmoinhat_item_message = itemView.findViewById(R.id.tinnhanmoinhat_item_massage);
            thoigiantinnhan_item_message = itemView.findViewById(R.id.thoigiantinnhan_item_message);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}

