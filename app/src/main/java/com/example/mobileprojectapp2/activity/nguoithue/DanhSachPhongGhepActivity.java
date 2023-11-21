package com.example.mobileprojectapp2.activity.nguoithue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.CommentAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhongCuaQuanAdapter;
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

public class DanhSachPhongGhepActivity extends AppCompatActivity {
    private int idTaiKhoan = 1;
    private ImageView imgBack;
    private RecyclerView rcvPhongCuaQuan;
    private PhongCuaQuanAdapter phongAdapter;
    private LinkedList<PhongTro> listPhong;
    private LinkedList<PhongBinhLuan> listComment;
    private CommentAdapter commentAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_danh_sach_phong_ghep_layout);
        rcvPhongCuaQuan = findViewById(R.id.rcvPhongCuaQuan);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listComment = new LinkedList<>();

        //
        listPhong = new LinkedList<>();
        phongAdapter = new PhongCuaQuanAdapter(this, listPhong,R.layout.nguoithue_cardview_item_room_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvPhongCuaQuan.setLayoutManager(layoutManager);
        rcvPhongCuaQuan.setAdapter(phongAdapter);

        doDuLieuPhong();

        phongAdapter.setOnClickItemRoomListener(new PhongCuaQuanAdapter.OnClickItemRoomListener() {
            @Override
            public void setOnClickComment(int position, View view) {
                showBottomSheetComment(position, view);
            }

            @Override
            public void setOnClickCItem(int position, View view) {
                //TODO: Chuyển qua màn hình chi tiết phòng
            }
        });
    }
    private void doDuLieuPhong() {
        listPhong.clear();
        ApiServiceMinh.apiService.layTatCaPhongTro(Const.PHONG_GHEP, Const.NHO_DEN_LON).enqueue(new Callback<List<PhongTro>>() {
            @Override
            public void onResponse(Call<List<PhongTro>> call, Response<List<PhongTro>> response) {
                if (response.code() == 200){
                    listPhong.addAll(response.body());
                    phongAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhongTro>> call, Throwable t) {

            }
        });
    }

    private void showBottomSheetComment(int position, View view) {
        listComment.clear();
        View viewBottomSheetCommnent = getLayoutInflater().inflate(R.layout.chutro_buttom_sheet_comment_layout, null, false);
        BottomSheetDialog bottomSheetComment = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomSheetComment.setContentView(viewBottomSheetCommnent);

        databaseReference.child("comment").child(listPhong.get(position).getId()+"").addValueEventListener(new ValueEventListener() {
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
        RecyclerView rcvComment = viewBottomSheetCommnent.findViewById(R.id.rcvComment);
        // set
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtComment.onEditorAction(EditorInfo.IME_ACTION_DONE);
                if (edtComment.length() > 0 && edtComment.length() <= 255) {
                    com.example.mobileprojectapp2.api.chutro.ApiServiceMinh.apiService.themBinhLuanChoPhong(listPhong.get(position).getId(), idTaiKhoan, edtComment.getText().toString().trim()).enqueue(new Callback<PhongBinhLuan>() {
                        @Override
                        public void onResponse(Call<PhongBinhLuan> call, Response<PhongBinhLuan> response) {
                            if (response.code() == 201) {
                                edtComment.setText("");
                                listComment.addFirst(response.body());
                                commentAdapter.notifyDataSetChanged();
                                databaseReference.child("comment").child(listPhong.get(position).getId()+"").child(response.body().getId()+"").setValue(response.body().getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    Toast.makeText(DanhSachPhongGhepActivity.this, "Bình luận tối đa 255 ký tự và phải nhập dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //recyclerview
        rcvComment = viewBottomSheetCommnent.findViewById(R.id.rcvComment);
        commentAdapter = new CommentAdapter(this, listComment, R.layout.chutro_cardview_comment_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(commentAdapter);


        bottomSheetComment.show();


    }
    private void getCommentFromAPI(int position) {
        listComment.clear();
        Log.d("TAG", "getCommentFromAPI: " + listPhong.get(position).getId());
        com.example.mobileprojectapp2.api.chutro.ApiServiceMinh.apiService.layTatCaBinhLuanCuaPhong(listPhong.get(position).getId()).enqueue(new Callback<List<PhongBinhLuan>>() {
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