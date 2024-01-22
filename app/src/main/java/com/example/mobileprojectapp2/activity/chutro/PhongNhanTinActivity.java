package com.example.mobileprojectapp2.activity.chutro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.TinNhanAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.component.MFCM;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.TenSender;
import com.example.mobileprojectapp2.datamodel.TinNhan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhongNhanTinActivity extends AppCompatActivity {

    private int daXem =1;
    RecyclerView recyclerView;
    ArrayList<TinNhan> arrayList;
    TinNhanAdapter tinNhanAdapter;
    private int senderId;
    private int idDoiPhuong;
    private String tenSender;
    private int idPhong;
    private int trangThai;
    private String ten;
    private String hinh;
    AppCompatImageView ic_back;
    TextView textName;
    EditText inputMess;
    AppCompatImageView sendMess;
    CircleImageView avt_doiPhuong;
    AppCompatImageView info;
    SharedPreferences sharedPreferences;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_massage);
        anhXa();
        khoiTaoFireBase();
        arrayList = new ArrayList<>();
        layShared();
        tinNhanAdapter = new TinNhanAdapter(this,arrayList,R.layout.cardview_send_message,R.layout.cardview_recieve_message,senderId);
        setDuLieu();
        setSuKien();
        setListTinNhan();
        setSuKienDaXem();
        }
        private void layTrangThaiCuaDoiPhuong(){

        }
        private void setSuKienDaXem(){
            if(trangThai!=-1&&trangThai==0){
                Call<Integer> call = ApiServiceNghiem.apiService.capNhatTrangThaiDaXem(idPhong,senderId);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        }
        private void setSuKienGuiTinNhan(){
            sendMess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guiTinNhan();
                }
            });
        }
        private void setSuKien(){
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        }
    private String formatDate(Timestamp timestamp){
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm");
        return newFormat.format(date);
    }
        private void guiTinNhan(){
            RequestBody noiDung = RequestBody.create(MediaType.parse("multipart/form-data"),inputMess.getText().toString());
            Call<TinNhan> call = ApiServiceNghiem.apiService.guiTinNhan(idPhong,senderId,noiDung);
            call.enqueue(new Callback<TinNhan>() {
                @Override
                public void onResponse(Call<TinNhan> call, Response<TinNhan> response) {
                    if(response.body()!=null) {
                        Date currentTime = Calendar.getInstance().getTime();
                        Timestamp tsTemp = new Timestamp(currentTime.getTime());
//                    Date date = new Date(tsTemp.getTime());
//                    SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
                        if (arrayList.size() != 0) {
                            TinNhan tinNhan = new TinNhan(arrayList.get(arrayList.size() - 1).getId() + 1, idPhong, senderId, inputMess.getText().toString(), tsTemp);
                            arrayList.add(tinNhan);
                            tinNhanAdapter.notifyDataSetChanged();
//                                recyclerView.smoothScrollToPosition(arrayList.size()-1);
                        } else {
                            TinNhan tinNhan = new TinNhan(1, idPhong, senderId, inputMess.getText().toString(), tsTemp);
                            arrayList.add(tinNhan);
                            tinNhanAdapter.notifyDataSetChanged();
//                                recyclerView.smoothScrollToPosition(arrayList.size()-1);
                        }
                        recyclerView.smoothScrollToPosition(arrayList.size() - 1);
                        capNhatTinNhanNew(idPhong, inputMess.getText().toString(), formatDate(response.body().getCreated_at()));
                    }else{
                        thongBao("Xin Chờ, Dữ Liệu Quá Nhiều Để Load App");
                    }
                }

                @Override
                public void onFailure(Call<TinNhan> call, Throwable t) {

                }
            });
        }
    private void capNhatTinNhanNew(int idPhong, String noiDung, String thoiGian){

        RequestBody noiDungAPi = RequestBody.create(MediaType.parse("multipart/form-data"),inputMess.getText().toString());
        RequestBody thoiGianApi = RequestBody.create(MediaType.parse("multipart/form-data"),thoiGian);
        Call<Integer> call = ApiServiceNghiem.apiService.capNhatTinNhanMoiNhat(senderId,idPhong,noiDungAPi,thoiGianApi);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body()!=null) {
                                Date date = new Date();
                                MFCM.sendNotificationForAccountID(idDoiPhuong, date.getSeconds(), tenSender, noiDung);
                    thongBaoReset(inputMess.getText().toString());
                    inputMess.setText("");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                thongBao("Lỗi");
            }
        });
    }

    private void thongBaoReset(String mes){
        databaseReference.child("phongTinNhan").child(idPhong+"").setValue(System.currentTimeMillis()+"").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                thongBao("Lỗi Hệ Thống");

            }
        });
        databaseReference.child("thongBaoReset").child(idDoiPhuong+"").setValue(System.currentTimeMillis()+"").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                thongBao("Lỗi Hệ Thống");
            }
        });
    }

        private void setListTinNhan(){
            databaseReference.child("phongTinNhan").child(idPhong+"").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listTinNhan();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    thongBao("Lỗi Hệ Thống Tin Nhắn");
                }
            });
        }
        private void scrollLastPotition(){
        recyclerView.setAdapter(tinNhanAdapter);
        recyclerView.smoothScrollToPosition(arrayList.size()-1);
        }
        private void listTinNhan(){
        Call<ArrayList<TinNhan>> call = ApiServiceNghiem.apiService.layDanhSachTinNhan(idPhong);
        call.enqueue(new Callback<ArrayList<TinNhan>>() {
            @Override
            public void onResponse(Call<ArrayList<TinNhan>> call, Response<ArrayList<TinNhan>> response) {

                if(response.body()!=null){
                    arrayList.clear();
                    if(response.body().size()!=0){
                        arrayList.addAll(response.body());
                        tinNhanAdapter.notifyDataSetChanged();
                        setSuKienGuiTinNhan();
                        scrollLastPotition();
                    }else{
                        setSuKienGuiTinNhan();
                    }
                }else{
                    thongBao("Error Nhắn Tin Quá nhanh");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TinNhan>> call, Throwable t) {

            }
        });
        }

    private void layShared(){
        sharedPreferences = getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        senderId = sharedPreferences.getInt("idTaiKhoan",-1);
        tenSender = sharedPreferences.getString("tenCuaSender","0001");
        ten = getIntent().getStringExtra("ten");
        hinh = getIntent().getStringExtra("hinh");
        idPhong = getIntent().getIntExtra("idPhong",-1);
        idDoiPhuong = getIntent().getIntExtra("idDoiPhuong",-1);
        trangThai = getIntent().getIntExtra("trangThaiSender",-1);
    }
    private void setDuLieu(){
        textName.setText(getIntent().getStringExtra("ten"));
        tinNhanAdapter.setAnhDoiPhuong(getIntent().getStringExtra("hinh"));
        Glide.with(getApplicationContext()).load(Const.DOMAIN+getIntent().getStringExtra("hinh")).into(avt_doiPhuong);
    }
    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }
    private void khoiTaoFireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void anhXa(){
        recyclerView = findViewById(R.id.chatRecyclerView);
        ic_back = findViewById(R.id.ic_back);
        textName = findViewById(R.id.textName);
        inputMess= findViewById(R.id.inputMessage);
        sendMess = findViewById(R.id.sendMess);
        avt_doiPhuong = findViewById(R.id.avt_doiphuong);
        info = findViewById(R.id.infor_doiPhuong);
    }
}