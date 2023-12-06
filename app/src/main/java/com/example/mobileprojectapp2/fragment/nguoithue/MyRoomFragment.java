package com.example.mobileprojectapp2.fragment.nguoithue;

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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.nguoithue.RenterActivity;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
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

public class MyRoomFragment extends AbstractFragment{
    private int idTaiKhoan;
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private LinkedList<PhongBinhLuan> listComment;
    private ViewGroup container;
    private List<PhongTroChuTro> phongTroOfChuTroList;
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
        fragmentLayout = inflater.inflate(R.layout.nguoithue_fragment_my_room_layout, container, false);

        ImageView commen = fragmentLayout.findViewById(R.id.edtComment);


        return fragmentLayout;
    }

    private void showBottomSheetComment(int position, View view) {

        pageComment = 1;

        View viewBottomSheetCommnent = getLayoutInflater().inflate(R.layout.chutro_buttom_sheet_comment_layout, container, false);
        BottomSheetDialog bottomSheetComment = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        bottomSheetComment.setContentView(viewBottomSheetCommnent);

        databaseReference.child("comment").child(phongTroOfChuTroList.get(position).getIdPhongTro() + "").addValueEventListener(new ValueEventListener() {
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
                                databaseReference.child("comment").child(phongTroOfChuTroList.get(position).getIdPhongTro() + "").child(response.body().getId() + "").setValue(response.body().getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
}
