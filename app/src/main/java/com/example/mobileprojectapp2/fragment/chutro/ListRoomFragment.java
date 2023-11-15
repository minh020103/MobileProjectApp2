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
import com.example.mobileprojectapp2.activity.chutro.EditRoomActivity;
import com.example.mobileprojectapp2.activity.chutro.MotelRoomOwnerActivity;
import com.example.mobileprojectapp2.activity.chutro.RenterListActivity;
import com.example.mobileprojectapp2.activity.chutro.SearchActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.Comment;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
import com.example.mobileprojectapp2.datamodel.PhongDanhGia;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.player.RatingPlayer;
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
                if(scrollY == 0){
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
                Intent intent = new Intent(getActivity(), AddRoomActivity.class);
                startActivity(intent);
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
                                        if (response.code() == 200){
                                            if (response.body() == 1){
                                                phongTroOfChuTroList.remove(phongTroOfChuTroList.get(position));
                                                roomAdapter.notifyDataSetChanged();
                                                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {
                                        Log.d("TAG", "onFailure: "+call.request());
                                        Log.d("TAG", "onFailure: "+t);
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
                ApiServiceMinh.apiService.layDanhGiaCuaNguoiDunngChoPhong(idTaiKhoan, phongTroOfChuTroList.get(position).getIdPhongTro()).enqueue(new Callback<PhongDanhGia>() {
                    @Override
                    public void onResponse(Call<PhongDanhGia> call, Response<PhongDanhGia> response) {
                        if (response.code() == 200) {
                            ratingPlayer.setStarForRating(response.body().getDanhGia());
                        }
                    }

                    @Override
                    public void onFailure(Call<PhongDanhGia> call, Throwable t) {

                    }
                });

                // chọn sao
                ratingPlayer.rating();
                //  hàm sử lý số sao đã chọn được ủy quyền
                ratingPlayer.setiRatingPlayer(new RatingPlayer.IRatingPlayer() {
                    @Override
                    public void layDanhGia(int rating) {
                        // được ủy quyền ra ngoài để lấy lượng đánh giá
                        Log.d("TAG", "setOnClickRating: " + rating);
                        ApiServiceMinh.apiService.danhGiaChoPhong(idTaiKhoan, phongTroOfChuTroList.get(position).getIdPhongTro(), rating).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.code() == 200) {
                                    Toast.makeText(getContext(), "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                                    dialog.hide();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    }
                });

            }
        });
    }

        private void showBottomSheetComment(int position, View view) {

        pageComment = 1;

        View viewBottomSheetCommnent = getLayoutInflater().inflate(R.layout.chutro_buttom_sheet_comment_layout, container, false);
        BottomSheetDialog bottomSheetComment = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        bottomSheetComment.setContentView(viewBottomSheetCommnent);

        databaseReference.child("comment").child(phongTroOfChuTroList.get(position).getIdPhongTro()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getCommentFromAPI(position);
                Log.d("TAG", "onDataChange: GET OK");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Ánh xạ
        ImageView imgSend = viewBottomSheetCommnent.findViewById(R.id.imgSend);
        EditText edtComment = viewBottomSheetCommnent.findViewById(R.id.edtComment);
        // set
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtComment.onEditorAction(EditorInfo.IME_ACTION_DONE);
                if (edtComment.length() > 0 && edtComment.length() <= 255) {
                    ApiServiceMinh.apiService.themBinhLuanChoPhong(phongTroOfChuTroList.get(position).getIdPhongTro(), idTaiKhoan, edtComment.getText().toString().trim()).enqueue(new Callback<PhongBinhLuan>() {
                        @Override
                        public void onResponse(Call<PhongBinhLuan> call, Response<PhongBinhLuan> response) {
                            if (response.code() == 201) {
                                edtComment.setText("");
                                listComment.addFirst(response.body());
                                commentAdapter.notifyDataSetChanged();
                                databaseReference.child("comment").child(phongTroOfChuTroList.get(position).getIdPhongTro()+"").child(response.body().getId()+"").setValue(response.body().getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("TAG", "onDataChange: NEW OK");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<PhongBinhLuan> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Bình luận tối đa 255 ký tự và phải nhập dữ liệu", Toast.LENGTH_SHORT).show();
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


    private void getCommentFromAPI(int position) {
        listComment.clear();
        Log.d("TAG", "getCommentFromAPI: " + phongTroOfChuTroList.get(position).getIdPhongTro());
        ApiServiceMinh.apiService.layTatCaBinhLuanCuaPhong(phongTroOfChuTroList.get(position).getIdPhongTro()).enqueue(new Callback<List<PhongBinhLuan>>() {
            @Override
            public void onResponse(Call<List<PhongBinhLuan>> call, Response<List<PhongBinhLuan>> response) {
                if (response.code() == 200) {
                        listComment.clear();
                        listComment.addAll(response.body());
                        commentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhongBinhLuan>> call, Throwable t) {

            }
        });
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


}
