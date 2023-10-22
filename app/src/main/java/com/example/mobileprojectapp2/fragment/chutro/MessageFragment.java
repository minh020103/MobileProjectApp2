package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.RoomMassageActivity;
import com.example.mobileprojectapp2.adapter.ListTinNhanAdapter;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;

import java.util.ArrayList;

public class MessageFragment extends AbstractFragment{
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PhongTinNhan> arrayList;
    ListTinNhanAdapter listTinNhanAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragmant_message_layout, container, false);
        anhXa(fragmentLayout);
        setDuLieu();

        return fragmentLayout;
    }
    private void setDuLieu(){
        arrayList = new ArrayList<>();
        arrayList.add(new PhongTinNhan(1,1,2,"Hello Bạn!","2 phút"));
        arrayList.add(new PhongTinNhan(2,1,3,"Hello Bạn Yêu!","6 giờ"));
        arrayList.add(new PhongTinNhan(3,1,4,"Anh Yêu Em!","2 giờ"));
        arrayList.add(new PhongTinNhan(4,1,5,"Hello Bae!","12 giờ"));
        arrayList.add(new PhongTinNhan(5,1,6,"Hello Baby!","10 giờ"));
        arrayList.add(new PhongTinNhan(6,1,7,"Em ăn cơm chưa!","7 giờ"));
        arrayList.add(new PhongTinNhan(7,1,8,"Em đâu rồi!","8 giờ"));
        arrayList.add(new PhongTinNhan(8,1,9,"Hello Bạn Yêu!","6 giờ"));
        arrayList.add(new PhongTinNhan(9,1,10,"Anh Yêu Em!","2 giờ"));
        arrayList.add(new PhongTinNhan(10,1,11,"Hello Bae!","12 giờ"));
        arrayList.add(new PhongTinNhan(11,1,12,"Hello Baby!","10 giờ"));
        arrayList.add(new PhongTinNhan(12,1,13,"Em ăn cơm chưa!","7 giờ"));
        listTinNhanAdapter = new ListTinNhanAdapter(getActivity(),R.layout.cardview_item_message,arrayList);
        listTinNhanAdapter.setOnClickItemListener(new ListTinNhanAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int position, View v) {
                Intent intent = new Intent(getActivity(), RoomMassageActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listTinNhanAdapter);
    }
    private void anhXa(View fragment){
        recyclerView = fragment.findViewById(R.id.recyclerViewTinNhan);
    }
}
