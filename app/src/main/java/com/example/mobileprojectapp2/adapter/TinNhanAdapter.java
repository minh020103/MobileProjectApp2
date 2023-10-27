package com.example.mobileprojectapp2.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.RoomMassageActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.TinNhan;
import com.makeramen.roundedimageview.RoundedImageView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TinNhanAdapter extends RecyclerView.Adapter<TinNhanAdapter.MyViewHolder> {


    Activity activity;
    ArrayList<TinNhan> arrayList;
    int layoutSendId;
    int layoutReceiveId;
    int sender;

    private  String anhDoiPhuong;

    public String getAnhDoiPhuong() {
        return anhDoiPhuong;
    }

    public void setAnhDoiPhuong(String anhDoiPhuong) {
        this.anhDoiPhuong = anhDoiPhuong;
    }

    public TinNhanAdapter(Activity activity, ArrayList<TinNhan> arrayList, int layoutSendId, int layoutReceiveId, int sender) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.layoutSendId = layoutSendId;
        this.layoutReceiveId = layoutReceiveId;
        this.sender = sender;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        CardView cardview = (CardView) inflater.inflate(viewType,parent,false);
        return new MyViewHolder(cardview);
    }


    private String formatDate(Timestamp timestamp){
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
        return newFormat.format(date);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TinNhan tinNhan = arrayList.get(position);
        holder.thoiGian.setText(formatDate(arrayList.get(position).getCreated_at()));
        if(arrayList.get(position).getIdTaiKhoan()!=sender){
            Glide.with(activity.getLayoutInflater().getContext()).load(Const.DOMAIN +anhDoiPhuong).into(holder.roundedImageView);
        }
        holder.textMessage.setText(tinNhan.getNoiDung());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(arrayList.get(position).getIdTaiKhoan()==sender){
            return layoutSendId;
        }
        return layoutReceiveId;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        TextView thoiGian;
        TextView textMessage;
        RoundedImageView roundedImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thoiGian = itemView.findViewById(R.id.thoiGian);
            textMessage = itemView.findViewById(R.id.textMessage);
            roundedImageView = itemView.findViewById(R.id.img_doiphuong);
        }
    }
}
