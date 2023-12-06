package com.example.mobileprojectapp2.recyclerviewadapter.chutro;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.model.Selected;

import java.util.List;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.SelectedViewHolder> {
    private Activity activity;
    private List<Selected> list;

    private int layoutID;

    private MyOnCLickListener myOnCLickListener;

    public void setMyOnCLickListener(MyOnCLickListener myOnCLickListener) {
        this.myOnCLickListener = myOnCLickListener;
    }

    public SelectedAdapter(Activity activity, List<Selected> list, int layoutID) {
        this.activity = activity;
        this.list = list;
        this.layoutID = layoutID;
    }

    @NonNull
    @Override
    public SelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = activity.getLayoutInflater();
//        LinearLayout view = (LinearLayout) inflater.inflate(layoutID, parent, false);
//    return new TienIchViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
        return new SelectedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedViewHolder holder, int position) {
        Selected selected = list.get(position);
        if (selected == null) {
            return;
        }
//        holder.tvTenTienIch.setText("abc");
//        Log.d("TAG", "onBindViewHolder: " + tienIch);

        holder.tv_name_selected.setText(selected.getName());
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.img_close) {
                    myOnCLickListener.OnCLickCloseItem(position, v);
                } else {
                    myOnCLickListener.OnClickImg(position, v);
                }
            }
        };
    }

    public interface MyOnCLickListener {
        void OnClickImg(int position, View v);

        void OnCLickCloseItem(int position, View v);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class SelectedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View.OnClickListener onClickListener;

        private TextView tv_name_selected;
        private ImageView imgClose;

        public SelectedViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name_selected = itemView.findViewById(R.id.tv_name_selected);
            imgClose = itemView.findViewById(R.id.img_close);
            itemView.setOnClickListener(this);
            imgClose.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v);
        }
    }
}
