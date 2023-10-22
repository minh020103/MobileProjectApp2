package com.example.mobileprojectapp2.fragment.chutro;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.datamodel.Comment;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.player.RatingPlayer;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.CommentAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.MotelRoomAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.LinkedList;
import java.util.List;

public class ListRoomFragment extends AbstractFragment {
    private RecyclerView rcvListMotelRoom;
    private LinearLayoutManager layoutManager;
    private MotelRoomAdapter roomAdapter;
    private List<PhongTro> phongTroList;
    private List<HinhAnh> listHA;
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private List<Comment> listComment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_list_room_layout, container, false);
        anhXa(fragmentLayout);

        onClickItemInCardView();


        return fragmentLayout;
    }

    private void onClickItemInCardView() {
        roomAdapter.setOnClickItemRoomListener(new MotelRoomAdapter.OnClickItemRoomListener() {
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
                builder.create();


                builder.show();
                ImageView imgClose = viewDialog.findViewById(R.id.imgClose);
                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Close
                    }
                });
                RatingPlayer ratingPlayer = new RatingPlayer(viewDialog);
                ratingPlayer.setStarForRating(0);
                ratingPlayer.rating();
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
        View viewBottomSheetCommnent = getLayoutInflater().inflate(R.layout.chutro_buttom_sheet_layout, null);
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

        rcvListMotelRoom = fragmentLayout.findViewById(R.id.rcvListMotelRoom);
        phongTroList = new LinkedList<>();
        addList();
        layoutManager = new LinearLayoutManager(getContext());
        roomAdapter = new MotelRoomAdapter(getActivity(), phongTroList, R.layout.chutro_cardview_item_room_layout);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListMotelRoom.setLayoutManager(layoutManager);
        rcvListMotelRoom.setAdapter(roomAdapter);

    }

    private void addList() {
        listHA = new LinkedList<>();
        listHA.add(new HinhAnh(1, 1, "fsdrrf"));
        listHA.add(new HinhAnh(1, 1, "fsdrrf"));
        listHA.add(new HinhAnh(1, 1, "fsdrrf"));
        listHA.add(new HinhAnh(1, 1, "fsdrrf"));
        phongTroList.add(new PhongTro(listHA));
        phongTroList.add(new PhongTro(listHA));
        phongTroList.add(new PhongTro(listHA));
        phongTroList.add(new PhongTro(listHA));

        listComment = new LinkedList<>();
        listComment.add(new Comment());
        listComment.add(new Comment());
        listComment.add(new Comment());
        listComment.add(new Comment());
        listComment.add(new Comment());
        listComment.add(new Comment());
    }
}
