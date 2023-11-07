package com.example.mobileprojectapp2.fragment.chutro;

import android.content.Intent;
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
import com.example.mobileprojectapp2.activity.chutro.EditRoomActivity;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.Comment;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
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
    private final int idChuTro = 2;
    private final int idTaiKhoan = 3;
    private NestedScrollView ntsvListRoom;
    private RecyclerView rcvListMotelRoom;
    private LinearLayoutManager layoutManager;
    private MotelRoomAdapter roomAdapter;
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private LinkedList<PhongBinhLuan> listComment;
    private ViewGroup container;
    private LinearLayout llAdd;
    private List<PhongTroChuTro> phongTroOfChuTroList;
    private ProgressBar pbLoadmoreRoom;
    private int pageRoom = 1;
    private final int quantityRoom = 5;
    private NestedScrollView ntsvListComment;
    private int pageComment = 1;
    private final int quantityComment = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = null;
        fragmentLayout = inflater.inflate(R.layout.chutro_fragment_list_room_layout, container, false);
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
            }
        });
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
                        Log.d("TAG", "setOnClickRating: " + rating);
                    }
                });

            }
        });
    }

    private void showBottomSheetComment(int position, View view) {
        pageComment = 1;
        listComment.clear();
        View viewBottomSheetCommnent = getLayoutInflater().inflate(R.layout.chutro_buttom_sheet_comment_layout, container, false);
        BottomSheetDialog bottomSheetComment = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        bottomSheetComment.setContentView(viewBottomSheetCommnent);
        ProgressBar pbLoadmoreComment = viewBottomSheetCommnent.findViewById(R.id.pbLoadmoreComment);
        getCommentFromAPI(position, pbLoadmoreComment);
        //Ánh xạ
        ImageView imgSend = viewBottomSheetCommnent.findViewById(R.id.imgSend);
        EditText edtComment = viewBottomSheetCommnent.findViewById(R.id.edtComment);
        //nestedscrollview
        ntsvListComment = viewBottomSheetCommnent.findViewById(R.id.ntsvListComment);
        // set
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtComment.length() > 0 && edtComment.length() <= 255){
                    ApiServiceMinh.apiService.themBinhLuanChoPhong(phongTroOfChuTroList.get(position).getIdPhongTro(), idTaiKhoan, edtComment.getText().toString().trim()).enqueue(new Callback<PhongBinhLuan>() {
                        @Override
                        public void onResponse(Call<PhongBinhLuan> call, Response<PhongBinhLuan> response) {
                            if (response.code() == 201){
                                edtComment.setText("");
                                edtComment.onEditorAction(EditorInfo.IME_ACTION_DONE);
                                listComment.addFirst(response.body());
                                commentAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<PhongBinhLuan> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Bình luận tối đa 255 ký tự", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ntsvListComment.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("TAG", "onScrollChange: "+scrollY);
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    pageComment++;
                    pbLoadmoreComment.setVisibility(View.VISIBLE);
                    getCommentFromAPI(position, pbLoadmoreComment);

                }
            }
        });
        //recyclerview
        rcvComment = viewBottomSheetCommnent.findViewById(R.id.rcvComment);
        commentAdapter = new CommentAdapter(getActivity(), listComment, R.layout.chutro_cardview_comment_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(commentAdapter);


        bottomSheetComment.show();


    }



    private void getCommentFromAPI(int position, ProgressBar progressBar) {
        Log.d("TAG", "getCommentFromAPI: " + phongTroOfChuTroList.get(position).getIdPhongTro());
        ApiServiceMinh.apiService.layTatCaBinhLuanCuaPhong(phongTroOfChuTroList.get(position).getIdPhongTro(), pageComment, quantityComment).enqueue(new Callback<List<PhongBinhLuan>>() {
            @Override
            public void onResponse(Call<List<PhongBinhLuan>> call, Response<List<PhongBinhLuan>> response) {
                if (response.code() == 200) {
                    if (response.body() == null) {
                        pageComment--;
                        Toast.makeText(getContext(), "Đã hết bình luận", Toast.LENGTH_SHORT).show();
                    } else {
                        listComment.addAll(response.body());
                        commentAdapter.notifyDataSetChanged();
                    }

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<PhongBinhLuan>> call, Throwable t) {

            }
        });
    }


    private void anhXa(View fragmentLayout) {
        ntsvListRoom = fragmentLayout.findViewById(R.id.ntsvListRoom);
        pbLoadmoreRoom = fragmentLayout.findViewById(R.id.pbLoadmore);
        llAdd = fragmentLayout.findViewById(R.id.llAdd);
        rcvListMotelRoom = fragmentLayout.findViewById(R.id.rcvListMotelRoom);
        phongTroOfChuTroList = new LinkedList<>();
        layoutManager = new LinearLayoutManager(getContext());
        roomAdapter = new MotelRoomAdapter(getActivity(), phongTroOfChuTroList, R.layout.chutro_cardview_item_room_layout);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvListMotelRoom.setLayoutManager(layoutManager);
        rcvListMotelRoom.setAdapter(roomAdapter);
        listComment = new LinkedList<>();

    }


}
