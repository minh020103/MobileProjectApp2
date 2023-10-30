package com.example.mobileprojectapp2.fragment.chutro;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.AddRoomActivity;
import com.example.mobileprojectapp2.activity.chutro.EditRoomActivity;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.activity.chutro.RenterListActivity;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.Comment;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.player.RatingPlayer;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.CommentAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.MotelRoomAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRoomFragment extends AbstractFragment {
    private RecyclerView rcvListMotelRoom;
    private LinearLayoutManager layoutManager;
    private MotelRoomAdapter roomAdapter;
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private List<Comment> listComment;
    private ViewGroup container;
    private LinearLayout llAdd;
    private List<PhongTroChuTro> phongTroOfChuTroList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_list_room_layout, container, false);
        anhXa(fragmentLayout);

        this.container = container;
        onClickButtomInFragment();
        onClickItemInCardView();


        return fragmentLayout;
    }

    private void onClickButtomInFragment() {
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddRoomActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MotelRoomOwnerActivity.vp2Chutro.setUserInputEnabled(false);
        getDataFromAPI();
    }

    private void getDataFromAPI() {
        ApiServiceMinh.apiService.layTatCaPhongTroQuanLy(2).enqueue(new Callback<List<PhongTroChuTro>>() {
            @Override
            public void onResponse(Call<List<PhongTroChuTro>> call, Response<List<PhongTroChuTro>> response) {
                if (response.code() == 200){
                    phongTroOfChuTroList.clear();
                    phongTroOfChuTroList.addAll(response.body());
                    roomAdapter.notifyDataSetChanged();
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
                startActivity(intent);
            }

            @Override
            public void setOnClickDelete(int position, View view) {

            }

            @Override
            public void setOnClickComment(int position, View view) {
                showBottomSheetComment(position, view);
            }

            @Override
            public void setOnClickRating(int position, View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View viewDialog = inflater.inflate(R.layout.chutro_dialog_rating_layout, null);
                builder.setView(viewDialog);
                AlertDialog dialog = builder.create();


                //builder.show();
                dialog.show();
                ImageView imgClose = viewDialog.findViewById(R.id.imgClose);
                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Close
                        dialog.hide();
                    }
                });
                RatingPlayer ratingPlayer = new RatingPlayer(viewDialog);
                // đổi sao ra giao diện
                ratingPlayer.setStarForRating(0);
                // chọn sao
                ratingPlayer.rating();
                //  hàm sử lý số sao đã chọn được ủy quyền
                ratingPlayer.setiRatingPlayer(new RatingPlayer.IRatingPlayer() {
                    @Override
                    public void layDanhGia(int rating) {
                        // được ủy quyền ra ngoài để lấy lượng đánh giá
                        Log.d("TAG", "setOnClickRating: "+rating);
                    }
                });

            }
        });
    }

    private void showBottomSheetComment(int position, View view) {
        View viewBottomSheetCommnent = getLayoutInflater().inflate(R.layout.chutro_buttom_sheet_layout, container, false);
        BottomSheetDialog bottomSheetComment = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        bottomSheetComment.setContentView(viewBottomSheetCommnent);

        //
        rcvComment = viewBottomSheetCommnent.findViewById(R.id.rcvComment);
        commentAdapter = new CommentAdapter(getActivity(), listComment, R.layout.chutro_cardview_comment_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(commentAdapter);


        bottomSheetComment.show();


    }

    private void getDataForDialog(int position, View view) {

    }


    private void anhXa(View fragmentLayout) {
        llAdd = fragmentLayout.findViewById(R.id.llAdd);
        rcvListMotelRoom = fragmentLayout.findViewById(R.id.rcvListMotelRoom);
        phongTroOfChuTroList = new LinkedList<>();
        addList();
        layoutManager = new LinearLayoutManager(getContext());
        roomAdapter = new MotelRoomAdapter(getActivity(), phongTroOfChuTroList, R.layout.chutro_cardview_item_room_layout);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListMotelRoom.setLayoutManager(layoutManager);
        rcvListMotelRoom.setAdapter(roomAdapter);

    }

    private void addList() {




        listComment = new LinkedList<>();
        listComment.add(new Comment());
        listComment.add(new Comment());
        listComment.add(new Comment());
        listComment.add(new Comment());
        listComment.add(new Comment());
        listComment.add(new Comment());
    }
}
