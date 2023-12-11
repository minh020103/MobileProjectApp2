package com.example.mobileprojectapp2.fragment.nguoithue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.nguoithue.DetailPhongTroNguoiThueActivity;
import com.example.mobileprojectapp2.activity.nguoithue.DanhSachPhongGhepActivity;
import com.example.mobileprojectapp2.activity.nguoithue.DanhSachPhongGoiYActivity;
import com.example.mobileprojectapp2.activity.nguoithue.RenterActivity;
import com.example.mobileprojectapp2.activity.nguoithue.RoomOfDistrictActivity;
import com.example.mobileprojectapp2.activity.nguoithue.RoomRandomActivity;
import com.example.mobileprojectapp2.activity.nguoithue.SearchQuanActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh;
import com.example.mobileprojectapp2.component.MComponent;
import com.example.mobileprojectapp2.datamodel.Banner;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.Quan;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.CommentAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhongAdaprer;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.QuanAdaprer;
import com.example.mobileprojectapp2.viewpager2adapter.NguoiThueImageSlideViewPager2Adapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends AbstractFragment {
    private int idTaiKhoan = 1;
    private NestedScrollView nsvHome;
    private ViewGroup container;
    private ViewPager2 vp2Banner;
    private TextView tvTimPhong;
    private LinearLayout llGhep, llRandom, llSearch, llReComment;
    private RecyclerView rcvQuan, rcvPhong;
    // Adapter
    private QuanAdaprer quanAdaprer;
    private PhongAdaprer phongAdaprer;
    private NguoiThueImageSlideViewPager2Adapter imagesAdapter;
    private CommentAdapter commentAdapter;
    // List
    private LinkedList<Quan> listQuan;
    private LinkedList<PhongTro> listPhong;
    private LinkedList<Banner> listHinh;
    private LinkedList<PhongBinhLuan> listComment;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.nguoithue_fragment_home_layout, container, false);
        anhXa(fragmentLayout);
        sharedPreferences = getContext().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        this.container = container;
        listQuan = new LinkedList<>();
        listPhong = new LinkedList<>();
        listHinh = new LinkedList<>();
        listComment = new LinkedList<>();
        // set adapter for viewpager2
        // 1. adapter of quận

        imagesAdapter = new NguoiThueImageSlideViewPager2Adapter(getActivity(), listHinh, R.layout.chutro_item_image_layout);

        vp2Banner.setAdapter(imagesAdapter);
        batSuKien(fragmentLayout);
        // set adapter for RecyclerView
        // 1. adapter of quận
        quanAdaprer = new QuanAdaprer(getActivity(), listQuan, R.layout.nguoithue_item_quan_layout);
        LinearLayoutManager layoutManagerQuan = new LinearLayoutManager(getActivity());
        layoutManagerQuan.setOrientation(RecyclerView.VERTICAL);
        layoutManagerQuan = new GridLayoutManager(getActivity(), 3);
        rcvQuan.setLayoutManager(layoutManagerQuan);
        rcvQuan.setAdapter(quanAdaprer);
        // 2. adapter of phòng
        phongAdaprer = new PhongAdaprer(getActivity(), listPhong, R.layout.nguoithue_item_room_layout);
        LinearLayoutManager layoutManagerPhong = new LinearLayoutManager(getActivity());
        layoutManagerPhong.setOrientation(RecyclerView.VERTICAL);
        layoutManagerPhong = new GridLayoutManager(getActivity(), 2);
        rcvPhong.setLayoutManager(layoutManagerPhong);
        rcvPhong.setAdapter(phongAdaprer);


        batSuKienAdapterQuan();
        batSuKienPhong();
        return fragmentLayout;
    }

    private void batSuKienPhong() {
        phongAdaprer.setOnClick(new PhongAdaprer.OnClick() {
            @Override
            public void onClickItemListenner(int position, View view) {
                //TODO: Chuyển qua màn hình chi tiết
                Intent intent = new Intent(getActivity(), DetailPhongTroNguoiThueActivity.class);
                intent.putExtra("idPhong", listPhong.get(position).getId());
                startActivity(intent);


            }

            @Override
            public void onClickComment(int position, View view) {
                showBottomSheetComment(position, view);
            }
        });
    }

    private void batSuKienAdapterQuan() {
        quanAdaprer.setOnClick(new QuanAdaprer.OnClick() {
            @Override
            public void onClickItemListenner(int position, View view) {
                Intent intent = new Intent(getActivity(), RoomOfDistrictActivity.class);
                intent.putExtra("idQuan", listQuan.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        RenterActivity.viewPager2NguoiThue.setUserInputEnabled(true);
        getDataForImages();
        getDataForQuan();
        getDataForPhong();
    }

    private void getDataForImages() {
        listHinh.clear();
        ApiServiceMinh.apiService.layTatCaBanner().enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                if (response.code() == 200) {
                    vp2Banner.setBackground(null);
                    listHinh.addAll(response.body());
                    imagesAdapter.notifyDataSetChanged();
                } else {
                    vp2Banner.setBackground(getActivity().getResources().getDrawable(R.drawable.thuduc, getActivity().getTheme()));
                }
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                vp2Banner.setBackground(getActivity().getResources().getDrawable(R.drawable.thuduc, getActivity().getTheme()));
            }
        });
        imagesAdapter.notifyDataSetChanged();
    }

    private void getDataForPhong() {
        listPhong.clear();
        ApiServiceMinh.apiService.layTatCaPhongTro(Const.PHONG_TRONG, Const.NHO_DEN_LON).enqueue(new Callback<List<PhongTro>>() {
            @Override
            public void onResponse(Call<List<PhongTro>> call, Response<List<PhongTro>> response) {
                if (response.code() == 200) {
                    Log.d("TAG", "onResponse: OKKKK: " + response.body().size());
                    listPhong.addAll(response.body());
                    phongAdaprer.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhongTro>> call, Throwable t) {

            }
        });


    }

    private void getDataForQuan() {
        listQuan.clear();
        ApiServiceMinh.apiService.layTatCaQuan().enqueue(new Callback<List<Quan>>() {
            @Override
            public void onResponse(Call<List<Quan>> call, Response<List<Quan>> response) {
                if (response.code() == 200) {
                    listQuan.addAll(response.body());
                    quanAdaprer.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Quan>> call, Throwable t) {

            }
        });


    }

    private void batSuKien(View fragmentLayout) {
        fragmentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                RenterActivity.viewPager2NguoiThue.setUserInputEnabled(true);
                return false;
            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuyenQuaManHinhTimKiem();
            }
        });
        tvTimPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuyenQuaManHinhTimKiem();
            }
        });
        llGhep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DanhSachPhongGhepActivity.class);
                startActivity(intent);
            }
        });
        llReComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DanhSachPhongGoiYActivity.class);
                startActivity(intent);
            }
        });
        llRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RoomRandomActivity.class);
                startActivity(intent);
            }
        });
    }

    private void chuyenQuaManHinhTimKiem() {
        startActivity(new Intent(getActivity(), SearchQuanActivity.class));
    }

    private void anhXa(View fragmentLayout) {
        nsvHome = fragmentLayout.findViewById(R.id.nsvHome);
        vp2Banner = fragmentLayout.findViewById(R.id.vp2Banner);
        tvTimPhong = fragmentLayout.findViewById(R.id.tvTimPhong);
        llGhep = fragmentLayout.findViewById(R.id.llGhep);
        llRandom = fragmentLayout.findViewById(R.id.llRandom);
        rcvQuan = fragmentLayout.findViewById(R.id.rcvQuan);
        rcvPhong = fragmentLayout.findViewById(R.id.rcvPhong);
        llSearch = fragmentLayout.findViewById(R.id.llSearch);
        llReComment = fragmentLayout.findViewById(R.id.llReComment);
    }

    private void showBottomSheetComment(int position, View view) {
        MComponent.comment(getActivity(), container, listPhong.get(position).getId(), listComment, idTaiKhoan);
    }
}
