package com.example.mobileprojectapp2.recyclerviewadapter.chutro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {

    private Activity activity;
    private List<Bitmap> bitmapList;
    private int layoutID;
    private OnCLick onCLick;


    public ImagesAdapter(Activity activity, List<Bitmap> bitmapList, int layoutID) {
        this.activity = activity;
        this.bitmapList = bitmapList;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout view = (LinearLayout) inflater.inflate(layoutID,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bitmap hinhAnh = bitmapList.get(position);

        holder.imgItem.setImageBitmap(hinhAnh);
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLick.delete(position,view);
            }
        };
    }

    public interface OnCLick{
        void delete(int position, View v);
    }

    public void setOnCLick(OnCLick onCLick) {
        this.onCLick = onCLick;
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgItem, imgXoaHinh;
        View.OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            imgXoaHinh = itemView.findViewById(R.id.imgXoaHinh);

            imgXoaHinh.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(view);
        }
    }
}
