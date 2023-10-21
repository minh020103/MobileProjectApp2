package com.example.mobileprojectapp2.fragment.chutro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.MotelRoomAdapter;

import java.util.LinkedList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class ListRoomFragment extends AbstractFragment{
    private RecyclerView rcvListMotelRoom;
    private LinearLayoutManager layoutManager;
    private MotelRoomAdapter adapter;
    private List<PhongTro> list;
    private List<HinhAnh> listHA;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_list_room_layout, container, false);
        anhXa(fragmentLayout);
        
        
        return fragmentLayout;
    }

    private void anhXa(View fragmentLayout) {
        rcvListMotelRoom = fragmentLayout.findViewById(R.id.rcvListMotelRoom);
        list = new LinkedList<>();
        addList();
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new MotelRoomAdapter(getActivity(), list, R.layout.chutro_cardview_item_room_layout);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListMotelRoom.setLayoutManager(layoutManager);
        rcvListMotelRoom.setAdapter(adapter);
    }

    private void addList() {
        listHA = new LinkedList<>();
        listHA.add(new HinhAnh(1,1,"fsdrrf"));
        listHA.add(new HinhAnh(1,1,"fsdrrf"));
        listHA.add(new HinhAnh(1,1,"fsdrrf"));
        listHA.add(new HinhAnh(1,1,"fsdrrf"));
        list.add(new PhongTro(listHA));
        list.add(new PhongTro(listHA));
        list.add(new PhongTro(listHA));
        list.add(new PhongTro(listHA));


    }
}
