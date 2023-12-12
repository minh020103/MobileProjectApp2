package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.AddRoomActivity;
import com.example.mobileprojectapp2.activity.chutro.AuthenticationActivity;
import com.example.mobileprojectapp2.activity.chutro.EditRoomActivity;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.activity.chutro.RenterListActivity;
import com.example.mobileprojectapp2.activity.chutro.ReviewRoomActivity;
import com.example.mobileprojectapp2.activity.chutro.SearchActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.component.MComponent;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.model.ChuTro;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.CommentAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.MotelRoomAdapter;
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

public class ListRoomFragment extends AbstractFragment {
    private int idChuTro;
    private int idTaiKhoan;
    private NestedScrollView ntsvListRoom;
    private RecyclerView rcvListMotelRoom;
    private LinearLayoutManager layoutManager;
    private MotelRoomAdapter roomAdapter;
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private LinkedList<PhongBinhLuan> listComment;
    private ViewGroup container;
    private LinearLayout llAdd;
    private LinearLayout llSearch;
    private List<PhongTroChuTro> phongTroOfChuTroList;
    private ProgressBar pbLoadmoreRoom, pbReLoad;
    private int pageRoom = 1;
    private final int quantityRoom = 5;
    private int pageComment = 1;
    private final int quantityComment = 10;
    private boolean check = false;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_list_room_layout, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idChuTro = sharedPreferences.getInt("idChuTro", -1);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        anhXa(fragmentLayout);
        this.container = container;
        getDataFromAPI();
        onScrollView();
        onClickButtomInFragment();
        onClickItemInCardView();


        return fragmentLayout;
    }


    private void onScrollView() {
        ntsvListRoom.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    pageRoom++;
                    pbLoadmoreRoom.setVisibility(View.VISIBLE);
                    getDataFromAPI();

                }
                if (scrollY == 0) {
                    pageRoom = 1;
                    pbReLoad.setVisibility(View.VISIBLE);
                    phongTroOfChuTroList.clear();
                    getDataFromAPI();

                }
            }
        });
    }

    private void onClickButtomInFragment() {
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 22/11/2023: Kiểm tra tài khoản phải ở trạng thái xác thực thì mới được thêm phòng
                ApiServicePhuc.apiService.getChuTroById(idTaiKhoan).enqueue(new Callback<ChuTro>() {
                    @Override
                    public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                        if (response.code() == 200) {
                            if (response.body().getXacThuc() == 1) {
                                Intent intent = new Intent(getActivity(), AddRoomActivity.class);
                                startActivity(intent);
                            } else {
                                alertFailAddRoom("Bạn cần xác thực tài khoản trước khi thêm phòng.\nNhấn OK để chuyển qua màn hình xác thực.\nNhấn CANCAL để tắt thông báo.");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ChuTro> call, Throwable t) {

                    }
                });

            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MotelRoomOwnerActivity.vp2Chutro.setUserInputEnabled(false);
        pageRoom = 1;
        phongTroOfChuTroList.clear();
        getDataFromAPI();

    }

    private void getDataFromAPI() {
        ApiServiceMinh.apiService.layTatCaPhongTroQuanLy(idChuTro, pageRoom, quantityRoom).enqueue(new Callback<List<PhongTroChuTro>>() {
            @Override
            public void onResponse(Call<List<PhongTroChuTro>> call, Response<List<PhongTroChuTro>> response) {
                if (response.code() == 200) {
                    if (response.body().size() == 0) {
                        pageRoom--;
                        Toast.makeText(getActivity(), "Đã hết dữ liệu", Toast.LENGTH_LONG).show();
                    } else {
                        phongTroOfChuTroList.addAll(response.body());
                        roomAdapter.notifyDataSetChanged();
                    }
                    pbReLoad.setVisibility(View.GONE);
                    pbLoadmoreRoom.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PhongTroChuTro>> call, Throwable t) {

            }
        });
    }

    private void onClickItemInCardView() {
        roomAdapter.setOnClickItemRoomListener(new MotelRoomAdapter.OnClickItemRoomListener() {
            @Override
            public void setOnClickListPerson(int position, View view) {
                Intent intent = new Intent(getActivity(), RenterListActivity.class);
                intent.putExtra("idPhong", phongTroOfChuTroList.get(position).getIdPhongTro());
                intent.putExtra("soPhong", phongTroOfChuTroList.get(position).getPhongTro().getSoPhong());
                startActivity(intent);
            }

            @Override
            public void setOnClickEdit(int position, View view) {
                Intent intent = new Intent(getActivity(), EditRoomActivity.class);
                intent.putExtra("idPhong", phongTroOfChuTroList.get(position).getIdPhongTro());
                startActivity(intent);
            }

            @Override
            public void setOnClickDelete(int position, View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage(" Bạn có chắc sẽ xóa phòng này?\n Nếu chấp nhận thì phòng sẽ bị xóa!!");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Chấp nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ApiServiceMinh.apiService.xoaPhongTroCuaChuTro(idChuTro, phongTroOfChuTroList.get(position).getIdPhongTro()).enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if (response.code() == 200) {
                                            if (response.body() == 1) {
                                                phongTroOfChuTroList.remove(phongTroOfChuTroList.get(position));
                                                roomAdapter.notifyDataSetChanged();
                                                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {
                                        Log.d("TAG", "onFailure: " + call.request());
                                        Log.d("TAG", "onFailure: " + t);
                                    }
                                });
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }

            @Override
            public void setOnClickVideoReview(int position, View view) {
                Intent intent = new Intent(getActivity(), ReviewRoomActivity.class);
                intent.putExtra("idPhong", phongTroOfChuTroList.get(position).getIdPhongTro());
                startActivity(intent);
            }

            @Override
            public void setOnClickComment(int position, View view) {
                showBottomSheetComment(position, view);
            }

            @Override
            public void setOnClickRating(int position, View view) {
                MComponent.rating(getActivity(), phongTroOfChuTroList.get(position).getIdPhongTro(), idTaiKhoan);
            }

            @Override
            public void setOnClickControls(int position, View view, int trangThaiHoatDong) {
                int theFirst = 1;
                if (trangThaiHoatDong == Const.KHONG_HOAT_DONG && theFirst == 1) {
                    callApiBatTatHoatDong(position, Const.HOAT_DONG);
                    theFirst++;
                    Log.d("TAG_check", "setOnClickControls:"+trangThaiHoatDong + " "+theFirst);
                }
                if(trangThaiHoatDong == Const.HOAT_DONG && theFirst == 1) {
                    callApiBatTatHoatDong(position, Const.KHONG_HOAT_DONG);
                    theFirst++;
                    Log.d("TAG_check", "setOnClickControls:"+trangThaiHoatDong + " "+theFirst);
                }
            }


        });
    }
    private void callApiBatTatHoatDong(int position, int hoatDong){
        com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh.apiService.updateHoatDong(phongTroOfChuTroList.get(position).getIdPhongTro(), hoatDong, idChuTro).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == 200) {
                    if (response.body() == Const.PHONG_DA_CO_NGUOI_THUE) {
                        alertFail("Phòng đã có người thuê không thể tắt hoạt động");
                    }
                    if (response.body() == Const.DA_DAT_SO_LUONG_PHONG_TOI_DA) {
                        alertFail("Số lượng phòng hoạt động đã đạt tối đa gói dịch vụ không thể bật");
                    }
                    if (response.body() == Const.CHUA_DANG_KY_DICH_VU) {
                        alertFail("Bạn chưa đăng ký dịch vụ hãy đăng ký dịch vụ để bật hoạt động phòng");
                    }
                    if (response.body() == 1){
                        phongTroOfChuTroList.get(position).getPhongTro().setHoatDong(hoatDong);
                        roomAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

    }

    private void showBottomSheetComment(int position, View view) {
        MComponent.comment(getActivity(),  container, phongTroOfChuTroList.get(position).getIdPhongTro(), listComment,idTaiKhoan);

    }



    private void anhXa(View fragmentLayout) {
        ntsvListRoom = fragmentLayout.findViewById(R.id.ntsvListRoom);
        pbLoadmoreRoom = fragmentLayout.findViewById(R.id.pbLoadmore);
        pbReLoad = fragmentLayout.findViewById(R.id.pbReLoad);
        llAdd = fragmentLayout.findViewById(R.id.llAdd);
        llSearch = fragmentLayout.findViewById(R.id.llSearch);
        rcvListMotelRoom = fragmentLayout.findViewById(R.id.rcvListMotelRoom);
        phongTroOfChuTroList = new LinkedList<>();
        layoutManager = new LinearLayoutManager(getContext());
        roomAdapter = new MotelRoomAdapter(getActivity(), phongTroOfChuTroList, R.layout.chutro_cardview_item_room_layout);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListMotelRoom.setLayoutManager(layoutManager);
        rcvListMotelRoom.setAdapter(roomAdapter);
        listComment = new LinkedList<>();

    }

    private void alertFailAddRoom(String s) {
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Thông báo")
                .setMessage(s)
                .setIcon(R.drawable.iconp_fail)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void alertFail(String s) {
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Thông báo")
                .setMessage(s)
                .setIcon(R.drawable.iconp_fail)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


}
